package ppoo.seuJogo;

import java.util.ArrayList;
import java.util.*;

/**
 * Essa é a classe principal da aplicacao "World of Zull". "World of Zuul" é um
 * jogo de aventura muito simples, baseado em texto.
 * 
 * Usuários podem caminhar em um cenário. E é tudo! Ele realmente precisa ser
 * estendido para fazer algo interessante!
 * 
 * Para jogar esse jogo, crie uma instancia dessa classe e chame o método
 * "jogar".
 * 
 * Essa classe principal cria e inicializa todas as outras: ela cria os
 * ambientes, cria o analisador e começa o jogo. Ela também avalia e executa os
 * comandos que o analisador retorna.
 * 
 * @author Michael Kölling and David J. Barnes (traduzido e adaptado por Julio
 *         César Alves)
 */

public class Jogo {
    // analisador de comandos do jogo
    private Analisador analisador;
    // ambiente onde se encontra o jogador
    private Jogador jogador;
    private int infinito = Integer.MAX_VALUE;

    /**
     * Cria o jogo e incializa seu mapa interno.
     */
    public Jogo() {
        Ambiente inicial = criarAmbientes();
        analisador = new Analisador();
        jogador = new Jogador("Jogador", "Ladrao", 100.0, new Mao("Mão", "Somente sua mão.", infinito), new Armadura("Roupa velha", "Trapos rasgados e sujos", 5, 0), inicial);
    }

    /**
     * Cria todos os ambientes e liga as saidas deles
     */
    private Ambiente criarAmbientes() {
        Ambiente igreja, pousada, beco, praca;
        ArrayList<Item> itenspraca = new ArrayList<>();
        ArrayList<Item> itensigreja = new ArrayList<>();
        ArrayList<Item> itenspousada = new ArrayList<>();
        ArrayList<Item> itensbeco = new ArrayList<>();

        itenspraca.add(new Espada("Terrablade", "uma espada lendaria da terra", 900, 200));
        itenspraca.add(new Espada("Aguablade", "uma espada lendaria da agua", 900, 200));
        itenspraca.add(new Espada("Sunblade", "uma espada lendaria do sol", 900, 200));
        itenspraca.add(new Carta("Carta", "uma carta velha", "Beba águaaaaaa"));
        itenspraca.add(new Pocao("Pocao", "uma poção que recupera 50 de vida", 50, 1));
        itensbeco.add(new Pocao("Pocao", "uma poção que recupera 50 de vida", 50, 1));
        itensbeco.add(new Consumivel("Chave_Dourada", "uma chave de ouro com runas antigas gravadas", 1));
        itensbeco.add(new Adaga("Adaga", "uma adaga afiada", 100, 30.0));
        itensigreja.add(new Cajado("Cajado_Sagrado", "um cajado benzido pelo PAPA IX", 90, 30));
        itenspousada.add(new Armadura("Armadura_de_Ferro", "uma armadura de ferro", 50, 10));

        
        praca = new Ambiente("na praça central da cidade Moonlight.", itenspraca);
        pousada = new Ambiente("na pousada da bela cidade Moonlight.", itenspousada);
        igreja = new Ambiente("na velha igreja da cidade Moonlight.", itensigreja);
        beco = new Ambiente("em um beco escuro.", itensbeco);
        
        praca.ajustarSaida(Direcao.LESTE, pousada);
        praca.ajustarSaidaBloqueada(Direcao.NORTE, igreja, "Chave_Dourada");
        praca.ajustarSaida(Direcao.SUL, beco);
        pousada.ajustarSaida(Direcao.OESTE, praca);
        igreja.ajustarSaida(Direcao.SUL, praca);
        beco.ajustarSaida(Direcao.NORTE, praca);
        // cria os ambientes
        return praca;
    }

    /**
     * Rotina principal do jogo. Fica em loop ate terminar o jogo.
     */
    public void jogar() {
        imprimirBoasVindas();

        // Entra no loop de comando principal. Aqui nós repetidamente lemos comandos e
        // os executamos até o jogo terminar.

        boolean terminado = false;
        while (!terminado) {
            Comando comando = analisador.pegarComando();
            terminado = processarComando(comando);
        }
        System.out.println("Obrigado por jogar. Até mais!");
    }

    /**
     * Imprime a mensagem de abertura para o jogador.
     */
    private void imprimirBoasVindas() {
        System.out.println();
        System.out.println("Bem-vindo a vila de Moonlight");
        System.out.println("Voce foi contratado para descobrir o que ou quem esta causando a onda de assassinatos na cidade ");
        System.out.println("Digite 'ajuda' se você precisar de ajuda.");
        System.out.println();

        imprimirLocalizacaoAtual();
    }

