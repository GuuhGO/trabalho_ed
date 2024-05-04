package model;

public class Cliente {
	private String cpf;
	private String nome;
	private Endereco endereco;
	private String telefone;

	public Cliente() {
	}

	public Cliente(String nome, String cpf, String telefone) {
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = telefone;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setEndereco(String logradouro, int numero, String complemento, String cep) {
		this.endereco = new Endereco(logradouro, numero, complemento, cep);
	}

	public String getCelular() {
		return telefone;
	}

	public void setCelular(String celular) {
		this.telefone = celular;
	}

	public String getCsv() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(cpf);
		buffer.append(";");
		buffer.append(nome);
		buffer.append(";");
		if (this.endereco != null) {
			buffer.append(endereco.toString());
		}
		buffer.append(";");
		buffer.append(telefone);
		buffer.append(";");
		
		return buffer.toString();
	}
}

class ClienteJuridico extends Cliente {
	private String cnpj;
	private String fantasia;
	private String email;

	public ClienteJuridico() {

	}
}