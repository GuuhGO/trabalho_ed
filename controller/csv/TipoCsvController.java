package controller.csv;

import model.Tipo;

public class TipoCsvController extends BaseCsvController<Tipo> {

    private final static String HEADER = "código;nome;descrição";
    private final static String FILE_NAME = "tipos";

    public TipoCsvController() {}

    @Override
    public String getHeader() {
        return HEADER;
    }

    @Override
    public String getFileName() {
        return FILE_NAME;
    }

    @Override
    public Tipo objectBuilder(String csvLine) throws Exception {
        String[] campos = csvLine.split(";");
        if(campos.length != 3) {
            throw new Exception("Registro Inválido");
        }

        int codigo;
        String nome;
        String descricao;

        try {
            codigo    = Integer.parseInt(campos[0]);
            nome      = campos[1];
            descricao = campos[2];

        } catch (NumberFormatException e) {
            throw new Exception("Registro Inválido");
        }

        return new Tipo(codigo, nome, descricao);
    }
}
