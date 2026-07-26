package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import modelo.Cliente;
import modelo.StatusVenda;
import modelo.Vendedor;
import modelo.Venda;

/**
 * Classe responsável pela persistência de dados em arquivo de texto (.txt),
 * gravando e recuperando os objetos do sistema. Cada linha do arquivo representa
 * um objeto, com os campos separados por ";". Por ser texto puro, o arquivo pode
 * ser aberto e conferido em qualquer editor de texto comum.
 */
public class GravadorDeDados {

    public static final String NOME_ARQUIVO_VENDEDORES = "vendedores.txt";
    public static final String NOME_ARQUIVO_CLIENTES = "clientes.txt";
    public static final String NOME_ARQUIVO_VENDAS = "vendas.txt";

    private static final String SEPARADOR = ";";

    // VENDEDOR
    public void salvarVendedores(Collection<Vendedor> vendedores) throws IOException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(NOME_ARQUIVO_VENDEDORES));
            for (Vendedor v : vendedores) {
                writer.write(v.getId() + SEPARADOR + v.getNome() + SEPARADOR
                        + v.getCpf() + SEPARADOR + v.getPercentualComissao());
                writer.newLine();
            }
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public List<Vendedor> recuperarVendedores() throws IOException {
        List<Vendedor> vendedores = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(NOME_ARQUIVO_VENDEDORES));
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.isBlank()) continue;
                String[] campos = linha.split(SEPARADOR);
                Vendedor v = new Vendedor(campos[0], campos[1], campos[2], Double.parseDouble(campos[3]));
                vendedores.add(v);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return vendedores;
    }

    // CLIENTE

    public void salvarClientes(Collection<Cliente> clientes) throws IOException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(NOME_ARQUIVO_CLIENTES));
            for (Cliente c : clientes) {
                writer.write(c.getId() + SEPARADOR + c.getNome() + SEPARADOR + c.getCpf() + SEPARADOR
                        + c.getTelefone() + SEPARADOR + c.getLimiteCredito() + SEPARADOR + c.getLimiteUtilizado());
                writer.newLine();
            }
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public List<Cliente> recuperarClientes() throws IOException {
        List<Cliente> clientes = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(NOME_ARQUIVO_CLIENTES));
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.isBlank()) continue;
                String[] campos = linha.split(SEPARADOR);
                Cliente c = new Cliente(campos[0], campos[1], campos[2], campos[3], Double.parseDouble(campos[4]));
                c.setLimiteUtilizado(Double.parseDouble(campos[5]));
                clientes.add(c);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return clientes;
    }

    // VENDA

    public void salvarVendas(Collection<Venda> vendas) throws IOException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(NOME_ARQUIVO_VENDAS));
            for (Venda venda : vendas) {
                writer.write(venda.getId() + SEPARADOR + venda.getCliente().getId() + SEPARADOR
                        + venda.getVendedor().getId() + SEPARADOR + venda.getValorTotal() + SEPARADOR
                        + venda.getNumeroParcelas() + SEPARADOR + venda.getDataVenda() + SEPARADOR
                        + venda.getStatus());
                writer.newLine();
            }
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * Recupera as vendas relinkando cada uma ao respectivo Cliente e Vendedor,
     * a partir dos Maps já carregados previamente (o arquivo de vendas guarda
     * apenas os ids do cliente e do vendedor, não os dados inteiros).
     */
    public List<Venda> recuperarVendas(Map<String, Cliente> clientes, Map<String, Vendedor> vendedores)
            throws IOException {
        List<Venda> vendas = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(NOME_ARQUIVO_VENDAS));
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.isBlank()) continue;
                String[] campos = linha.split(SEPARADOR);
                Cliente cliente = clientes.get(campos[1]);
                Vendedor vendedor = vendedores.get(campos[2]);
                Venda venda = new Venda(campos[0], cliente, vendedor, Double.parseDouble(campos[3]),
                        Integer.parseInt(campos[4]), LocalDate.parse(campos[5]));
                venda.setStatus(StatusVenda.valueOf(campos[6]));
                vendas.add(venda);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return vendas;
    }
}