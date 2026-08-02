package persistencia;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import modelo.Cliente;
import modelo.Vendedor;
import modelo.Venda;

/**
 * Classe responsável pela persistência de dados em arquivo (.dat),
 * gravando e recuperando os OBJETOS do sistema por meio de serialização Java
 * (ObjectOutputStream / ObjectInputStream). As classes de modelo (Pessoa,
 * Cliente, Vendedor e Venda) implementam Serializable para que suas instâncias
 * possam ser gravadas e recuperadas , mantendo o estado de cada objeto.
 */
public class GravadorDeDados {

    public static final String NOME_ARQUIVO_VENDEDORES = "vendedores.dat";
    public static final String NOME_ARQUIVO_CLIENTES = "clientes.dat";
    public static final String NOME_ARQUIVO_VENDAS = "vendas.dat";

    // MÉTODOS AUXILIARES DE SERIALIZAÇÃO

    /**
     * Grava uma lista de objetos serializáveis em um arquivo, usando ObjectOutputStream.
     */
    private void salvarObjetos(String nomeArquivo, List<? extends Object> objetos) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            out.writeObject(objetos);
        }
    }

    /**
     * Recupera uma lista de objetos previamente gravada em um arquivo, usando ObjectInputStream.
     */
    @SuppressWarnings("unchecked")
    private <T> List<T> recuperarObjetos(String nomeArquivo) throws IOException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            return (List<T>) in.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Erro ao recuperar objetos do arquivo " + nomeArquivo, e);
        }
    }

    // VENDEDOR

    public void salvarVendedores(Collection<Vendedor> vendedores) throws IOException {
        salvarObjetos(NOME_ARQUIVO_VENDEDORES, new ArrayList<>(vendedores));
    }

    public List<Vendedor> recuperarVendedores() throws IOException {
        return recuperarObjetos(NOME_ARQUIVO_VENDEDORES);
    }

    // CLIENTE

    public void salvarClientes(Collection<Cliente> clientes) throws IOException {
        salvarObjetos(NOME_ARQUIVO_CLIENTES, new ArrayList<>(clientes));
    }

    public List<Cliente> recuperarClientes() throws IOException {
        return recuperarObjetos(NOME_ARQUIVO_CLIENTES);
    }

    // VENDA

    public void salvarVendas(Collection<Venda> vendas) throws IOException {
        salvarObjetos(NOME_ARQUIVO_VENDAS, new ArrayList<>(vendas));
    }

    /**
     * Recupera as vendas gravadas e relinka cada uma ao respectivo Cliente e Vendedor
     * já carregados nos Maps informados. Isso garante que a Venda recuperada aponte
     * para a MESMA instância de Cliente/Vendedor usada pelo restante do sistema
     * (e não para uma cópia independente criada pela desserialização).
     */
    public List<Venda> recuperarVendas(Map<String, Cliente> clientes, Map<String, Vendedor> vendedores)
            throws IOException {
        List<Venda> vendasRecuperadas = recuperarObjetos(NOME_ARQUIVO_VENDAS);
        List<Venda> vendas = new ArrayList<>();
        for (Venda venda : vendasRecuperadas) {
            Cliente cliente = clientes.get(venda.getCliente().getId());
            Vendedor vendedor = vendedores.get(venda.getVendedor().getId());
            venda.setCliente(cliente);
            venda.setVendedor(vendedor);
            vendas.add(venda);
        }
        return vendas;
    }
}
