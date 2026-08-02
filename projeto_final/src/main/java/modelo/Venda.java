package modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class Venda implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private Cliente cliente;
    private Vendedor vendedor;
    private double valorTotal;
    private int numeroParcelas;
    private LocalDate dataVenda;
    private StatusVenda status;

    public Venda(String id, Cliente cliente, Vendedor vendedor, double valorTotal,
                 int numeroParcelas, LocalDate dataVenda) {
        this.id = id;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.valorTotal = valorTotal;
        this.numeroParcelas = numeroParcelas;
        this.dataVenda = dataVenda;
        this.status = StatusVenda.ABERTA;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public int getNumeroParcelas() {
        return numeroParcelas;
    }

    public void setNumeroParcelas(int numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    public StatusVenda getStatus() {
        return status;
    }

    public void setStatus(StatusVenda status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Venda{" +
                "id='" + id + '\'' +
                ", cliente=" + cliente.getNome() +
                ", vendedor=" + vendedor.getNome() +
                ", valorTotal=" + valorTotal +
                ", numeroParcelas=" + numeroParcelas +
                ", dataVenda=" + dataVenda +
                ", status=" + status +
                '}';
    }
}