package model.cliente;


public class ClienteFisico extends BaseCliente {
    private String cpf;
    private String nome;

    public ClienteFisico(String nome, String cpf, String telefone, Endereco endereco) throws Exception {
        setTipoCliente("Físico");
        setNome(nome);
        setCpf(cpf);
        setTelefone(telefone);
        this.endereco = endereco;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) throws Exception {
        if (cpf.isEmpty() || cpf.equals("null")) {
            throw new Exception("Campo 'cpf' não pode ser vazio");
        }
        this.cpf = cpf;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) throws Exception {
        if (nome.isEmpty() || nome.equals("null")) {
            throw new Exception("Campo 'nome' não pode ser vazio");
        }
        this.nome = nome;
    }

    @Override
    public String getObjCsv() {
        //cpf/cnpj;tipo;nome/fantasia;logradouro;numero;complemento;cep;telefone;email
        String delimiter = ";";
        StringBuilder buffer = new StringBuilder(cpf + delimiter + getTipoCliente() + delimiter + nome + delimiter);
        if (this.getEndereco() != null) {
            buffer.append(getEndereco().getObjCsv());
            buffer.append(delimiter);
        } else {
            String temp = "null" + delimiter;
            buffer.append(temp.repeat(4));
        }
        buffer.append(getTelefone());
        buffer.append(delimiter);
        buffer.append("null");
        return buffer.toString();
    }

    @Override
    public String getCsvId() {
        return getCpf();
    }
}
