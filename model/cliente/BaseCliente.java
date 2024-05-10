package model.cliente;

public abstract class BaseCliente {
    private Endereco endereco;
    private String telefone;
    private String tipoCliente;

    public BaseCliente() {
    }

    public String getTelefone() {
        return this.telefone;
    }

    public void setTelefone(String telefone) throws Exception {
		if (telefone.isEmpty()){
            throw new Exception("Campo 'telefone' não pode ser vazio");
        }
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