package controller;

import datastrucures.genericList.List;
import model.IRegistroCsv;

import java.io.*;

public abstract class RegistroCsvController implements IRegistroCsvController {
    private String dirPath;
    private String fileName;
    private String header;

    public RegistroCsvController(String dirPath, String fileName, String header) {
        setFilePath(dirPath);
        setFileName(fileName);
        setHeader(header);
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return dirPath;
    }

    public void setFilePath(String dirPath){
        this.dirPath = dirPath;
    }

    @Override
    public void addNewRegistro(IRegistroCsv obj) throws IOException {
        File dir = new File(dirPath);
        File arq = new File(dirPath, fileName+".csv");
        if(dir.exists() && dir.isDirectory()) {
            if(!arq.exists()) {
                createFile();
            }

            FileWriter fw = new FileWriter(arq, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.write("\n");
            pw.write(obj.getObjCsv());
            pw.flush();
            pw.close();
            fw.close();
        }
        else {
            throw new IOException("Diretório inválido");
        }
    }

    @Override
    public void deleteRegistro(IRegistroCsv obj) throws IOException {
        File file = new File(dirPath, fileName+".csv");
        File tempFile = new File(dirPath, "temp" + fileName+".csv");

        if(file.exists() && file.isFile()) {
            FileInputStream  stream = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader buffer = new BufferedReader(reader);

            FileWriter fw = new FileWriter(tempFile);
            PrintWriter pw = new PrintWriter(fw);

            String lineToRemove = obj.getObjCsv();

            String currentLine;
            while((currentLine = buffer.readLine()) != null) {
                if(currentLine.equals(lineToRemove)) continue;
                if(!currentLine.isEmpty()) {
                    pw.println(currentLine);
                    pw.flush();
                }
            }

            pw.close();
            fw.close();
            reader.close();
            buffer.close();

            if(!file.delete()) {
                throw new IOException("Não foi possível apagar o arquivo");
            }

            if(!tempFile.renameTo(file)){
                throw new IOException("Não foi possível renomear o arquivo");
            }
        }
        else {
            throw new IOException("Arquivo inválido");
        }

    }

    @Override
    public String getRegistroById(String id) throws IOException {
        File file = new File(dirPath, fileName+".csv");
        if(file.exists() && file.isFile()) {
            FileInputStream  stream = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader buffer = new BufferedReader(reader);
            String currentLine;
            while((currentLine = buffer.readLine()) != null) {
                if(currentLine.startsWith(id)) {
                    return currentLine;
                }
            }
        }

        throw new IOException("Arquivo inválido");
    }

    @Override
    public List<String> getAllRegistros() throws IOException {
        File file = new File(dirPath, fileName+".csv");

        if(file.exists() && file.isFile()) {
            List<String> registroList = new List<>();

            FileInputStream  stream = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader buffer = new BufferedReader(reader);

            String currentLine;
            buffer.readLine();
            while((currentLine = buffer.readLine()) != null) {
                try {
                    registroList.addLast(currentLine);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }

            buffer.close();
            reader.close();

            return registroList;
        }
        throw  new IOException("Arquivo Inválido");
    }

    @Override
    public void createFile() throws IOException {
        File dir = new File(dirPath);
        File arq = new File(dirPath, fileName + ".csv");
        if(dir.exists() && dir.isDirectory()) {
            if(!arq.exists()) {
                FileWriter fw = new FileWriter(arq);
                PrintWriter pw = new PrintWriter(fw);
                pw.write(header);
                pw.flush();
                pw.close();
                fw.close();
            }
            else {
                throw new IOException("Arquivo já existe");
            }
        }
        else {
            throw new IOException("Diretório Inválido");
        }
    }
}
