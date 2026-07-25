package modelo;

public class Vendedor extends Pessoa {

    // Parametro da classe Vendedor
    // O restante ja vêm da classe Pessoa (extends Pessoa)
    private double percentualComissao;

    public Vendedor(String id, String nome, String cpf, double percentualComissao) {
        super(id, nome, cpf);
        this.percentualComissao = percentualComissao;
    }

    public double getPercentualComissao() {
        return percentualComissao;
    }

    public void setPercentualComissao(double percentualComissao) {
        this.percentualComissao = percentualComissao;
    }

    // toString para a mensagem de saida do Vendedor mais organizada e estilizada
    @Override
    public String toString() {
        return "Vendedor{" +
                "id='" + getId() + '\'' +
                ", nome='" + getNome() + '\'' +
                ", cpf='" + getCpf() + '\'' +
                ", percentualComissao=" + percentualComissao +
                '}';
    }
}