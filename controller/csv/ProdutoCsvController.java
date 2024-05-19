package controller.csv;

import controller.hashTables.TipoHashTable;
import datastrucures.genericList.List;
import model.Produto;
import model.Tipo;

import java.io.*;

public class ProdutoCsvController extends BaseRegistroCsvController<Produto> {

    private final TipoHashTable TABLE_TIPOS;

    public ProdutoCsvController(String dirPath, String fileName, String header, TipoHashTable tabelaTipos) {
        super(dirPath, fileName, header);
        this.TABLE_TIPOS = tabelaTipos;
    }

    @Override
    public Produto objectBuilder(String[] campos) throws Exception {
        if (campos.length != 5) {
            throw new Exception("Registro Inválido");
        }

        int codigo;
        String nome;
        double valor;
        int quantidade;
        int codigoTipo;

        try {
            codigo = Integer.parseInt(campos[0]);
            nome = campos[1];
            valor = Double.parseDouble(campos[2]);
            quantidade = Integer.parseInt(campos[3]);
            codigoTipo = Integer.parseInt(campos[4]);
        } catch (NumberFormatException e) {
            throw new Exception("Registro Inválido");
        }

        Tipo tipo = TABLE_TIPOS.get(codigoTipo);
        if (tipo == null) {
            tipo = new Tipo(codigoTipo, "Desconhecido", "Desconhecido");
        }

        return new Produto(codigo, nome, tipo, valor, quantidade);
    }
}
