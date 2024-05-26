package view;

import controller.csv.ClienteCsvController;
import controller.csv.ProdutoCsvController;
import controller.csv.TipoCsvController;
import controller.hashTables.ProdutoHashTable;
import controller.hashTables.TipoHashTable;
import datastrucures.genericList.List;
import model.Produto;
import model.Tipo;
import model.cliente.BaseCliente;
import model.cliente.ClienteFisico;
import model.cliente.ClienteJuridico;
import model.cliente.Endereco;


public class TestesNaner {
    static ClienteCsvController dbCliente = new ClienteCsvController();
    static TipoCsvController dbTipo = new TipoCsvController();
    static final TipoHashTable TIPO_TABLE = new TipoHashTable(15);
    static ProdutoCsvController dbProduto = new ProdutoCsvController(TIPO_TABLE);

    public static void main(String[] args) {
        try {
            ProdutoHashTable produtoTable = new ProdutoHashTable();
            List<Tipo> t = dbTipo.get();
            Produto p = new Produto(0, "Laranja", t.get(0), 5.60, 100);
            Produto p2 = new Produto(1, "Laranja", t.get(1), 5.60, 100);
            Produto p3 = new Produto(2, "Laranja", t.get(2), 5.60, 100);
            produtoTable.put(p);
            produtoTable.put(p2);
            produtoTable.put(p3);
            produtoTable.remove(0,0);
            produtoTable.remove(p2);
            System.out.println(produtoTable);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void testeWriteCliente() {
        try {
            Endereco e = new Endereco("Rua dos loucos", 69, "", "4206900");
            ClienteFisico cf = new ClienteFisico("João", "12345678974", "11999999999", e);
            ClienteJuridico cj = new ClienteJuridico("Plataforma 935", "000000000000", "11988888888", "comercio@plataforma.com", e);
            dbCliente.save(cf);
            dbCliente.save(cj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testeReadCliente() {
        try {
            ClienteJuridico cj = (ClienteJuridico) dbCliente.get("000000000000");
            ClienteFisico cf = (ClienteFisico) dbCliente.get("12345678974");
            System.out.println(cj);
            System.out.println(cf);

            List<BaseCliente> clientes = dbCliente.get();
            System.out.println(clientes);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void testeDeleteCliente() {
        try {
            ClienteFisico cf = (ClienteFisico) dbCliente.get("12345678974");
            dbCliente.delete("000000000000");
            dbCliente.delete(cf);
            System.out.println(dbCliente.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testeWriteTipo() {
        try {
            Tipo t = new Tipo(0, "bens de consumo", "usados por usuários-finais");
            dbTipo.save(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testeReadTipo() {
        try {
            Tipo t = dbTipo.get("0");
            System.out.println(t);

            List<Tipo> tipos = dbTipo.get();
            System.out.println(tipos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testeWriteProduto() {
        try {
            Tipo t = dbTipo.get("0");
            Produto p = new Produto(0, "Laranja", t, 5.60, 100);
            dbProduto.save(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testeReadProduto() {
        try {
            Tipo t = dbTipo.get("0");
            TIPO_TABLE.put(t);
            Produto p = dbProduto.get("0");
            System.out.println(p);

            List<Produto> produtos = dbProduto.get();
            System.out.println(produtos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}