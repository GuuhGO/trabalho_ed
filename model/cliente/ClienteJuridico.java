package model.cliente;


public class ClienteJuridico extends BaseCliente {
	private String cnpj;
	private String fantasia;
	private String email;

	public ClienteJuridico(String fantasia, String cnpj, String telefone, String email, Endereco endereco) throws Exception{
		setTipoCliente("Jurídico");
		setCnpj(cnpj);
		setFantasia(fantasia);
		setTelefone(telefone);
		setEmail(email);
		this.endereco = endereco;
	}

	public String getCnpj() {
		return this.cnpj;
	}

	public void setCnpj(String cnpj) throws Exception {
		if (cnpj.isEmpty() || cnpj.equals("null")) {
			throw new Exception("Campo 'cnpj' não pode ser vazio");
		}
		this.cnpj = cnpj;
	}

	public String getFantasia() {
		return this.fantasia;
	}

	public void setFantasia(String fantasia) throws Exception {
		if (fantasia.isEmpty() || fantasia.equals("null")) {
			throw new Exception("Campo 'fantasia' não pode ser vazio");
		}
		this.fantasia = fantasia;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws Exception {
		if (email.isEmpty() || email.equals("null")) {
			throw new Exception("Campo email não pode ser vazio!");
		}
		this.email = email;
	}

	@Override
	public String getObjCsv() {
		//cpf/cnpj;tipo;nome/fantasia;logradouro;numero;complemento;cep;telefone;email
		String delimiter = ";";
		StringBuilder buffer = new StringBuilder(cnpj + delimiter + getTipoCliente() + delimiter + fantasia + delimiter);
		if (this.getEndereco() != null) {
			buffer.append(getEndereco().getObjCsv());
			buffer.append(delimiter);
		} else {
			String temp = "null" + delimiter;
			buffer.append(temp.repeat(4));
		}
		buffer.append(getTelefone());
		buffer.append(delimiter);
		buffer.append(email);
		return buffer.toString();
	}

}