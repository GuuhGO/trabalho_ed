package model;

public class Cliente {
	private String cpf;
	private String nome;
	private Endereco endereco;
	private String telefone;
	private final String tipoCliente = "Físico";
	public Cliente() {
	}

	public Cliente(String nome, String cpf, String telefone) {
		setNome(nome);
		setCpf(cpf);
		setTelefone(telefone);
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		if (!cpf.equals("")) {
			this.cpf = cpf;
			return;
		}
		System.out.println("Parametro 'cpf' não pode ser vazio");
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		if (!nome.equals("")) {
			this.nome = nome;
			return;
		}
		System.out.println("Parâmetro 'nome' não pode ser vazio");
	}

	public void setEndereco(String logradouro, int numero, String cep) throws Exception {
		this.endereco = new Endereco(logradouro, numero, "", cep);
	}

	public void setEndereco(String logradouro, int numero, String complemento, String cep) throws Exception {
		this.endereco = new Endereco(logradouro, numero, complemento, cep);
	}

	public Endereco getEndereco() {
		return this.endereco;
	}
	
	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String celular) {
		this.telefone = celular;
	}

	public String getObjCsv() {
		StringBuffer buffer = new StringBuffer();
		//cpf/cnpj;tipo;nome/fantasia;endereco;telefone;email
		buffer.append(cpf);
		buffer.append(";");
		buffer.append(tipoCliente);
		buffer.append(";");
		buffer.append(nome);
		buffer.append(";");
		if (this.endereco != null)
			buffer.append(endereco.toString());
		buffer.append(";");
		buffer.append(telefone);
		buffer.append(";");
		return buffer.toString();
	}

}