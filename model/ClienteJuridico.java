package model;

public class ClienteJuridico extends Cliente {
    private String cnpj;
    private String fantasia;
    private String email;

    public ClienteJuridico(String fantasia, String cnpj, String telefone, String email) {
        this.setTipoCliente("Jurídico");
        setCnpj(cnpj);
        setFantasia(fantasia);
        setTelefone(telefone);
        setEmail(email);
    }

    public ClienteJuridico(String fantasia, String cnpj, String telefone, String email, String logradouro, String complemento, int numero, String cep) throws Exception {
        this.setTipoCliente("Jurídico");
        setCnpj(cnpj);
        setFantasia(fantasia);
        setTelefone(telefone);
        setEmail(email);
        setEndereco(logradouro, numero, complemento, cep);
    }

    public ClienteJuridico(String fantasia, String cnpj, String telefone, String email, String logradouro, int numero, String cep) throws Exception {
        this.setTipoCliente("Jurídico");
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
        String delimiter = ";";
        StringBuffer buffer = new StringBuffer();
        buffer.append(this.cnpj);
        buffer.append(delimiter);
        buffer.append(this.getTipoCliente());
        buffer.append(delimiter);
        buffer.append(this.fantasia);
        buffer.append(delimiter);
        if (this.getEndereco() != null)
            buffer.append(this.getEndereco().toString());
        buffer.append(delimiter);
        buffer.append(this.getTelefone());
        buffer.append(delimiter);
        buffer.append(this.email);
        return buffer.toString();
    }

}