package controller;

import controller.csv.TipoCsvController;
import datastrucures.genericList.List;
import model.Tipo;


public final class TipoRegistry {
    private static TipoRegistry INSTANCE = null;

    private final TipoCsvController DB_TIPO;
    private final List<Tipo> TIPO_LIST;
    private final Tipo TIPO_SEM_CATEGORIA; // Útil para tornar a leitura de produtos mais rápida


    private TipoRegistry() throws Exception {
        DB_TIPO = new TipoCsvController();
        TIPO_LIST = DB_TIPO.get();
        // garante que existe uma categoria para tipos não categorizados
        Tipo temp = this.get(0);
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


    List<Tipo> getTipoList() {
        return TIPO_LIST;
    }


    public Tipo getUncategorizedType() throws Exception {
        return TIPO_SEM_CATEGORIA;
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
            if (TIPO_LIST.get(i).getCodigo() == codigoTipo) {
                TIPO_LIST.remove(i);
                break;
            }
        }
    }


    public void add(Tipo tipo) throws Exception {
        TIPO_LIST.addLast(tipo);
        DB_TIPO.save(tipo);
    }


    public void remove(int codigoTipo) throws Exception {
        removeUpdateless(codigoTipo);
        ProdutoRegistry.getInstance().updateData();
    }


    public void remove(Tipo tipo) throws Exception {
        remove(tipo.getCodigo());
    }


    /**
     *Deve ser utilizado para modificar os dados de um tipo
        por exclusão e adição.

        - Garante que os produtos do tipo excluído não serão
          movidos para o tipo sem categoria caso o código do
          antigo e do novo sejam iguais.

        - Também realiza menos updates, o que é mais eficiente.
     */
    public void edit(Tipo old, Tipo _new) throws  Exception{
        removeUpdateless(old.getCodigo());
        add(_new);
        ProdutoRegistry.getInstance().updateData();
    }


    public Tipo get(int codigoTipo) throws Exception {
        int size = TIPO_LIST.size();
        Tipo target = null;

        for (int i = 0; i < size; i++) {
            var tipo = TIPO_LIST.get(i);
            if (tipo.getCodigo() == codigoTipo) {
                target = tipo;
                break;
            }
        }
        return target;
    }


    public List<Tipo> get() throws Exception {
        int size = TIPO_LIST.size();
        List<Tipo> result = new List<>();

        for (int i = 0; i < size; i++) {
            //Todo: apenas pular aqueles que dão erros?
            result.addLast(TIPO_LIST.get(i));
        }

        return result;
    }


    public String getHeader() {
        return DB_TIPO.getHeader();
    }


    public Tipo objectBuilder(String csvLine) throws Exception {
        return DB_TIPO.objectBuilder(csvLine);
    }
}