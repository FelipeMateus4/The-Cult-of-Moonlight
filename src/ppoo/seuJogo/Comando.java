package ppoo.seuJogo;

/**
 * Esta classe é parte da aplicação "World of Zuul". "World of Zuul" é um jogo
 * de aventura muito simples, baseado em texto.
 *
 * Essa classe guarda informações sobre um comando que foi digitado pelo
 * usuário. Um comando atualmente consiste em duas strings: uma palavra de
 * comando e uma segunda palavra (por exemplo, se o campo for "pegar mapa",
 * entao as duas strings obviamente serao "pegar" e "mapa").
 * 
 * Isso é usado assim: comandos já estão validados como comandos válidos. Se o
 * usuário entrou um comando inválido (uma palavra que não é conhecida) então a
 * palavra de comando é <null>.
 *
 * Se o comando tem só uma palavra, a segunda palavra é <null>.
 * 
 * @author Michael Kölling and David J. Barnes (traduzido e adaptado por Julio
 *         César Alves)
 */

public class Comando {
    // PalavraDeComando (correspondente à primeira palavra)
    private PalavraDeComando palavraDeComando;
    // segunda palavra que representa um complemento do comando
    private String segundaPalavra;

    /**
     * Cria um objeto comando. Primeira e segunda palavra devem ser fornecidas, mas
     * a segunda palavra ppde ser null.
     * 
     * @param primeiraPalavra Objeto da primeira palavra do comando.
     * @param segundaPalavra  A segunda palavra do comando. Null se não existir
     *                        segunda palavra
     */
    public Comando(PalavraDeComando primeiraPalavra, String segundaPalavra) {
        palavraDeComando = primeiraPalavra;
        this.segundaPalavra = segundaPalavra;
    }

    /**
     * Retorna a palavra de comando (a primeira palavra) deste comando. Se o comando
     * não foi entendido, o resultado eh null.
     * 
     * @return A palavra de comando.
     */
    public PalavraDeComando getPalavraDeComando() {
        return palavraDeComando;
    }

    /**
     * @return A segunda palavra deste comando. Retorna null se não existe segunda
     *         palavra.
     */
    public String getSegundaPalavra() {
        return segundaPalavra;
    }

    /**
     * @return true se o comando não foi entendido.
     */
    public boolean ehDesconhecido() {
        return (palavraDeComando == PalavraDeComando.DESCONHECIDA);
    }

    /**
     * @return true se o comando tem uma segunda palavra.
     */
    public boolean temSegundaPalavra() {
        return (segundaPalavra != null);
    }
}
