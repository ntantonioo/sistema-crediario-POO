package persistencia;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;

import modelo.Cliente;
import modelo.Vendedor;
import modelo.Venda;

/**
 * Classe responsável pela persistência de dados em arquivo, por meio da
 * gravação e recuperação de objetos (serialização).
 */
public class GravadorDeDados {

    public static final String NOME_ARQUIVO_VENDEDORES = "vendedores.dat";
    public static final String NOME_ARQUIVO_CLIENTES = "clientes.dat";
    public static final String NOME_ARQUIVO_VENDAS = "vendas.dat";

    //VENDEDOR

    public void salvarVendedores(Collection<Vendedor> vendedores) throws IOException {
        ObjectOutputStream gravador = null;
        try {
            gravador = new ObjectOutputStream(new FileOutputStream(NOME_ARQUIVO_VENDEDORES));
            // Copia para uma ArrayList antes de gravar: vendedores.values() do Map
            // retorna uma "view" (HashMap$Values) que NÃO é Serializable.
            gravador.writeObject(new ArrayList<>(vendedores));
        } finally {
            if (gravador != null) {
                gravador.close();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public Collection<Vendedor> recuperarVendedores() throws IOException {
        ObjectInputStream leitor = null;
        try {
            leitor = new ObjectInputStream(new FileInputStream(NOME_ARQUIVO_VENDEDORES));
            Collection<Vendedor> vendedoresRecuperados = (Collection<Vendedor>) leitor.readObject();
            return vendedoresRecuperados;
        } catch (ClassNotFoundException e) {
            throw new IOException("Classe desconhecida: " + e.getMessage());
        } finally {
            if (leitor != null) {
                leitor.close();
            }
        }
    }

    //CLIENTE

    public void salvarClientes(Collection<Cliente> clientes) throws IOException {
        ObjectOutputStream gravador = null;
        try {
            gravador = new ObjectOutputStream(new FileOutputStream(NOME_ARQUIVO_CLIENTES));
            gravador.writeObject(new ArrayList<>(clientes));
        } finally {
            if (gravador != null) {
                gravador.close();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public Collection<Cliente> recuperarClientes() throws IOException {
        ObjectInputStream leitor = null;
        try {
            leitor = new ObjectInputStream(new FileInputStream(NOME_ARQUIVO_CLIENTES));
            Collection<Cliente> clientesRecuperados = (Collection<Cliente>) leitor.readObject();
            return clientesRecuperados;
        } catch (ClassNotFoundException e) {
            throw new IOException("Classe desconhecida: " + e.getMessage());
        } finally {
            if (leitor != null) {
                leitor.close();
            }
        }
    }

    //VENDA

    public void salvarVendas(Collection<Venda> vendas) throws IOException {
        ObjectOutputStream gravador = null;
        try {
            gravador = new ObjectOutputStream(new FileOutputStream(NOME_ARQUIVO_VENDAS));
            gravador.writeObject(new ArrayList<>(vendas));
        } finally {
            if (gravador != null) {
                gravador.close();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public Collection<Venda> recuperarVendas() throws IOException {
        ObjectInputStream leitor = null;
        try {
            leitor = new ObjectInputStream(new FileInputStream(NOME_ARQUIVO_VENDAS));
            Collection<Venda> vendasRecuperadas = (Collection<Venda>) leitor.readObject();
            return vendasRecuperadas;
        } catch (ClassNotFoundException e) {
            throw new IOException("Classe desconhecida: " + e.getMessage());
        } finally {
            if (leitor != null) {
                leitor.close();
            }
        }
    }
}