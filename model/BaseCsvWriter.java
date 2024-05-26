package model;


import java.io.*;

public abstract class BaseCsvWriter<T extends  ICsv> implements ICsvWriter<T> {
    protected static final String DIR_PATH = "./src/files/";
    protected static String fileName;
    protected static String header;

    public BaseCsvWriter() {}

    @Override
    public void save(ICsv obj) throws IOException {
        File dir = new File(DIR_PATH);
        File arq = new File(DIR_PATH, fileName+".csv");
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
    public void delete(ICsv obj) throws IOException {
        File file = new File(DIR_PATH, fileName+".csv");
        File tempFile = new File(DIR_PATH, "temp" + fileName+".csv");

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
        File file = new File(DIR_PATH, fileName+".csv");
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

    private void createFile() throws IOException {
        File dir = new File(DIR_PATH);
        File arq = new File(DIR_PATH, fileName + ".csv");
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
    public T getById(String id) throws Exception {
        String registroCsv = getRegistroById(String.valueOf(id));
        if (registroCsv == null) {
            throw new Exception("Produto não encontrado");
        }

        return objectBuilder(registroCsv);
    }
}
