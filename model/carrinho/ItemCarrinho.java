package model.carrinho;

import model.Produto;
import model.Tipo;

public class ItemCarrinho {
    private Produto produto;
    private Tipo tipoProduto;

    public ItemCarrinho(Produto produto, Tipo tipoProduto) {
        this.produto = produto;
        this.tipoProduto = tipoProduto;
    }

    @Override
    public String toString() {
        return "Produto: " + produto.getNome() + ", Tipo: " + tipoProduto.getNome();
    }
}
