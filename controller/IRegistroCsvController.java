package controller;

import datastrucures.genericList.List;
import model.IRegistroCsv;

import java.io.IOException;

public interface IRegistroCsvController {
    List<IRegistroCsv> getAllObjects() throws IOException;
    void addNewRegistro(IRegistroCsv obj) throws IOException;
    void deleteRegistro(IRegistroCsv obj) throws IOException;
    String getRegistroById(String id) throws IOException;
    IRegistroCsv getObjectById(String id) throws IOException;
    void createFile() throws IOException;
}
