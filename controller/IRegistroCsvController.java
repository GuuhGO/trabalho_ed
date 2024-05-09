package controller;

import datastrucures.genericList.List;
import model.IRegistroCsv;

import java.io.IOException;

public interface IRegistroCsvController {
    void addNewRegistro(IRegistroCsv obj) throws IOException;
    void deleteRegistro(IRegistroCsv obj) throws IOException;
    String getRegistroById(String id) throws IOException;
    List<String> getAllRegistros() throws IOException;
    void createFile() throws IOException;
}
