package controller.Csv;

import datastrucures.genericList.List;
import model.IRegistroCsv;

import java.io.IOException;

public class ProdutoCsvController extends RegistroCsvController {
    public ProdutoCsvController(String dirPath, String fileName, String header) {
        super(dirPath, fileName, header);
    }

    public IRegistroCsv getObjectById(String id) throws IOException {
        // TODO: IMPLEMENTAR
        return null;
    }

    public List<IRegistroCsv> getAllObjects() throws IOException {
        // TODO: IMPLEMENTAR
        return null;
    }
}
