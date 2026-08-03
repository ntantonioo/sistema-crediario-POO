package modelo;

import java.io.Serializable;

public class Vendedor extends Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

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