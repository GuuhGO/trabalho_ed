package controller.csv;

import model.Tipo;

public class TipoRegistroCsvController extends BaseRegistroCsvController<Tipo> {

    public TipoRegistroCsvController(String dirPath, String fileName, String header) {
        super(dirPath, fileName, header);
    }

    @Override
    public Tipo objectBuilder(String[] campos) throws Exception {
        if(campos.length != 3) {
            throw new Exception("Registro Inválido");
        }

        int    codigo;
        String nome;
        String descricao;

        try {
            codigo      = Integer.parseInt(campos[0]);
            nome        = campos[1];
            descricao   = campos[2];

        } catch (NumberFormatException e) {
            throw new Exception("Registro Inválido");
        }

        return new Tipo(codigo, nome, descricao);
    }
}
