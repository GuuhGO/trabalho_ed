package controller.csv;

import datastrucures.genericList.List;
import model.Produto;

import java.io.IOException;

public class ProdutoCsvController extends BaseRegistroCsvController {

    public Produto getObjectById(String id) throws IOException {
        // TODO: IMPLEMENTAR
        return null;
    }

    public List<Produto> getAllObjects() throws IOException {
        // TODO: IMPLEMENTAR
        return null;
    }

    public ProdutoCsvController(String dirPath, String fileName, String header) {
        super(dirPath, fileName, header);
    }
}
