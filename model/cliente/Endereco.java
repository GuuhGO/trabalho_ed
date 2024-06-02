package model.cliente;

import model.ICsv;

public class Endereco implements ICsv {
	private String logradouro;
	private int numero;
	private String complemento = "";
	private String cep;
	private String errorMessage = "";

	public Endereco(String logradouro, int numero, String complemento, String cep) throws IllegalArgumentException {
		setLogradouro(logradouro);
		setComplemento(complemento);
		setNumero(numero);
		setCep(cep);
	}

	public String getLogradouro() {
		return this.logradouro;
	}

	public void setLogradouro(String logradouro) throws IllegalArgumentException {
		if (logradouro.isEmpty() || logradouro.equals("null")) {
			errorMessage = "Logradouro NÃO pode ser vazio\n";
			throw new IllegalArgumentException(errorMessage);
		}
		this.logradouro = logradouro;
	}

	public int getNumero() {
		return this.numero;
	}

	public void setNumero(int numero) throws IllegalArgumentException {
		if (numero < 0) {
			errorMessage = "Número NÃO pode ser menor que zero\n";
			throw new IllegalArgumentException(errorMessage);
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

	public void setCep(String cep) throws IllegalArgumentException {
		if (cep.isEmpty()) {
			errorMessage = "CEP NÃO pode ser vazio\n";
			throw new IllegalArgumentException(errorMessage);
		}
		this.cep = cep;
	}

	@Override
	public String getObjCsv() {
		String delimiter = ";";
		StringBuffer buffer = new StringBuffer();
		buffer.append(this.logradouro);
		buffer.append(delimiter);
		buffer.append(this.numero);
		buffer.append(delimiter);
		if (!this.complemento.isEmpty()) {
			buffer.append(this.complemento);
		} else {
			buffer.append("null");
		}
		buffer.append(delimiter);
		buffer.append(this.cep);
		return buffer.toString();
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(this.logradouro);
		buffer.append(", ");
		buffer.append(this.numero);
		if (!this.complemento.isEmpty()) {
			buffer.append("-");
			buffer.append(this.complemento);
		}
		buffer.append(" - ");
		buffer.append(this.cep);
		return buffer.toString();
	}

	@Override
	public String getCsvId() {
		return logradouro + ";" + numero;
	}

	@Override
	public boolean compareAllFields(String reference) {
		return (logradouro.toUpperCase().contains(reference) || String.valueOf(numero).toUpperCase().contains(reference)
				|| complemento.toUpperCase().contains(reference) || cep.toUpperCase().contains(reference));
	}
}