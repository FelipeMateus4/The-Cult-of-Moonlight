package ppoo.seuJogo;

import java.util.Scanner;

/**
 * Esta classe é parte da aplicacao "World of Zuul". "World of Zuul" é um jogo
 * de aventura muito simples, baseado em texto.
 * 
 * Esse analisador lê a entrada do usuario e tenta interpretá-la como um comando
 * "Adventure". Cada vez que é chamado, ele lê uma linha do terminal e tenta
 * interpretar a linha como um comando de duas palavras. Ele retorna o comando
 * como um objeto da classe Comando.
 *
 * O analisador tem um conjunto de palavras de comando conhecidas. Ele compara a
 * entrada do usuário com os comandos conhecidos, e se a entrada não é um dos
 * comandos conhecidos, ele retorna um objeto comando que é marcado como um
 * comando desconhecido.
 * 
 * @author Michael Kölling and David J. Barnes (traduzido e adaptado por Julio
 *         César Alves)
 */
public class Analisador {
    // guarda todas as palavras de comando validas
    private PalavrasComando palavrasDeComando;
    // origem da entrada de comandos
    private Scanner entrada;

    /**
     * Cria um analisador para ler do terminal.
     */
    public Analisador() {
        palavrasDeComando = new PalavrasComando();
        entrada = new Scanner(System.in);
    }

    /**
     * @return O próximo comando do usuario
     */
    public Comando pegarComando() {
        // guardará uma linha inteira
        String linha;
        // guardará até duas palavras usadas no comando
        String palavra1 = null;
        String palavra2 = null;

        // imprime o prompt
        System.out.print("> ");

        // obtém uma linha de comando do usuário
        linha = entrada.nextLine();

        // quebra o comando do usuário em várias palavras, usando espaços em branco como
        // separadores.
        // Exemplo: se ele digitar "ir norte", o comando vai gerar um vetor com as duas
        // palavras ["ir", "norte"].
        String[] palavras = linha.trim().split("\\s+");

        // guarda a primeira e a segunda palavras digitadas pelo usuário, se houverem.
        if (palavras.length >= 1) {
            palavra1 = palavras[0];
        }
        if (palavras.length >= 2) {
            palavra2 = palavras[1];
        }

        // Cria um comando de acordo com as palavras obtidas
        return new Comando(palavrasDeComando.obterPalavraDeComando(palavra1), palavra2);
    }

    /**
     * Retorna as palavras de comando válidas do jogo
     * 
     * @return Uma lista que representa os comandos válidos do jogo
     */
    public String getComandosValidos() {
        String comandos = "";
        for (PalavraDeComando palavraDeComando : palavrasDeComando.getComandosValidos()) {
            comandos += palavraDeComando + " ";
        }
        return comandos;
    }
}
