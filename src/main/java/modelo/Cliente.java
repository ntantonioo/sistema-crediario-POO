package modelo;

public class Cliente extends Pessoa {

    //Atributos "únicos" para a Classe Cliente
    // O restante dos atributos necessários para o cliente estão presentes na classe Pessoa
    private String telefone;
    private double limiteCredito;
    private double limiteUtilizado;

    // Construtor para a busca e/ou alteração de dados
    public Cliente(String id, String nome, String cpf, String telefone, double limiteCredito) {
        super(id, nome, cpf);
        this.telefone = telefone;
        this.limiteCredito = limiteCredito;
        this.limiteUtilizado = 0.0;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public double getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public double getLimiteUtilizado() {
        return limiteUtilizado;
    }

    public void setLimiteUtilizado(double limiteUtilizado) {
        this.limiteUtilizado = limiteUtilizado;
    }

    public double getLimiteDisponivel() {
        return limiteCredito - limiteUtilizado;
    }

    // toString para a saida da mensagem mais "bonitinha" e organizada na aba do Cliente
    @Override
    public String toString() {
        return "Cliente{" +
                "id='" + getId() + '\'' +
                ", nome='" + getNome() + '\'' +
                ", cpf='" + getCpf() + '\'' +
                ", telefone='" + telefone + '\'' +
                ", limiteCredito=" + limiteCredito +
                ", limiteUtilizado=" + limiteUtilizado +
                '}';
    }
}