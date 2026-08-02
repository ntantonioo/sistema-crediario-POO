package modelo;

public class Cliente extends Pessoa {

    private static final long serialVersionUID = 1L;

    // Parametros pessoais da classe Cliente, o restante vem da classse abstrata Pessoa
    private String telefone;
    private double limiteCredito;
    private double limiteUtilizado;

    public Cliente(String id, String nome, String cpf, String telefone, double limiteCredito) {
        // o super conecta o construtor da Pessoa para o construtor do Cliente
        super(id, nome, cpf);
        this.telefone = telefone;
        this.limiteCredito = limiteCredito;
        this.limiteUtilizado = 0.0;
    }

    // Métodos Getters e Setters para a busca e alteração de valores
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

    // toString para saída da resposta
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