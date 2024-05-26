package controller.csv;

import datastrucures.genericList.List;
import model.ICsv;

import java.io.*;

public abstract class BaseCsvController<T extends ICsv> implements ICsvController<T> {
    public static final String DIR_PATH = "./src/files";
    public static final String DELIMITER = ";";

    public BaseCsvController() {
    }

    // Usados na escrita/leitura do csv. Devem ser implementados pela classe filha
    public abstract String getHeader();
    public abstract String getFileName();
    public abstract T objectBuilder(String csvLine) throws Exception;

    @Override
    public void save(T obj) throws IOException {
        File file = getValidatedFile();

        PrintWriter pw = new PrintWriter(new FileWriter(file, true));
        pw.write("\n" + obj.getObjCsv());
        pw.flush();
        pw.close();
    }

    @Override
    public void delete(T obj) throws IOException {
        File file = getValidatedFile();
        File temp = new File(DIR_PATH, "temp" + getFileName() + ".csv");

        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            PrintWriter pw = new PrintWriter(new FileWriter(temp));
            String lineToRemove = obj.getObjCsv();
            String currentLine;
            boolean firstPrint = true;

            while ((currentLine = buffer.readLine()) != null) {
                // skip linhas vazias e o target
                if (currentLine.equals(lineToRemove) || currentLine.isBlank()) {
                    continue;
                }
                // garante que não haverão espaços em branco após a deleção
                if(firstPrint) {
                    pw.print(currentLine);
                    firstPrint = false;
                } else {
                    pw.println();
                    pw.print(currentLine);
                }
                pw.flush();
            }
            pw.close();
        }

        if (!file.delete()) {
            throw new IOException("Não foi possível apagar o arquivo");
        }

        if (!temp.renameTo(file)) {
            throw new IOException("Não foi possível renomear o arquivo");
        }
    }

    @Override
    public void delete(String id) throws IOException {
        File file = getValidatedFile();
        File temp = new File(DIR_PATH, "temp" + getFileName() + ".csv");

        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            PrintWriter pw = new PrintWriter(new FileWriter(temp));
            String currentLine;
            boolean firstPrint = true;

            while ((currentLine = buffer.readLine()) != null) {
                // skip linhas vazias e o target
                if ((currentLine.startsWith(id)) || currentLine.isBlank()) {
                    continue;
                }
                if(firstPrint) {
                    pw.print(currentLine);
                    firstPrint = false;
                } else {
                    pw.println();
                    pw.print(currentLine);
                }
                pw.flush();
            }
            pw.close();
        }

        if (!file.delete()) {
            throw new IOException("Não foi possível apagar o arquivo");
        }

        if (!temp.renameTo(file)) {
            throw new IOException("Não foi possível renomear o arquivo");
        }

    }

    private String getRegistroById(String id) throws IOException {
        File file = getValidatedFile();
        String result = null;

        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            String currentLine;
            while ((currentLine = buffer.readLine()) != null) {
                if(currentLine.isBlank()) continue;
                if (currentLine.substring(0, currentLine.indexOf(';')).equals(id)) {
                    result = currentLine;
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public T get(String id) throws Exception {
        String registroCsv = getRegistroById(id);
        if (registroCsv == null) {
            throw new Exception("Produto não encontrado");
        }

        return objectBuilder(registroCsv);
    }

    @Override
    public List<T> get() throws IOException {
        File file = getValidatedFile();

        List<T> list = new List<>();

        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            buffer.readLine(); // pular o header
            String currentLine;
            while ((currentLine = buffer.readLine()) != null) {
                try {
                    list.addLast(objectBuilder(currentLine));
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }

        return list;
    }

    private void createFile() throws IOException {
        File dir = new File(DIR_PATH);
        File arq = new File(DIR_PATH, getFileName() + ".csv");
        if (!dir.exists() || !dir.isDirectory()) {
            throw new IOException("Diretório Inválido");
        }
        if (arq.exists()) {
            throw new IOException("Arquivo já existe");
        }

        PrintWriter pw = new PrintWriter(new FileWriter(arq));
        pw.write(getHeader());
        pw.flush();
        pw.close();
    }

    private File getValidatedFile() throws IOException {
        try {
            // Cria o arquivo ainda não existe
            createFile();
        } catch (IOException e) {
            if (e.getMessage().equals("Diretório Inválido")) {
                throw e;
            }
        }
        File file = new File(DIR_PATH, getFileName() + ".csv");
        if (!file.isFile()) {
            throw new IOException("Arquivo inválido");
        }
        return file;
    }
}
