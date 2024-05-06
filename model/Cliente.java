package model;

public abstract class Cliente {
    private Endereco endereco;
    private String telefone;
    private String tipoCliente;

    public Cliente() {
    }

    public String getTelefone() {
        return this.telefone;
    }

    public void setTelefone(String telefone) {
		this.telefone = telefone;
    }

    public Endereco getEndereco() {
        return this.endereco;
    }

    public void setEndereco(String logradouro, int numero, String cep) throws Exception {
        this.endereco = new Endereco(logradouro, numero, "", cep);
    }

    public void setEndereco(String logradouro, int numero, String complemento, String cep) throws Exception {
		this.endereco = new Endereco(logradouro, numero, complemento, cep);
    }

    public String getTipoCliente() {
        return this.tipoCliente;
    }

	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}
}