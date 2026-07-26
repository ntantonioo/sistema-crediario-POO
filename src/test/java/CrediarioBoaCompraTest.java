import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import excecoes.ClienteNaoEncontradoException;
import excecoes.LimiteCreditoExcedidoException;
import excecoes.VendaNaoEncontradaException;
import excecoes.VendedorNaoEncontradoException;
import modelo.Cliente;
import modelo.Vendedor;
import modelo.Venda;
import servico.CrediarioBoaCompra;
import servico.SistemaCrediario;

public class CrediarioBoaCompraTest {

    private SistemaCrediario sistema;

    @BeforeEach
    public void configurar() {
        sistema = new CrediarioBoaCompra();
    }

    @Test
    public void testCadastrarEPesquisarVendedor() throws VendedorNaoEncontradoException {
        Vendedor vendedor = new Vendedor("V1", "Abner", "111.111.111-11", 5.0);
        sistema.cadastrarVendedor(vendedor);

        Vendedor encontrado = sistema.pesquisarVendedorPorId("V1");
        assertEquals("João", encontrado.getNome());

        List<Vendedor> porNome = sistema.pesquisarVendedorPorNome("joão");
        assertEquals(1, porNome.size());
    }

    @Test
    public void testCadastrarEPesquisarCliente() throws ClienteNaoEncontradoException {
        Cliente cliente = new Cliente("C1", "Luiza", "222.222.222-22", "83999990000", 1000.0);
        sistema.cadastrarCliente(cliente);

        Cliente encontrado = sistema.pesquisarClientePorId("C1");
        assertEquals("Maria", encontrado.getNome());

        List<Cliente> comLimite = sistema.listarClientesComLimiteDisponivel();
        assertEquals(1, comLimite.size());
    }

    @Test
    public void testCadastrarVendaComSucesso() throws ClienteNaoEncontradoException,
            VendedorNaoEncontradoException, LimiteCreditoExcedidoException {

        Vendedor vendedor = new Vendedor("V1", "Abner", "111.111.111-11", 5.0);
        Cliente cliente = new Cliente("C1", "Luiza", "222.222.222-22", "83999990000", 1000.0);
        sistema.cadastrarVendedor(vendedor);
        sistema.cadastrarCliente(cliente);

        Venda venda = new Venda("VD1", cliente, vendedor, 300.0, 3, LocalDate.now());
        sistema.cadastrarVenda(venda);

        List<Venda> vendasDoCliente = sistema.pesquisarVendasPorCliente("C1");
        assertEquals(1, vendasDoCliente.size());

        double total = sistema.calcularTotalVendidoPorVendedor("V1");
        assertEquals(300.0, total);
    }

    @Test
    public void testCadastrarVendaExcedeLimiteLancaExcecao() throws ClienteNaoEncontradoException,
            VendedorNaoEncontradoException {

        Vendedor vendedor = new Vendedor("V1", "Abner", "111.111.111-11", 5.0);
        Cliente cliente = new Cliente("C1", "Luiza", "222.222.222-22", "83999990000", 100.0);
        sistema.cadastrarVendedor(vendedor);
        sistema.cadastrarCliente(cliente);

        Venda venda = new Venda("VD1", cliente, vendedor, 500.0, 3, LocalDate.now());

        assertThrows(LimiteCreditoExcedidoException.class, () -> sistema.cadastrarVenda(venda));
    }

    @Test
    public void testRemoverVendedorClienteVenda() throws ClienteNaoEncontradoException,
            VendedorNaoEncontradoException, LimiteCreditoExcedidoException, VendaNaoEncontradaException {

        Vendedor vendedor = new Vendedor("V1", "Abner", "111.111.111-11", 5.0);
        Cliente cliente = new Cliente("C1", "Luiza", "222.222.222-22", "83999990000", 1000.0);
        sistema.cadastrarVendedor(vendedor);
        sistema.cadastrarCliente(cliente);

        Venda venda = new Venda("VD1", cliente, vendedor, 300.0, 3, LocalDate.now());
        sistema.cadastrarVenda(venda);

        sistema.removerVenda("VD1");
        assertTrue(sistema.pesquisarVendasPorCliente("C1").isEmpty());

        sistema.removerCliente("C1");
        sistema.removerVendedor("V1");

        assertThrows(ClienteNaoEncontradoException.class, () -> sistema.pesquisarClientePorId("C1"));
        assertThrows(VendedorNaoEncontradoException.class, () -> sistema.pesquisarVendedorPorId("V1"));
    }

    @Test
    public void testListarVendasPorVendedor() throws ClienteNaoEncontradoException,
            VendedorNaoEncontradoException, LimiteCreditoExcedidoException {

        Vendedor vendedor = new Vendedor("V1", "Abner", "111.111.111-11", 5.0);
        Cliente cliente = new Cliente("C1", "Luiza", "222.222.222-22", "83999990000", 1000.0);
        sistema.cadastrarVendedor(vendedor);
        sistema.cadastrarCliente(cliente);

        sistema.cadastrarVenda(new Venda("VD1", cliente, vendedor, 200.0, 2, LocalDate.now()));
        sistema.cadastrarVenda(new Venda("VD2", cliente, vendedor, 150.0, 1, LocalDate.now()));

        List<Venda> vendasDoVendedor = sistema.listarVendasPorVendedor("V1");
        assertEquals(2, vendasDoVendedor.size());
    }

    @Test
    public void testRemoverVendaInexistenteLancaExcecao() {
        assertThrows(VendaNaoEncontradaException.class, () -> sistema.removerVenda("INEXISTENTE"));
    }

    @Test
    public void testSalvarERecuperarDados() throws ClienteNaoEncontradoException,
            VendedorNaoEncontradoException, LimiteCreditoExcedidoException, IOException {

        Vendedor vendedor = new Vendedor("V1", "Abner", "111.111.111-11", 5.0);
        Cliente cliente = new Cliente("C1", "Luiza", "222.222.222-22", "83999990000", 1000.0);
        sistema.cadastrarVendedor(vendedor);
        sistema.cadastrarCliente(cliente);
        sistema.cadastrarVenda(new Venda("VD1", cliente, vendedor, 300.0, 3, LocalDate.now()));

        sistema.salvarDados();

        SistemaCrediario novoSistema = new CrediarioBoaCompra();
        novoSistema.recuperarDados();

        assertEquals("Abner", novoSistema.pesquisarVendedorPorId("V1").getNome());
        assertEquals("Luiza", novoSistema.pesquisarClientePorId("C1").getNome());
        assertFalse(novoSistema.pesquisarVendasPorCliente("C1").isEmpty());
    }
}