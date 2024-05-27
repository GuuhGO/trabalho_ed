package controller.csv;

import model.cliente.BaseCliente;
import model.cliente.ClienteFisico;
import model.cliente.ClienteJuridico;
import model.cliente.Endereco;
import view.TelaEclipse;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ClienteCsvController extends BaseCsvController<BaseCliente> implements ActionListener {
	private TelaEclipse tela;
	private JTextField tfClienteNome;
	private JTextField tfClienteCpf_Cnpj;
	private JTextField tfClienteTelefone;
	private JTextField tfClienteEmail;
	private JTextField tfEndLogradouro;
	private JTextField tfEndNumero;
	private JTextField tfEndComplemento;
	private JTextField tfEndCep;
	private JComboBox<String> cbClienteTipo;

	public ClienteCsvController() {
	}

	public ClienteCsvController(TelaEclipse tela, JComboBox<String> cbClienteTipo, JTextField tfClienteNome,
			JTextField tfClienteCpf_Cnpj, JTextField tfClienteTelefone, JTextField tfClienteEmail,
			JTextField tfEndLogradouro, JTextField tfEndNumero, JTextField tfEndComplemento, JTextField tfEndCep) {
		this.tela = tela;
		this.cbClienteTipo = cbClienteTipo;
		this.tfClienteNome = tfClienteNome;
		this.tfClienteCpf_Cnpj = tfClienteCpf_Cnpj;
		this.tfClienteTelefone = tfClienteTelefone;
		this.tfClienteEmail = tfClienteEmail;
		this.tfEndLogradouro = tfEndLogradouro;
		this.tfEndNumero = tfEndNumero;
		this.tfEndComplemento = tfEndComplemento;
		this.tfEndCep = tfEndCep;
	}

	@Override
	public String getHeader() {
		return "cpf_cnpj;tipo;nome_fantasia;logradouro;numero;complemento;cep;telefone;email";
	}

	@Override
	public String getFileName() {
		return "clientes";
	}

	@Override
	public BaseCliente objectBuilder(String csvLine) throws Exception {
		String[] campos = csvLine.split(DELIMITER);
		int length = campos.length;
		if (length != 9) {
			throw new Exception("Registro Inválido");
		}

		return switch (campos[1]) {
		case "Físico" -> buildClienteFisico(campos);
		case "Jurídico" -> buildClienteJuridico(campos);
		default -> throw new Exception("Registro Inválido");
		};
	}

	private ClienteJuridico buildClienteJuridico(String[] campos) throws Exception {
		String cnpj = campos[0];
		String fantasia = campos[2];
		String telefone = campos[7];
		String email = campos[8];
		String logradouro = campos[3];
		int numero = Integer.parseInt(campos[4]);
		String complemento = campos[5];
		String cep = campos[6];

		Endereco endereco = enderecoBuilder(logradouro, numero, complemento, cep);

		return new ClienteJuridico(fantasia, cnpj, telefone, email, endereco);
	}

	private ClienteFisico buildClienteFisico(String[] campos) throws Exception {
		String cpf = campos[0];
		String nome = campos[2];
		String telefone = campos[7];
		String logradouro = campos[3];
		int numero = Integer.parseInt(campos[4]);
		String cep = campos[6];
		String complemento = campos[5];

		Endereco endereco = enderecoBuilder(logradouro, numero, complemento, cep);

		return new ClienteFisico(nome, cpf, telefone, endereco);
	}

	private Endereco enderecoBuilder(String logradouro, int numero, String complemento, String cep) throws Exception {
		logradouro = logradouro.equals("null") ? "Não Encontrado" : logradouro;
		complemento = complemento.equals("null") ? "" : complemento;
		cep = cep.equals("null") ? "Não Encontrado" : cep;
		return new Endereco(logradouro, numero, complemento, cep);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		String actionCommand = evt.getActionCommand();
		if (actionCommand.toUpperCase().equals("SALVAR")) {
			cadastrar();
		}
	}

	private void cadastrar() {
		String strFields = viewToCsv();
		clearTextFields();
		try {
			BaseCliente cli = objectBuilder(strFields);
			save(cli);
			tela.initializeTable();
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

	private String viewToCsv() {
		StringBuilder builder = new StringBuilder();

		String cpf = tfClienteCpf_Cnpj.getText();
		String tipoCliente = cbClienteTipo.getSelectedItem().toString();
		String nome = tfClienteNome.getText();
		String logradouro = tfEndLogradouro.getText();
		String txNumero = tfEndNumero.getText();
		int numero = txNumero.isEmpty() ? 0 : Integer.parseInt(txNumero);
		String complemento = tfEndComplemento.getText();
		String cep = tfEndCep.getText();
		String telefone = tfClienteTelefone.getText();
		String email = tfClienteEmail.getText();

		// Aplicando operação ternária para verificar se a string está vazia
		cpf = cpf.isEmpty() ? "null" : cpf;
		tipoCliente = tipoCliente.isEmpty() ? "null" : tipoCliente;
		nome = nome.isEmpty() ? "null" : nome;
		logradouro = logradouro.isEmpty() ? "null" : logradouro;
		complemento = complemento.isEmpty() ? "null" : complemento;
		cep = cep.isEmpty() ? "null" : cep;
		telefone = telefone.isEmpty() ? "null" : telefone;
		email = email.isEmpty() ? "null" : email;

		builder.append(cpf);
		builder.append(DELIMITER);
		builder.append(tipoCliente);
		builder.append(DELIMITER);
		builder.append(nome);
		builder.append(DELIMITER);
		builder.append(logradouro);
		builder.append(DELIMITER);
		builder.append(numero);
		builder.append(DELIMITER);
		builder.append(complemento);
		builder.append(DELIMITER);
		builder.append(cep);
		builder.append(DELIMITER);
		builder.append(telefone);
		builder.append(DELIMITER);
		builder.append(email);

		return builder.toString();
	}

	private void clearTextFields() {
		tfClienteCpf_Cnpj.setText("");
		cbClienteTipo.getSelectedItem().toString();
		tfClienteNome.setText("");
		tfEndLogradouro.setText("");
		tfEndNumero.setText("");
		tfEndComplemento.setText("");
		tfEndCep.setText("");
		tfClienteTelefone.setText("");
		tfClienteEmail.setText("");
	}
}
