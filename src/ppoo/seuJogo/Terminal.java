package ppoo.seuJogo;

import ppoo.baseJogo.EntidadeGrafica;
import ppoo.baseJogo.InterfaceUsuario;

import java.util.Scanner;

public class Terminal implements InterfaceUsuario {
    private Scanner scanner;

    public Terminal() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void exibirMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    @Override
    public String obterComando() {
        return scanner.nextLine();
    }

    @Override
    public String obterInformacao(String instrucao) {
        System.out.println(instrucao);
        return scanner.nextLine();
    }

    @Override
    public void ambienteAtualMudou(EntidadeGrafica ambiente) {
    }

    @Override
    public void jogadorPegouItem(EntidadeGrafica item) {
        // Não precisa fazer nada para terminal, mas vamos exibir uma mensagem de exemplo
        System.out.println("Você pegou um item: " + item.getNome());
    }

    @Override
    public void jogadorDescartouItem(EntidadeGrafica item) {
        // Não precisa fazer nada para terminal, mas vamos exibir uma mensagem de exemplo
        System.out.println("Você descartou um item: " + item.getNome());
    }

    @Override
    public void limparMensagens() {
        // Não precisa fazer nada para terminal, pois as mensagens não são acumuladas.
    }
}
