package model;

public class ClienteJuridico extends Cliente {
	private String cnpj;
	private String fantasia;
	private String email;
	private final String tipoCliente = "Jurídico";
	public ClienteJuridico() {
	}

	public ClienteJuridico(String cnpj, String fantasia, String telefone, String email) {
		setCnpj(cnpj);
		setFantasia(fantasia);
		setTelefone(telefone);
		setEmail(email);
	}

	public ClienteJuridico(String cnpj, String fantasia, String logradouro, String complemento, int numero, String cep,
			String telefone, String email) throws Exception {
		setCnpj(cnpj);
		setFantasia(fantasia);
		setTelefone(telefone);
		setEmail(email);
		setEndereco(logradouro, numero, complemento, cep);
	}

	public ClienteJuridico(String cnpj, String fantasia, String logradouro, int numero, String cep, String telefone,
			String email) throws Exception {
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

	@Override
	public String getObjCsv() {
		StringBuffer buffer = new StringBuffer();
		//cpf/cnpj;tipo;nome/fantasia;endereco;telefone;email
		buffer.append(cnpj);
		buffer.append(";");
		buffer.append(tipoCliente);
		buffer.append(";");
		buffer.append(fantasia);
		buffer.append(";");
		if (super.getEndereco() != null)
			buffer.append(super.getEndereco().toString());
		buffer.append(";");
		buffer.append(super.getTelefone());
		buffer.append(";");
		buffer.append(getEmail());
		return buffer.toString();
	}

}