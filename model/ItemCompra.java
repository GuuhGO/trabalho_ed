 package model;

import model.cliente.BaseCliente;

 public class ItemCompra implements ICsv {
    private final int ID_COMPRA;
    private final BaseCliente CLIENTE;
    private final Produto PRODUTO;

    public ItemCompra(Produto PRODUTO, int ID_COMPRA, BaseCliente CLIENTE) {
        this.ID_COMPRA = ID_COMPRA;
        this.CLIENTE = CLIENTE;
        this.PRODUTO = PRODUTO;
        diminuirEstoque();
    }

    public void aumentarEstoque() {
        int quantidadeEstoque = getPRODUTO().getQuantidadeEstoque();
        getPRODUTO().setQuantidadeEstoque(++quantidadeEstoque);
    }

    public void diminuirEstoque() {
        int quantidadeEstoque = getPRODUTO().getQuantidadeEstoque();
        getPRODUTO().setQuantidadeEstoque(--quantidadeEstoque);
    }

    @Override
    public String getObjCsv() {
        return  getID_COMPRA() + ";" +
                getCLIENTE().getCsvId() + ";" +
                getPRODUTO().getCodigo() + ";" +
                getPRODUTO().getTipo().getCodigo() + ";" +
                getPRODUTO().getNome() + ";" +
                getPRODUTO().getTipo().getNome() + ";" +
                getPRODUTO().getValor();
    }

    @Override
    public String getCsvId() {
        return String.valueOf(getID_COMPRA());
    }

     @Override
     public boolean compareAllFields(String reference) {
         return false;
     }

     @Override
    public String toString() {
        return getObjCsv();
    }

    public int getID_COMPRA() {
        return ID_COMPRA;
    }

    public BaseCliente getCLIENTE() {
        return CLIENTE;
    }

    public Produto getPRODUTO() {
        return PRODUTO;
    }
}
