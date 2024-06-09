package controller;

import controller.csv.TipoCsvController;
import datastructures.genericList.List;
import model.ICsv;
import model.Tipo;
import view.TelaEclipse;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public final class TipoController implements ActionListener {
    private static TipoController INSTANCE = null;

    private final TipoCsvController DB_TIPO;
    private final List<ICsv> TIPO_LIST;
    private final Tipo TIPO_SEM_CATEGORIA; // Útil para tornar a leitura de produtos mais rápida

    private boolean viewSetted = false;
    private TelaEclipse SCREEN;


    private TipoController() throws Exception {
        DB_TIPO = new TipoCsvController();
        TIPO_LIST = DB_TIPO.get();
        // garante que existe uma categoria para tipos não categorizados
        Tipo temp = new Tipo(0, "Produtos não categorizados", "Bens sem alguma categorização específicada");
        try {
            temp = (Tipo) this.get(0);
        } catch (Exception e) {
            this.add(temp);
        }
        TIPO_SEM_CATEGORIA = temp;
    }


    public static TipoController getInstance() throws Exception {
        if (INSTANCE == null) {
            INSTANCE = new TipoController();
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
        int targetCode = Integer.parseInt(codigoTipo);
        for (int i = 0; i < size; i++) {
            var tipo = (Tipo) TIPO_LIST.get(i);
            if (tipo.getCodigo() == targetCode) {
                return tipo;
            }
        }
        throw new Exception("Tipo não encontrado");
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
        int cont = 0;
        while (true) {
            try {
                get(cont);
                cont++;
            } catch (Exception e) {
                if (e.getMessage().equals("Tipo não encontrado")) {
                    return cont;
                }
            }
        }
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
        ProdutoController.getInstance().updateData();
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
        ProdutoController.getInstance().updateData();
    }


    public void setView(TelaEclipse SCREEN) throws Exception {
        if (!viewSetted) {
            this.SCREEN = SCREEN;
            this.viewSetted = true;
            return;
        }
        throw new Exception("View já definida");
    }


    @Override
    public void actionPerformed(ActionEvent evt) {
        String actionPerformed = evt.getActionCommand();
        try {
            if (actionPerformed.equalsIgnoreCase("SALVAR")) {
                cadastrar();
            }
            if (actionPerformed.equalsIgnoreCase("INIT_EDITAR")) {
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


    private void excluir() throws Exception {
        JTable t = SCREEN.getTableTipos();
        int index = t.getSelectedRow();
        if (index == -1) {
            throw new Exception("Nenhum tipo selecionado");
        }
        TableModel m = t.getModel();
        int code = Integer.parseInt((String) m.getValueAt(index, 1));
        remove(code);
        SCREEN.loadTypeTable();
        SCREEN.loadProductTable();
    }


    private void pesquisar() throws Exception {
        String searchTerm = SCREEN.getTfBuscaTipo().getText();
        SCREEN.getTfBuscaTipo().setText("");
        if (searchTerm == null || searchTerm.isBlank()) {
            SCREEN.loadTypeTable();
            return;
        }

        int code;
        try {
            code = Integer.parseInt(searchTerm);
        } catch (NumberFormatException e) {
            //TODO mostrar erro
            return;
        }

        List<ICsv> result = new List<>();
        List<ICsv> list = DB_TIPO.get();

        if (list != null) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                Tipo item = (Tipo) list.get(i);
                if (item.getCodigo() == code) {
                    result.addLast(item);
                    break;
                }
            }
        }

        JTable t = SCREEN.getTableTipos();
        SCREEN.carregarDados(t, DB_TIPO.getHeader(), result);
        t.getColumnModel().getColumn(0).setMaxWidth(26);
        t.getColumnModel().getColumn(1).setMaxWidth(46);
    }


    private void initEditFields() throws Exception {
        JTable table = SCREEN.getTableTipos();
        int index = table.getSelectedRow();
        if (index == -1) {
            throw new Exception("Nenhum produto selecionado");
        }
        TableModel m = table.getModel();
        int code = Integer.parseInt((String) m.getValueAt(index, 1));
        Tipo t = (Tipo) get(code);
        SCREEN.setTypeForm(t);
        SCREEN.toggleTypeView(false);
    }


    private void editar() throws Exception {
        Tipo _new = SCREEN.getTypeForm();
        Tipo old = (Tipo) get(_new.getCodigo());
        edit(old, _new);
        SCREEN.clearTypeFields();
        SCREEN.loadTypeTable();
        SCREEN.loadProductTable();
    }


    private void cadastrar() throws Exception {
        Tipo t = SCREEN.getTypeForm();
        try {
            get(t.getCodigo());
        } catch (Exception e) {
            if (e.getMessage().equals("Tipo não encontrado")) {
                add(t);
            }
            else {
                throw new Exception(e);
            }
        }
        SCREEN.clearTypeFields();
        SCREEN.loadTypeTable();
    }
}