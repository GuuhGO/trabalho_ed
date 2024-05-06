package model;

//test
public class Endereco {
    private String logradouro;
    private int numero;
    private String complemento = "";
    private String cep;
    private StringBuffer errorMessage = new StringBuffer("ERRO NA CRIAÇÃO DO ENDEREÇO:\n");

    public Endereco(String logradouro, int numero, String complemento, String cep) throws Exception {
        setLogradouro(logradouro);
        setComplemento(complemento);
        setNumero(numero);
        setCep(cep);
    }

    public String getLogradouro() {
        return this.logradouro;
    }

    public void setLogradouro(String logradouro) throws Exception {
        boolean bLog = (!logradouro.isEmpty());
        if (!bLog) {
            errorMessage.append("Logradouro NÃO pode ser vazio\n");
            throw new Exception(errorMessage.toString());
        }
        this.logradouro = logradouro;
    }

    public int getNumero() {
        return this.numero;
    }

    public void setNumero(int numero) throws Exception {
        boolean bNum = (numero < 0);
        if (bNum) {
            errorMessage.append("Número NÃO pode ser menor que zero\n");
            throw new Exception(errorMessage.toString());
        }
        this.numero = numero;
    }

    public String getComplemento() {
        return this.complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCep() {
        return this.cep;
    }

    public void setCep(String cep) throws Exception {
        boolean bCep = (cep.isEmpty());
        if (bCep) {
            errorMessage.append("CEP NÃO pode ser vazio\n");
            throw new Exception(errorMessage.toString());
        }
        this.cep = cep;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(this.logradouro);
        buffer.append(", ");
        buffer.append(this.numero);
        if (!complemento.isEmpty()) {
            buffer.append("-");
            buffer.append(this.complemento);
        }
        buffer.append(" - ");
        buffer.append(this.cep);
        return buffer.toString();
    }

}