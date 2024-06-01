package controller;

import controller.csv.TipoCsvController;
import datastrucures.genericList.List;
import model.ICsv;
import model.Tipo;
import view.TelaEclipse;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public final class TipoRegistry implements ActionListener {
    private static TipoRegistry INSTANCE = null;

    private final TipoCsvController DB_TIPO;
    private final List<ICsv> TIPO_LIST;
    private final Tipo TIPO_SEM_CATEGORIA; // Útil para tornar a leitura de produtos mais rápida

    private boolean viewSetted = false;
    private TelaEclipse tela;
    private JTextField tfCodigo;
    private JTextField tfNome;
    private JTextArea taDescricao;


    private TipoRegistry() throws Exception {
        DB_TIPO = new TipoCsvController();
        TIPO_LIST = DB_TIPO.get();
        // garante que existe uma categoria para tipos não categorizados
        Tipo temp = (Tipo) this.get(0);
        if (temp != null) {
            TIPO_SEM_CATEGORIA = temp;
        }
        else {
            temp = new Tipo(0, "Produtos não categorizados", "Bens sem alguma categorização específicada");
            TIPO_SEM_CATEGORIA = temp;
            this.add(TIPO_SEM_CATEGORIA);
        }
    }


    public static TipoRegistry getInstance() throws Exception {
        if (INSTANCE == null) {
            INSTANCE = new TipoRegistry();
        }
        return INSTANCE;
    }


    List<ICsv> getTipoList() {
        return TIPO_LIST;
    }


    public Tipo getUncategorizedType() {
        return TIPO_SEM_CATEGORIA;
    }


    public String getHeader() {
        return DB_TIPO.getHeader();
    }


    public ICsv get(String codigoTipo) throws Exception {
        int size = TIPO_LIST.size();
        Tipo target = null;
        int targetCode = Integer.parseInt(codigoTipo);

        for (int i = 0; i < size; i++) {
            var tipo = (Tipo) TIPO_LIST.get(i);
            if (tipo.getCodigo() == targetCode) {
                target = tipo;
                break;
            }
        }
        return target;
    }

    public ICsv get(int codigoTipo) throws Exception {
        return get(String.valueOf(codigoTipo));
    }


    public List<ICsv> get() throws Exception {
        int size = TIPO_LIST.size();
        List<ICsv> result = new List<>();

        for (int i = 0; i < size; i++) {
            //Todo: apenas pular aqueles que dão erros?
            result.addLast(TIPO_LIST.get(i));
        }

        return result;
    }


    /**
     * @return O menor valor disponível para ser código de um novo tipo
     */
    public int getProximoCodigoDisponivel() {
        int size = TIPO_LIST.size();
        int result = size;
        for (int i = 0; i < size; i++) {
            try {
                Tipo element = (Tipo) get(i);
                if (element.getCodigo() != i) {
                    result = i;
                    break;
                }
            } catch (Exception e) {/*Na próxima chamada do método getNextCódigo resolve*/}
        }
        return result;
    }


    public void add(Tipo tipo) throws Exception {
        TIPO_LIST.addLast(tipo);
        DB_TIPO.save(tipo);
    }


    /**
     * Remove um tipo do registro de tipos sem atualizar o registro de produtos
     */
    private void removeUpdateless(int codigoTipo) throws Exception {
        if (codigoTipo == 0) {
            throw new Exception("Não é permitido alterar Produtos não categorizados");
        }
        DB_TIPO.delete(String.valueOf(codigoTipo));
        int size = TIPO_LIST.size();

        for (int i = 0; i < size; i++) {
            Tipo element = (Tipo) TIPO_LIST.get(i);
            if (element.getCodigo() == codigoTipo) {
                TIPO_LIST.remove(i);
                break;
            }
        }
    }


    public void remove(int codigoTipo) throws Exception {
        removeUpdateless(codigoTipo);
        ProdutoRegistry.getInstance().updateData();
    }


    /**
     * Deve ser utilizado para modificar os dados de um tipo
     * por exclusão e adição.
     * <p>
     * - Garante que os produtos do tipo excluído não serão
     * movidos para o tipo sem categoria caso o código do
     * antigo e do novo sejam iguais.
     * <p>
     * - Também realiza menos updates, o que é mais eficiente.
     */
    public void edit(Tipo old, Tipo _new) throws Exception {
        removeUpdateless(old.getCodigo());
        add(_new);
        ProdutoRegistry.getInstance().updateData();
    }


    public void setView(TelaEclipse tela, JTextField tfCodigo, JTextField tfNome, JTextArea taDescricao) throws Exception {
        if (!viewSetted) {
            this.tela = tela;
            this.tfCodigo = tfCodigo;
            this.tfNome = tfNome;
            this.taDescricao = taDescricao;
            this.viewSetted = true;
        }
        else {
            throw new Exception("View já definida");
        }
    }


    private Tipo viewToTipo() {
        int codigo = Integer.parseInt((tfCodigo.getText()));
        String nome = tfNome.getText();
        String descricao = taDescricao.getText();

        return new Tipo(codigo, nome, descricao);
    }


    private void clearTextFields() {
        taDescricao.setText("");
        tfNome.setText("");
        tfCodigo.setText(String.valueOf(this.getProximoCodigoDisponivel()));
    }


    @Override
    public void actionPerformed(ActionEvent evt) {
        String actionPerformed = evt.getActionCommand();
        if (actionPerformed.equalsIgnoreCase("SALVAR")) {
            try {
                cadastrar();
                tela.carregarTableTipo();
            } catch (Exception e) {
                /*TODO*/
                e.printStackTrace();
            }
        }
        if (actionPerformed.equalsIgnoreCase("EDITAR")) {
            try {
                editar();
                tela.carregarTableTipo();
                tela.carregarTableProduto();
            } catch (Exception e) {
                /*TODO*/
                e.printStackTrace();
            }
        }
    }


    private void editar() throws Exception{
        Tipo _new = viewToTipo();
        Tipo old = (Tipo) get(_new.getCodigo());
        clearTextFields();
        edit(old, _new);
    }


    private void cadastrar() throws Exception{
        Tipo tipo = viewToTipo();
        if(get(tipo.getCodigo()) != null) {
            throw new Exception("Já existe um tipo com esse código");
        }
        clearTextFields();
        add(tipo);
    }
}