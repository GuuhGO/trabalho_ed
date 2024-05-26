package model;


import java.io.*;

public abstract class BaseCsvWriter<T extends  ICsv> implements ICsvWriter<T> {
    protected static final String DIR_PATH = "./src/files/";

    public String getFileName() {
        return null;
    }

    public String getHeader() {
        return null;
    }

    public BaseCsvWriter() {}


    private String getRegistroById(String id) throws IOException {
        File file = new File(DIR_PATH, getFileName()+".csv");
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

    protected void createFile() throws IOException {
        File dir = new File(DIR_PATH);
        File arq = new File(DIR_PATH, getFileName() + ".csv");
        if(dir.exists() && dir.isDirectory()) {
            if(!arq.exists()) {
                FileWriter fw = new FileWriter(arq);
                PrintWriter pw = new PrintWriter(fw);
                pw.write(getHeader());
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
    public T getById(String id) throws Exception {
        String registroCsv = getRegistroById(String.valueOf(id));
        if (registroCsv == null) {
            throw new Exception("Produto não encontrado");
        }

        return objectBuilder(registroCsv);
    }
}
