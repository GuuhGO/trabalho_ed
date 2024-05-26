package model.cliente;

import model.BaseCsvWriter;
import model.ICsv;

import java.io.*;

public abstract class BaseCliente extends BaseCsvWriter<BaseCliente> implements ICsv {
	protected Endereco endereco;
	private String telefone;
	private String tipoCliente;

	@Override
	public String getFileName() {
		return "cliente";
	}

	@Override
	public String getHeader() {
		return "cpf_cnpj;tipoCliente;nome_fantasia;logradouro;numero;complemento;cep;telefone;email";
	}

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


	@Override
	public void save() throws IOException {
		File dir = new File(DIR_PATH);
		File arq = new File(DIR_PATH, getFileName()+".csv");
		if(dir.exists() && dir.isDirectory()) {
			if(!arq.exists()) {
				createFile();
			}

			FileWriter fw = new FileWriter(arq, true);
			PrintWriter pw = new PrintWriter(fw);
			pw.write("\n");
			pw.write(this.getObjCsv());
			pw.flush();
			pw.close();
			fw.close();
		}
		else {
			throw new IOException("Diretório inválido");
		}
	}

	@Override
	public void delete() throws IOException {
		File file = new File(DIR_PATH, getFileName()+".csv");
		File tempFile = new File(DIR_PATH, "temp" + getFileName()+".csv");

		if(file.exists() && file.isFile()) {
			FileInputStream stream = new FileInputStream(file);
			InputStreamReader reader = new InputStreamReader(stream);
			BufferedReader buffer = new BufferedReader(reader);

			FileWriter fw = new FileWriter(tempFile);
			PrintWriter pw = new PrintWriter(fw);

			String lineToRemove = this.getObjCsv();

			String currentLine;
			while((currentLine = buffer.readLine()) != null) {
				if(currentLine.equals(lineToRemove)) continue;
				if(!currentLine.isEmpty()) {
					pw.println(currentLine);
					pw.flush();
				}
			}
			pw.close();
			fw.close();
			reader.close();
			buffer.close();

			if(!file.delete()) {
				throw new IOException("Não foi possível apagar o arquivo");
			}

			if(!tempFile.renameTo(file)){
				throw new IOException("Não foi possível renomear o arquivo");
			}
		}
		else {
			throw new IOException("Arquivo inválido");
		}
	}

	@Override
	public BaseCliente objectBuilder(String csv) throws Exception {
		String[] campos = csv.split(";");
		int length = campos.length;
		if(length != 9) {
			throw new Exception("Registro Inválido");
		}

		if(campos[1].equals("Físico")) {
			String cpf = campos[0];
			String nome = campos[2];
			String telefone = campos[7];

			String logradouro = campos[3];
			int numero = Integer.parseInt(campos[4]);
			String complemento = campos[5].equals("null") ? "" : campos[5];
			String cep = campos[6];

			Endereco endereco = new Endereco(logradouro, numero, complemento, cep);

			return new ClienteFisico(nome, cpf, telefone, endereco);
		} else if(campos[1].equals("Jurídico")) {
			String cnpj = campos[0];
			String fantasia = campos[2];
			String telefone = campos[7];
			String email = campos[8];

			String logradouro = campos[3];
			int numero = Integer.parseInt(campos[4]);
			String complemento = campos[5].equals("null") ? "" : campos[5];
			String cep = campos[6];

			Endereco endereco = new Endereco(logradouro, numero, complemento, cep);

			return new ClienteJuridico(fantasia, cnpj, telefone, email, endereco);
		}
		else {
			throw new Exception("Registro Inválido");
		}
	}
}