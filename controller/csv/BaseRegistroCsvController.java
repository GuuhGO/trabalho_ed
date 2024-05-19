package controller.csv;

import datastrucures.genericList.List;
import model.IRegistroCsv;

import java.io.*;

public abstract class BaseRegistroCsvController<T> implements IRegistroCsvController<T> {
    private String dirPath;
    private String fileName;
    private String header;

    public BaseRegistroCsvController(String dirPath, String fileName, String header) {
        setFilePath(dirPath);
        setFileName(fileName);
        setHeader(header);
    }

    public String getHeader() {
        return header;
    }

    protected void setHeader(String header) {
        this.header = header;
    }

    public String getFileName() {
        return fileName;
    }

    protected void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return dirPath;
    }

    protected void setFilePath(String dirPath){
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

    private String getRegistroById(String id) throws IOException {
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
            return null;
        }
        else {
            throw new IOException("Arquivo inválido");
        }
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

    @Override
    public List<T> getAllObjects() throws IOException {
        File file = new File(dirPath, fileName+".csv");
        if (!file.exists() || !file.isFile()) {
            throw new IOException("Arquivo inválido");
        }

        List<T> list = new List<>();

        FileInputStream stream = new FileInputStream(file);
        InputStreamReader reader = new InputStreamReader(stream);
        BufferedReader buffer = new BufferedReader(reader);

        String currentLine = buffer.readLine(); // pula a mãe de alguém
        while((currentLine = buffer.readLine()) != null) {
            try {
                list.addLast(objectBuilder(currentLine.split(";")));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        return list;
    }

    @Override
    public T getObjectById(int id) throws Exception {
        String registroCsv = getRegistroById(String.valueOf(id));
        if (registroCsv == null) {
            throw new Exception("Produto não encontrado");
        }

        return objectBuilder(registroCsv.split(";"));
    }
}
