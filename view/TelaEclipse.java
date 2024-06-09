package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import controller.CarrinhoController;
import controller.ComprasController;
import controller.ProdutoController;
import controller.TipoController;
import controller.csv.ClienteCsvController;
import controller.csv.ItemCompraCsvController;
import datastructures.genericList.List;
import model.ICsv;
import model.Produto;
import model.Tipo;
import model.cliente.BaseCliente;
import model.cliente.ClienteFisico;
import model.cliente.ClienteJuridico;
import model.cliente.Endereco;

public class TelaEclipse extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaEclipse frame = new TelaEclipse();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	};

	private JButton btnCancelar;
	private JButton btnCancelarCadCliente;
	private JButton btnCancelarTipoCadastro;
	private JButton btnCancelCart;
	private JButton btnEditarCliente;
	private JButton btnEditarProduto;
	private JButton btnEditarTipo;
	private JButton btnExcluiProduto;
	private JButton btnExcluir;
	private JButton btnExcluirCliente;
	private JButton btnExcluiTipo;
	private JButton btnFiltraProduto;
	private JButton btnNovoCliente;
	private JButton btnNovoProduto;
	private JButton btnNovoTipo;
	private JButton btnOpenCart;
	private JButton btnPesquisaProduto;
	private JButton btnPesquisar;
	private JButton btnPesquisarCliente;
	private JButton btnPesquisaTipo;
	private JButton btnSalvar;
	private JButton btnSalvarCliente;
	private JButton btnSalvarProduto;
	private JButton btnSalvarTipoCadastro;
	private JButton btnSearchProdCart;
	private JPanel cadastroClientes;
	private JPanel cadastroProduto;
	private JPanel cadastroTipo;
	private CarrinhoController cartCtrl = new CarrinhoController(this);
	private JComboBox<String> cbClienteTipo;
	private JComboBox<String> cbListaTipo;
	private JPanel contentPane;
	private ClienteCsvController customerCtrl = new ClienteCsvController(this);
	private JLayeredPane layerCadastroProduto;
	private JLayeredPane layerCadastroTipo;
	private JLabel lblBuscaProduto;
	private JLabel lblBuscaTipo;
	private JLabel lblCadastrarCliente;
	private JLabel lblCadastrarProduto;
	private JLabel lblCadastrarTipo;
	private JLabel lblCartNum;
	private JLabel lblClienteCpf_Cnpj;
	private JLabel lblClienteEmail;
	private JLabel lblClienteNome;
	private JLabel lblClienteTelefone;
	private JLabel lblClienteTipo;
	private JLabel lblCodigo;
	private JLabel lblCodigoProduto;
	private JLabel lblCpfCarrinho;
	private JLabel lblDescricao;
	private JLabel lblEndCep;
	private JLabel lblEnderecoComplemento;
	private JLabel lblEnderecoLogradouro;
	private JLabel lblEnderecoNumero;
	private JLabel lblErrorCadastroCliente;
	private JLabel lblErrorCart;
	private JLabel lblErrorListaCliente;
	private JLabel lblNomeProduto;
	private JLabel lblNomeTipo;
	private JLabel lblSearch;
	private JLabel lblTituloCarrinho;
	private JLabel lblTituloClientes;
	private JLabel lblTituloProdutos;
	private JLabel lblTituloTipos;
	private JPanel listaClientes;
	private JPanel listaProdutos;
	private JPanel listaTipos;
	private JPanel pnCarrinho;
	private JScrollPane scrollPane;
	private JScrollPane scrollPaneProdutos;
	private JScrollPane scrollPaneTipos;
	private JScrollPane spCartItems;
	private JScrollPane spProdTable;
	private JTabbedPane tabbedPane;
	private JPanel tabCarrinho = new JPanel();
	private JPanel tabCliente = new JPanel();
	private JTable tableCliente;
	private JTable tableProduto;
	private JTable tableTipos;
	private JPanel tabProdutos = new JPanel();
	private JPanel tabTipos = new JPanel();
	private JTextArea taDescricaoTipo;
	private JTable tbCartItems;
	private JTable tbProdCart;
	private JTextField tfBusca;
	private JTextField tfBuscaProduto;
	private JTextField tfBuscaTipo;
	private JTextField tfClienteCpf_Cnpj;
	private JTextField tfClienteEmail;
	private JTextField tfClienteNome;
	private JTextField tfClienteTelefone;
	private JTextField tfCodigoProduto;
	private JTextField tfCodigoTipo;
	private JTextField tfCpfCarrinho;
	private JTextField tfEndCep;
	private JTextField tfEndComplemento;
	private JTextField tfEndLogradouro;
	private JTextField tfEndNumero;
	private JTextField tfNomeProduto;
	private JTextField tfNomeTipo;
	private JTextField tfQuantidadeProduto;
	private JTextField tfSearch;
	private JTextField tfSearchProdCart;
	private JTextField tfValorProduto;
	private JButton btnComprar;
	private JLabel lblCartCustomerID;
	private JLabel lblCartCustomerName;
	private JButton btnAddToCart;

	public TelaEclipse() {

        setTitle("Loja");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 740, 400);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(0, 0, 727, 363);
        tabbedPane.addTab("Clientes", null, tabCliente, "Cliente");
        tabbedPane.addTab("Tipos", null, tabTipos, "Tipo");
        tabbedPane.addTab("Produtos", null, tabProdutos, "Produto");
		tabbedPane.addTab("Carrinho", null, tabCarrinho, "Carrinho");
		tabbedPane.addTab("Vendas", null, tabCompras, "Vendas");
        tabbedPane.addChangeListener(this::updateResolution);
        contentPane.add(tabbedPane);

        tabCliente.setLayout(null);
        tabTipos.setLayout(null);
        tabProdutos.setLayout(null);
		tabCarrinho.setLayout(null);
		tabCompras.setLayout(null);

        initListaClientes();
        initCadastroClientes();
        initListaTipos();
        initCadastroTipos();
        initListaProdutos();
        initCadastroProduto();
		initCarrinho();
		initListaVendas();

        
        loadCustomersTable();
        loadTypeTable();
        loadProductTable();
        loadProdCartTable();
        loadCartItemsTable();
		loadSalesTable();
    }

	public void carregarDados(JTable table, String csvHeader, List<ICsv> list) {
		String[] columnNames = ("#;" + csvHeader).split(";");
		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		int tamanho = list.size();
		for (int i = 0; i < tamanho; i++) {
			try {
				ICsv data = list.get(i);
				if (data != null) {
					String[] campos = ((i + 1) + ";" + data.getObjCsv()).split(";");
					tableModel.addRow(campos);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		table.setModel(tableModel);
	}

	public void loadTypeTable() {
		List<ICsv> listTipos;
		String csvHeader;
		try {
			TipoController registry = TipoController.getInstance();
			listTipos = registry.get();
			csvHeader = registry.getHeader();
			carregarDados(tableTipos, csvHeader, listTipos);
			tableTipos.getColumnModel().getColumn(0).setMaxWidth(26);
			tableTipos.getColumnModel().getColumn(1).setMaxWidth(46);
		} catch (Exception e) {
			e.printStackTrace();
			printError(lblErrorCadastroCliente, e);
		}
	}

	public void clearCustomerFields() {
		tfClienteCpf_Cnpj.setText("");
		tfClienteNome.setText("");
		tfEndLogradouro.setText("");
		tfEndNumero.setText("");
		tfEndComplemento.setText("");
		tfEndCep.setText("");
		tfClienteTelefone.setText("");
		tfClienteEmail.setText("");
		lblErrorCadastroCliente.setText("");
	}

	public JButton getBtnSalvarCliente() {
		return btnSalvarCliente;
	}

	public BaseCliente getCustomerForm() throws Exception {
		String tipo = cbClienteTipo.getSelectedItem().toString();
		String nome_fantasia = tfClienteNome.getText();
		String cpf_cnpj = tfClienteCpf_Cnpj.getText();
		String telefone = tfClienteTelefone.getText();
		String email = tfClienteEmail.getText();
		String engLogradouro = tfEndLogradouro.getText();
		int endNumero = tfEndNumero.getText().isEmpty() ? 0 : Integer.parseInt(tfEndNumero.getText());
		String endComplemento = tfEndComplemento.getText();
		String endCep = tfEndCep.getText();

		Endereco end;
		end = new Endereco(engLogradouro, endNumero, endComplemento, endCep);
		if (tipo.toUpperCase().equals("FÍSICO")) {
			ClienteFisico cf = new ClienteFisico(nome_fantasia, cpf_cnpj, telefone, end);
			return cf;
		}
		if (tipo.toUpperCase().equals("JURÍDICO")) {
			ClienteJuridico cj = new ClienteJuridico(nome_fantasia, cpf_cnpj, telefone, email, end);
			return cj;
		}
		throw new IllegalArgumentException("Tipo do cliente deve ser \"Físico\" ou \"Jurídico\"");
	}


	public JTextField getCustomerSearch() {
		return tfSearch;
	}

	public JTable getCustomersTable() {
		return tableCliente;
	}


	public JLabel getLblErrorCadastro() {
		return lblErrorCadastroCliente;
	}

	public JLabel getLblErrorCart() {
		return lblErrorCart;
	}

	public JLabel getLblErrorListaCliente() {
		return lblErrorListaCliente;
	}

	public JTextField getTfClienteCpf_Cnpj() {
		return tfClienteCpf_Cnpj;
	}

	public JTextField getTfCpfCarrinho() {
		return tfCpfCarrinho;
	}

	public JTextField getTfSearchProdCart() {
		return tfSearchProdCart;
	}

	public void loadCustomersTable() {
		List<ICsv> listClientes;
		try {
			listClientes = customerCtrl.get();
			JTable tb = getCustomersTable();
			carregarDados(tb, customerCtrl.getHeader(), listClientes);
			tb.getColumnModel().getColumn(0).setMaxWidth(34);
		} catch (IOException e) {
			e.printStackTrace();
			printError(lblErrorCadastroCliente, e);
		}
	}

	public void pesquisarTipo(String codigo) {
		if (codigo == null || codigo.isBlank()) {
			loadTypeTable();
			return;
		}
		try {
			Integer.parseInt(codigo);
		} catch (NumberFormatException e) {
			return;
		}
		List<ICsv> listTipos = new List<>();
		String csvHeader;
		try {
			TipoController registry = TipoController.getInstance();
			ICsv item = registry.get(codigo);
			listTipos.addLast(item);
			csvHeader = registry.getHeader();
			carregarDados(tableTipos, csvHeader, listTipos);
			tableTipos.getColumnModel().getColumn(0).setMaxWidth(26);
			tableTipos.getColumnModel().getColumn(1).setMaxWidth(46);
		} catch (Exception e) {
			e.printStackTrace();
			printError(lblErrorCadastroCliente, e);
		}
	}

	public void printError(JLabel lblError, Exception err) {
		lblError.setText(err.getMessage());
	}

	public void setCustomerForm(BaseCliente customer) {

		if (customer.getTipoCliente().toUpperCase().equals("JURÍDICO")) {
			ClienteJuridico tempCj = (ClienteJuridico) customer;
			cbClienteTipo.setSelectedIndex(1);
			tfClienteCpf_Cnpj.setText(tempCj.getCnpj().replace("null", ""));
			tfClienteNome.setText(tempCj.getNome().replace("null", ""));
			tfClienteEmail.setText(tempCj.getEmail().replace("null", ""));
		}
		if (customer.getTipoCliente().toUpperCase().equals("FÍSICO")) {
			ClienteFisico tempCf = (ClienteFisico) customer;
			cbClienteTipo.setSelectedIndex(0);
			tfClienteCpf_Cnpj.setText(tempCf.getCpf().replace("null", ""));
			tfClienteNome.setText(tempCf.getNome().replace("null", ""));
		}
		Endereco tempEnd = customer.getEndereco();
		tfEndLogradouro.setText(tempEnd.getLogradouro().replace("null", ""));
		tfEndNumero.setText(String.valueOf(tempEnd.getNumero()).replace("null", ""));
		tfEndComplemento.setText(tempEnd.getComplemento().replace("null", ""));
		tfEndCep.setText(tempEnd.getCep().replace("null", ""));
		tfClienteTelefone.setText(customer.getTelefone().replace("null", ""));

		lblErrorCadastroCliente.setText("");

	}

	public void toggleTextField(JTextField targetObject, boolean enabled) {
		Color bg = enabled ? Color.WHITE : Color.LIGHT_GRAY;
		tfSearchProdCart.setEnabled(enabled);
		tfSearchProdCart.setBackground(bg);
	}

	private void excluirTipo() {
		int selectedRow = tableTipos.getSelectedRow();
		TableModel model = tableTipos.getModel();
		String codigoTipo = (String) model.getValueAt(selectedRow, 1);
		try {
			int id = Integer.parseInt(codigoTipo);
			TipoController instance = TipoController.getInstance();
			instance.remove(id);
		} catch (NumberFormatException error) {
		} catch (Exception errorGeral) {
		}
	}

	private void initCadastroClientes() {
		// Tela de Cadastro de Clientes
		cadastroClientes = new JPanel();
		cadastroClientes.setVisible(false);
		cadastroClientes.setBounds(0, 0, 722, 336);
		cadastroClientes.setLayout(null);

		// Título da Tela de Cadastro
		lblCadastrarCliente = new JLabel("CADASTRAR CLIENTE");
		lblCadastrarCliente.setHorizontalAlignment(SwingConstants.LEFT);
		lblCadastrarCliente.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCadastrarCliente.setBounds(213, 3, 194, 38);
		cadastroClientes.add(lblCadastrarCliente);

		// Label do Nome
		lblClienteNome = new JLabel("Nome");
		lblClienteNome.setHorizontalAlignment(SwingConstants.LEFT);
		lblClienteNome.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblClienteNome.setBounds(165, 51, 60, 38);
		cadastroClientes.add(lblClienteNome);

		// Campo do Nome do Cliente
		tfClienteNome = new JTextField();
		tfClienteNome.setBounds(232, 58, 223, 24);
		tfClienteNome.setColumns(10);
		cadastroClientes.add(tfClienteNome);

		// Label do CPF/CNPJ
		lblClienteCpf_Cnpj = new JLabel("CPF/CNPJ");
		lblClienteCpf_Cnpj.setHorizontalAlignment(SwingConstants.LEFT);
		lblClienteCpf_Cnpj.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblClienteCpf_Cnpj.setBounds(470, 51, 86, 38);
		cadastroClientes.add(lblClienteCpf_Cnpj);

		// Campo do CPF/CNPJ
		tfClienteCpf_Cnpj = new JTextField();
		tfClienteCpf_Cnpj.setColumns(10);
		tfClienteCpf_Cnpj.setBounds(566, 58, 139, 24);
		cadastroClientes.add(tfClienteCpf_Cnpj);

		// Label do Telefone
		lblClienteTelefone = new JLabel("Telefone");
		lblClienteTelefone.setHorizontalAlignment(SwingConstants.LEFT);
		lblClienteTelefone.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblClienteTelefone.setBounds(12, 113, 86, 38);
		cadastroClientes.add(lblClienteTelefone);

		// Campo do Telefone
		tfClienteTelefone = new JTextField();
		tfClienteTelefone.setColumns(10);
		tfClienteTelefone.setBounds(118, 120, 190, 24);
		cadastroClientes.add(tfClienteTelefone);

		// Label do Email
		lblClienteEmail = new JLabel("Email");
		lblClienteEmail.setHorizontalAlignment(SwingConstants.LEFT);
		lblClienteEmail.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblClienteEmail.setBounds(388, 113, 60, 38);
		cadastroClientes.add(lblClienteEmail);

		// Campo do Email
		tfClienteEmail = new JTextField();
		tfClienteEmail.setBounds(470, 120, 203, 24);
		tfClienteEmail.setEnabled(false);
		tfClienteEmail.setBackground(Color.LIGHT_GRAY);
		tfClienteEmail.setColumns(10);
		cadastroClientes.add(tfClienteEmail);

		// Label do Tipo do Cliente
		lblClienteTipo = new JLabel("Tipo");
		lblClienteTipo.setHorizontalAlignment(SwingConstants.LEFT);
		lblClienteTipo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblClienteTipo.setBounds(12, 51, 44, 38);
		cadastroClientes.add(lblClienteTipo);

		// ComboBox para escolher o Tipo do Cliente
		cbClienteTipo = new JComboBox<String>();
		cbClienteTipo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cbClienteTipo.setModel(new DefaultComboBoxModel<>(new String[] { "Físico", "Jurídico" }));
		cbClienteTipo.setBounds(65, 57, 86, 26);
		cbClienteTipo.addActionListener(this::toggleEmailField);
		cadastroClientes.add(cbClienteTipo);

		// Label do Logradouro
		lblEnderecoLogradouro = new JLabel("Logradouro");
		lblEnderecoLogradouro.setHorizontalAlignment(SwingConstants.LEFT);
		lblEnderecoLogradouro.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblEnderecoLogradouro.setBounds(12, 179, 102, 38);
		cadastroClientes.add(lblEnderecoLogradouro);

		// Campo do Logradouro
		tfEndLogradouro = new JTextField();
		tfEndLogradouro.setBounds(122, 186, 190, 24);
		tfEndLogradouro.setColumns(10);
		cadastroClientes.add(tfEndLogradouro);

		// Label do Número
		lblEnderecoNumero = new JLabel("Nº");
		lblEnderecoNumero.setHorizontalAlignment(SwingConstants.LEFT);
		lblEnderecoNumero.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblEnderecoNumero.setBounds(364, 179, 29, 38);
		cadastroClientes.add(lblEnderecoNumero);

		// Campo do Número
		tfEndNumero = new JTextField();
		tfEndNumero.setColumns(10);
		tfEndNumero.setBounds(403, 186, 37, 24);
		cadastroClientes.add(tfEndNumero);

		// Label do Complemento
		lblEnderecoComplemento = new JLabel("Complemento");
		lblEnderecoComplemento.setHorizontalAlignment(SwingConstants.LEFT);
		lblEnderecoComplemento.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblEnderecoComplemento.setBounds(481, 179, 127, 38);
		cadastroClientes.add(lblEnderecoComplemento);

		// Campo do Complemento
		tfEndComplemento = new JTextField();
		tfEndComplemento.setColumns(10);
		tfEndComplemento.setBounds(634, 186, 60, 24);
		cadastroClientes.add(tfEndComplemento);

		// Label do CEP
		lblEndCep = new JLabel("CEP");
		lblEndCep.setHorizontalAlignment(SwingConstants.LEFT);
		lblEndCep.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblEndCep.setBounds(12, 236, 50, 38);
		cadastroClientes.add(lblEndCep);

		// Campo do CEP
		tfEndCep = new JTextField();
		tfEndCep.setColumns(10);
		tfEndCep.setBounds(120, 243, 190, 24);
		cadastroClientes.add(tfEndCep);

	// Botão para Salvar novo Cliente
		btnSalvarCliente = new JButton("SALVAR");
		getBtnSalvarCliente().setFont(new Font("Tahoma", Font.BOLD, 12));
		getBtnSalvarCliente().setForeground(new Color(0, 128, 0));
		getBtnSalvarCliente().setBounds(590, 290, 110, 29);
		cadastroClientes.add(getBtnSalvarCliente());

		btnCancelarCadCliente = new JButton("CANCELAR");
		btnCancelarCadCliente.addActionListener(e -> {
			clearCustomerFields();
			tfClienteCpf_Cnpj.setEnabled(true);
			btnSalvarCliente.setText("SALVAR");
			toggleCustomerView(true);
		});
		btnCancelarCadCliente.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCancelarCadCliente.setForeground(new Color(255, 0, 0));
		btnCancelarCadCliente.setBounds(470, 290, 110, 29);
		cadastroClientes.add(btnCancelarCadCliente);

		getBtnSalvarCliente().addActionListener(customerCtrl);

		tabCliente.add(cadastroClientes);

		lblErrorCadastroCliente = new JLabel("");
		getLblErrorCadastro().setHorizontalAlignment(SwingConstants.LEFT);
		getLblErrorCadastro().setForeground(new Color(255, 0, 0));
		getLblErrorCadastro().setFont(new Font("Tahoma", Font.PLAIN, 10));
		getLblErrorCadastro().setBounds(12, 284, 381, 35);
		cadastroClientes.add(getLblErrorCadastro());
	}

	private void initCadastroTipos() {
		cadastroTipo = new JPanel();
		cadastroTipo.setBounds(0, 0, 621, 336);
		cadastroTipo.setLayout(null);

		layerCadastroTipo = new JLayeredPane();
		layerCadastroTipo.setBounds(0, 0, 621, 278);

		cadastroTipo.setVisible(false);

		lblCadastrarTipo = new JLabel("CADASTRAR TIPO");
		lblCadastrarTipo.setHorizontalAlignment(SwingConstants.LEFT);
		lblCadastrarTipo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCadastrarTipo.setBounds(213, 3, 194, 38);

		lblCodigo = new JLabel("Código:");
		lblCodigo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCodigo.setHorizontalAlignment(SwingConstants.LEFT);
		lblCodigo.setBounds(10, 58, 75, 38);

		tfCodigoTipo = new JTextField();
		tfCodigoTipo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tfCodigoTipo.setEditable(false);
		tfCodigoTipo.setEnabled(false);
		tfCodigoTipo.setBounds(95, 65, 61, 24);
		tfCodigoTipo.setColumns(10);

		lblNomeTipo = new JLabel("Nome:");
		lblNomeTipo.setHorizontalAlignment(SwingConstants.LEFT);
		lblNomeTipo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNomeTipo.setBounds(207, 58, 75, 38);

		tfNomeTipo = new JTextField();
		tfNomeTipo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tfNomeTipo.setColumns(10);
		tfNomeTipo.setBounds(292, 65, 319, 24);

		lblDescricao = new JLabel("Descrição:");
		lblDescricao.setHorizontalAlignment(SwingConstants.LEFT);
		lblDescricao.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDescricao.setBounds(10, 107, 101, 38);

		taDescricaoTipo = new JTextArea();
		taDescricaoTipo.setLineWrap(true);
		taDescricaoTipo.setBorder(new LineBorder(new Color(0, 0, 0)));
		taDescricaoTipo.setBounds(10, 156, 601, 111);

		btnSalvarTipoCadastro = new JButton("SALVAR");
		btnSalvarTipoCadastro.setForeground(new Color(0, 128, 0));
		btnSalvarTipoCadastro.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSalvarTipoCadastro.setBounds(511, 288, 87, 29);
		btnSalvarTipoCadastro.addActionListener(tipoCtrl);
		btnSalvarTipoCadastro.addActionListener(e -> {
			btnSalvarTipoCadastro.setActionCommand("SALVAR");
			cbListaTipo.setModel(criarComboBoxTipos(true));
			cbTipoProduto.setModel(criarComboBoxTipos(false));
		});

		btnCancelarTipoCadastro = new JButton("CANCELAR");
		btnCancelarTipoCadastro.setForeground(Color.RED);
		btnCancelarTipoCadastro.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancelarTipoCadastro.setBounds(394, 288, 107, 29);
		btnCancelarTipoCadastro.addActionListener(e -> {
			btnSalvarTipoCadastro.setActionCommand("SALVAR");
			clearTypeFields();
			toggleTypeView(true);
		});


		layerCadastroTipo.add(lblCadastrarTipo);
		layerCadastroTipo.add(lblCodigo);
		layerCadastroTipo.add(tfCodigoTipo);
		layerCadastroTipo.add(lblNomeTipo);
		layerCadastroTipo.add(tfNomeTipo);
		layerCadastroTipo.add(lblDescricao);
		layerCadastroTipo.add(taDescricaoTipo);

		clearTypeFields();

		cadastroTipo.add(layerCadastroTipo);

		cadastroTipo.add(btnSalvarTipoCadastro);
		cadastroTipo.add(btnCancelarTipoCadastro);

		tabTipos.add(cadastroTipo);
	}
	private void initCarrinho() {
		pnCarrinho = new JPanel();
		pnCarrinho.setLayout(null);
		pnCarrinho.setBounds(0, 0, 722, 336);
		tabCarrinho.add(pnCarrinho);

//		lblTituloCarrinho = new JLabel("CARRINHO");
//		lblTituloCarrinho.setHorizontalAlignment(SwingConstants.CENTER);
//		lblTituloCarrinho.setFont(new Font("Tahoma", Font.PLAIN, 20));
//		lblTituloCarrinho.setBounds(254, 10, 178, 30);
//		pnCarrinho.add(lblTituloCarrinho);

		lblCpfCarrinho = new JLabel("Informar CPF");
		lblCpfCarrinho.setBounds(10, 19, 150, 21);
		pnCarrinho.add(lblCpfCarrinho);

		tfCpfCarrinho = new JTextField();
		lblCpfCarrinho.setLabelFor(tfCpfCarrinho);
		tfCpfCarrinho.setToolTipText("Informar CPF");
		tfCpfCarrinho.setColumns(10);
		tfCpfCarrinho.setBounds(10, 49, 118, 19);
		pnCarrinho.add(getTfCpfCarrinho());

		btnOpenCart = new JButton("Abrir Carrinho");
		btnOpenCart.setMargin(new Insets(2, 2, 2, 2));
		btnOpenCart.setBounds(10, 79, 90, 21);
		btnOpenCart.addActionListener(cartCtrl);
		pnCarrinho.add(btnOpenCart);

//		btnCancelCart = new JButton("Cancelar");
//		btnCancelCart.setMargin(new Insets(2, 2, 2, 2));
//		btnCancelCart.setBounds(105, 79, 60, 21);
//		pnCarrinho.add(btnCancelCart);

		tfSearchProdCart = new JTextField();
		toggleTextField(tfSearchProdCart, false);
		tfSearchProdCart.setToolTipText("Pesquisar produto");
		tfSearchProdCart.setColumns(10);
		tfSearchProdCart.setBounds(10, 129, 150, 21);
		pnCarrinho.add(tfSearchProdCart);


		spProdTable = new JScrollPane();
		spProdTable.setBounds(10, 160, 438, 166);
		pnCarrinho.add(spProdTable);

		tbProdCart = new JTable();
		spProdTable.setViewportView(tbProdCart);

		btnSearchProdCart = new JButton("Pesquisar");
		btnSearchProdCart.setBounds(170, 129, 70, 21);
		btnSearchProdCart.setMargin(new Insets(2, 2, 2, 2));
		btnSearchProdCart.setEnabled(false);
		btnSearchProdCart.addActionListener(cartCtrl);
		pnCarrinho.add(btnSearchProdCart);

		spCartItems = new JScrollPane();
		spCartItems.setBounds(467, 50, 245, 247);
		pnCarrinho.add(spCartItems);

		tbCartItems = new JTable();
		spCartItems.setViewportView(tbCartItems);

		lblErrorCart = new JLabel("");
		lblErrorCart.setForeground(new Color(255, 0, 0));
		lblErrorCart.setBounds(10, 106, 150, 13);
		pnCarrinho.add(lblErrorCart);
		
		btnAddToCart = new JButton("Adicionar");
		btnAddToCart.setToolTipText("Adiciona o elemento selecionado");
		btnAddToCart.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnAddToCart.setBackground(new Color(128, 255, 128));
		btnAddToCart.setMargin(new Insets(2, 2, 2, 2));
		btnAddToCart.setBounds(298, 129, 70, 21);
		btnAddToCart.addActionListener(cartCtrl);
		btnAddToCart.setEnabled(false);
		pnCarrinho.add(btnAddToCart);
		
		btnDelFromCart = new JButton("Remover");
		btnDelFromCart.setToolTipText("Remove o último elemento adicionado");
		btnDelFromCart.setMargin(new Insets(2, 2, 2, 2));
		btnDelFromCart.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnDelFromCart.setBackground(new Color(255, 128, 128));
		btnDelFromCart.setBounds(378, 129, 70, 21);
		btnDelFromCart.addActionListener(cartCtrl);
		btnDelFromCart.setEnabled(false);
		pnCarrinho.add(btnDelFromCart);
		
		btnComprar = new JButton("Comprar");
		btnComprar.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnComprar.setForeground(new Color(255, 255, 255));
		btnComprar.setMargin(new Insets(2, 2, 2, 2));
		btnComprar.setBackground(new Color(62, 196, 75));
		btnComprar.setBounds(642, 19, 70, 21);
		btnComprar.addActionListener(cartCtrl);
		pnCarrinho.add(btnComprar);
		
		btnCancelCart = new JButton("Cancelar");
		btnCancelCart.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnCancelCart.setForeground(new Color(255, 255, 255));
		btnCancelCart.setMargin(new Insets(2, 2, 2, 2));
		btnCancelCart.setBackground(new Color(255, 0, 0));
		btnCancelCart.setBounds(562, 19, 70, 21);
		btnCancelCart.addActionListener(cartCtrl);
		pnCarrinho.add(btnCancelCart);
		
		lblCartNum = new JLabel("");
		lblCartNum.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblCartNum.setBounds(170, 19, 150, 20);
		pnCarrinho.add(lblCartNum);
		
		lblCartCustomerID = new JLabel("");
		lblCartCustomerID.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblCartCustomerID.setBounds(170, 48, 150, 20);
		pnCarrinho.add(lblCartCustomerID);
		
		lblCartCustomerName = new JLabel("");
		lblCartCustomerName.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblCartCustomerName.setBounds(170, 80, 150, 20);
		pnCarrinho.add(lblCartCustomerName);
		
		lblFinalPrice = new JLabel("Total: R$");
		lblFinalPrice.setHorizontalAlignment(SwingConstants.LEFT);
		lblFinalPrice.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblFinalPrice.setBounds(562, 301, 151, 30);
		pnCarrinho.add(lblFinalPrice);
		
		
		
	}

	public void loadCartItemsTable() {
		// "código;nome;valor"
		String productHeader[] = produtoCtrl.getHeader().split(";");
		String vetHeader[] = {productHeader[0], productHeader[2], productHeader[3]};
		String header = String.join(";", vetHeader);
		carregarDados(tbCartItems, header, new List<ICsv>());
		TableColumn column = tbCartItems.getColumnModel().getColumn(0);
		tbCartItems.removeColumn(column);
		tbCartItems.getColumnModel().getColumn(0).setMaxWidth(40);
		tbCartItems.getColumnModel().getColumn(2).setMaxWidth(60);
		
	}

	public void loadProdCartTable() {
		carregarDados(tbProdCart, produtoCtrl.getHeader(), new List<ICsv>());
		tbProdCart.getColumnModel().getColumn(0).setMaxWidth(30);
	}

	private void initListaClientes() {
		// Tela de Lista de Clientes
		listaClientes = new JPanel();
		listaClientes.setBounds(0, 0, 722, 336);
		listaClientes.setLayout(null);

		// Título da Tela de Lista de Clientes
		lblTituloClientes = new JLabel("LISTA DE CLIENTES");
		lblTituloClientes.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTituloClientes.setBounds(266, 5, 178, 30);
		listaClientes.add(lblTituloClientes);

		// Label da Pesquisa
		lblSearch = new JLabel("Pesquisar");
		lblSearch.setBounds(24, 27, 150, 21);
		listaClientes.add(lblSearch);

		// Campo de Pesquisa/Busca
		tfSearch = new JTextField();
		tfSearch.setToolTipText("Pesquisar Cliente");
		tfSearch.setBounds(24, 51, 150, 19);
		tfSearch.setColumns(10);
		listaClientes.add(tfSearch);

		// Botão para Pesquisar Cliente
		btnPesquisarCliente = new JButton("Pesquisar");
		btnPesquisarCliente.setBounds(184, 50, 100, 21);
		btnPesquisarCliente.addActionListener(customerCtrl);
		listaClientes.add(btnPesquisarCliente);

		// Botão Excluir Cliente
		btnExcluirCliente = new JButton("Excluir");
		btnExcluirCliente.setBounds(290, 50, 100, 21);
		btnExcluirCliente.addActionListener(customerCtrl);
		listaClientes.add(btnExcluirCliente);

		// Botão para Cadastrar Novo Cliente
		btnNovoCliente = new JButton("Novo Cliente");
		btnNovoCliente.addActionListener(e -> {
			clearCustomerFields();
			toggleCustomerView(false);
		});
		btnNovoCliente.setBounds(603, 50, 109, 21);
		listaClientes.add(btnNovoCliente);

		// Tabela de Clientes
		tableCliente = new JTable();
		scrollPane = new JScrollPane(getCustomersTable());
		scrollPane.setBounds(14, 109, 708, 227);
		listaClientes.add(scrollPane);

		tabCliente.add(listaClientes);

		lblErrorListaCliente = new JLabel("");
		getLblErrorListaCliente().setForeground(new Color(255, 0, 0));
		getLblErrorListaCliente().setBounds(24, 80, 260, 19);
		listaClientes.add(getLblErrorListaCliente());

		btnEditarCliente = new JButton("Editar");
		btnEditarCliente.setBounds(290, 81, 100, 21);
		btnEditarCliente.addActionListener(evt -> {
			if (tableCliente.getSelectedRow() != -1)
				toggleCustomerView(false);
		});
		btnEditarCliente.addActionListener(customerCtrl);
		listaClientes.add(btnEditarCliente);
	}




	public void clearTypeFields() {
		tfCodigoTipo.setText(String.valueOf(tipoCtrl.getProximoCodigoDisponivel()));
		tfNomeTipo.setText("");
		taDescricaoTipo.setText("");
	}

	public void toggleTypeView(boolean listView) {
		listaTipos.setVisible(listView);
		cadastroTipo.setVisible(!listView);
	}

	public JTable getTableTipos() {
		return tableTipos;
	}

	public JTextField getTfBuscaTipo() {
		return tfBuscaTipo;
	}

	public Tipo getTypeForm() throws Exception {
		int code = Integer.parseInt(tfCodigoTipo.getText());
		String name = tfNomeTipo.getText();
		String description = taDescricaoTipo.getText();
		return new Tipo(code, name, description);
	}

	public void setTypeForm(Tipo t) {
		tfCodigoTipo.setText(String.valueOf(t.getCodigo()));
		tfNomeTipo.setText(t.getNome());
		taDescricaoTipo.setText(t.getDescricao());
	}


	private void initListaTipos() {
		listaTipos = new JPanel();
		listaTipos.setBounds(0, 0, 621, 336);
		listaTipos.setLayout(null);

		lblTituloTipos = new JLabel("TIPOS");
		lblTituloTipos.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTituloTipos.setBounds(273, 11, 60, 30);

		lblBuscaTipo = new JLabel("Pesquisar Código");
		lblBuscaTipo.setBounds(24, 27, 150, 21);

		tfBuscaTipo = new JTextField();
		tfBuscaTipo.setBounds(24, 51, 150, 19);
		tfBuscaTipo.setColumns(10);

		btnPesquisaTipo = new JButton("Pesquisar");
		btnPesquisaTipo.setBounds(184, 50, 100, 21);
		btnPesquisaTipo.setActionCommand("PESQUISAR");
		btnPesquisaTipo.addActionListener(tipoCtrl);

		btnExcluiTipo = new JButton("Excluir");
		btnExcluiTipo.setBounds(290, 50, 100, 21);
		btnExcluiTipo.setActionCommand("EXCLUIR");
		btnExcluiTipo.addActionListener(tipoCtrl);
		btnExcluiTipo.addActionListener(e -> {
			cbListaTipo.setModel(criarComboBoxTipos(true));
			cbTipoProduto.setModel(criarComboBoxTipos(false));
		});

		btnNovoTipo = new JButton("Novo Tipo");
		btnNovoTipo.setBounds(487, 50, 89, 23);
		btnNovoTipo.addActionListener(e -> {
			clearTypeFields();
			toggleTypeView(false);
		});

		JButton btnEditarTipo = new JButton("Editar");
		btnEditarTipo.setBounds(290, 82, 100, 21);
		btnEditarTipo.setActionCommand("INIT_EDITAR");
		btnEditarTipo.addActionListener(tipoCtrl);
		btnEditarTipo.addActionListener(e -> {
				btnSalvarTipoCadastro.setActionCommand("EDITAR");
				cbListaTipo.setModel(criarComboBoxTipos(true));
				cbTipoProduto.setModel(criarComboBoxTipos(false));
		});

		tableTipos = new JTable();
		scrollPaneTipos = new JScrollPane(tableTipos);
		scrollPaneTipos.setBounds(14, 109, 592, 227);

		listaTipos.add(lblTituloTipos);
		listaTipos.add(lblBuscaTipo);
		listaTipos.add(tfBuscaTipo);
		listaTipos.add(btnPesquisaTipo);
		listaTipos.add(btnExcluiTipo);
		listaTipos.add(btnNovoTipo);
		listaTipos.add(btnEditarTipo);
		listaTipos.add(scrollPaneTipos);

		tabTipos.add(listaTipos);
	}

	private boolean prepararCamposParaEditarTipo() {
		int selectedRow = tableTipos.getSelectedRow();
		String codigoTipo = (String) tableTipos.getModel().getValueAt(selectedRow, 1);
		try {
			Tipo t = (Tipo) TipoController.getInstance().get(codigoTipo);
			if (t == null) {
				return false;
			}
			tfCodigoTipo.setText(String.valueOf(codigoTipo));
			tfNomeTipo.setText(t.getNome());
			taDescricaoTipo.setText(t.getDescricao());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void toggleCustomerView(boolean viewLista) {
		listaClientes.setVisible(viewLista);
		cadastroClientes.setVisible(!viewLista);
	}

	private void toggleEmailField(ActionEvent e) {
		String selectedItem = (String) cbClienteTipo.getSelectedItem();
		if (selectedItem.toUpperCase().equals("FÍSICO")) {
			tfClienteEmail.setEnabled(false);
			tfClienteEmail.setBackground(Color.LIGHT_GRAY);
		} else if (selectedItem.toUpperCase().equals("JURÍDICO")) {
			tfClienteEmail.setEnabled(true);
			tfClienteEmail.setBackground(Color.WHITE);
		}
	}

	private void updateResolution(ChangeEvent e) {
		int selectedIndex = tabbedPane.getSelectedIndex();
		int x = getX();
		int y = getY();
		if (selectedIndex == 0 || selectedIndex == 2 || selectedIndex == 3 || selectedIndex == 4)
			setBounds(x, y, 740, 400);
		if (selectedIndex == 1)
			setBounds(x, y, 640, 400);
	}

	// a partir daqui
	private void excluirProduto() {
		int selectedRow = tableProduto.getSelectedRow();
		TableModel model = tableProduto.getModel();
		String codigoProduto = (String) model.getValueAt(selectedRow, 1);
		try {
			int id = Integer.parseInt(codigoProduto);
			produtoCtrl.remove(id);
		} catch (NumberFormatException error) {
			/* TODO */} catch (Exception e) {
			/* TODO 2 */}
	}

	private void pesquisarProduto(String codigo) {
		int codigoProduto;
		if (codigo == null || codigo.isBlank()) {
			loadProductTable();
			return;
		}
		try {
			codigoProduto = Integer.parseInt(codigo);
		} catch (NumberFormatException e) {
			return;
		}

		try {
			List<ICsv> target = new List<>();
			String csvHeader;

			ProdutoController pR = ProdutoController.getInstance();

			String selectedComboBox = (String) cbListaTipo.getSelectedItem();
			if (selectedComboBox.equals("-")) {
				target.addLast(pR.get(codigoProduto));
			} else {
				int codigoTipo = Integer.parseInt(selectedComboBox.split("-")[0]);
				target.addLast(pR.get(codigoProduto, codigoTipo));
			}
			csvHeader = pR.getHeader();
			carregarDados(tableProduto, csvHeader, target);
			tableProduto.getColumnModel().getColumn(0).setMaxWidth(26);
			tableProduto.getColumnModel().getColumn(1).setMaxWidth(46);
			tableProduto.getColumnModel().getColumn(2).setMaxWidth(46);
			tableProduto.getColumnModel().getColumn(3).setMinWidth(270);
		} catch (Exception e) {
			// TODO: produto não encontrado
		}

	}

	private DefaultComboBoxModel<String> criarComboBoxTipos(boolean tipoVazio) {
		DefaultComboBoxModel<String> m = new DefaultComboBoxModel<>();
		if (tipoVazio) {
			m.addElement("-");
		}
		try {
			List<ICsv> tipos = tipoCtrl.get();
			int size = tipos.size();
			for (int i = 0; i < size; i++) {
				Tipo item = (Tipo) tipos.get(i);
				m.addElement(item.getCodigo() + "-" + item.getNome());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	public List<ICsv> loadProductTable() {
		try {
			List<ICsv> data = getProdutosFiltrados();
			String csvHeader = produtoCtrl.getHeader();
			carregarDados(tableProduto, csvHeader, data);
			tableProduto.getColumnModel().getColumn(0).setMaxWidth(26);
			tableProduto.getColumnModel().getColumn(1).setMaxWidth(46);
			tableProduto.getColumnModel().getColumn(2).setMaxWidth(46);
			tableProduto.getColumnModel().getColumn(3).setMinWidth(270);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private List<ICsv> getProdutosFiltrados() throws Exception {
		List<ICsv> data;
		String selectedComboBox = (String) cbListaTipo.getSelectedItem();
		if (selectedComboBox.equals("-")) {
			data = produtoCtrl.get();
		} else {
			int codigoTipo = Integer.parseInt(selectedComboBox.split("-")[0]);
			data = produtoCtrl.getByTipe(codigoTipo);
		}
		return data;
	}

	private boolean prepararCamposParaEditarProduto() {
		int selectedRow = tableProduto.getSelectedRow();
		try {
			int codigoProduto = Integer.parseInt((String) tableProduto.getModel().getValueAt(selectedRow, 1));
			Produto p = (Produto) ProdutoController.getInstance().get(codigoProduto);
			tfCodigoProduto.setText(String.valueOf(codigoProduto));
			tfNomeProduto.setText(p.getNome());
			tfValorProduto.setText(String.valueOf(p.getValor()));
			tfQuantidadeProduto.setText(String.valueOf(p.getQuantidadeEstoque()));
			return true;
		} catch (Exception e) {
			return false;
		}
	}


	private void atualizarTfCodigoTipo() {
		try {
			TipoController registry = TipoController.getInstance();
			tfCodigoTipo.setText(String.valueOf(registry.getProximoCodigoDisponivel()));
		} catch (Exception ex) {
			/* TODO */}
	}

	private final ProdutoController produtoCtrl;
    {
        try {
            produtoCtrl = ProdutoController.getInstance();
			produtoCtrl.setView(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

	private final TipoController tipoCtrl;
    {
        try {
            tipoCtrl = TipoController.getInstance();
			tipoCtrl.setView(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

	public void toggleProductView(boolean listView) {
		listaProdutos.setVisible(listView);
		cadastroProduto.setVisible(!listView);
	}

	public JTextField getTfBuscaProduto() {
		return tfBuscaProduto;
	}

    private JComboBox<String> cbTipoProduto;

	private void initListaProdutos() {
		listaProdutos = new JPanel();
		listaProdutos.setBounds(0, 0, 722, 336);

		lblTituloProdutos = new JLabel("PRODUTOS");
		lblTituloProdutos.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTituloProdutos.setBounds(273, 11, 117, 30);

		lblBuscaProduto = new JLabel("Pesquisar Código");
		lblBuscaProduto.setBounds(24, 27, 150, 21);

		tfBuscaProduto = new JTextField();
		tfBuscaProduto.setBounds(24, 51, 238, 19);
		tfBuscaProduto.setColumns(10);

		btnPesquisaProduto = new JButton("Pesquisar");
		btnPesquisaProduto.setBounds(283, 50, 100, 21);
		btnPesquisaProduto.setActionCommand("PESQUISAR");
		btnPesquisaProduto.addActionListener(produtoCtrl);

		btnExcluiProduto = new JButton("Excluir");
		btnExcluiProduto.setBounds(389, 50, 100, 21);
		btnExcluiProduto.setActionCommand("EXCLUIR");
		btnExcluiProduto.addActionListener(produtoCtrl);

		btnNovoProduto = new JButton("Novo Produto");
		btnNovoProduto.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNovoProduto.setBounds(599, 50, 113, 23);
		btnNovoProduto.setActionCommand("NOVO_PRODUTO");
		btnNovoProduto.addActionListener(produtoCtrl);
		btnNovoProduto.addActionListener(e -> {
			clearProductFields();
			toggleProductView(false);
		});

		btnEditarProduto = new JButton("Editar");
		btnEditarProduto.setBounds(495, 50, 100, 21);
		btnEditarProduto.setActionCommand("INIT_EDITAR");
		btnEditarProduto.addActionListener(produtoCtrl);
		btnEditarProduto.addActionListener(e -> btnSalvarProduto.setActionCommand("EDITAR"));

		btnFiltraProduto = new JButton("Filtrar");
		btnFiltraProduto.setBounds(283, 75, 100, 21);
		btnFiltraProduto.addActionListener(e -> loadProductTable());

		tableProduto = new JTable();
		scrollPaneProdutos = new JScrollPane(tableProduto);
		scrollPaneProdutos.setBounds(14, 109, 698, 227);

		cbListaTipo = new JComboBox<>();
		cbListaTipo.setModel(criarComboBoxTipos(true));
		cbListaTipo.setBounds(24, 76, 238, 22);

		listaProdutos.add(lblTituloProdutos);
		listaProdutos.add(lblBuscaProduto);
		listaProdutos.add(tfBuscaProduto);
		listaProdutos.add(btnPesquisaProduto);
		listaProdutos.setLayout(null);
		listaProdutos.setLayout(null);
		listaProdutos.add(lblTituloProdutos);
		listaProdutos.add(lblBuscaProduto);
		listaProdutos.add(tfBuscaProduto);
		listaProdutos.add(btnPesquisaProduto);
		listaProdutos.add(btnExcluiProduto);
		listaProdutos.add(btnNovoProduto);
		listaProdutos.add(btnEditarProduto);
		listaProdutos.add(btnFiltraProduto);
		listaProdutos.add(scrollPaneProdutos);
		listaProdutos.add(cbListaTipo);

		tabProdutos.add(listaProdutos);
	}

	public Produto getProductForm() throws Exception {
		int code = Integer.parseInt(tfCodigoProduto.getText());
		String name = tfNomeProduto.getText();
		double value = tfValorProduto.getText().isBlank() ? 0 : Double.parseDouble(tfValorProduto.getText());
		int ammount = tfQuantidadeProduto.getText().isBlank() ? 0 : Integer.parseInt(tfQuantidadeProduto.getText());
		String selectedType = (String) cbTipoProduto.getSelectedItem();
		Tipo type = (Tipo) tipoCtrl.get(selectedType.split("-")[0]);
		return new Produto(code, name, type, value, ammount);
	}

	public void setProductForm(Produto p) {
		tfCodigoProduto.setText(String.valueOf(p.getCodigo()));
		tfNomeProduto.setText(p.getNome());
		cbTipoProduto.setSelectedIndex(0);
		tfValorProduto.setText(String.valueOf(p.getValor()));
		tfQuantidadeProduto.setText(String.valueOf(p.getQuantidadeEstoque()));
	}

	public void clearProductFields() {
		tfCodigoProduto.setText(String.valueOf(produtoCtrl.getProximoCodigoDisponivel()));
		tfNomeProduto.setText("");
		cbTipoProduto.setSelectedIndex(0);
		tfValorProduto.setText("");
		tfQuantidadeProduto.setText("");
	}

	public JTable getTableProduto() {
		return tableProduto;
	}

	private void initCadastroProduto() {
		cadastroProduto = new JPanel();
		cadastroProduto.setBounds(0, 0, 722, 336);
		cadastroProduto.setLayout(null);

		layerCadastroProduto = new JLayeredPane();
		layerCadastroProduto.setBounds(0, 0, 722, 336);
		cadastroProduto.add(layerCadastroProduto);

		cadastroProduto.setVisible(false);

		lblCadastrarProduto = new JLabel("CADASTRAR PRODUTO");
		lblCadastrarProduto.setHorizontalAlignment(SwingConstants.LEFT);
		lblCadastrarProduto.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCadastrarProduto.setBounds(234, 0, 223, 38);
		layerCadastroProduto.add(lblCadastrarProduto);

		lblCodigoProduto = new JLabel("Código:");
		lblCodigoProduto.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCodigoProduto.setHorizontalAlignment(SwingConstants.LEFT);
		lblCodigoProduto.setBounds(10, 58, 75, 38);
		layerCadastroProduto.add(lblCodigoProduto);

		tfCodigoProduto = new JTextField();
		tfCodigoProduto.setEnabled(false);
		tfCodigoProduto.setEditable(false);
		tfCodigoProduto.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tfCodigoProduto.setBounds(95, 65, 75, 24);
		layerCadastroProduto.add(tfCodigoProduto);
		tfCodigoProduto.setColumns(10);

		lblNomeProduto = new JLabel("Nome:");
		lblNomeProduto.setHorizontalAlignment(SwingConstants.LEFT);
		lblNomeProduto.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNomeProduto.setBounds(207, 58, 75, 38);
		layerCadastroProduto.add(lblNomeProduto);

		tfNomeProduto = new JTextField();
		tfNomeProduto.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tfNomeProduto.setColumns(10);
		tfNomeProduto.setBounds(283, 65, 405, 24);
		layerCadastroProduto.add(tfNomeProduto);

		JLabel lblValorProduto = new JLabel("Valor:");
		lblValorProduto.setHorizontalAlignment(SwingConstants.LEFT);
		lblValorProduto.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblValorProduto.setBounds(10, 126, 75, 38);
		layerCadastroProduto.add(lblValorProduto);

		tfValorProduto = new JTextField();
		tfValorProduto.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tfValorProduto.setColumns(10);
		tfValorProduto.setBounds(136, 133, 128, 24);
		layerCadastroProduto.add(tfValorProduto);

		tfQuantidadeProduto = new JTextField();
		tfQuantidadeProduto.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tfQuantidadeProduto.setColumns(10);
		tfQuantidadeProduto.setBounds(136, 196, 128, 24);
		layerCadastroProduto.add(tfQuantidadeProduto);

		JLabel lblQuantidadeProduto = new JLabel("Quantidade:");
		lblQuantidadeProduto.setHorizontalAlignment(SwingConstants.LEFT);
		lblQuantidadeProduto.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblQuantidadeProduto.setBounds(10, 186, 116, 38);
		layerCadastroProduto.add(lblQuantidadeProduto);

		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setHorizontalAlignment(SwingConstants.LEFT);
		lblTipo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTipo.setBounds(303, 126, 75, 38);
		layerCadastroProduto.add(lblTipo);

		cbTipoProduto = new JComboBox<>();
		cbTipoProduto.setModel(criarComboBoxTipos(false));
		cbTipoProduto.setBounds(388, 135, 300, 22);
		layerCadastroProduto.add(cbTipoProduto);

		clearProductFields();

		btnSalvarProduto = new JButton("SALVAR");
		btnSalvarProduto.setActionCommand("SALVAR");
		btnSalvarProduto.setForeground(new Color(0, 128, 0));
		btnSalvarProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSalvarProduto.setBounds(618, 290, 87, 29);
		layerCadastroProduto.add(btnSalvarProduto);
		btnSalvarProduto.addActionListener(e -> {
			btnSalvarProduto.setActionCommand("SALVAR");
		});
		btnSalvarProduto.addActionListener(produtoCtrl);

		JButton btnCancelarProduto = new JButton("CANCELAR");
		btnCancelarProduto.setForeground(Color.RED);
		btnCancelarProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancelarProduto.setBounds(501, 290, 107, 29);
		layerCadastroProduto.add(btnCancelarProduto);
		btnCancelarProduto.addActionListener(e -> {
			toggleProductView(true);
			btnSalvarProduto.setActionCommand("SALVAR");
			clearProductFields();
		});

		tabProdutos.add(cadastroProduto);
	}

	private void enableDisableEmailField(ActionEvent e) {
		String selectedItem = (String) cbClienteTipo.getSelectedItem();
		if (selectedItem.equalsIgnoreCase("FÍSICO")) {
			tfClienteEmail.setEnabled(false);
			tfClienteEmail.setBackground(Color.LIGHT_GRAY);
		} else if (selectedItem.equalsIgnoreCase("JURÍDICO")) {
			tfClienteEmail.setEnabled(true);
			tfClienteEmail.setBackground(Color.WHITE);
		}
	}


	private final ComprasController salesCtrl = new ComprasController(this);
	private JLabel lblErrorSalesList;
	private JButton btnSearchSale;
	private JButton btnViewSalesDetails;
	private JPanel tabCompras = new JPanel();
	private JPanel listSales;
	private JLabel lblSalesTitle;
	private JLabel lblSearchSales;
	private JLabel lblSaleTotal;
	private JTextField tfSearchSales;
	private JScrollPane scrollPaneCompras;
	private JTable salesTable;
	private JLabel lblFinalPrice;
	private JButton btnDelFromCart;

	public JTable getSalesTable() {
		return this.salesTable;
	}

	public JTextField getTfSearchSales() {
		return tfSearchSales;
	}

	public JLabel getLblErrorSalesList() {
		return lblErrorSalesList;
	}

	public JLabel getLblSaleTotal() {
		return lblSaleTotal;
	}

	private void initListaVendas() {

		listSales = new JPanel();
		listSales.setBounds(0, 0, 722, 336);
		listSales.setLayout(null);

		lblSalesTitle = new JLabel("VENDAS");
		lblSalesTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblSalesTitle.setBounds(310, 11, 117, 30);

		salesTable = new JTable();
		scrollPaneCompras = new JScrollPane(salesTable);
		scrollPaneCompras.setBounds(14, 109, 698, 217);

		lblErrorSalesList = new JLabel("");
		lblErrorSalesList.setBounds(14, 75, 117, 30);
		lblErrorSalesList.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblErrorSalesList.setForeground(new Color(255, 0, 0));

		lblSearchSales = new JLabel("Pesquisar Venda");
		lblSearchSales.setBounds(14, 27, 100, 21);

		tfSearchSales = new JTextField();
		tfSearchSales.setBounds(14, 51, 150, 19);
		tfSearchSales.setToolTipText("Id da compra");
		tfSearchSales.setColumns(10);

		btnSearchSale = new JButton("Pesquisar");
		btnSearchSale.setActionCommand("PESQUISAR");
		btnSearchSale.setBounds(180, 50, 100, 21);
		btnSearchSale.addActionListener(salesCtrl);
//		TOTAL VENDA: #######
		lblSaleTotal = new JLabel("");
		lblSaleTotal.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSaleTotal.setForeground(new Color(0, 100, 0));
		lblSaleTotal.setBounds(523, 51, 250, 21);

		listSales.add(lblSalesTitle);
		listSales.add(scrollPaneCompras);
		listSales.add(lblSearchSales);
		listSales.add(tfSearchSales);
		listSales.add(btnSearchSale);
		listSales.add(lblSaleTotal);
		listSales.add(lblErrorSalesList);
		tabCompras.add(listSales);
	}


	public void loadSalesTable() {
		var salesController = new ItemCompraCsvController();
        try {
			List<ICsv> salesList = salesController.get();
			String header = salesController.getHeader();
			carregarDados(salesTable, header, salesList);
			salesTable.getColumnModel().getColumn(0).setMaxWidth(26);
			salesTable.getColumnModel().getColumn(1).setMaxWidth(80);
			salesTable.getColumnModel().getColumn(2).setMinWidth(100);
		} catch (IOException e) {
			e.printStackTrace();
			printError(lblErrorSalesList, e);
        }

    }

	public JButton getBtnOpenCart() {
		return btnOpenCart;
	}

	public JLabel getLblCartNum() {
		return lblCartNum;
	}

	public JLabel getLblCartCustomerID() {
		return lblCartCustomerID;
	}

	public JLabel getLblCartCustomerName() {
		return lblCartCustomerName;
	}

	public JTable getTbProdCart() {
		return tbProdCart;
	}

	public ProdutoController getProdutoCtrl() {
		return produtoCtrl;
	}

	public JButton getBtnAddToCart() {
		return btnAddToCart;
	}

	public JButton getBtnSearchProdCart() {
		return btnSearchProdCart;
	}

	public JTable getTbCartItems() {
		return tbCartItems;
	}

	public JLabel getLblFinalPrice() {
		return lblFinalPrice;
	}

	public JButton getBtnDelFromCart() {
		return btnDelFromCart;
	}
}
