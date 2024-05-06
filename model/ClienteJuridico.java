package model;

public class ClienteJuridico extends Cliente {
    private String cnpj;
    private String fantasia;
    private String email;

    public ClienteJuridico(String fantasia, String cnpj, String telefone, String email) {
        this.tipoCliente = "Jurídico";
        setCnpj(cnpj);
        setFantasia(fantasia);
        setTelefone(telefone);
        setEmail(email);
    }

    public ClienteJuridico(String fantasia, String cnpj, String logradouro, String complemento, int numero, String cep,
                           String telefone, String email) throws Exception {
        this.tipoCliente = "Jurídico";
        setCnpj(cnpj);
        setFantasia(fantasia);
        setTelefone(telefone);
        setEmail(email);
        setEndereco(logradouro, numero, complemento, cep);
    }

    public ClienteJuridico(String fantasia, String cnpj, String logradouro, int numero, String cep, String telefone,
                           String email) throws Exception {
        this.tipoCliente = "Jurídico";
        setCnpj(cnpj);
        setFantasia(fantasia);
        setTelefone(telefone);
        setEmail(email);
        setEndereco(logradouro, numero, cep);
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getFantasia() {
        return fantasia;
    }

    public void setFantasia(String fantasia) {
        this.fantasia = fantasia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getObjCsv() {
        //cpf/cnpj;tipo;nome/fantasia;endereco;telefone;email
        StringBuffer buffer = new StringBuffer();
        buffer.append(cnpj);
        buffer.append(";");
        buffer.append(tipoCliente);
        buffer.append(";");
        buffer.append(fantasia);
        buffer.append(";");
        if (super.getEndereco() != null)
            buffer.append(super.getEndereco().toString());
        buffer.append(";");
        buffer.append(super.getTelefone());
        buffer.append(";");
        buffer.append(getEmail());
        return buffer.toString();
    }

}