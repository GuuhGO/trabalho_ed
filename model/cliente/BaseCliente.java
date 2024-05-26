package model.cliente;

import model.BaseCsvWriter;
import model.ICsv;

public abstract class BaseCliente extends BaseCsvWriter<BaseCliente> implements ICsv {
	protected Endereco endereco;
	private String telefone;
	private String tipoCliente;
	protected static String fileName = "cliente";
	protected static String header = "cpf_cnpj;tipoCliente;nome_fantasia;logradouro;numero;complemento;cep;telefone;email";

	public BaseCliente() {}

	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String telefone) throws Exception {
		if (telefone.isEmpty()) {
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

	public void setTipoCliente(String tipoCliente) throws Exception {
		if (tipoCliente.equals("Físico") || tipoCliente.equals("Jurídico")) {
			this.tipoCliente = tipoCliente;
			return;
		}
		throw new Exception("Tipo de cliente deve ser \"Físico\" ou \"Jurídico\"\n");
	}

	protected Endereco enderecoBuilder(String[] campos) throws Exception {
		if (campos[2].equals("null")) {
			return null;
		}

		String logradouro = campos[2];
		int numero;
		try {
			numero = Integer.parseInt(campos[3]);
		} catch (NumberFormatException e) {
			throw new Exception("Endereço inválido");
		}

		String complemento = !campos[4].equals("null") ? campos[4] : "";
		String cep = campos[5];

		return new Endereco(logradouro, numero, complemento, cep);
	}
}