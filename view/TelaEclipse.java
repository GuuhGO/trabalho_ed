package view;

import controller.ProdutoRegistry;
import controller.TipoRegistry;
import controller.csv.ClienteCsvController;
import datastrucures.genericList.List;
import model.ICsv;
import model.Tipo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class TelaEclipse extends JFrame {

    private static final long serialVersionUID = 1L;
    private ClienteCsvController clienteController;
    private JButton btnExcluir;
    private JButton btnNovoCliente;
    private JButton btnPesquisar;
    private JComboBox<String> cbClienteTipo;
    private JLabel lblSearch;
    private JLabel lblTituloClientes;
    private JPanel cadastroClientes;
    private JPanel contentPane;
    private JPanel listaClientes;
    private JPanel listaTipos;
    private JPanel listaProdutos;
    private JScrollPane scrollPane;
    private JTable tableCliente;
    private JTable tableTipos;
    private JTable tableProduto;
    private JTextField tfBusca;
    private JTextField tfBuscaTipo;
    private JTextField tfBuscaProduto;
    private JTextField tfClienteCpf_Cnpj;
    private JTextField tfClienteEmail;
    private JTextField tfClienteNome;
    private JTextField tfClienteTelefone;
    private JTextField tfEndCep;
    private JTextField tfEndComplemento;
    private JTextField tfEndLogradouro;
    private JTextField tfEndNumero;
    private JPanel tabCliente = new JPanel();
    private JLabel lblCadastrarCliente;
    private JLabel lblClienteNome;
    private JLabel lblClienteCpf_Cnpj;
    private JLabel lblClienteTelefone;
    private JLabel lblClienteEmail;
    private JLabel lblClienteTipo;
    private JLabel lblEnderecoLogradouro;
    private JLabel lblEnderecoNumero;
    private JLabel lblEnderecoComplemento;
    private JLabel lblEndCep;
    private JButton btnSalvar;
    private JButton btnCancelar;
    private JTabbedPane tabbedPane;
    private JPanel tabTipos = new JPanel();
    private JLabel lblTituloTipos;
    private JLabel lblBuscaTipo;
    private JButton btnPesquisaTipo;
    private JButton btnExcluiTipo;
    private JButton btnNovoTipo;
    private JButton btnEditarTipo;
    private JScrollPane scrollPaneTipos;
    private JPanel cadastroTipo;
    private JLayeredPane layerCadastroTipo;
    private JLabel lblCadastrarTipo;
    private JLabel lblCodigo;
    private JTextField tfCodigoTipo;
    private JLabel lblNomeTipo;
    private JTextField tfNomeTipo;
    private JLabel lblDescricao;
    private JTextArea taDescricaoTipo;
    private JButton btnSalvarTipoCadastro;
    private JButton btnCancelarTipoCadastro;
    private JPanel tabProdutos = new JPanel();
    private JLabel lblTituloProdutos;
    private JLabel lblBuscaProduto;
    private JButton btnPesquisaProduto;
    private JButton btnExcluiProduto;
    private JButton btnNovoProduto;
    private JButton btnEditarProduto;
    private JScrollPane scrollPaneProdutos;
    private JComboBox<String> cbListaTipo;
    private JButton btnFiltraProduto;

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
    }

    public TelaEclipse() {
        initialize();
        carregarTableClientes();
        carregarTableTipo();
        carregarTableProduto();
    }

    private void initialize() {

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
        contentPane.add(tabbedPane);

        tabCliente.setLayout(null);
        tabTipos.setLayout(null);
        tabProdutos.setLayout(null);

        initCadastroClientes();
        initListaClientes();
        initListaTipos();
        initCadastroTipos();
        initListaProdutos();

        tabbedPane.addChangeListener(this::updateResolution);
    }


    private void excluirProduto() {
        int selectedRow = tableProduto.getSelectedRow();
        TableModel model = tableProduto.getModel();
        String codigoProduto = (String) model.getValueAt(selectedRow, 1);
        try {
            int id = Integer.parseInt(codigoProduto);
            ProdutoRegistry instance = ProdutoRegistry.getInstance();
            instance.remove(id);
        } catch (NumberFormatException error) {/*TODO*/} catch (Exception e) {/*TODO 2*/}
    }


    private void pesquisarProduto(String codigo) {
        int codigoProduto;
        if (codigo == null || codigo.isBlank()) {
            carregarTableProduto();
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

            ProdutoRegistry pR = ProdutoRegistry.getInstance();

            String selectedComboBox = (String) cbListaTipo.getSelectedItem();
            if (selectedComboBox.equals("-")) {
                target.addLast(pR.get(codigoProduto));
            }
            else {
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
            //TODO: produto não encontrado
        }

    }


    private JComboBox<String> criarComboBoxTipos() {
        JComboBox<String> cb = new JComboBox<>();
        DefaultComboBoxModel<String> m = new DefaultComboBoxModel<>();
        m.addElement("-");
        try {
            List<ICsv> tipos = TipoRegistry.getInstance().get();
            int size = tipos.size();
            for (int i = 0; i < size; i++) {
                Tipo item = (Tipo) tipos.get(i);
                m.addElement(item.getCodigo() + "-" + item.getNome());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        cb.setModel(m);
        return cb;
    }


    public void carregarTableProduto() {
        try {
            ProdutoRegistry produtoRegistry = ProdutoRegistry.getInstance();
            List<ICsv> data = getProdutosFiltrados(produtoRegistry);
            String csvHeader = produtoRegistry.getHeader();
            carregarDados(tableProduto, csvHeader, data);
            tableProduto.getColumnModel().getColumn(0).setMaxWidth(26);
            tableProduto.getColumnModel().getColumn(1).setMaxWidth(46);
            tableProduto.getColumnModel().getColumn(2).setMaxWidth(46);
            tableProduto.getColumnModel().getColumn(3).setMinWidth(270);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<ICsv> getProdutosFiltrados(ProdutoRegistry produtoRegistry) throws Exception {
        List<ICsv> data;
        String selectedComboBox = (String) cbListaTipo.getSelectedItem();
        if (selectedComboBox.equals("-")) {
            data = produtoRegistry.get();
        }
        else {
            int codigoTipo = Integer.parseInt(selectedComboBox.split("-")[0]);
            data = produtoRegistry.getByTipe(codigoTipo);
        }
        return data;
    }


    private boolean prepararCamposParaEditarTipo() {
        int selectedRow = tableTipos.getSelectedRow();
        String codigoTipo = (String) tableTipos.getModel().getValueAt(selectedRow, 1);
        try {
            Tipo t = (Tipo) TipoRegistry.getInstance().get(codigoTipo);
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


    private void excluirTipo() {
        int selectedRow = tableTipos.getSelectedRow();
        TableModel model = tableTipos.getModel();
        String codigoTipo = (String) model.getValueAt(selectedRow, 1);
        try {
            int id = Integer.parseInt(codigoTipo);
            TipoRegistry instance = TipoRegistry.getInstance();
            instance.remove(id);
        } catch (NumberFormatException error) {/*TODO*/} catch (Exception errorGeral) {/*TODO 2*/}
    }


    public void pesquisarTipo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            carregarTableTipo();
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
            TipoRegistry registry = TipoRegistry.getInstance();
            ICsv item = registry.get(codigo);
            listTipos.addLast(item);
            csvHeader = registry.getHeader();
            carregarDados(tableTipos, csvHeader, listTipos);
            tableTipos.getColumnModel().getColumn(0).setMaxWidth(26);
            tableTipos.getColumnModel().getColumn(1).setMaxWidth(46);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void carregarTableTipo() {
        List<ICsv> listTipos;
        String csvHeader;
        try {
            TipoRegistry registry = TipoRegistry.getInstance();
            listTipos = registry.get();
            csvHeader = registry.getHeader();
            carregarDados(tableTipos, csvHeader, listTipos);
            tableTipos.getColumnModel().getColumn(0).setMaxWidth(26);
            tableTipos.getColumnModel().getColumn(1).setMaxWidth(46);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateResolution(ChangeEvent e) {
        int selectedIndex = tabbedPane.getSelectedIndex();
        int x = (int) getX();
        int y = (int) getY();
        if (selectedIndex == 0 || selectedIndex == 2)
            setBounds(x, y, 740, 400);
        if (selectedIndex == 1)
            setBounds(x, y, 640, 400);
    }

    public void carregarTableClientes() {
        List<ICsv> listClientes;
        try {
            listClientes = clienteController.get();
            carregarDados(tableCliente, clienteController.getHeader(), listClientes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void carregarDados(JTable table, String csvHeader, List<ICsv> list) {
        String[] columnNames = ("#;" + csvHeader).split(";");
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
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
        tfBusca = new JTextField();
        tfBusca.setToolTipText("Pesquisar");
        tfBusca.setBounds(24, 51, 150, 19);
        tfBusca.setColumns(10);
        listaClientes.add(tfBusca);

        // Botão para Pesquisar
        btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnPesquisar.setBounds(184, 50, 100, 21);
        listaClientes.add(btnPesquisar);

        // Botão Excluir Cliente
        btnExcluir = new JButton("Excluir");
        btnExcluir.setBounds(290, 50, 100, 21);
        listaClientes.add(btnExcluir);

        // Botão para Cadastrar Novo Cliente
        btnNovoCliente = new JButton("Novo Cliente");
        btnNovoCliente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listaClientes.setVisible(false);
                cadastroClientes.setVisible(true);
            }
        });
        btnNovoCliente.setBounds(603, 50, 109, 21);
        listaClientes.add(btnNovoCliente);

        // Tabela de Clientes
        tableCliente = new JTable();
        scrollPane = new JScrollPane(tableCliente);
        scrollPane.setBounds(14, 109, 708, 227);
        listaClientes.add(scrollPane);

        tabCliente.add(listaClientes);
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
        cbClienteTipo.setModel(new DefaultComboBoxModel<>(new String[]{"Físico", "Jurídico"}));
        cbClienteTipo.setBounds(65, 57, 86, 26);
        cbClienteTipo.addActionListener(this::enableDisableEmailField);
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
        btnSalvar = new JButton("SALVAR");
        btnSalvar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnSalvar.setForeground(new Color(0, 128, 0));
        btnSalvar.setBounds(618, 290, 87, 29);
        cadastroClientes.add(btnSalvar);

        btnCancelar = new JButton("CANCELAR");
        btnCancelar.addActionListener(e -> {
            cadastroClientes.setVisible(false);
            listaClientes.setVisible(true);
        });
        btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnCancelar.setForeground(new Color(255, 0, 0));
        btnCancelar.setBounds(501, 290, 107, 29);
        cadastroClientes.add(btnCancelar);

        clienteController = new ClienteCsvController(cbClienteTipo, tfClienteNome, tfClienteCpf_Cnpj, tfClienteTelefone,
                tfClienteEmail, tfEndLogradouro, tfEndNumero, tfEndComplemento, tfEndCep);
        btnSalvar.addActionListener(clienteController);

        tabCliente.add(cadastroClientes);
    }

    private void initListaTipos() {
        listaTipos = new JPanel();
        listaTipos.setBounds(0, 0, 621, 336);
        listaTipos.setLayout(null);


        lblTituloTipos = new JLabel("TIPOS");
        lblTituloTipos.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblTituloTipos.setBounds(273, 11, 60, 30);
        listaTipos.add(lblTituloTipos);


        lblBuscaTipo = new JLabel("Pesquisar Código");
        lblBuscaTipo.setBounds(24, 27, 150, 21);
        listaTipos.add(lblBuscaTipo);

        tfBuscaTipo = new JTextField();
        tfBuscaTipo.setBounds(24, 51, 150, 19);
        listaTipos.add(tfBuscaTipo);
        tfBuscaTipo.setColumns(10);

        btnPesquisaTipo = new JButton("Pesquisar");
        btnPesquisaTipo.setBounds(184, 50, 100, 21);
        listaTipos.add(btnPesquisaTipo);
        btnPesquisaTipo.addActionListener(e -> {
            pesquisarTipo(tfBuscaTipo.getText());
            tfBuscaTipo.setText("");
        });


        btnExcluiTipo = new JButton("Excluir");
        btnExcluiTipo.setBounds(290, 50, 100, 21);
        listaTipos.add(btnExcluiTipo);
        btnExcluiTipo.addActionListener(e -> {
            excluirTipo();
            carregarTableTipo();
        });

        btnNovoTipo = new JButton("Novo Tipo");
        btnNovoTipo.setBounds(487, 50, 89, 23);
        listaTipos.add(btnNovoTipo);
        btnNovoTipo.addActionListener(e -> {
            cadastroTipo.setVisible(true);
            listaTipos.setVisible(false);
        });

        tableTipos = new JTable();
        scrollPaneTipos = new JScrollPane(tableTipos);
        scrollPaneTipos.setBounds(14, 109, 592, 227);
        listaTipos.add(scrollPaneTipos);

        tabTipos.add(listaTipos);

        btnEditarTipo = new JButton("Editar");
        btnEditarTipo.setBounds(290, 82, 100, 21);
        listaTipos.add(btnEditarTipo);
        btnEditarTipo.addActionListener(e -> {
            if (prepararCamposParaEditarTipo()) {
                btnSalvarTipoCadastro.setActionCommand("EDITAR");
                listaTipos.setVisible(false);
                cadastroTipo.setVisible(true);
            }
        });
    }


    private void initCadastroTipos() {
        cadastroTipo = new JPanel();
        cadastroTipo.setBounds(0, 0, 621, 336);
        cadastroTipo.setLayout(null);

        layerCadastroTipo = new JLayeredPane();
        layerCadastroTipo.setBounds(0, 0, 621, 278);
        cadastroTipo.add(layerCadastroTipo);

        cadastroTipo.setVisible(false);

        lblCadastrarTipo = new JLabel("CADASTRAR TIPO");
        lblCadastrarTipo.setHorizontalAlignment(SwingConstants.LEFT);
        lblCadastrarTipo.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblCadastrarTipo.setBounds(213, 3, 194, 38);
        layerCadastroTipo.add(lblCadastrarTipo);

        lblCodigo = new JLabel("Código:");
        lblCodigo.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblCodigo.setHorizontalAlignment(SwingConstants.LEFT);
        lblCodigo.setBounds(10, 58, 75, 38);
        layerCadastroTipo.add(lblCodigo);

        tfCodigoTipo = new JTextField();
        tfCodigoTipo.setFont(new Font("Tahoma", Font.PLAIN, 13));
        tfCodigoTipo.setEditable(false);
        tfCodigoTipo.setEnabled(false);
        tfCodigoTipo.setBounds(95, 65, 61, 24);
        layerCadastroTipo.add(tfCodigoTipo);
        tfCodigoTipo.setColumns(10);

        lblNomeTipo = new JLabel("Nome:");
        lblNomeTipo.setHorizontalAlignment(SwingConstants.LEFT);
        lblNomeTipo.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNomeTipo.setBounds(207, 58, 75, 38);
        layerCadastroTipo.add(lblNomeTipo);

        tfNomeTipo = new JTextField();
        tfNomeTipo.setFont(new Font("Tahoma", Font.PLAIN, 13));
        tfNomeTipo.setColumns(10);
        tfNomeTipo.setBounds(292, 65, 319, 24);
        layerCadastroTipo.add(tfNomeTipo);

        lblDescricao = new JLabel("Descrição:");
        lblDescricao.setHorizontalAlignment(SwingConstants.LEFT);
        lblDescricao.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblDescricao.setBounds(10, 107, 101, 38);
        layerCadastroTipo.add(lblDescricao);

        taDescricaoTipo = new JTextArea();
        taDescricaoTipo.setLineWrap(true);
        taDescricaoTipo.setBorder(new LineBorder(new Color(0, 0, 0)));
        taDescricaoTipo.setBounds(10, 156, 601, 111);
        layerCadastroTipo.add(taDescricaoTipo);

        btnSalvarTipoCadastro = new JButton("SALVAR");
        btnSalvarTipoCadastro.setForeground(new Color(0, 128, 0));
        btnSalvarTipoCadastro.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnSalvarTipoCadastro.setBounds(511, 288, 87, 29);
        cadastroTipo.add(btnSalvarTipoCadastro);
        try {
            TipoRegistry registry = TipoRegistry.getInstance();
            btnSalvarTipoCadastro.addActionListener(registry);
            registry.setView(this, tfCodigoTipo, tfNomeTipo, taDescricaoTipo);
            tfCodigoTipo.setText(String.valueOf(registry.getProximoCodigoDisponivel()));
        } catch (Exception e) { /*TODO*/ }
        btnSalvarTipoCadastro.addActionListener(e -> {
            btnSalvarTipoCadastro.setActionCommand("SALVAR");
        });


        btnCancelarTipoCadastro = new JButton("CANCELAR");
        btnCancelarTipoCadastro.setForeground(Color.RED);
        btnCancelarTipoCadastro.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnCancelarTipoCadastro.setBounds(394, 288, 107, 29);
        cadastroTipo.add(btnCancelarTipoCadastro);
        btnCancelarTipoCadastro.addActionListener(e -> {
            listaTipos.setVisible(true);
            cadastroTipo.setVisible(false);
            btnSalvarTipoCadastro.setActionCommand("SALVAR");
            taDescricaoTipo.setText("");
            tfNomeTipo.setText("");
            try {
                TipoRegistry registry = TipoRegistry.getInstance();
                tfCodigoTipo.setText(String.valueOf(registry.getProximoCodigoDisponivel()));
            } catch (Exception ex) {/*TODO*/}
        });

        tabTipos.add(cadastroTipo);
    }


    private void initListaProdutos() {
        listaProdutos = new JPanel();
        listaProdutos.setBounds(0, 0, 722, 336);
        listaProdutos.setLayout(null);


        lblTituloProdutos = new JLabel("PRODUTOS");
        lblTituloProdutos.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblTituloProdutos.setBounds(273, 11, 117, 30);
        listaProdutos.add(lblTituloProdutos);


        lblBuscaProduto = new JLabel("Pesquisar Código");
        lblBuscaProduto.setBounds(24, 27, 150, 21);
        listaProdutos.add(lblBuscaProduto);

        tfBuscaProduto = new JTextField();
        tfBuscaProduto.setBounds(24, 51, 238, 19);
        listaProdutos.add(tfBuscaProduto);
        tfBuscaProduto.setColumns(10);

        btnPesquisaProduto = new JButton("Pesquisar");
        btnPesquisaProduto.setBounds(283, 50, 100, 21);
        listaProdutos.add(btnPesquisaProduto);
        btnPesquisaProduto.addActionListener(e -> {
            pesquisarProduto(tfBuscaProduto.getText());
            tfBuscaProduto.setText("");
        });

        btnExcluiProduto = new JButton("Excluir");
        btnExcluiProduto.setBounds(389, 50, 100, 21);
        listaProdutos.add(btnExcluiProduto);
        btnExcluiProduto.addActionListener(e -> {
            excluirProduto();
            carregarTableProduto();
        });

        btnNovoProduto = new JButton("Novo Produto");
        btnNovoProduto.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnNovoProduto.setBounds(599, 50, 113, 23);
        listaProdutos.add(btnNovoProduto);
        btnNovoProduto.addActionListener(e -> {
            /*TODO*/
        });

        btnEditarProduto = new JButton("Editar");
        btnEditarProduto.setBounds(495, 50, 100, 21);
        listaProdutos.add(btnEditarProduto);
        btnEditarProduto.addActionListener(e -> {
            /*TODO*/
        });

        btnFiltraProduto = new JButton("Filtrar");
        btnFiltraProduto.setBounds(283, 75, 100, 21);
        listaProdutos.add(btnFiltraProduto);
        btnFiltraProduto.addActionListener(e -> {
            carregarTableProduto();
        });

        tableProduto = new JTable();
        scrollPaneProdutos = new JScrollPane(tableProduto);
        scrollPaneProdutos.setBounds(14, 109, 698, 227);
        listaProdutos.add(scrollPaneProdutos);

        tabProdutos.add(listaProdutos);

        cbListaTipo = criarComboBoxTipos();
        cbListaTipo.setBounds(24, 76, 238, 22);
        listaProdutos.add(cbListaTipo);

    }


    private void enableDisableEmailField(ActionEvent e) {
        String selectedItem = (String) cbClienteTipo.getSelectedItem();
        if (selectedItem.toUpperCase().equals("FÍSICO")) {
            tfClienteEmail.setEnabled(false);
            tfClienteEmail.setBackground(Color.LIGHT_GRAY);
        }
        else if (selectedItem.toUpperCase().equals("JURÍDICO")) {
            tfClienteEmail.setEnabled(true);
            tfClienteEmail.setBackground(Color.WHITE);
        }
    }
}
