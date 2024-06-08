package controller.csv;

import model.ICsv;
import model.cliente.BaseCliente;
import model.cliente.ClienteFisico;
import model.cliente.ClienteJuridico;
import model.cliente.Endereco;
import view.TelaEclipse;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import datastrucures.genericList.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ClienteCsvController extends BaseCsvController<BaseCliente> implements ActionListener {

	public ClienteCsvController(TelaEclipse tela) {
		screen = tela;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		String actionCommand = evt.getActionCommand();

		if (actionCommand.toUpperCase().equals("SALVAR")) {
			cadastrarCliente();
		}
		if (actionCommand.toUpperCase().equals("PESQUISAR")) {
			pesquisarClientes();
		}
		if (actionCommand.toUpperCase().equals("EXCLUIR")) {
			excluirCliente();
		}
		if (actionCommand.toUpperCase().equals("EDITAR")) {
			editarCliente();
		}
		if (actionCommand.toUpperCase().equals("ATUALIZAR")) {
			String cpf = screen.getTfClienteCpf_Cnpj().getText();
			excluirCliente(cpf);
			cadastrarCliente();
			screen.toggleCustomerView(true);
		}
	}

	@Override
	public String getFileName() {
		return "clientes";
	}

	@Override
	public String getHeader() {
		return "cpf_cnpj;tipo;nome_fantasia;logradouro;numero;complemento;cep;telefone;email";
	}

	@Override
	public BaseCliente objectBuilder(String csvLine) throws Exception {
		String[] campos = csvLine.split(DELIMITER);
		int length = campos.length;
		if (length != 9) {
			throw new Exception("Registro Inválido");
		}
		String cpf_cnpj = campos[0], tipo = campos[1], nome_fantasia = campos[2], telefone = campos[7],
				email = campos[8], logradouro = campos[3], complemento = campos[5], cep = campos[6];
		int numero = Integer.parseInt(campos[4]);

		Endereco endereco = new Endereco(logradouro, numero, complemento, cep);

		if (tipo.toUpperCase().equals("FÍSICO")) {
			return new ClienteFisico(nome_fantasia, cpf_cnpj, telefone, endereco);
		} else if (tipo.toUpperCase().equals("JURÍDICO")) {
			return new ClienteJuridico(nome_fantasia, cpf_cnpj, telefone, email, endereco);
		} else {
			String message = String.format("Registro \"%s\" inválido", csvLine);
			throw new Exception(message);
		}
	}

	private void cadastrarCliente() {
		try {
			BaseCliente baseCustomer = screen.getCustomerForm();
			screen.clearCustomerFields();
			save(baseCustomer);
			screen.loadCustomersTable();
		} catch (Exception err) {
			err.printStackTrace();
			JLabel lblError = screen.getLblErrorCadastro();
			screen.printError(lblError, err);
		}
	}

	private void editarCliente() {
		JTable customersTable = screen.getCustomersTable();
		int indSelectedRow = customersTable.getSelectedRow();
		if (indSelectedRow != -1) {
			screen.getLblErrorListaCliente().setText("");
			String cpf = (String) customersTable.getValueAt(indSelectedRow, 1);
			String tipo = (String) customersTable.getValueAt(indSelectedRow, 2);
			String nome_fantasia = (String) customersTable.getValueAt(indSelectedRow, 3);
			String logradouro = (String) customersTable.getValueAt(indSelectedRow, 4);
			String strNumero = (String) customersTable.getValueAt(indSelectedRow, 5);
			String complemento = (String) customersTable.getValueAt(indSelectedRow, 6);
			String cep = (String) customersTable.getValueAt(indSelectedRow, 7);
			String telefone = (String) customersTable.getValueAt(indSelectedRow, 8);
			String email = (String) customersTable.getValueAt(indSelectedRow, 9);
			String csvFields = String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s", cpf, tipo, nome_fantasia, logradouro, strNumero, complemento, cep, telefone, email);

			try {
				BaseCliente tempCli = objectBuilder(csvFields);
				screen.setCustomerForm(tempCli);
				screen.getBtnSalvarCliente().setText("ATUALIZAR");
				screen.getTfClienteCpf_Cnpj().setEnabled(false);
			} catch (Exception e) {
				e.printStackTrace();
				screen.printError(screen.getLblErrorCadastro(), e);
			}
			return;
		}
		screen.printError(screen.getLblErrorListaCliente(), new Exception("Nenhum cliente selecionado"));
	}

	private void excluirCliente() {
		JTable customersTable = screen.getCustomersTable();
		int indSelectedRow = customersTable.getSelectedRow();
		if (indSelectedRow != -1) {
			String cpf = (String) customersTable.getValueAt(indSelectedRow, 1);
			try {
				delete(cpf);
				DefaultTableModel tableModel = (DefaultTableModel) customersTable.getModel();
				tableModel.removeRow(indSelectedRow);
			} catch (IOException ioe) {
				System.out.println(ioe);
				screen.printError(screen.getLblErrorCadastro(), ioe);
			}
			return;
		}
		screen.printError(screen.getLblErrorListaCliente(), new Exception("Nenhum cliente selecionado"));

	}

	private void excluirCliente(String cpf) {
		try {
			delete(cpf);
		} catch (IOException ioe) {
			System.out.println(ioe);
			screen.printError(screen.getLblErrorCadastro(), ioe);
		}
		return;
	}

	private List<ICsv> pesquisarClientes() {
		List<ICsv> matchCustomers = new List<ICsv>();
		screen.getLblErrorListaCliente().setText("");
		try {
			String search = screen.getCustomerSearch().getText();
			if (search == null || search.isBlank()) {
				screen.loadCustomersTable();
				return null;
			}
			List<ICsv> allCustomers = get();

			int size = allCustomers.size();
			for (int i = 0; i < size; i++) {
				BaseCliente tempCli = (BaseCliente) allCustomers.get(i);
				if (tempCli.compareAllFields(search.toUpperCase())) {
					matchCustomers.addLast(tempCli);
				}
			}
			screen.carregarDados(screen.getCustomersTable(), getHeader(), matchCustomers);
			JTable tb = screen.getCustomersTable();
			tb.getColumnModel().getColumn(0).setMaxWidth(34);
		} catch (Exception err) {
			screen.printError(screen.getLblErrorCadastro(), err);
		}
		return matchCustomers;
	}
}
