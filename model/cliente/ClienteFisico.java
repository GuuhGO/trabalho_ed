package model.cliente;


import datastrucures.genericList.List;

import java.io.*;

public class ClienteFisico extends BaseCliente {
    private String cpf;
    private String nome;

    public ClienteFisico(String nome, String cpf, String telefone) throws Exception {
        this.setTipoCliente("Físico");
        setNome(nome);
        setCpf(cpf);
        setTelefone(telefone);
    }

    public ClienteFisico(String nome, String cpf, String telefone, String logradouro, int numero, String complemento, String cep) throws Exception {
        this.setTipoCliente("Físico");
        setNome(nome);
        setCpf(cpf);
        setTelefone(telefone);
        setEndereco(logradouro, numero, complemento, cep);
    }

    public ClienteFisico(String nome, String cpf, String telefone, Endereco endereco) throws Exception {
        this.setTipoCliente("Físico");
        setNome(nome);
        setCpf(cpf);
        setTelefone(telefone);
        this.endereco = endereco;
    }

    public ClienteFisico(String nome, String cpf, String telefone, String logradouro, int numero, String cep) throws Exception {
        this.setTipoCliente("Físico");
        setNome(nome);
        setCpf(cpf);
        setTelefone(telefone);
        setEndereco(logradouro, numero, "", cep);
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) throws Exception {
        if (cpf.isEmpty()) {
            throw new Exception("Campo 'cpf' não pode ser vazio");
        }
        this.cpf = cpf;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) throws Exception {
        if (nome.isEmpty()) {
            throw new Exception("Campo 'nome' não pode ser vazio");
        }
        this.nome = nome;
    }

    @Override
    public String getObjCsv() {
        //cpf/cnpj;tipo;nome/fantasia;logradouro;numero;complemento;cep;telefone;email
        String delimiter = ";";
        StringBuffer buffer = new StringBuffer();
        buffer.append(this.cpf);
        buffer.append(delimiter);
        buffer.append(this.getTipoCliente());
        buffer.append(delimiter);
        buffer.append(this.nome);
        buffer.append(delimiter);
        if (this.getEndereco() != null) {
            buffer.append(this.getEndereco().getObjCsv());
            buffer.append(delimiter);
        } else {
            String temp = "null" + delimiter;
            buffer.append(temp.repeat(4));
        }
        buffer.append(this.getTelefone());
        buffer.append(delimiter);
        buffer.append("null");
        return buffer.toString();
    }

    @Override
    public ClienteFisico getById(String id) throws Exception {
        return (ClienteFisico) super.getById(id);
    }

    public List<ClienteFisico> getAll() throws IOException {
        File file = new File(DIR_PATH, getFileName()+".csv");
        if (!file.exists() || !file.isFile()) {
            throw new IOException("Arquivo inválido");
        }

        List<ClienteFisico> list = new List<>();

        FileInputStream stream = new FileInputStream(file);
        InputStreamReader reader = new InputStreamReader(stream);
        BufferedReader buffer = new BufferedReader(reader);


        String currentLine = buffer.readLine(); // pula a mãe de alguém
        while((currentLine = buffer.readLine()) != null) {
            try {
                list.addLast((ClienteFisico) objectBuilder(currentLine));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        return list;
    }
}
