package controller;

import controller.csv.ProdutoCsvController;
import controller.hashTables.ProdutoHashTable;
import datastrucures.genericList.List;
import model.Produto;
import model.Tipo;

public class ProdutoCsvSyncHashTable {
    private final ProdutoCsvController DB_PRODUTO;
    private final ProdutoHashTable TABLE_PRODUTO;

    public ProdutoCsvSyncHashTable(List<Tipo> tipoList) throws Exception {
        DB_PRODUTO = new ProdutoCsvController(tipoList);
        TABLE_PRODUTO = new ProdutoHashTable();
        populateHashTable();
    }

    public String getHeader() {
        return DB_PRODUTO.getHeader();
    }

    public Produto objectBuilder(String csvLine) throws Exception {
        return DB_PRODUTO.objectBuilder(csvLine);
    }

    private void populateHashTable() throws Exception {
        List<Produto> produtoList = DB_PRODUTO.get();
        int size = produtoList.size();
        while (--size >= 0) {
            TABLE_PRODUTO.put(produtoList.get(size));
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
