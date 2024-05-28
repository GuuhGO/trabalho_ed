package controller.csv;

import controller.TipoRegistry;
import datastrucures.genericList.List;
import model.Produto;
import model.Tipo;


public class ProdutoCsvController extends BaseCsvController<Produto> {

    private static final String HEADER = "código;nome;valor;quantidadeEstoque;códigoTipo";
    private static final String FILE_NAME = "produtos";
    private final List<Tipo> TIPOS;

    public ProdutoCsvController(List<Tipo> tabelaTipos) {
        this.TIPOS = tabelaTipos;
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

        Tipo tipo = searchTipo(codigoTipo);

        return new Produto(codigo, nome, tipo, valor, quantidade);
    }

    private Tipo searchTipo(int codigo) throws Exception {
        int size = TIPOS.size();
        Tipo result = null;

        for (int i = 0; i < size; i++) {
            Tipo tipo = TIPOS.get(i);
            if (tipo.getCodigo() == codigo) {
                result = tipo;
                break;
            }
        }

        if (result == null) {
            result = TipoRegistry.getInstance().getUncategorizedType();
        }

        return result;
    }
}
