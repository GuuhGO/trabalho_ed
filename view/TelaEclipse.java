package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.csv.ClienteCsvController;
import datastrucures.genericList.List;
import model.cliente.BaseCliente;

public class TelaEclipse extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfClienteNome;
	private JTextField tfClienteCpf_Cnpj;
	private JTextField tfClienteTelefone;
	private JTextField tfClienteEmail;
	private JTextField tfEndLogradouro;
	private JTextField tfEndNumero;
	private JTextField tfEndComplemento;
	private JTextField tfBusca;
	private JTextField tfEndCep;
	private JPanel listaClientes;
	private JPanel cadastroClientes;
	private DefaultTableModel tableModel;
	private JTable table;
	private ClienteCsvController cliCont;
	private JComboBox<String> cbClienteTipo;
	private JScrollPane scrollPane; 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaEclipse frame = new TelaEclipse();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaEclipse() {
		initialize();
		initializeTable();
	}

	private void initialize() {
		setTitle("Loja");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 626, 363);
		contentPane.add(tabbedPane);

		JPanel tabCliente = new JPanel();
		tabbedPane.addTab("Cliente", null, tabCliente, "Cadastro de Cliente");
		tabCliente.setLayout(null);

		listaClientes = new JPanel();
		listaClientes.setBounds(0, 0, 621, 336);
		tabCliente.add(listaClientes);
		listaClientes.setLayout(null);

		tfBusca = new JTextField();
		tfBusca.setToolTipText("Pesquisar CPF/CNPJ");
		tfBusca.setBounds(24, 51, 150, 19);
		listaClientes.add(tfBusca);
		tfBusca.setColumns(10);

		JButton btnNewButton = new JButton("Pesquisar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(184, 50, 100, 21);
		listaClientes.add(btnNewButton);

		JButton btnNovoCliente = new JButton("Novo Cliente");
		btnNovoCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listaClientes.setVisible(false);
				cadastroClientes.setVisible(true);
			}
		});
		btnNovoCliente.setBounds(487, 50, 109, 21);

		listaClientes.add(btnNovoCliente);

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(290, 50, 100, 21);
		listaClientes.add(btnExcluir);

		JLabel lblNewLabel = new JLabel("CLIENTES");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(266, 5, 88, 30);
		listaClientes.add(lblNewLabel);

		JLabel lblSearch = new JLabel("Pesquisar CPF/CNPJ");
		lblSearch.setBounds(24, 27, 150, 21);
		listaClientes.add(lblSearch);

		cadastroClientes = new JPanel();
		cadastroClientes.setVisible(false);
		cadastroClientes.setBounds(0, 0, 621, 336);
		tabCliente.add(cadastroClientes);
		cadastroClientes.setLayout(null);

		JLayeredPane lyrFields = new JLayeredPane();
		lyrFields.setBounds(0, 0, 621, 278);
		cadastroClientes.add(lyrFields);

		JLabel lblClienteNome = new JLabel("Nome");
		lblClienteNome.setHorizontalAlignment(SwingConstants.LEFT);
		lblClienteNome.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblClienteNome.setBounds(158, 51, 60, 38);
		lyrFields.add(lblClienteNome);

		JLabel lblClienteCpf_Cnpj = new JLabel("CPF/CNPJ");
		lblClienteCpf_Cnpj.setHorizontalAlignment(SwingConstants.LEFT);
		lblClienteCpf_Cnpj.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblClienteCpf_Cnpj.setBounds(407, 51, 86, 38);
		lyrFields.add(lblClienteCpf_Cnpj);

		JLabel lblClienteTelefone = new JLabel("Telefone");
		lblClienteTelefone.setHorizontalAlignment(SwingConstants.LEFT);
		lblClienteTelefone.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblClienteTelefone.setBounds(12, 108, 86, 38);
		lyrFields.add(lblClienteTelefone);

		JLabel lblClienteTipo = new JLabel("Tipo");
		lblClienteTipo.setHorizontalAlignment(SwingConstants.LEFT);
		lblClienteTipo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblClienteTipo.setBounds(12, 51, 44, 38);
		lyrFields.add(lblClienteTipo);

		JLabel lblClienteEmail = new JLabel("Email");
		lblClienteEmail.setHorizontalAlignment(SwingConstants.LEFT);
		lblClienteEmail.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblClienteEmail.setBounds(324, 108, 60, 38);
		lyrFields.add(lblClienteEmail);

		cbClienteTipo = new JComboBox<>();
		cbClienteTipo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cbClienteTipo.setModel(new DefaultComboBoxModel<String>(new String[] { "Físico", "Jurídico" }));
		cbClienteTipo.setBounds(65, 58, 86, 26);
		cbClienteTipo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedItem = (String) cbClienteTipo.getSelectedItem();
				if (selectedItem.toUpperCase().equals("FÍSICO")) {
					tfClienteEmail.setEnabled(false);
					tfClienteEmail.setBackground(Color.LIGHT_GRAY);
				} else if (selectedItem.toUpperCase().equals("JURÍDICO")) {
					tfClienteEmail.setEnabled(true);
					tfClienteEmail.setBackground(Color.WHITE);
				}
			}
		});

		lyrFields.add(cbClienteTipo);

		tfClienteNome = new JTextField();
		tfClienteNome.setBounds(225, 58, 175, 24);
		lyrFields.add(tfClienteNome);
		tfClienteNome.setColumns(10);

		tfClienteCpf_Cnpj = new JTextField();
		tfClienteCpf_Cnpj.setColumns(10);
		tfClienteCpf_Cnpj.setBounds(500, 58, 109, 24);
		lyrFields.add(tfClienteCpf_Cnpj);

		tfClienteTelefone = new JTextField();
		tfClienteTelefone.setColumns(10);
		tfClienteTelefone.setBounds(118, 115, 190, 24);
		lyrFields.add(tfClienteTelefone);

		tfClienteEmail = new JTextField();
		tfClienteEmail.setBounds(400, 115, 203, 24);
		tfClienteEmail.setEnabled(false);
		tfClienteEmail.setBackground(Color.LIGHT_GRAY);

		lyrFields.add(tfClienteEmail);
		tfClienteEmail.setColumns(10);

		JLabel lblEnderecoLogradouro = new JLabel("Logradouro");
		lblEnderecoLogradouro.setHorizontalAlignment(SwingConstants.LEFT);
		lblEnderecoLogradouro.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblEnderecoLogradouro.setBounds(12, 174, 102, 38);
		lyrFields.add(lblEnderecoLogradouro);

		JLabel lblEnderecoNumero = new JLabel("Nº");
		lblEnderecoNumero.setHorizontalAlignment(SwingConstants.LEFT);
		lblEnderecoNumero.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblEnderecoNumero.setBounds(322, 174, 29, 38);
		lyrFields.add(lblEnderecoNumero);

		JLabel lblEnderecoComplemento = new JLabel("Complemento");
		lblEnderecoComplemento.setHorizontalAlignment(SwingConstants.LEFT);
		lblEnderecoComplemento.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblEnderecoComplemento.setBounds(408, 174, 127, 38);
		lyrFields.add(lblEnderecoComplemento);

		tfEndLogradouro = new JTextField();
		tfEndLogradouro.setBounds(122, 181, 190, 24);
		lyrFields.add(tfEndLogradouro);
		tfEndLogradouro.setColumns(10);

		tfEndNumero = new JTextField();
		tfEndNumero.setColumns(10);
		tfEndNumero.setBounds(361, 181, 37, 24);
		lyrFields.add(tfEndNumero);

		tfEndComplemento = new JTextField();
		tfEndComplemento.setColumns(10);
		tfEndComplemento.setBounds(545, 181, 60, 24);
		lyrFields.add(tfEndComplemento);

		// BOTÃO SALVAR
		JButton btnSalvar = new JButton("SALVAR");
		btnSalvar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSalvar.setForeground(new Color(0, 128, 0));
		btnSalvar.setBounds(511, 288, 87, 29);
		cadastroClientes.add(btnSalvar);

		// BOTÃO CANCELAR
		JButton btnCancelar = new JButton("CANCELAR");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastroClientes.setVisible(false);
				listaClientes.setVisible(true);
			}
		});
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancelar.setForeground(new Color(255, 0, 0));
		btnCancelar.setBounds(394, 288, 107, 29);
		cadastroClientes.add(btnCancelar);

		JLabel lblEndCep = new JLabel("CEP");
		lblEndCep.setHorizontalAlignment(SwingConstants.LEFT);
		lblEndCep.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblEndCep.setBounds(12, 227, 50, 38);
		lyrFields.add(lblEndCep);

		tfEndCep = new JTextField();
		tfEndCep.setColumns(10);
		tfEndCep.setBounds(120, 234, 190, 24);
		lyrFields.add(tfEndCep);

		JLabel lblCadastrarCliente = new JLabel("CADASTRAR CLIENTE");
		lblCadastrarCliente.setHorizontalAlignment(SwingConstants.LEFT);
		lblCadastrarCliente.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCadastrarCliente.setBounds(213, 3, 194, 38);
		lyrFields.add(lblCadastrarCliente);

		cliCont = new ClienteCsvController(this, cbClienteTipo, tfClienteNome, tfClienteCpf_Cnpj, tfClienteTelefone,
				tfClienteEmail, tfEndLogradouro, tfEndNumero, tfEndComplemento, tfEndCep);
		btnSalvar.addActionListener(cliCont);
		
	}

	public void initializeTable() {
		if(scrollPane != null) {
			scrollPane.remove(table);
			listaClientes.remove(scrollPane);
		}
		String[] columnNames = ("#;" + cliCont.getHeader()).split(";");
		tableModel = new DefaultTableModel(columnNames, 0);
		table = new JTable(tableModel);
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(14, 109, 592, 227);
		listaClientes.add(scrollPane);
		listaClientes.revalidate();
		listaClientes.repaint();
		
		List<BaseCliente> clientes;
		try {
			clientes = cliCont.get();
		} catch (IOException e1) {
			clientes = new List<BaseCliente>();
			e1.printStackTrace();
		}

		int tamanho = clientes.size();
		List<String[]> data = new List<String[]>();
		for (int i = 0; i < tamanho; i++) {
			try {
				BaseCliente c = clientes.get(i);
				String campos[] = ((i+1) + ";" + c.getObjCsv()).split(";");
				data.addLast(campos);
				tableModel.addRow(campos);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
