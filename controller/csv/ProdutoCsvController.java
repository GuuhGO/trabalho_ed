package controller.csv;

import controller.TipoController;
import datastrucures.genericList.List;
import model.ICsv;
import model.Produto;
import model.Tipo;


public class ProdutoCsvController extends BaseCsvController<Produto> {

    private static final String HEADER = "código;tipo;nome;valor;estoque";
    private static final String FILE_NAME = "produtos";
    private final List<ICsv> TIPOS;

    public ProdutoCsvController(List<ICsv> tabelaTipos) {
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
        double valor;
        int quantidade;
        int codigoTipo;

        try {
            codigo = Integer.parseInt(campos[0]);
            codigoTipo = Integer.parseInt(campos[1]);
            valor = Double.parseDouble(campos[3]);
            quantidade = Integer.parseInt(campos[4]);
        } catch (NumberFormatException e) {
            throw new Exception("Registro Inválido");
        }

        Tipo tipo = searchTipo(codigoTipo);

        return new Produto(codigo, campos[2], tipo, valor, quantidade);
    }

    private Tipo searchTipo(int codigo) throws Exception {
        int size = TIPOS.size();
        Tipo result = null;

        for (int i = 0; i < size; i++) {
            Tipo tipo = (Tipo) TIPOS.get(i);
            if (tipo.getCodigo() == codigo) {
                result = tipo;
                break;
            }
        }

        if (result == null) {
            result = TipoController.getInstance().getUncategorizedType();
        }

        return result;
    }
}
