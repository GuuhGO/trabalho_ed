package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.naming.LimitExceededException;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import controller.csv.ClienteCsvController;
import controller.csv.ItemCompraCsvController;
import datastructures.genericQueue.Queue;
import datastructures.genericStack.Stack;
import datastrucures.genericList.List;
import model.ICsv;
import model.ItemCompra;
import model.Produto;
import model.cliente.BaseCliente;
import model.cliente.ClienteFisico;
import model.cliente.ClienteJuridico;
import view.TelaEclipse;

public class CarrinhoController implements ICsv, ActionListener {
	private static int count = 0;
	private int ID_COMPRA;
	private final List<ItemCompra> ITEM_LIST = new List<>();
	private double valorTotal;
	private BaseCliente CLIENTE;
	private ItemCompraCsvController DB_COMPRAS = new ItemCompraCsvController();
	private ClienteCsvController customerCtrl = new ClienteCsvController();
	private TelaEclipse screen;

	public CarrinhoController(TelaEclipse screen) {
		this.screen = screen;
	}

	public CarrinhoController(BaseCliente cliente, int ID_COMPRA, TelaEclipse screen) throws Exception {
		setCLIENTE(cliente);
		this.ID_COMPRA = getNextAvailableId();
		this.screen = screen;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if (actionCommand.equalsIgnoreCase("ABRIR CARRINHO")) {
			openCart();
		}
		if (actionCommand.equalsIgnoreCase("CANCELAR")) {
			cancelCart();
		}
	}

	private void cancelCart() {
		count = 0;
		JLabel lblErrorCart = screen.getLblErrorCart();
		JTextField tfSearch = screen.getTfSearchProdCart();
		JLabel tfLblCartCustomer = screen.getLblCartCustomer();
		JTextField tfCpfCarrinho = screen.getTfCpfCarrinho();
		tfSearch.setText("");
		tfCpfCarrinho.setText("");
		tfLblCartCustomer.setText("");
		lblErrorCart.setText("");
		lblErrorCart.setForeground(Color.RED);
		screen.toggleTextField(tfSearch, false);
	}
	private void openCart() {
		JLabel tfLblCartCustomer = screen.getLblCartCustomer();
		JLabel lblErrorCart = screen.getLblErrorCart();
		JTextField tfSearchProdCart = screen.getTfSearchProdCart();
		String cpf = screen.getTfCpfCarrinho().getText();
		try {
			lblErrorCart.setText("");
			lblErrorCart.setForeground(Color.GREEN);
			setCLIENTE(customerCtrl.get(cpf));
			this.ID_COMPRA = getNextAvailableId();
			lblErrorCart.setText("Cliente Encontrado");
			String nome_fantasia = "";
			if (CLIENTE.getTipoCliente().equalsIgnoreCase("Físico")) {
				ClienteFisico cf = (ClienteFisico) CLIENTE;
				nome_fantasia = cf.getNome();
			}
			if (CLIENTE.getTipoCliente().equalsIgnoreCase("Jurídico")) {
				ClienteJuridico cj = (ClienteJuridico) CLIENTE;
				nome_fantasia = cj.getFantasia();
			}
			tfLblCartCustomer.setText(nome_fantasia);
			screen.toggleTextField(tfSearchProdCart, true);
		} catch (Exception e1) {
			e1.printStackTrace();
			lblErrorCart.setForeground(Color.RED);
			tfLblCartCustomer.setText("");
			screen.printError(lblErrorCart, e1);
			screen.toggleTextField(tfSearchProdCart, false);
			count = 0;
		}
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void adicionarItem(Produto produto) throws Exception {
		if (produto.getQuantidadeEstoque() < 1) {
			throw new Exception("Produto sem estoque");
		}
		ItemCompra item = new ItemCompra(produto, ID_COMPRA, getCLIENTE());
		ITEM_LIST.addLast(item);
		valorTotal += produto.getValor();
	}

	public void removerItem(int index) throws Exception {
		if (index < 0 || index > ITEM_LIST.size()) {
			throw new Exception("Índice inválido");
		}
		ItemCompra itemCarrinho = ITEM_LIST.get(index);
		itemCarrinho.aumentarEstoque();
		ITEM_LIST.remove(index);
		valorTotal -= itemCarrinho.getPRODUTO().getValor();
	}

	public Stack<ICsv> getItensStack() throws Exception {
		int size = ITEM_LIST.size();
		Stack<ICsv> itemStack = new Stack<>();
		for (int i = 0; i < size; i++) {
			itemStack.push(ITEM_LIST.get(i));
		}
		return itemStack;
	}

	public Queue<ICsv> getItensQueue() throws Exception {
		int size = ITEM_LIST.size();
		Queue<ICsv> itemQueue = new Queue<>();
		for (int i = 0; i < size; i++) {
			itemQueue.insert(ITEM_LIST.get(i));
		}
		return itemQueue;
	}

	public int getNextAvailableId() throws Exception {
		int index = 0;
		while (true) {
			try {
				DB_COMPRAS.get(String.valueOf(index++));
			} catch (Exception e) {
				if (e.getMessage().equalsIgnoreCase("Registro não encontrado")) {
					return index; // valor disponível para criação de um novo carrinho
				}
				throw new Exception(e);
			}
		}
	}

	public void save() throws Exception {
		int size = ITEM_LIST.size();
		for (int i = 0; i < size; i++) {
			DB_COMPRAS.save(ITEM_LIST.get(i));
		}
		ProdutoRegistry.getInstance().updateData();
	}

	@Override
	public String getObjCsv() {
		int size = ITEM_LIST.size();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			try {
				sb.append("\n");
				sb.append(ITEM_LIST.get(i));
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		return sb.toString();
	}

	@Override
	public String getCsvId() {
		return String.valueOf(ID_COMPRA);
	}

	@Override
	public boolean compareAllFields(String reference) {
		return false;
	}

	@Override
	public String toString() {
		return "Carrinho: " + ID_COMPRA + " - Cliente: " + getCLIENTE().getCsvId();
	}

	public BaseCliente getCLIENTE() {
		return CLIENTE;
	}

	public void setCLIENTE(BaseCliente cLIENTE) throws LimitExceededException {
		// Regra para limitar em, no máximo, 1 instância de BaseCliente
		if (count < 1) {
			CLIENTE = cLIENTE;
			count++;
			return;
		}
		throw new LimitExceededException("Limite de 1 carrinho");
	}

}