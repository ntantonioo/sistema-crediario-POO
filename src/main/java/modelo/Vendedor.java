package modelo;

public class Vendedor extends Pessoa {

    // O Vendedor apenas possui o percentualComissao como parametro unico
    private double percentualComissao;

    public Vendedor(String id, String nome, String cpf, double percentualComissao) {
        //super que vem da classe Pessoa
        super(id, nome, cpf);
        this.percentualComissao = percentualComissao;
    }

    public double getPercentualComissao() {
        return percentualComissao;
    }

    public void setPercentualComissao(double percentualComissao) {
        this.percentualComissao = percentualComissao;
    }

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