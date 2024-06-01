package controller;

import controller.csv.ProdutoCsvController;
import controller.hashTables.ProdutoHashTable;
import datastrucures.genericList.List;
import model.ICsv;
import model.Produto;
import model.Tipo;
import view.TelaEclipse;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class ProdutoRegistry implements ActionListener {
    private static ProdutoRegistry INSTANCE = null;

    private final ProdutoCsvController DB_PRODUTO;
    private ProdutoHashTable TABLE_PRODUTO;
    private boolean viewSetted;
    private TelaEclipse tela;
    private JTextField tfCodigo;
    private JTextField tfNome;
    private JTextField tfValor;
    private JTextField tfQuantidade;
    private JComboBox<String> cbTipo;


    private ProdutoRegistry() throws Exception {
        TipoRegistry tipoRegistry = TipoRegistry.getInstance();
        DB_PRODUTO = new ProdutoCsvController(tipoRegistry.getTipoList());
        TABLE_PRODUTO = new ProdutoHashTable();
        updateData(); // lê o arquivo popula a table
    }


    void updateData() throws Exception {
        List<ICsv> produtoList = DB_PRODUTO.get();
        int size = produtoList.size();

        TABLE_PRODUTO = new ProdutoHashTable();

        for (int i = 0; i < size; i++) {
            Produto p = (Produto) produtoList.get(i);
            if(p.getTipo().getCodigo() == 0) {
                /*
                 Atualiza os códigos de tipos quando um produto
                 sem tipo é encontrado ele recebe o tipo n°0, porém
                 o seu valor no csv não é alterado. Isso garante que
                 os valores do csv também sejam 0
                */
                DB_PRODUTO.delete(p);
                DB_PRODUTO.save(p);
            }
            TABLE_PRODUTO.put(p);
        }
    }


    public static ProdutoRegistry getInstance() throws Exception{
        if(INSTANCE == null) {
            INSTANCE = new ProdutoRegistry();
        }
        return INSTANCE;
    }


    public String getHeader() {
        return DB_PRODUTO.getHeader();
    }
    
    public int getProximoCodigoDisponivel() {
    	int cont = 0;
    	while(true) {
    		try {
    			get(cont);
    			cont++;
    		} catch(Exception e) {
    			if(e.getMessage().equalsIgnoreCase("Produto não encontrado")) {
    				return cont;
    			}
    		}
    	}
    }


    public void add(Produto p) throws Exception {
        DB_PRODUTO.save(p);
        TABLE_PRODUTO.put(p);
    }


    public void remove(Produto p) throws Exception {
        DB_PRODUTO.delete(p);
        TABLE_PRODUTO.remove(p);
    }
    
    public void remove(int codigoProduto) throws Exception {
    	Produto produto = TABLE_PRODUTO.get(codigoProduto);
    	remove(produto);
    }


    public void edit(Produto old, Produto _new) throws Exception {
        remove(old);
        add(_new);
    }


    public List<ICsv> get() throws Exception {
        return TABLE_PRODUTO.getAllProducts();
    }

    
    public ICsv get(int codigoProduto, int codigoTipo) throws Exception {
    	List<ICsv> produtos = getByTipe(codigoTipo);
    	int size = produtos.size();
    	for(int i = 0; i < size; i++) {
    		Produto p = (Produto) produtos.get(i);
    		if(p.getCodigo() == codigoProduto) {
    			return p;
    		}
    	}
    	throw new Exception("Produto não encontrado");
    }

    public ICsv get(int codigoProduto) throws Exception {
        return TABLE_PRODUTO.get(codigoProduto);
    }


    public List<ICsv> getByTipe(int codigoTipo) throws Exception {
        return TABLE_PRODUTO.getByType(codigoTipo);
    }

    public void setView(TelaEclipse tela, JTextField tfCodigo, JTextField tfNome, JTextField tfValor, JTextField tfQuantidade, JComboBox<String> cbTipo) throws Exception {
        if (!viewSetted) {
            this.tela = tela;
            this.tfCodigo = tfCodigo;
            this.tfNome = tfNome;
            this.tfValor = tfValor;
            this.tfQuantidade = tfQuantidade;
            this.cbTipo = cbTipo;
            this.viewSetted = true;
        }
        else {
            throw new Exception("View já definida");
        }
    }

    public Produto viewToProduto() throws Exception {
        int codigo = Integer.parseInt(tfCodigo.getText());
        String nome = tfNome.getText();
        double valor = Double.parseDouble(tfValor.getText());
        int quantidade = Integer.parseInt(tfQuantidade.getText());
        String tipoSelecionado = (String) cbTipo.getSelectedItem();
        Tipo tipo = (Tipo) TipoRegistry.getInstance().get(tipoSelecionado.split("-")[0]);
        return new Produto(codigo, nome, tipo, valor, quantidade);
    }

    public void clearTextFields() {
        tfCodigo.setText(String.valueOf(getProximoCodigoDisponivel()));
        tfNome.setText("");
        tfValor.setText("");
        tfQuantidade.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        String actionPerformed = evt.getActionCommand();
        if (actionPerformed.equalsIgnoreCase("SALVAR")) {
            try {
                cadastrar();
                tela.carregarTableProduto();
            } catch (Exception e) {
                /*TODO*/
                e.printStackTrace();
            }
        }
        if (actionPerformed.equalsIgnoreCase("EDITAR")) {
            try {
                editar();
                tela.carregarTableProduto();
            } catch (Exception e) {
                /*TODO*/
                e.printStackTrace();
            }
        }
    }

    private void editar() throws Exception {
        Produto _new = viewToProduto();
        Produto old =  (Produto) get(_new.getCodigo());
        clearTextFields();
        edit(old, _new);
    }

    private void cadastrar() throws Exception {
        Produto p = viewToProduto();
        try {
            get(p.getCodigo());
        } catch (Exception e) {
            if(e.getMessage().equals("Produto não encontrado")) {
                add(p);
            }
        }
        clearTextFields();
    }
}
