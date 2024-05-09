package model.carrinho;

import model.Produto;
import model.Tipo;
import model.carrinho.ItemCarrinho;
import model.cliente.Cliente;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {
    private Cliente cliente;
    private List<Object> itens;

    public Carrinho(Cliente cliente) {
        this.cliente = cliente;
        this.itens = new ArrayList<>();
    }

    public void adicionarItem(Produto produto, Tipo tipoProduto) {
        ItemCarrinho item = new ItemCarrinho(produto, tipoProduto);
        itens.add(item);
    }

    public void removerItem(int index) {
        if (index >= 0 && index < itens.size()) {
            itens.remove(index);
        } else {
            System.out.println("Índice inválido.");
        }
    }

    public List<Object> getItens() {
        return this.itens;
    }

}

