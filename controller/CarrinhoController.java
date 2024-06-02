package controller;

import controller.csv.ItemCompraCsvController;
import datastructures.genericQueue.Queue;
import datastructures.genericStack.Stack;
import datastrucures.genericList.List;
import model.ICsv;
import model.Produto;
import model.ItemCompra;
import model.cliente.BaseCliente;


public class CarrinhoController implements ICsv {
    ItemCompraCsvController DB_COMPRAS = new ItemCompraCsvController();
    private final int ID_COMPRA;
    private final BaseCliente CLIENTE;
    private final List<ItemCompra> ITEM_LIST;
    private double valorTotal;


    public CarrinhoController(BaseCliente cliente, int ID_COMPRA) {
        this.CLIENTE = cliente;
        this.ID_COMPRA = ID_COMPRA;
        this.ITEM_LIST = new List<>();
    }


    public double getValorTotal() {
        return valorTotal;
    }


    public void adicionarItem(Produto produto) throws Exception {
        if (produto.getQuantidadeEstoque() < 1) {
            throw new Exception("Produto sem estoque");
        }
        ItemCompra item = new ItemCompra(produto, ID_COMPRA, CLIENTE);
        ITEM_LIST.addLast(item);
        valorTotal += produto.getValor();
    }


    public void removerItem(int index) throws Exception {
        if (index < 0 || index > ITEM_LIST.size()) {
            throw new Exception("Índice inválido");
        }
        ItemCompra itemCarrinho = ITEM_LIST.get(index);
        itemCarrinho.aumentarEstoque();
        ITEM_LIST.remove(index);
        valorTotal -= itemCarrinho.getPRODUTO().getValor();
    }


    public Stack<ICsv> getItensStack() throws Exception {
        int size = ITEM_LIST.size();
        Stack<ICsv> itemStack = new Stack<>();
        for (int i = 0; i < size; i++) {
            itemStack.push(ITEM_LIST.get(i));
        }
        return itemStack;
    }


    public Queue<ICsv> getItensQueue() throws Exception{
        int size = ITEM_LIST.size();
        Queue<ICsv> itemQueue = new Queue<>();
        for (int i = 0; i < size; i++) {
            itemQueue.insert(ITEM_LIST.get(i));
        }
        return itemQueue;
    }


    public int getNextAvailableId() throws Exception {
        int index = 0;
        while (true) {
            try {
                DB_COMPRAS.get(String.valueOf(index++));
            } catch (Exception e) {
                if(e.getMessage().equalsIgnoreCase("Registro não encontrado")) {
                    return index; // valor disponível para criação de um novo carrinho
                }
                throw new Exception(e);
            }
        }
    }


    public void save() throws Exception {
        int size = ITEM_LIST.size();
        for (int i = 0; i < size; i++) {
            DB_COMPRAS.save(ITEM_LIST.get(i));
        }
        ProdutoController.getInstance().updateData();
    }


    @Override
    public String getObjCsv() {
        int size = ITEM_LIST.size();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            try {
                sb.append("\n");
                sb.append(ITEM_LIST.get(i));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return sb.toString();
    }


    @Override
    public String getCsvId() {
        return String.valueOf(ID_COMPRA);
    }

    @Override
    public boolean compareAllFields(String reference) {
        return false;
    }

    @Override
    public String toString() {
        return "Carrinho: " + ID_COMPRA + " - Cliente: " + CLIENTE.getCsvId();
    }
}