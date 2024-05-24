package controller.csv;

import datastrucures.genericList.List;
import model.ICsv;

import java.io.IOException;

public interface ICsvController<T> {
    void addRegistro(ICsv obj) throws IOException;
    void deleteRegistro(ICsv obj) throws IOException;
    T getObjectById(int id) throws Exception;
    List<T> getAllObjects() throws IOException;
    T objectBuilder(String[] campos) throws Exception;
    void createFile() throws IOException;
}
