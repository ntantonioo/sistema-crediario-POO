package servico;

import java.io.IOException;
import java.util.List;

import excecoes.ClienteNaoEncontradoException;
import excecoes.LimiteCreditoExcedidoException;
import excecoes.VendaNaoEncontradaException;
import excecoes.VendedorNaoEncontradoException;
import modelo.Cliente;
import modelo.Vendedor;
import modelo.Venda;

/**
 * Interface (Façade) do Sistema de Vendas de Crediário.
 * Reúne as principais funcionalidades relacionadas a Vendedor, Cliente e Venda.
 * Contando tambem com seus Exception
 */
public interface SistemaCrediario {

    // Vendedor
    void cadastrarVendedor(Vendedor vendedor);

    void removerVendedor(String id) throws VendedorNaoEncontradoException;

    Vendedor pesquisarVendedorPorId(String id) throws VendedorNaoEncontradoException;

    List<Vendedor> pesquisarVendedorPorNome(String nome);

    // Cliente
    void cadastrarCliente(Cliente cliente);

    void removerCliente(String id) throws ClienteNaoEncontradoException;

    Cliente pesquisarClientePorId(String id) throws ClienteNaoEncontradoException;

    List<Cliente> listarClientesComLimiteDisponivel();

    // Venda
    void cadastrarVenda(Venda venda)
            throws LimiteCreditoExcedidoException, ClienteNaoEncontradoException, VendedorNaoEncontradoException;

    void removerVenda(String id) throws VendaNaoEncontradaException;

    List<Venda> pesquisarVendasPorCliente(String clienteId);

    List<Venda> listarVendasPorVendedor(String vendedorId);

    double calcularTotalVendidoPorVendedor(String vendedorId);

    // Persistência
    void salvarDados() throws IOException;

    void recuperarDados() throws IOException;
}