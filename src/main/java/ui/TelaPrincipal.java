package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import excecoes.ClienteNaoEncontradoException;
import excecoes.LimiteCreditoExcedidoException;
import excecoes.VendaNaoEncontradaException;
import excecoes.VendedorNaoEncontradoException;
import modelo.Cliente;
import modelo.Vendedor;
import modelo.Venda;
import servico.CrediarioBoaCompra;
import servico.SistemaCrediario;

/**
 * Interface gráfica do Sistema de Vendas de Crediário.
 * Apresenta uma barra de menu com as funcionalidades do sistema.
 */
public class TelaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;

    private SistemaCrediario sistema = new CrediarioBoaCompra();
    private JTextArea areaSaida = new JTextArea();

    public TelaPrincipal() {
        super("Sistema de Vendas de Crediário");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        areaSaida.setEditable(false);
        add(new JScrollPane(areaSaida), BorderLayout.CENTER);

        setJMenuBar(criarBarraDeMenu());
    }

    private JMenuBar criarBarraDeMenu() {
        JMenuBar menuBar = new JMenuBar();

        // Menu do Vendedor (Cadastro; Pesquisa por nome; remoção)
        JMenu menuVendedor = new JMenu("Vendedor");
        JMenuItem itemCadastrarVendedor = new JMenuItem("Cadastrar");
        JMenuItem itemPesquisarVendedor = new JMenuItem("Pesquisar por nome");
        JMenuItem itemRemoverVendedor = new JMenuItem("Remover");
        itemCadastrarVendedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarVendedor();
            }
        });
        itemPesquisarVendedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pesquisarVendedorPorNome();
            }
        });
        itemRemoverVendedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerVendedor();
            }
        });
        menuVendedor.add(itemCadastrarVendedor);
        menuVendedor.add(itemPesquisarVendedor);
        menuVendedor.add(itemRemoverVendedor);

        // Menu do Cliente (Cadastro; Listagem de limite disponível para compra; Remoção)
        JMenu menuCliente = new JMenu("Cliente");
        JMenuItem itemCadastrarCliente = new JMenuItem("Cadastrar");
        JMenuItem itemListarClientesLimite = new JMenuItem("Listar com limite disponível");
        JMenuItem itemRemoverCliente = new JMenuItem("Remover");
        itemCadastrarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarCliente();
            }
        });
        itemListarClientesLimite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarClientesComLimiteDisponivel();
            }
        });
        itemRemoverCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerCliente();
            }
        });
        menuCliente.add(itemCadastrarCliente);
        menuCliente.add(itemListarClientesLimite);
        menuCliente.add(itemRemoverCliente);

        // Menu da Venda (Cadastro; pesquisa por cliente; listagem de vendas por vendedor; total vendido por vendedor; remoção)
        JMenu menuVenda = new JMenu("Venda");
        JMenuItem itemCadastrarVenda = new JMenuItem("Cadastrar");
        JMenuItem itemPesquisarVendasCliente = new JMenuItem("Pesquisar por cliente");
        JMenuItem itemListarVendasVendedor = new JMenuItem("Listar por vendedor");
        JMenuItem itemTotalVendedor = new JMenuItem("Total vendido por vendedor");
        JMenuItem itemRemoverVenda = new JMenuItem("Remover");
        itemCadastrarVenda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarVenda();
            }
        });
        itemPesquisarVendasCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pesquisarVendasPorCliente();
            }
        });
        itemListarVendasVendedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarVendasPorVendedor();
            }
        });
        itemTotalVendedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularTotalVendidoPorVendedor();
            }
        });
        itemRemoverVenda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerVenda();
            }
        });
        menuVenda.add(itemCadastrarVenda);
        menuVenda.add(itemPesquisarVendasCliente);
        menuVenda.add(itemListarVendasVendedor);
        menuVenda.add(itemTotalVendedor);
        menuVenda.add(itemRemoverVenda);

        // Menu do Arquivo (Salvar os dados e recuperação dos dados)
        JMenu menuArquivo = new JMenu("Arquivo");
        JMenuItem itemSalvar = new JMenuItem("Salvar dados");
        JMenuItem itemRecuperar = new JMenuItem("Recuperar dados");
        itemSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarDados();
            }
        });
        itemRecuperar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recuperarDados();
            }
        });
        menuArquivo.add(itemSalvar);
        menuArquivo.add(itemRecuperar);

        menuBar.add(menuVendedor);
        menuBar.add(menuCliente);
        menuBar.add(menuVenda);
        menuBar.add(menuArquivo);
        return menuBar;
    }

    private void cadastrarVendedor() {
        String id = JOptionPane.showInputDialog(this, "ID do vendedor:");
        if (id == null) return;
        String nome = JOptionPane.showInputDialog(this, "Nome:");
        String cpf = JOptionPane.showInputDialog(this, "CPF:");
        String comissaoStr = JOptionPane.showInputDialog(this, "Percentual de comissão:");
        try {
            double comissao = Double.parseDouble(comissaoStr);
            sistema.cadastrarVendedor(new Vendedor(id, nome, cpf, comissao));
            areaSaida.append("Vendedor cadastrado: " + nome + "\n");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Percentual de comissão inválido.");
        }
    }

    private void pesquisarVendedorPorNome() {
        String nome = JOptionPane.showInputDialog(this, "Nome (ou parte) do vendedor:");
        if (nome == null) return;
        List<Vendedor> encontrados = sistema.pesquisarVendedorPorNome(nome);
        areaSaida.append("Vendedores encontrados: " + encontrados + "\n");
    }

    private void removerVendedor() {
        String id = JOptionPane.showInputDialog(this, "ID do vendedor a remover:");
        if (id == null) return;
        try {
            sistema.removerVendedor(id);
            areaSaida.append("Vendedor removido: " + id + "\n");
        } catch (VendedorNaoEncontradoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void cadastrarCliente() {
        String id = JOptionPane.showInputDialog(this, "ID do cliente:");
        if (id == null) return;
        String nome = JOptionPane.showInputDialog(this, "Nome:");
        String cpf = JOptionPane.showInputDialog(this, "CPF:");
        String telefone = JOptionPane.showInputDialog(this, "Telefone:");
        String limiteStr = JOptionPane.showInputDialog(this, "Limite de crédito:");
        try {
            double limite = Double.parseDouble(limiteStr);
            sistema.cadastrarCliente(new Cliente(id, nome, cpf, telefone, limite));
            areaSaida.append("Cliente cadastrado: " + nome + "\n");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Limite de crédito inválido.");
        }
    }

    private void listarClientesComLimiteDisponivel() {
        List<Cliente> clientes = sistema.listarClientesComLimiteDisponivel();
        areaSaida.append("Clientes com limite disponível: " + clientes + "\n");
    }

    private void removerCliente() {
        String id = JOptionPane.showInputDialog(this, "ID do cliente a remover:");
        if (id == null) return;
        try {
            sistema.removerCliente(id);
            areaSaida.append("Cliente removido: " + id + "\n");
        } catch (ClienteNaoEncontradoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void cadastrarVenda() {
        String id = JOptionPane.showInputDialog(this, "ID da venda:");
        if (id == null) return;
        String clienteId = JOptionPane.showInputDialog(this, "ID do cliente:");
        String vendedorId = JOptionPane.showInputDialog(this, "ID do vendedor:");
        String valorStr = JOptionPane.showInputDialog(this, "Valor total:");
        String parcelasStr = JOptionPane.showInputDialog(this, "Número de parcelas:");
        try {
            Cliente cliente = sistema.pesquisarClientePorId(clienteId);
            Vendedor vendedor = sistema.pesquisarVendedorPorId(vendedorId);
            double valor = Double.parseDouble(valorStr);
            int parcelas = Integer.parseInt(parcelasStr);
            Venda venda = new Venda(id, cliente, vendedor, valor, parcelas, LocalDate.now());
            sistema.cadastrarVenda(venda);
            areaSaida.append("Venda cadastrada: " + id + "\n");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Valor ou número de parcelas inválido.");
        } catch (ClienteNaoEncontradoException | VendedorNaoEncontradoException
                 | LimiteCreditoExcedidoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void pesquisarVendasPorCliente() {
        String clienteId = JOptionPane.showInputDialog(this, "ID do cliente:");
        if (clienteId == null) return;
        List<Venda> encontradas = sistema.pesquisarVendasPorCliente(clienteId);
        areaSaida.append("Vendas do cliente " + clienteId + ": " + encontradas + "\n");
    }

    private void listarVendasPorVendedor() {
        String vendedorId = JOptionPane.showInputDialog(this, "ID do vendedor:");
        if (vendedorId == null) return;
        List<Venda> vendas = sistema.listarVendasPorVendedor(vendedorId);
        areaSaida.append("Vendas do vendedor " + vendedorId + ": " + vendas + "\n");
    }

    private void calcularTotalVendidoPorVendedor() {
        String vendedorId = JOptionPane.showInputDialog(this, "ID do vendedor:");
        if (vendedorId == null) return;
        double total = sistema.calcularTotalVendidoPorVendedor(vendedorId);
        areaSaida.append("Total vendido pelo vendedor " + vendedorId + ": " + total + "\n");
    }

    private void removerVenda() {
        String id = JOptionPane.showInputDialog(this, "ID da venda a remover:");
        if (id == null) return;
        try {
            sistema.removerVenda(id);
            areaSaida.append("Venda removida: " + id + "\n");
        } catch (VendaNaoEncontradaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void salvarDados() {
        try {
            sistema.salvarDados();
            areaSaida.append("Dados salvos com sucesso.\n");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar dados: " + ex.getMessage());
        }
    }

    private void recuperarDados() {
        try {
            sistema.recuperarDados();
            areaSaida.append("Dados recuperados com sucesso.\n");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao recuperar dados: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        TelaPrincipal tela = new TelaPrincipal();
        tela.setVisible(true);
    }
}