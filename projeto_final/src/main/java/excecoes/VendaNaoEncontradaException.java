package excecoes;

public class VendaNaoEncontradaException extends Exception {

    private static final long serialVersionUID = 1L;

    public VendaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}