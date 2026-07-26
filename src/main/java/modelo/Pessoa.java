package modelo;

/**
 * Classe abstract que guarda parametros que se repetem, assim evitando um código de "macarrão", ou seja, bagunçado
 * ela guarda o id, nome e cpf. As outras classes implentam ela para buscar certos valores
 */
public abstract class Pessoa {

    private String id;
    private String nome;
    private String cpf;

    public Pessoa(String id, String nome, String cpf) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
    }

    //Métodos Getters e Setters
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