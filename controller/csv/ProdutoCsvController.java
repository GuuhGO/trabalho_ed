package controller.csv;

import controller.hashTables.TipoHashTable;
import model.Produto;
import model.Tipo;


public class ProdutoCsvController extends BaseCsvController<Produto> {

    private static final String HEADER = "código;nome;valor;quantidadeEstoque;códigoTipo";
    private static final String FILE_NAME = "produtos";
    private final TipoHashTable TABLE_TIPOS;

    public ProdutoCsvController(TipoHashTable tabelaTipos) {
        this.TABLE_TIPOS = tabelaTipos;
    }

    @Override
    public String getHeader() {
        return HEADER;
    }

    @Override
    public String getFileName() {
        return FILE_NAME;
    }

    @Override
    public Produto objectBuilder(String csvLine) throws Exception {
        String[] campos = csvLine.split(";");
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
