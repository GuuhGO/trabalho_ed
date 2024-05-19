package controller.csv;

import model.cliente.BaseCliente;
import datastrucures.genericList.List;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;


public class ClienteCsvController extends BaseRegistroCsvController<BaseCliente> {

    public ClienteCsvController(String dirPath, String fileName, String header) {
        super(dirPath, fileName, header);
    }

    @Override
    public BaseCliente getObjectById(int id) throws Exception {
        String registroCsv = getRegistroById(String.valueOf(id));
        if(registroCsv == null) {
            throw new Exception("Cliente não encontrado");
        }
        return objectBuilder(registroCsv.split(";"));
    }

    @Override
    public List<BaseCliente> getAllObjects() throws IOException{
        File file = new File(dirPath, fileName + ".csv");
        if(!file.exists() || !file.isFile()){
            throw new IOException("Arquivo inválido");
        }
        List<BaseCliente> list = new List<>();
        FileInputStream stream = new FileInputStream(file);
        InputStreamReader reader = new InputStreamReader(stream);
        BufferedReader buffer = new BufferedReader(reader);

        String currentLine = buffer.readLine(); // Ignora cabeçalho
        while((currentLine = buffer.readLine()) != null) {
            try {
                String[] fields = currentLine.split(";");
                list.addLast(objectBuilder(fields));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return list;
    }

    @Override
    public BaseCliente objectBuilder(String[] campos) throws Exception {
        return null;
    }

}
