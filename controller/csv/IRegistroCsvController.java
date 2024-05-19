package controller.csv;

import datastrucures.genericList.List;
import model.IRegistroCsv;
import model.Produto;

import java.io.IOException;

public interface IRegistroCsvController<T> {
    void addNewRegistro(IRegistroCsv obj) throws IOException;
    void deleteRegistro(IRegistroCsv obj) throws IOException;
    String getRegistroById(String id) throws IOException;
    T getObjectById(int id) throws Exception;
    List<T> getAllObjects() throws IOException;
    T objectBuilder(String[] campos) throws Exception;
    void createFile() throws IOException;
}
