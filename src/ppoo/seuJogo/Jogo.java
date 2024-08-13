package ppoo.seuJogo;

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
    private Timer timer;
    private TimerTask task;
    private boolean estavaToxico;
    /**
     * Cria o jogo e incializa seu mapa interno.
     */
    public Jogo() {
        Ambiente inicial = criarAmbientes();
        analisador = new Analisador();
        jogador = new Jogador("Radahn", "Guerreiro", 100.0, new Mao("Mão", 1.0, "Somente sua mão.", infinito), 
        new Armadura("Roupa velha", "Trapos rasgados e sujos", 5, 0), 
        new Acessorio("Pulseira elegante", "uma bela pulseira que você ganhou de sua tia Gilda", "de te deixar feliz"), inicial);
    }

    /**
     * Cria todos os ambientes e liga as saidas deles
     */
    private Ambiente criarAmbientes() {
        Ambiente igreja, pousada, praca, floresta, cemiterio, cabana, salaBoss, salaTesouro;
        AmbienteEscuro beco, salaoEspera, taverna, tunelEsgoto1, tunelEsgoto2, tunelEsgoto3, tunelEsgoto4, tunelEsgoto5, tunelEsgoto6, tunelEsgoto7, tunelEsgoto8, tunelEsgoto9;
        AmbienteToxico esgoto;
        
        ArrayList<Item> itenspraca = new ArrayList<>();
        ArrayList<Item> itensigreja = new ArrayList<>();
        ArrayList<Item> itenspousada = new ArrayList<>();
        ArrayList<Item> itensbeco = new ArrayList<>();
        itenspraca.add(new Acessorio("Lampiao", "um lampião velho", "iluminar"));
        itenspraca.add(new Acessorio("Lampiao", "um lampião velho", "iluminar"));
        itenspraca.add(new Acessorio("Mascara_Respiratoria", "uma mascara que protege contra gases tóxicos", "proteger"));
        itenspraca.add(new Espada("Terrablade", 20.0, "uma espada lendária da terra", 100));
        itenspraca.add(new Espada("Aguablade", 15.0, "uma espada lendária da agua",  100));
        itenspraca.add(new Espada("Aeroblade", 10.0, "uma espada lendária do ar", 100));
        itenspousada.add(new Carta("Carta", "uma carta velha", "A carta diz: 'O tesouro está no túnel'"));
        itensigreja.add(new Pocao("Pocao_Grande", "uma poção que recupera 50 de vida", 50, 2));
        itensbeco.add(new Pocao("Pocao_Pequena", "uma poção que recupera 50 de vida", 20, 5));
        itenspraca.add(new Pocao("Pocao_Media", "uma poção que recupera 50 de vida", 50, 3));
        itensbeco.add(new Consumivel("Chave_Dourada", "uma chave de ouro com runas antigas gravadas", 1));
        itensbeco.add(new Adaga("Adaga", 10.0, "uma adaga afiada", 100));
        itensigreja.add(new Cajado("Cajado_Sagrado", 18.0, "um cajado benzido pelo padre local", 90));
        itenspousada.add(new Armadura("Armadura_de_Ferro", "uma armadura de ferro", 50, 10));

        
        praca = new Ambiente("na praça central da cidade Moonlight.", itenspraca);
        pousada = new Ambiente("na pousada da bela cidade Moonlight.", itenspousada);
        igreja = new Ambiente("na velha igreja da cidade Moonlight.", itensigreja);
        beco = new AmbienteEscuro("em um beco escuro.", itensbeco);
        floresta = new Ambiente("na floresta");
        cemiterio = new Ambiente("no cemitério");
        cabana = new Ambiente("na cabana");
        esgoto = new AmbienteToxico("no esgoto");
        tunelEsgoto1 = new AmbienteEscuro("em um túnel do esgoto");
        tunelEsgoto2 = new AmbienteEscuro("em um túnel do esgoto");
        tunelEsgoto3 = new AmbienteEscuro("em um túnel do esgoto");
        tunelEsgoto4 = new AmbienteEscuro("em um túnel do esgoto");
        tunelEsgoto5 = new AmbienteEscuro("em um túnel do esgoto");
        tunelEsgoto6 = new AmbienteEscuro("em um túnel do esgoto");
        tunelEsgoto7 = new AmbienteEscuro("em um túnel do esgoto");
        tunelEsgoto8 = new AmbienteEscuro("em um túnel do esgoto");
        tunelEsgoto9 = new AmbienteEscuro("em um túnel do esgoto");
        salaoEspera = new AmbienteEscuro("no salão de espera");
        taverna = new AmbienteEscuro("na taverna");
        salaBoss = new Ambiente("na sala do boss");
        salaTesouro = new Ambiente("na sala do tesouro");
        
        praca.ajustarSaida(Direcao.LESTE, pousada);
        praca.ajustarSaidaBloqueada(Direcao.NORTE, igreja, "Chave_Dourada", "A igreja está trancada. Talvez você precise de uma chave.");
        praca.ajustarSaida(Direcao.SUL, beco);
        praca.ajustarSaida(Direcao.OESTE, floresta);
        praca.ajustarSaida(Direcao.NORDESTE, taverna);

        taverna.ajustarSaida(Direcao.SUDOESTE, praca);
        taverna.ajustarSaida(Direcao.SUL, pousada);

        pousada.ajustarSaida(Direcao.OESTE, praca);
        pousada.ajustarSaida(Direcao.NORTE, taverna);

        igreja.ajustarSaida(Direcao.SUL, praca);

        beco.ajustarSaida(Direcao.NORTE, praca);
        beco.ajustarSaida(Direcao.BAIXO, esgoto);

        floresta.ajustarSaida(Direcao.LESTE, praca);

        cemiterio.ajustarSaida(Direcao.SUL, floresta);

        cabana.ajustarSaida(Direcao.LESTE, floresta);

        esgoto.ajustarSaida(Direcao.CIMA, beco);
        esgoto.ajustarSaida(Direcao.NOROESTE, tunelEsgoto1);
        esgoto.ajustarSaida(Direcao.NORTE, tunelEsgoto2);
        esgoto.ajustarSaida(Direcao.NORDESTE, tunelEsgoto3);

        tunelEsgoto1.ajustarSaida(Direcao.SUDESTE, esgoto);
        tunelEsgoto1.ajustarSaida(Direcao.LESTE, tunelEsgoto2);
        tunelEsgoto1.ajustarSaida(Direcao.NORDESTE, tunelEsgoto5);
        tunelEsgoto1.ajustarSaida(Direcao.NORTE, tunelEsgoto4);

        tunelEsgoto2.ajustarSaida(Direcao.OESTE, tunelEsgoto1);
        tunelEsgoto2.ajustarSaida(Direcao.NORTE, tunelEsgoto5);
        tunelEsgoto2.ajustarSaida(Direcao.NORDESTE, tunelEsgoto6);
        tunelEsgoto2.ajustarSaida(Direcao.LESTE, tunelEsgoto3);
        tunelEsgoto2.ajustarSaida(Direcao.SUL, esgoto);
        tunelEsgoto2.ajustarSaida(Direcao.NOROESTE, tunelEsgoto4);

        tunelEsgoto3.ajustarSaida(Direcao.OESTE, tunelEsgoto2);
        tunelEsgoto3.ajustarSaida(Direcao.NOROESTE, tunelEsgoto5);
        tunelEsgoto3.ajustarSaida(Direcao.NORTE, tunelEsgoto6);

        tunelEsgoto4.ajustarSaida(Direcao.SUL, tunelEsgoto1);
        tunelEsgoto4.ajustarSaida(Direcao.SUDESTE, tunelEsgoto2);
        tunelEsgoto4.ajustarSaida(Direcao.NORTE, tunelEsgoto7);
        tunelEsgoto4.ajustarSaida(Direcao.NORDESTE, tunelEsgoto8);
        tunelEsgoto4.ajustarSaida(Direcao.LESTE, tunelEsgoto5);
        tunelEsgoto4.ajustarSaida(Direcao.OESTE, salaTesouro);

        tunelEsgoto5.ajustarSaida(Direcao.OESTE, tunelEsgoto4);
        tunelEsgoto5.ajustarSaida(Direcao.SUL, tunelEsgoto2);
        tunelEsgoto5.ajustarSaida(Direcao.SUDESTE, tunelEsgoto3);
        tunelEsgoto5.ajustarSaida(Direcao.NORTE, tunelEsgoto8);
        tunelEsgoto5.ajustarSaida(Direcao.NORDESTE, tunelEsgoto9);
        tunelEsgoto5.ajustarSaida(Direcao.LESTE, tunelEsgoto6);
        tunelEsgoto5.ajustarSaida(Direcao.NOROESTE, tunelEsgoto7);
        tunelEsgoto5.ajustarSaida(Direcao.SUDOESTE, tunelEsgoto1);

        tunelEsgoto6.ajustarSaida(Direcao.OESTE, tunelEsgoto5);
        tunelEsgoto6.ajustarSaida(Direcao.SUL, tunelEsgoto3);
        tunelEsgoto6.ajustarSaida(Direcao.SUDOESTE, tunelEsgoto2);
        tunelEsgoto6.ajustarSaida(Direcao.NORTE, tunelEsgoto9);

        tunelEsgoto7.ajustarSaida(Direcao.SUL, tunelEsgoto4);
        tunelEsgoto7.ajustarSaida(Direcao.SUDESTE, tunelEsgoto5);
        tunelEsgoto7.ajustarSaida(Direcao.LESTE, tunelEsgoto8);

        tunelEsgoto8.ajustarSaida(Direcao.OESTE, tunelEsgoto7);
        tunelEsgoto8.ajustarSaida(Direcao.SUDOESTE, tunelEsgoto4);
        tunelEsgoto8.ajustarSaida(Direcao.SUL, tunelEsgoto5);
        tunelEsgoto8.ajustarSaida(Direcao.SUDESTE, tunelEsgoto6);
        tunelEsgoto8.ajustarSaida(Direcao.LESTE, tunelEsgoto9);

        tunelEsgoto9.ajustarSaida(Direcao.OESTE, tunelEsgoto8);
        tunelEsgoto9.ajustarSaida(Direcao.SUDOESTE, tunelEsgoto5);
        tunelEsgoto9.ajustarSaida(Direcao.SUL, tunelEsgoto6);
        tunelEsgoto8.ajustarSaida(Direcao.NORDESTE, salaoEspera);

        salaoEspera.ajustarSaida(Direcao.SUL, tunelEsgoto9);
        salaoEspera.ajustarSaida(Direcao.NORTE, salaBoss);

        salaBoss.ajustarSaida(Direcao.SUL, salaoEspera);

        salaTesouro.ajustarSaida(Direcao.LESTE, tunelEsgoto4);

        Npc padre = new Npc("Padre", "um padre idoso", "O padre diz: 'O segredo está na igreja, meu filho.'");
        Npc recepcionista = new Npc("Recepcionista", "uma recepcionista jovem", "A recepcionista diz: 'Bem-vindo à pousada!'");
        Inimigo esqueleto = new Inimigo("Esqueleto", "um esqueleto", 2, 10);
        Inimigo zumbi = new Inimigo("Zumbi", "um zumbi", 2, 15);


        praca.adicionarNpc(padre);
        pousada.adicionarNpc(recepcionista);

        praca.adicionarInimigo(esqueleto);
        praca.adicionarInimigo(zumbi);

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
            verificarAmbienteToxico();
        }
        System.out.println("Obrigado por jogar. Até mais!");
        if (timer != null) { 
            timer.cancel(); 
        }
    }

    /**
     * Imprime a mensagem de abertura para o jogador.
     */
    private void imprimirBoasVindas() {
        System.out.println();
        System.out.println("Bem-vindo a vila de Moonlight");
        System.out.println("Você foi contratado para descobrir o que ou quem está causando a onda de assassinatos na cidade ");
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
            System.out.println("Eu não entendi o que você disse...");
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
        else if (palavraDeComando == PalavraDeComando.DESEQUIPAR) {
            desequipar(comando);
        } 
        else if (palavraDeComando == PalavraDeComando.CONVERSAR) {
            conversar(comando);
        } else if (palavraDeComando == PalavraDeComando.ATACAR) {
            atacar(comando);
        } else if (palavraDeComando == PalavraDeComando.SAIR) {
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
        Item item = jogador.getLocalizacaoAtual().getItem(nomeItem);

        if(jogador.getLocalizacaoAtual().isEscuro() && !jogador.getAcessorioAtual().getEfeito().equals("iluminar")) {
            System.out.println("você não consegue pegar nenhum item, pois está escuro");
        }
        else if (item == null) {
            System.out.println("Não existe esse item aqui.");
        }
        else {
            Boolean verificar = jogador.adicionarItem(item);
            if (verificar) {
                jogador.getLocalizacaoAtual().removerItemAmbiente(nomeItem);
                System.out.println("Item coletado.");
            } else {
                System.out.println("Mochila cheia. Remova um item com o comando largar para adicionar outro.");
            }
        }
    }

    private void atacar(Comando comando) {
        if (!comando.temSegundaPalavra()) {
            System.out.println("Atacar o que?");
            return;
        }

        String nomeInimgo = comando.getSegundaPalavra();
        Inimigo inimigo = jogador.getLocalizacaoAtual().getInimigo(nomeInimgo);

        if (inimigo == null) {
            System.out.println("Não existe esse NPC aqui.");
        } else {
            jogador.atacar(inimigo);
            if (inimigo.getVida() <= 0) {
                System.out.println("Você derrotou " + nomeInimgo + ".");
                jogador.getLocalizacaoAtual().removerInimigo(nomeInimgo);
            }
        }
    }

    private void conversar(Comando comando) {
        if (!comando.temSegundaPalavra()) {
            System.out.println("Conversar com quem?");
            return;
        }

        String nomeNpc = comando.getSegundaPalavra();
        Npc npc = jogador.getLocalizacaoAtual().getNpc(nomeNpc);

        if (npc == null) {
            System.out.println("Não existe esse NPC aqui.");
        } else {
            System.out.println(npc.getDialogo());
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
                jogador.setArmaAtual(new Mao("Mão", 1.0, "mãos com socos fortes.", infinito));
            else if (itemProcurado.getNome().equals(jogador.getArmaduraAtual().getNome())) 
                jogador.setArmaduraAtual(new Armadura("Roupa velha", "uma roupa rasgada e suja", 5, 0));
            else if (itemProcurado.getNome().equals(jogador.getAcessorioAtual().getNome())) 
                jogador.setAcessorioAtual(new Acessorio("Pulseira elegante", "uma bela pulseira que você ganhou de sua tia Gilda", "de te deixar feliz"));;
                System.out.println("Você largou " + nomeItem + " no chão.");
        }
        else {
            System.out.println("Item nao encontrado na mochila");
        }
    }

    private void desequipar(Comando comando) {
        if (!comando.temSegundaPalavra()) {
            System.out.println("Desequipar o que?");
            return;
        } 
        String nomeItem = comando.getSegundaPalavra();

        Item itemProcurado = jogador.getItemEspecifico(nomeItem);

        if (itemProcurado != null) {
            if (itemProcurado.getNome().equals(jogador.getArmaAtual().getNome())) {
                jogador.setArmaAtual(new Mao("Mão", 1.0, "mãos com socos fortes.", infinito));
                System.out.println("Você desequipou " + nomeItem + ".");
            }
            else if (itemProcurado.getNome().equals(jogador.getArmaduraAtual().getNome())) {
                jogador.setArmaduraAtual(new Armadura("Roupa velha", "uma roupa rasgada e suja", 5, 0));
                System.out.println("Você desequipou " + nomeItem + ".");
            }
            else if (itemProcurado.getNome().equals(jogador.getAcessorioAtual().getNome())) {
                jogador.setAcessorioAtual(new Acessorio("Pulseira elegante", "uma bela pulseira que você ganhou de sua tia Gilda", "de te deixar feliz"));
                System.out.println("Você desequipou " + nomeItem + ".");
            } else {
                System.out.println("Item não equipado");
            }
        }
        else {
            System.out.println("Item não equipado");
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
            double vidaAntiga = jogador.getVidaJogador();
            if (jogador.getVidaJogador() == 100) {
                System.out.println("Sua vida já está cheia.");
                return;
            }
            bebivel.beberPocao(jogador);
            double vidaNova = jogador.getVidaJogador();
            System.out.println("Você recuperou " + (vidaNova - vidaAntiga) + " de vida.");
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
        Acessorio acessorioAtual = jogador.getAcessorioAtual();
        
        Equipavel equipavel = (Equipavel) armaAtual;
        equipavel.equipado(jogador);

        Equipavel equipavel2 = (Equipavel) armaduraAtual;
        equipavel2.equipado(jogador);

        Equipavel equipavel3 = (Equipavel) acessorioAtual;
        equipavel3.equipado(jogador);
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

        if (proximoAmbiente == jogador.getLocalizacaoAtual()) {
            System.out.println(proximoAmbiente.getMotivo(direcao));
        }
        if (proximoAmbiente == null) {
            System.out.println("Não há passagem!");
        }
        else {
            jogador.setLocalizacaoAtual(proximoAmbiente);
            if (jogador.getLocalizacaoAtual().isToxico() && !jogador.getAcessorioAtual().getEfeito().equals("proteger")) {
                // Quero que o jogador perca 10 de vida a cada 5 segundos
                iniciarControleAmbienteToxico();
            }


            if (jogador.getLocalizacaoAtual().getInimigos().size() > 0) {
                List<Inimigo> inimigos = jogador.getLocalizacaoAtual().getInimigos();
                System.out.println("Você encontrou um inimigo!");
                for (Inimigo inimigo : inimigos) {
                    System.out.println("Nome: " + inimigo.getNome() + " Vida: " + inimigo.getVida());
                    inimigo.iniciarAtaquesPeriodicos(jogador);
                }
            }

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
        System.out.println(jogador.getLocalizacaoAtual().getDescricaoPequena(jogador));
        System.out.println();
    }

    private void imprimirDescricaoLonga() {
        System.out.println(jogador.getLocalizacaoAtual().getDescricaoLonga(jogador));
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

    private void verificarAmbienteToxico() {
        boolean ambienteToxicoAtual = jogador.getLocalizacaoAtual().isToxico(); // Atualiza o estado atual
        
        // Verifica se houve mudança no estado do ambiente
        if (ambienteToxicoAtual && !estavaToxico) {
            iniciarControleAmbienteToxico(); // Inicia o Timer se o ambiente ficou tóxico
        } else if (!ambienteToxicoAtual && estavaToxico) {
            pararControleAmbienteToxico(); // Para o Timer se o ambiente deixou de ser tóxico
        }
        
        estavaToxico = ambienteToxicoAtual; // Atualiza o estado anterior
    }
    
    private void iniciarControleAmbienteToxico() {
        if (timer != null) {
            timer.cancel(); // Cancela o timer anterior, se existir
        }
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                if (jogador.getLocalizacaoAtual().isToxico() && !jogador.getAcessorioAtual().getNome().equals("Mascara_Respiratoria")) {
                    jogador.perderVida(20);
                    System.out.println("Você está em um ambiente tóxico e perdeu 20 de vida! Em 25 segundos, perderá mais 20 de vida.");
                    System.out.println("Vida atual: " + jogador.getVidaJogador());
                    if (jogador.getVidaJogador() <= 0) {
                        System.out.println("Você morreu devido à toxicidade do ambiente!");
                        System.exit(0); // Finaliza o jogo se a vida chegar a 0
                    }
                }
            }
        };
        timer.scheduleAtFixedRate(task, 25000, 25000); // Ajuste o intervalo (1000 ms = 1 segundo)
    }
    
    private void pararControleAmbienteToxico() {
        if (timer != null) {
            timer.cancel(); // Cancela o timer quando o ambiente deixa de ser tóxico
        }
    }

}
