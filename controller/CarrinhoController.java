package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.naming.LimitExceededException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.csv.ClienteCsvController;
import controller.csv.ItemCompraCsvController;
import datastructures.genericStack.Stack;
import datastructures.genericList.List;
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
	private final Stack<ItemCompra> ITEM_STACK = new Stack<>();
	private double valorTotal;
	private BaseCliente CLIENTE;
	private ItemCompraCsvController DB_COMPRAS = new ItemCompraCsvController();
	private ClienteCsvController customerCtrl = new ClienteCsvController();
	private ProdutoController produtoCtrl;
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
			openCart(e);
		}
		if (actionCommand.equalsIgnoreCase("CANCELAR")) {
			cancelCart(e);
		}
		if (actionCommand.equalsIgnoreCase("PESQUISAR")) {
			searchProdCart(e);
		}
		if (actionCommand.equalsIgnoreCase("ADICIONAR")) {
			addToCart(e);
		}
		if (actionCommand.equalsIgnoreCase("REMOVER")) {
			delFromCart(e);
		}
		if (actionCommand.equalsIgnoreCase("COMPRAR")) {
			buyCart(e);
		}
	}

	private void openCart(ActionEvent e) {
		JButton btnOpenCart = screen.getBtnOpenCart();
		JButton btnSearchProdCart = screen.getBtnSearchProdCart();
		JButton btnAddToCart = screen.getBtnAddToCart();
		JButton btnDelFromCArt = screen.getBtnDelFromCart();
		JButton btnComprar = screen.getBtnComprar();
		JLabel lblCartNum = screen.getLblCartNum();
		JLabel lblCartCustomerID = screen.getLblCartCustomerID();
		JLabel lblCartCustomerName = screen.getLblCartCustomerName();
		JLabel lblErrorCart = screen.getLblErrorCart();
		JTextField tfSearchProdCart = screen.getTfSearchProdCart();
		String cpf = screen.getTfCpfCarrinho().getText();
		try {
			produtoCtrl = ProdutoController.getInstance();

			lblErrorCart.setText("");
			lblErrorCart.setForeground(Color.GREEN);
			lblErrorCart.setText("Cliente Encontrado");
			setCLIENTE(customerCtrl.get(cpf));
			this.ID_COMPRA = getNextAvailableId();
			String nome_fantasia = "";
			if (CLIENTE.getTipoCliente().equalsIgnoreCase("Físico")) {
				ClienteFisico cf = (ClienteFisico) CLIENTE;
				nome_fantasia = cf.getNome();
			}
			if (CLIENTE.getTipoCliente().equalsIgnoreCase("Jurídico")) {
				ClienteJuridico cj = (ClienteJuridico) CLIENTE;
				nome_fantasia = cj.getNome();
			}

			btnOpenCart.setEnabled(false);
			btnSearchProdCart.setEnabled(true);
			btnAddToCart.setEnabled(true);
			btnDelFromCArt.setEnabled(true);
			btnComprar.setEnabled(true);

			updateLblText(lblCartNum, "Carrinho: " + ID_COMPRA);
			updateLblText(lblCartCustomerID, "CPF/CNPJ: " + getCLIENTE().getCsvId());
			updateLblText(lblCartCustomerName, getCLIENTE().getNome());

			screen.toggleTextField(tfSearchProdCart, true);

			fillProdCartTable();
		} catch (Exception e1) {
			e1.printStackTrace();
			lblErrorCart.setForeground(Color.RED);
			updateLblText(lblCartNum, "");
			updateLblText(lblCartCustomerID, "");
			updateLblText(lblCartCustomerName, "");
			screen.printError(lblErrorCart, e1);
			screen.toggleTextField(tfSearchProdCart, false);
		}
	}

	private void cancelCart(ActionEvent e) {
		count = 0;
		JButton btnOpenCart = screen.getBtnOpenCart();
		JButton btnSearchProdCart = screen.getBtnSearchProdCart();
		JButton btnAddToCart = screen.getBtnAddToCart();
		JButton btnDelFromCart = screen.getBtnDelFromCart();
		JButton btnComprar = screen.getBtnComprar();
		JLabel lblErrorCart = screen.getLblErrorCart();
		JLabel lblCartNum = screen.getLblCartNum();
		JLabel lblCartCustomerID = screen.getLblCartCustomerID();
		JLabel lblCartCustomerName = screen.getLblCartCustomerName();
		JLabel lblFinalPrice = screen.getLblFinalPrice();
		JTextField tfSearch = screen.getTfSearchProdCart();
		JTextField tfCpfCarrinho = screen.getTfCpfCarrinho();
		JTable tbProdCart = screen.getTbProdCart();
		JTable tbCartItems = screen.getTbCartItems();

		valorTotal = 0;
		try {
			int size = ITEM_STACK.size();
			for (int i = 0; i < size; i++) {
				ItemCompra item = ITEM_STACK.pop();
				item.aumentarEstoque();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}

		tfSearch.setText("");
		tfCpfCarrinho.setText("");
		updateLblText(lblCartNum, "");
		updateLblText(lblCartCustomerID, "");
		updateLblText(lblCartCustomerName, "");
		updateLblText(lblFinalPrice, "Total: R$");
		lblErrorCart.setText("");
		lblErrorCart.setForeground(Color.RED);

		btnOpenCart.setEnabled(true);
		btnSearchProdCart.setEnabled(false);
		btnAddToCart.setEnabled(false);
		btnDelFromCart.setEnabled(false);
		btnComprar.setEnabled(false);

		screen.toggleTextField(tfSearch, false);
		((DefaultTableModel) tbProdCart.getModel()).setRowCount(0);
		((DefaultTableModel) tbCartItems.getModel()).setRowCount(0);
	}

	private void addToCart(ActionEvent e) {
		JTable tbProdCart = screen.getTbProdCart();
		JLabel lblFinalPrice = screen.getLblFinalPrice();
		int indSelectedRow = tbProdCart.getSelectedRow();
		if (indSelectedRow != -1) {
			String[] row = getRowData(tbProdCart, indSelectedRow);
			int codProd = Integer.parseInt(row[1]);
			try {
				Produto prodCart = (Produto) produtoCtrl.get(codProd);
				adicionarItem(prodCart);
				fillCartTable();
				tbProdCart.setValueAt(prodCart.getQuantidadeEstoque(), indSelectedRow, 5); // Atualiza estoque na tabela
																							// de visualização
				String fValorTotal = String.format("Total: R$%.2f", valorTotal);
				updateLblText(lblFinalPrice, fValorTotal);
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, e1.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			}
			return;
		}
		JOptionPane.showMessageDialog(null, "Nenhum item selecionado");
	}

	private List<ICsv> searchProdCart(ActionEvent e) {
		List<ICsv> matchProducts = new List<>();
		try {
			String search = screen.getTfSearchProdCart().getText();
			if (search == null || search.isBlank()) {
				fillProdCartTable();
				return null;
			}
			List<ICsv> allProducts = produtoCtrl.get();
			int size = allProducts.size();
			for (int i = 0; i < size; i++) {
				Produto tempProd = (Produto) allProducts.get(i);
				if (tempProd.compareAllFields(search.toUpperCase())) {
					System.out.println("Produto Encontrado " + tempProd.getCodigo());
					matchProducts.addLast(tempProd);
				}
			}
			JTable tbProdCart = screen.getTbProdCart();
			screen.carregarDados(tbProdCart, produtoCtrl.getHeader(), matchProducts);
			tbProdCart.getColumnModel().getColumn(0).setMaxWidth(30);
			tbProdCart.getColumnModel().getColumn(1).setMaxWidth(46);
			tbProdCart.getColumnModel().getColumn(2).setMaxWidth(46);
			tbProdCart.getColumnModel().getColumn(3).setMinWidth(200);
			
		} catch (Exception err) {
			JOptionPane.showMessageDialog(null, err.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		return matchProducts;
	}

	private void delFromCart(ActionEvent e) {
		JTable tbCartItems = screen.getTbCartItems();
		JTable tbProdCart = screen.getTbProdCart();
		JLabel lblFinalPrice = screen.getLblFinalPrice();
		DefaultTableModel tableModel = (DefaultTableModel) tbCartItems.getModel();
		try {
			ItemCompra item = ITEM_STACK.top();
			tableModel.removeRow(0);
			removerItem();
			searchProdCart(e);
			String fValorTotal = String.format("Total: R$%.2f", valorTotal);
			updateLblText(lblFinalPrice, fValorTotal);
		} catch (Exception e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Carrinho vazio", "ERROR", JOptionPane.ERROR_MESSAGE);
		}

	}

	private void buyCart(ActionEvent e) {
		int size = ITEM_STACK.size();
		if (size > 0) {
			try {
				save();
				JOptionPane.showMessageDialog(null, "Compra Realizada com Sucesso!", "INFO",
						JOptionPane.INFORMATION_MESSAGE);
				cancelCart(e);
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
			return;
		}
		JOptionPane.showMessageDialog(null, "Carrinho Vazio", "Information", JOptionPane.INFORMATION_MESSAGE);
	}

	private String[] getRowData(JTable tbProdCart, int indSelectedRow) {
		StringBuilder row = new StringBuilder();
		int totalColumns = tbProdCart.getColumnCount();
		for (int i = 0; i < totalColumns; i++) {
			row.append(tbProdCart.getValueAt(indSelectedRow, i));
			row.append(i < totalColumns - 1 ? ";" : "");
		}
		return row.toString().split(";");
	}

	private void fillProdCartTable() throws Exception {
		JTable tbProdCart = screen.getTbProdCart();
		List<ICsv> list = produtoCtrl.get();
		screen.carregarDados(tbProdCart, produtoCtrl.getHeader(), list);
		tbProdCart.getColumnModel().getColumn(0).setMaxWidth(30);
		tbProdCart.getColumnModel().getColumn(1).setMaxWidth(46);
		tbProdCart.getColumnModel().getColumn(2).setMaxWidth(46);
		tbProdCart.getColumnModel().getColumn(3).setMinWidth(200);
	}

	private void fillCartTable() {
		JTable tbCartItems = screen.getTbCartItems();
		String vetHeader[] = produtoCtrl.getHeader().split(";");
		String finalHeader[] = { vetHeader[0], vetHeader[2], vetHeader[3] };
		DefaultTableModel tableModel = (DefaultTableModel) tbCartItems.getModel();
		int size = ITEM_STACK.size();
		ItemCompra item = ITEM_STACK.top();
		Produto prod = item.getPRODUTO();
		String[] data = { String.valueOf(size + 1), String.valueOf(prod.getCodigo()), prod.getNome(),
				String.valueOf(prod.getValor()) };
		tableModel.insertRow(0, data);
	}

	private void updateLblText(JLabel label, String newText) {
		label.setText(newText);
		label.setToolTipText(newText);
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void adicionarItem(Produto produto) throws Exception {
		if (produto.getQuantidadeEstoque() < 1) {
			String message = String.format("Produto #%d - %s sem estoque!", produto.getCodigo(), produto.getNome());
			throw new Exception(message);
		}
		ItemCompra item = new ItemCompra(produto, ID_COMPRA, getCLIENTE());
//		ITEM_LIST.addLast(item);
		ITEM_STACK.push(item);
		valorTotal += produto.getValor();
	}

	public void removerItem() throws Exception {
		ItemCompra itemCarrinho = ITEM_STACK.pop();
		itemCarrinho.aumentarEstoque();
		valorTotal -= itemCarrinho.getPRODUTO().getValor();
	}

//	public Stack<ICsv> getItensStack() throws Exception {
//		int size = ITEM_LIST.size();
//		Stack<ICsv> itemStack = new Stack<>();
//		for (int i = 0; i < size; i++) {
//			itemStack.push(ITEM_LIST.get(i));
//		}
//		return itemStack;
//	}
//
//	public Queue<ICsv> getItensQueue() throws Exception {
//		int size = ITEM_LIST.size();
//		Queue<ICsv> itemQueue = new Queue<>();
//		for (int i = 0; i < size; i++) {
//			itemQueue.insert(ITEM_LIST.get(i));
//		}
//		return itemQueue;
//	}

	public int getNextAvailableId() throws Exception {
		int index = 0;
		while (true) {
			try {
				DB_COMPRAS.get(String.valueOf(index));
				index++;
			} catch (Exception e) {
				if (e.getMessage().equalsIgnoreCase("Registro não encontrado")) {
					return index; // valor disponível para criação de um novo carrinho
				}
				throw new Exception(e);
			}
		}
	}

	public void save() throws Exception {
		int size = ITEM_STACK.size();
		for (int i = 0; i < size; i++) {
			ItemCompra item = ITEM_STACK.pop();
			Produto prod = item.getPRODUTO();
			DB_COMPRAS.save(item);
			produtoCtrl.edit(prod, prod);
		}
		ProdutoController.getInstance().updateData();
	}

	@Override
	public String getObjCsv() {
		int size = ITEM_STACK.size();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			try {
				sb.append("\n");
				sb.append(ITEM_STACK.pop());
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
		return String.format("Carrinho: %d", ID_COMPRA);
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