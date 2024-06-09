package controller;

import controller.csv.ProdutoCsvController;
import controller.hashTables.ProdutoHashTable;
import datastructures.genericList.List;
import model.ICsv;
import model.Produto;
import view.TelaEclipse;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class ProdutoController implements ActionListener {
	private static ProdutoController INSTANCE = null;

	private final ProdutoCsvController DB_PRODUTO;
	private ProdutoHashTable TABLE_PRODUTO;
	private boolean viewSetted;
	private TelaEclipse SCREEN;

	private ProdutoController() throws Exception {
		TipoController tipoController = TipoController.getInstance();
		DB_PRODUTO = new ProdutoCsvController(tipoController.getTipoList());
		TABLE_PRODUTO = new ProdutoHashTable();
		updateData(); // lê o arquivo popula a table
	}

	void updateData() throws Exception {
		List<ICsv> produtoList = DB_PRODUTO.get();
		int size = produtoList.size();

		TABLE_PRODUTO = new ProdutoHashTable();

		for (int i = 0; i < size; i++) {
			Produto p = (Produto) produtoList.get(i);
			if (p.getTipo().getCodigo() == 0) {
				/*
				 * Atualiza os códigos de tipos quando um produto sem tipo é encontrado ele
				 * recebe o tipo n°0, porém o seu valor no csv não é alterado. Isso garante que
				 * os valores do csv também sejam 0
				 */
				DB_PRODUTO.delete(p);
				DB_PRODUTO.save(p);
			}
			TABLE_PRODUTO.put(p);
		}
	}

	public static ProdutoController getInstance() throws Exception {
		if (INSTANCE == null) {
			INSTANCE = new ProdutoController();
		}
		return INSTANCE;
	}

	public String getHeader() {
		return DB_PRODUTO.getHeader();
	}

	public int getProximoCodigoDisponivel() {
		int cont = 0;
		while (true) {
			try {
				get(cont);
				cont++;
			} catch (Exception e) {
				if (e.getMessage().equalsIgnoreCase("Produto não encontrado")) {
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
		for (int i = 0; i < size; i++) {
			Produto p = (Produto) produtos.get(i);
			if (p.getCodigo() == codigoProduto) {
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

	public void setView(TelaEclipse SCREEN) throws Exception {
		if (!viewSetted) {
			this.SCREEN = SCREEN;
			this.viewSetted = true;
		} else {
			throw new Exception("View já definida");
		}
	}


	@Override
	public void actionPerformed(ActionEvent evt) {
		String actionPerformed = evt.getActionCommand();
		try {
			if (actionPerformed.equalsIgnoreCase("SALVAR")) {
				cadastrar();
			}
			if(actionPerformed.equalsIgnoreCase("INIT_EDITAR")) {
				initEditFields();
			}
			if (actionPerformed.equalsIgnoreCase("EDITAR")) {
				editar();
			}
			if (actionPerformed.equalsIgnoreCase("PESQUISAR")) {
				pesquisar();
			}
			if (actionPerformed.equalsIgnoreCase("EXCLUIR")) {
				excluir();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void initEditFields() throws Exception {
		JTable t = SCREEN.getTableProduto();
		int index= t.getSelectedRow();
		if(index == -1) {
			throw new Exception("Nenhum produto selecionado");
		}
		TableModel m = t.getModel();
		int code = Integer.parseInt((String) m.getValueAt(index, 1));
		Produto p = (Produto) get(code);
		SCREEN.setProductForm(p);
		SCREEN.toggleProductView(false);
	}

	private void excluir() throws Exception {
		JTable t = SCREEN.getTableProduto();
		int index= t.getSelectedRow();
		if(index == -1) {
			throw new Exception("Nenhum produto selecionado");
		}
		TableModel m = t.getModel();
		int code = Integer.parseInt((String) m.getValueAt(index, 1));
		remove(code);
		SCREEN.loadProductTable();
	}

	private void pesquisar() throws Exception{
		String searchTerm = SCREEN.getTfBuscaProduto().getText();
		SCREEN.getTfBuscaProduto().setText("");
		if(searchTerm == null || searchTerm.isBlank()) {
			SCREEN.loadProductTable();
			return;
		}

		int code;
		try {
			code = Integer.parseInt(searchTerm);
		} catch (NumberFormatException e){
			//TODO mostrar erro
			return;
		}

		List<ICsv> result = new List<>();
		List<ICsv> list = SCREEN.loadProductTable();
		if (list != null) {
			int size = list.size();
			for (int i = 0; i < size; i++) {
				Produto item = (Produto) list.get(i);
				if(item.getCodigo() == code) {
					result.addLast(item);
					break;
				}
			}

		}
		JTable t = SCREEN.getTableProduto();
		SCREEN.carregarDados(t, DB_PRODUTO.getHeader(), result);
		t.getColumnModel().getColumn(0).setMaxWidth(26);
		t.getColumnModel().getColumn(1).setMaxWidth(46);
		t.getColumnModel().getColumn(2).setMaxWidth(46);
		t.getColumnModel().getColumn(3).setMinWidth(270);

	}

	private void editar() throws Exception {
		Produto _new = SCREEN.getProductForm();
		Produto old = (Produto) get(_new.getCodigo());
		edit(old, _new);
		SCREEN.clearProductFields();
		SCREEN.loadProductTable();
	}

	private void cadastrar() throws Exception {
		Produto p = SCREEN.getProductForm();
		try {
			get(p.getCodigo());
		} catch (Exception e) {
			if (e.getMessage().equals("Produto não encontrado")) {
				add(p);
			}
			else {
				throw new Exception(e);
			}
		}
		SCREEN.clearProductFields();
		SCREEN.loadProductTable();
	}
}