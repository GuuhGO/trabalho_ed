package model;

public class Endereco {
	private String logradouro;
	private int numero;
	private String complemento;
	private String cep;
	
	
	public Endereco(String logradouro, int numero, String complemento, String cep) {
		super();
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.cep = cep;
	}
	
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(this.logradouro);
		buffer.append(", ");
		buffer.append(this.numero);
		buffer.append("-");
		buffer.append(this.complemento);
		buffer.append(" - ");
		buffer.append(this.cep);
		return buffer.toString();
	}
	 
}