package controller;

import controller.csv.ProdutoCsvController;
import controller.hashTables.ProdutoHashTable;
import datastrucures.genericList.List;
import model.ICsv;
import model.Produto;

public final class ProdutoRegistry {
    private static ProdutoRegistry INSTANCE = null;

    private final ProdutoCsvController DB_PRODUTO;
    private ProdutoHashTable TABLE_PRODUTO;


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


    public Produto objectBuilder(String csvLine) throws Exception {
        return DB_PRODUTO.objectBuilder(csvLine);
    }


    public void add(Produto p) throws Exception {
        DB_PRODUTO.save(p);
        TABLE_PRODUTO.put(p);
    }


    public void remove(Produto p) throws Exception {
        DB_PRODUTO.delete(p);
        TABLE_PRODUTO.remove(p);
    }


    public void edit(Produto old, Produto _new) throws Exception {
        remove(old);
        add(_new);
    }


    public List<Produto> get() throws Exception {
        return TABLE_PRODUTO.getAllProducts();
    }


    public Produto get(int codigoProduto) throws Exception {
        return TABLE_PRODUTO.get(codigoProduto);
    }


    public List<Produto> get(int codigoTipo, boolean searchTipo) throws Exception {
        if (!searchTipo) {
            return get();
        }
        return TABLE_PRODUTO.getByType(codigoTipo);
    }
}
