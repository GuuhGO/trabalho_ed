package controller.csv;

import model.Produto;
import model.Tipo;
import model.ItemCompra;
import model.cliente.BaseCliente;

public class ItemCompraCsvController extends BaseCsvController<ItemCompra> {
    @Override
    public String getHeader() {
        return "id_compra;id_cliente;codigo_produto;codigo_tipo;nome_produto;nome_tipo_produto;valor_produto";
    }

    @Override
    public String getFileName() {
        return "compras";
    }

    @Override
    public ItemCompra objectBuilder(String csvLine) throws Exception {
        String[] fields = csvLine.split(";");
        if(fields.length != getHeader().split(";").length) {
            throw new Exception("Registro Inválido");
        }

        int idCompra;
        int codigoProduto;
        int codigoTipo;
        double valor;

        try {
            idCompra = Integer.parseInt(fields[0]);
            codigoProduto = Integer.parseInt(fields[2]);
            codigoTipo = Integer.parseInt(fields[3]);
            valor = Double.parseDouble(fields[6]);
        } catch (NumberFormatException e) {
            throw new Exception("Registro Inválido");
        }

        BaseCliente cliente = getBaseCliente(fields);

        Tipo tipo = new Tipo(codigoTipo, fields[5], "Desconhecido");
        Produto produto = new Produto(codigoProduto, fields[4], tipo, valor, 0);


        return new ItemCompra(produto, idCompra, cliente);
    }

    private static BaseCliente getBaseCliente(String[] fields) throws Exception {
        ClienteCsvController clienteCsvController = new ClienteCsvController();
        BaseCliente cliente;
        try {
            cliente = clienteCsvController.get(String.valueOf(fields[1]));
        } catch (Exception e) {
            if (!e.getMessage().equalsIgnoreCase("Registro não encontrado")) {
                throw new Exception(e);
            }
            String clienteBase = "%s;Físico;Desconhecido;null;0;null;null;Desconhecido;null";
            cliente = clienteCsvController.objectBuilder(String.format(clienteBase, fields[1]));
        }
        return cliente;
    }
}
