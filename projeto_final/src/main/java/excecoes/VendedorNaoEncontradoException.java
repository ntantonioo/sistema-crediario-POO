package excecoes;

public class VendedorNaoEncontradoException extends Exception {

    private static final long serialVersionUID = 1L;

    public VendedorNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}