    /**
     * Dado um comando, processa-o (ou seja, executa-o)
     * 
     * @param comando O Comando a ser processado.
     * @return true se o comando finaliza o jogo.
     */
    private boolean processarComando(Comando comando) {
        boolean querSair = false;

        if (comando.ehDesconhecido()) {
            System.out.println("Eu não entendi o que voce disse...");
            return false;
        }

        PalavraDeComando palavraDeComando = comando.getPalavraDeComando();

        if (palavraDeComando == PalavraDeComando.AJUDA) {
            imprimirAjuda();
        }
        else if (palavraDeComando == PalavraDeComando.IR) {
            irParaAmbiente(comando);
        }
        else if (palavraDeComando == PalavraDeComando.OBSERVAR) {
            observar(comando);
        }
        else if (palavraDeComando == PalavraDeComando.PEGAR) {
            pegar(comando);
        }
        else if (palavraDeComando == PalavraDeComando.INVENTARIO) {
            System.out.println(jogador.getItensCarregados());
        }
        else if (palavraDeComando == PalavraDeComando.USAR) {
            usar(comando);
        }
        else if (palavraDeComando == PalavraDeComando.LER) {
            ler(comando);
        }
        else if (palavraDeComando == PalavraDeComando.LARGAR) {
            largar(comando);
        }
        else if(palavraDeComando == PalavraDeComando.BEBER) {
            beber(comando);
        }
        else if (palavraDeComando == PalavraDeComando.EQUIPADO) {
            equipado(comando);
        }
        else if (palavraDeComando == PalavraDeComando.EQUIPAR) {
            equipar(comando);
        }
        else if (palavraDeComando == PalavraDeComando.SAIR) {
            querSair = sair(comando);
        }
        return querSair;
    }

    /**
     * Exibe informações de ajuda. Aqui nós imprimimos algo bobo e enigmático e a
     * lista de palavras de comando
     */
    private void imprimirAjuda() {
        System.out.println("Suas palavras de comando são:");
        System.out.println("   " + analisador.getComandosValidos());
    }

    /**
     * Trata o comando "observar", exibindo as informações da localização atual do
     * jogador
     * 
     * @param comando Objeto comando a ser tratado
     */
    private void observar(Comando comando) {
        // se há segunda palavra, explica para o usuário que não pode...
        if (comando.temSegundaPalavra()) {
            System.out.println("Não é possível observar detalhes de alguma coisa");
            return;
        }

        imprimirDescricaoLonga();
    }

    /**
     * Trata o comando "pegar", adicionando o item ao inventário do jogador
     * 
     * @param comando Objeto comando a ser tratado
     */
    private void pegar(Comando comando) {

        if(!comando.temSegundaPalavra()) {
            System.out.println("Pegar o que?");
            return;
        } 

        String nomeItem = comando.getSegundaPalavra();
        Item item = jogador.getLocalizacaoAtual().coletarItemAmbiente(nomeItem);

        if (item == null) {
            System.out.println("Não existe esse item aqui.");
        }
        else {
            jogador.adicionarItem(item);
            System.out.println("Item coletado.");
        }
    }

    private void largar(Comando comando) {
        if (!comando.temSegundaPalavra()) {
            System.out.println("Largar o que?");
            return;
        } 
        String nomeItem = comando.getSegundaPalavra();

        Item itemProcurado = jogador.getItemEspecifico(nomeItem);

        if (itemProcurado != null) {
            jogador.removerItem(nomeItem);
            jogador.getLocalizacaoAtual().largarItem(itemProcurado);
            if (itemProcurado.getNome().equals(jogador.getArmaAtual().getNome())) 
                jogador.setArmaAtual(new Mao("Mão", "mãos com socos fortes.", infinito));
            else if (itemProcurado.getNome().equals(jogador.getArmaduraAtual().getNome())) 
                jogador.setArmaduraAtual(new Armadura("Roupa velha", "uma roupa rasgada e suja", 5, 0));
            System.out.println("Você largou " + nomeItem + " no chão.");
        }
        else {
            System.out.println("Item nao encontrado na mochila");
        }
    }

    private void beber(Comando comando) {
        if (!comando.temSegundaPalavra()) {
            System.out.println("Beber o que?");
            return;
        }
        String nomeItem = comando.getSegundaPalavra();
    
        Item itemProcurado = jogador.getItemEspecifico(nomeItem);
    
        if (itemProcurado == null) {
            System.out.println("O item não foi encontrado.");
            return;
        }
    
        if (itemProcurado instanceof Bebivel) {
            Bebivel bebivel = (Bebivel) itemProcurado;
            bebivel.beberPocao(jogador);
        } else {
            System.out.println("O item " + nomeItem + " não pode ser bebido.");
        }
    }

