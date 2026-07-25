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
 * essa é a Classe responsável pela persistência de dados em arquivo de texto (CSV/TXT),
 * gravando e recuperando os objetos do sistema. Cada linha do arquivo representa
 * um objeto, com os campos separados por ;.
 */
public class GravadorDeDados {

    private static final String SEPARADOR = ";";

    //VENDEDOR

    public void salvarVendedores(Collection<Vendedor> vendedores, String caminhoArquivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (Vendedor v : vendedores) {
                writer.write(v.getId() + SEPARADOR + v.getNome() + SEPARADOR
                        + v.getCpf() + SEPARADOR + v.getPercentualComissao());
                writer.newLine();
            }
        }
    }

    public List<Vendedor> recuperarVendedores(String caminhoArquivo) throws IOException {
        List<Vendedor> vendedores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.isBlank()) continue;
                String[] campos = linha.split(SEPARADOR);
                Vendedor v = new Vendedor(campos[0], campos[1], campos[2], Double.parseDouble(campos[3]));
                vendedores.add(v);
            }
        }
        return vendedores;
    }

    // CLIENTE

    public void salvarClientes(Collection<Cliente> clientes, String caminhoArquivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (Cliente c : clientes) {
                writer.write(c.getId() + SEPARADOR + c.getNome() + SEPARADOR + c.getCpf() + SEPARADOR
                        + c.getTelefone() + SEPARADOR + c.getLimiteCredito() + SEPARADOR + c.getLimiteUtilizado());
                writer.newLine();
            }
        }
    }

    public List<Cliente> recuperarClientes(String caminhoArquivo) throws IOException {
        List<Cliente> clientes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.isBlank()) continue;
                String[] campos = linha.split(SEPARADOR);
                Cliente c = new Cliente(campos[0], campos[1], campos[2], campos[3], Double.parseDouble(campos[4]));
                c.setLimiteUtilizado(Double.parseDouble(campos[5]));
                clientes.add(c);
            }
        }
        return clientes;
    }

    // VENDA

    public void salvarVendas(Collection<Venda> vendas, String caminhoArquivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (Venda venda : vendas) {
                writer.write(venda.getId() + SEPARADOR + venda.getCliente().getId() + SEPARADOR
                        + venda.getVendedor().getId() + SEPARADOR + venda.getValorTotal() + SEPARADOR
                        + venda.getNumeroParcelas() + SEPARADOR + venda.getDataVenda() + SEPARADOR
                        + venda.getStatus());
                writer.newLine();
            }
        }
    }

    /**
     * Recupera as vendas relinkando cada uma ao respectivo Cliente e Vendedor,
     * a partir dos Maps já carregados previamente.
     */
    public List<Venda> recuperarVendas(String caminhoArquivo, Map<String, Cliente> clientes,
                                       Map<String, Vendedor> vendedores) throws IOException {
        List<Venda> vendas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
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
        }
        return vendas;
    }
}