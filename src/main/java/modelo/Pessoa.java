package modelo;

public abstract class Pessoa {
    /**
     * A classe Pessoa foi feita para ser uma classe Abstrata que guarda parametros ultilizados
    tanto pela classe Cliente como pela Vendedor.
     * Evita repetições de parametros como "id","nome","cpf"
     */
    private String id;
    private String nome;
    private String cpf;

    public Pessoa(String id, String nome, String cpf) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}