package servico;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import excecoes.ClienteNaoEncontradoException;
import excecoes.LimiteCreditoExcedidoException;
import excecoes.VendaNaoEncontradaException;
import excecoes.VendedorNaoEncontradoException;
import modelo.Cliente;
import modelo.Vendedor;
import modelo.Venda;
import persistencia.GravadorDeDados;

/**
 * Implementação do sistema de vendas de crediário.
 * Utiliza Maps para armazenar Vendedores, Clientes e Vendas.
 */
public class CrediarioBoaCompra implements SistemaCrediario {

    private static final String ARQUIVO_VENDEDORES = "vendedores.csv";
    private static final String ARQUIVO_CLIENTES = "clientes.csv";
    private static final String ARQUIVO_VENDAS = "vendas.csv";

    private Map<String, Vendedor> vendedores = new HashMap<>();
    private Map<String, Cliente> clientes = new HashMap<>();
    private Map<String, Venda> vendas = new HashMap<>();

    private GravadorDeDados gravadorDeDados = new GravadorDeDados();

    //VENDEDOR

    @Override
    public void cadastrarVendedor(Vendedor vendedor) {
        vendedores.put(vendedor.getId(), vendedor);
    }

    @Override
    public void removerVendedor(String id) throws VendedorNaoEncontradoException {
        if (!vendedores.containsKey(id)) {
            throw new VendedorNaoEncontradoException("Vendedor não encontrado: " + id);
        }
        vendedores.remove(id);
    }

    @Override
    public Vendedor pesquisarVendedorPorId(String id) throws VendedorNaoEncontradoException {
        Vendedor vendedor = vendedores.get(id);
        if (vendedor == null) {
            throw new VendedorNaoEncontradoException("Vendedor não encontrado: " + id);
        }
        return vendedor;
    }

    @Override
    public List<Vendedor> pesquisarVendedorPorNome(String nome) {
        return vendedores.values().stream()
                .filter(v -> v.getNome().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());
    }

    //CLIENTE

    @Override
    public void cadastrarCliente(Cliente cliente) {
        clientes.put(cliente.getId(), cliente);
    }

    @Override
    public void removerCliente(String id) throws ClienteNaoEncontradoException {
        if (!clientes.containsKey(id)) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado: " + id);
        }
        clientes.remove(id);
    }

    @Override
    public Cliente pesquisarClientePorId(String id) throws ClienteNaoEncontradoException {
        Cliente cliente = clientes.get(id);
        if (cliente == null) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado: " + id);
        }
        return cliente;
    }

    @Override
    public List<Cliente> listarClientesComLimiteDisponivel() {
        return clientes.values().stream()
                .filter(c -> c.getLimiteDisponivel() > 0)
                .collect(Collectors.toList());
    }

    //VENDA

    @Override
    public void cadastrarVenda(Venda venda)
            throws LimiteCreditoExcedidoException, ClienteNaoEncontradoException, VendedorNaoEncontradoException {

        Cliente cliente = pesquisarClientePorId(venda.getCliente().getId());
        pesquisarVendedorPorId(venda.getVendedor().getId());

        if (cliente.getLimiteDisponivel() < venda.getValorTotal()) {
            throw new LimiteCreditoExcedidoException(
                    "Limite de crédito insuficiente para o cliente: " + cliente.getNome());
        }

        cliente.setLimiteUtilizado(cliente.getLimiteUtilizado() + venda.getValorTotal());
        vendas.put(venda.getId(), venda);
    }

    @Override
    public void removerVenda(String id) throws VendaNaoEncontradaException {
        Venda venda = vendas.get(id);
        if (venda == null) {
            throw new VendaNaoEncontradaException("Venda não encontrada: " + id);
        }
        Cliente cliente = venda.getCliente();
        cliente.setLimiteUtilizado(cliente.getLimiteUtilizado() - venda.getValorTotal());
        vendas.remove(id);
    }

    @Override
    public List<Venda> pesquisarVendasPorCliente(String clienteId) {
        return vendas.values().stream()
                .filter(v -> v.getCliente().getId().equals(clienteId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Venda> listarVendasPorVendedor(String vendedorId) {
        return vendas.values().stream()
                .filter(v -> v.getVendedor().getId().equals(vendedorId))
                .collect(Collectors.toList());
    }

    @Override
    public double calcularTotalVendidoPorVendedor(String vendedorId) {
        return vendas.values().stream()
                .filter(v -> v.getVendedor().getId().equals(vendedorId))
                .mapToDouble(Venda::getValorTotal)
                .sum();
    }

    //PERSISTENCIA

    @Override
    public void salvarDados() throws IOException {
        gravadorDeDados.salvarVendedores(vendedores.values(), ARQUIVO_VENDEDORES);
        gravadorDeDados.salvarClientes(clientes.values(), ARQUIVO_CLIENTES);
        gravadorDeDados.salvarVendas(vendas.values(), ARQUIVO_VENDAS);
    }

    @Override
    public void recuperarDados() throws IOException {
        List<Vendedor> listaVendedores = gravadorDeDados.recuperarVendedores(ARQUIVO_VENDEDORES);
        List<Cliente> listaClientes = gravadorDeDados.recuperarClientes(ARQUIVO_CLIENTES);

        vendedores = new HashMap<>();
        for (Vendedor v : listaVendedores) {
            vendedores.put(v.getId(), v);
        }

        clientes = new HashMap<>();
        for (Cliente c : listaClientes) {
            clientes.put(c.getId(), c);
        }

        List<Venda> listaVendas = gravadorDeDados.recuperarVendas(ARQUIVO_VENDAS, clientes, vendedores);
        vendas = new HashMap<>();
        for (Venda venda : listaVendas) {
            vendas.put(venda.getId(), venda);
        }
    }
}