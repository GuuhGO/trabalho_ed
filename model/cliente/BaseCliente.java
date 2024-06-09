package model.cliente;

import model.ICsv;

public abstract class BaseCliente implements ICsv {
	protected Endereco endereco;
	private String telefone;
	private String tipoCliente;

	public BaseCliente() {
	}

	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String telefone) throws IllegalArgumentException {
		if (telefone.isEmpty() || telefone.equals("null")) {
			throw new IllegalArgumentException("Campo 'telefone' não pode ser vazio");
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

	public void setTipoCliente(String tipoCliente) throws IllegalArgumentException {
		if (tipoCliente.equals("Físico") || tipoCliente.equals("Jurídico")) {
			this.tipoCliente = tipoCliente;
			return;
		}
		throw new IllegalArgumentException("Tipo de cliente deve ser \"Físico\" ou \"Jurídico\"\n");
	}

	public String getNome() {
		return "";
	}
	
	
	public boolean compareAllFields(String reference) {
		return (tipoCliente.toUpperCase().contains(reference) || telefone.toUpperCase().contains(reference)
				|| endereco.compareAllFields(reference));
	}
}