    private void equipado(Comando comando) {
        if (comando.temSegundaPalavra()) {
            System.out.println("Não é possível equipar algo aqui. Para equipar algo, use o comando 'equipar' e o nome do item.");
            return;
        }
        
        Arma armaAtual = jogador.getArmaAtual();
        Armadura armaduraAtual = jogador.getArmaduraAtual();
        
        Equipavel equipavel = (Equipavel) armaAtual;
        equipavel.equipado(jogador);

        Equipavel equipavel2 = (Equipavel) armaduraAtual;
        equipavel2.equipado(jogador);

    }

    private void equipar(Comando comando) {
        if (!comando.temSegundaPalavra()) {
            System.out.println("Equipar o que?");
            return;
        }

        String nomeItem = comando.getSegundaPalavra();

        Item itemProcurado = jogador.getItemEspecifico(nomeItem);

        if (itemProcurado == null) {
            System.out.println("O item não foi encontrado.");
            return;
        }

        if (itemProcurado instanceof Equipavel) {
            Equipavel equipavel = (Equipavel) itemProcurado;
            boolean bool = equipavel.equipar(jogador);
            if (bool) {
                System.out.println("Item equipado.");
            }
        } else {
            System.out.println("O item " + nomeItem + " não pode ser equipado.");
        }
    }


    /**
     * Tenta ir em uma direcao. Se existe uma saída para lá entra no novo ambiente,
     * caso contrário imprime mensagem de erro.
     */
    private void irParaAmbiente(Comando comando) {
        // se não há segunda palavra, não sabemos pra onde ir...
        if (!comando.temSegundaPalavra()) {
            System.out.println("Ir pra onde?");
            return;
        }

        Direcao direcao = Direcao.pelaString(comando.getSegundaPalavra());
        
        // Tenta sair do ambiente atual
        Ambiente proximoAmbiente = jogador.getLocalizacaoAtual().getSaida(direcao);

        if (proximoAmbiente == null) {
            System.out.println("Não há passagem!");
        }
        else {
            jogador.setLocalizacaoAtual(proximoAmbiente);

            imprimirLocalizacaoAtual();
        }
    }

    private void usar(Comando comando) {
        if (!comando.temSegundaPalavra()) {
            System.out.println("Usar o que?");
            return;
        }

        String nomeItem = comando.getSegundaPalavra();

        if (jogador.temItem(nomeItem)) {
            Saida saidaBloqueada = jogador.getLocalizacaoAtual().getSaidaBloqueada(nomeItem);
            if (jogador.getLocalizacaoAtual().getSaidaBloqueada(nomeItem) != null) {
                saidaBloqueada.desbloquear();
                jogador.removerItem(nomeItem);
                System.out.println("A saída foi destravada.");
            } else {
                System.out.println("Esse item não pode ser usado aqui.");
            }
        } else {
            System.out.println("Você não possui esse item.");
        }
    }

    private void ler(Comando comando) {
        if (!comando.temSegundaPalavra()) {
            System.out.println("Ler o que?");
            return;
        }
    
        String nomeItem = comando.getSegundaPalavra();

        Item itemProcurado = jogador.getItemEspecifico(nomeItem);
        
        if (itemProcurado == null) {
            System.out.println("O item não foi encontrado.");
            return;
        }
    
        if (itemProcurado instanceof Legivel) {
            Legivel legivel = (Legivel) itemProcurado;
            legivel.lerCarta(jogador);
        } else {
            System.out.println("O item " + nomeItem + " não pode ser lido.");
        }
        
    }

    /**
     * Exibe as informações da localização atual para o jogador
     */
    private void imprimirLocalizacaoAtual() {
        System.out.println(jogador.getLocalizacaoAtual().getDescricaoPequena());
        System.out.println();
    }

    private void imprimirDescricaoLonga() {
        System.out.println(jogador.getLocalizacaoAtual().getDescricaoLonga());
        System.out.println();
    }

    /**
     * "Sair" foi digitado. Verifica o resto do comando pra ver se nós queremos
     * realmente sair do jogo.
     * 
     * @return true, se este comando sai do jogo, false, caso contrário.
     */
    private boolean sair(Comando comando) {
        if (comando.temSegundaPalavra()) {
            System.out.println("Sair o que?");
            return false;
        }
        else {
            return true; // sinaliza que nós realmente queremos sair
        }
    }
}
