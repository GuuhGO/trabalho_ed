package model;

public class ClienteFisico extends Cliente {
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

    public ClienteFisico(String nome, String cpf, String telefone, String logradouro, int numero, String cep) throws Exception {
        this.setTipoCliente("Físico");
        setNome(nome);
        setCpf(cpf);
        setTelefone(telefone);
        setEndereco(logradouro, numero, "", cep);
    }

    public String getCpf() {
        return cpf;
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

    public String getObjCsv() {
        //cpf/cnpj;tipo;nome/fantasia;endereco;telefone;email
        String delimiter = ";";
        StringBuffer buffer = new StringBuffer();
        buffer.append(this.cpf);
        buffer.append(delimiter);
        buffer.append(this.getTipoCliente());
        buffer.append(delimiter);
        buffer.append(this.nome);
        buffer.append(delimiter);
        if (this.getEndereco() != null)
            buffer.append(this.getEndereco().toString());
        buffer.append(delimiter);
        buffer.append(this.getTelefone());
        buffer.append(delimiter);
        return buffer.toString();
    }
}