package ppoo.seuJogo;

import java.io.IOException;
import java.util.*;

public class Jogo {
    private Analisador analisador;
    private Jogador jogador;
    private Map<String, Ambiente> ambientes;
    private int infinito = Integer.MAX_VALUE;
    private Timer timer;
    private TimerTask task;
    private boolean estavaToxico;
    private boolean terminado;

    public Jogo(String caminhoConfiguracao) {
        try {
            ConfiguracaoJogo configuracao = new ConfiguracaoJogo(caminhoConfiguracao);
            ambientes = configuracao.getAmbientes();
            analisador = new Analisador();
            Ambiente ambienteInicial = configuracao.getAmbienteInicial();
            if (ambienteInicial == null) {
                throw new RuntimeException("Nenhum ambiente inicial encontrado na configuração.");
            }
            jogador = new Jogador(configuracao.getNomeJogador(), configuracao.getClasseJogador(), 100.0, 
                    new Mao("Mão", 1.0, "Somente sua mão.", Integer.MAX_VALUE),
                    new Armadura("Roupa velha", "Trapos rasgados e sujos", 5, 0),
                    new Acessorio("Pulseira elegante", "uma bela pulseira que você ganhou de sua tia Gilda", "de te deixar feliz"),
                    ambienteInicial);
            System.out.println(configuracao.getClasseJogador());
        } catch (IOException e) {
            System.out.println("Erro ao carregar o arquivo de configuração: " + e.getMessage());
            e.printStackTrace();
        } catch (RuntimeException e) {
            System.out.println("Erro na configuração do jogo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void jogar() {
        if (jogador == null) {
            System.out.println("Erro: O jogo não pode ser iniciado sem um jogador.");
            return;
        }
        imprimirBoasVindas();
        verificacaoInicial();
        terminado = false;
        while (!terminado) {
            Comando comando = analisador.pegarComando();
            terminado = processarComando(comando);
            if (jogador.getVidaJogador() <= 0) {
                System.out.println("VOCE MORREU, JA ERA COLEGA!!!!!!!!!!!!!");
                terminado = true;
            }
            verificarAmbienteToxico();
        }
        System.out.println("Obrigado por jogar. Até mais!");
        if (timer != null) {
            timer.cancel();
        }
    }

    private void imprimirBoasVindas() {
        System.out.println();
        System.out.println("Bem-vindo a vila de Moonlight");
        System.out.println("Você foi contratado para descobrir o que ou quem está causando a onda de assassinatos na cidade ");
        System.out.println("Digite 'ajuda' se você precisar de ajuda.");
        System.out.println();
        imprimirLocalizacaoAtual();
    }

    private boolean processarComando(Comando comando) {
        boolean querSair = false;
        if (comando.ehDesconhecido()) {
            System.out.println("Eu não entendi o que você disse...");
            return false;
        }
        PalavraDeComando palavraDeComando = comando.getPalavraDeComando();
        if (palavraDeComando == PalavraDeComando.AJUDA) {
            imprimirAjuda();
        } else if (palavraDeComando == PalavraDeComando.IR) {
            irParaAmbiente(comando);
        } else if (palavraDeComando == PalavraDeComando.OBSERVAR) {
            observar(comando);
        } else if (palavraDeComando == PalavraDeComando.PEGAR) {
            pegar(comando);
        } else if (palavraDeComando == PalavraDeComando.INVENTARIO) {
            getItensCarregados();
        } else if (palavraDeComando == PalavraDeComando.USAR) {
            usar(comando);
        } else if (palavraDeComando == PalavraDeComando.LER) {
            ler(comando);
        } else if (palavraDeComando == PalavraDeComando.LARGAR) {
            largar(comando);
        } else if (palavraDeComando == PalavraDeComando.BEBER) {
            beber(comando);
        } else if (palavraDeComando == PalavraDeComando.EQUIPADO) {
            equipado(comando);
        } else if (palavraDeComando == PalavraDeComando.EQUIPAR) {
            equipar(comando);
        } else if (palavraDeComando == PalavraDeComando.DESEQUIPAR) {
            desequipar(comando);
        } else if (palavraDeComando == PalavraDeComando.CONVERSAR) {
            conversar(comando);
        } else if (palavraDeComando == PalavraDeComando.ATACAR) {
            atacar(comando);
        } else if (palavraDeComando == PalavraDeComando.SAIR) {
            querSair = sair(comando);
        }
        return querSair;
    }

    private void getItensCarregados() {
        System.out.println(jogador.getItensCarregados());
    }

    private void imprimirAjuda() {
        System.out.println("Suas palavras de comando são:");
        System.out.println("   " + analisador.getComandosValidos());
    }

    private void observar(Comando comando) {
        if (comando.temSegundaPalavra()) {
            System.out.println("Não é possível observar detalhes de alguma coisa");
            return;
        }
        imprimirDescricaoLonga();
    }

    private void verificacaoInicial() {
        if (jogador.getLocalizacaoAtual().isToxico() && !jogador.getAcessorioAtual().getEfeito().equals("proteger")) {
            iniciarControleAmbienteToxico();
        }
        if (jogador.getLocalizacaoAtual().getInimigos().size() > 0) {
            List<Inimigo> inimigos = jogador.getLocalizacaoAtual().getInimigos();
            System.out.println("Você encontrou um inimigo!");
            for (Inimigo inimigo : inimigos) {
                System.out.println("Nome: " + inimigo.getNome() + " Vida: " + inimigo.getVida());
            }
        }
        if (jogador.getLocalizacaoAtual().isEscuro() && !jogador.getAcessorioAtual().getEfeito().equals("iluminar")) {
            System.out.println("Está escuro aqui. Você não consegue ver nada.");
        }
    }

    private void pegar(Comando comando) {
        if (!comando.temSegundaPalavra()) {
            System.out.println("Pegar o que?");
            return;
        }
        String nomeItem = comando.getSegundaPalavra();
        Item item = jogador.getLocalizacaoAtual().getItem(nomeItem);
        if (jogador.getLocalizacaoAtual().isEscuro() && !jogador.getAcessorioAtual().getEfeito().equals("iluminar")) {
            System.out.println("você não consegue pegar nenhum item, pois está escuro");
        } else if (item == null) {
            System.out.println("Não existe esse item aqui.");
        } else {
            boolean verificar = jogador.adicionarItem(item);
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
    String nomeInimigo = comando.getSegundaPalavra();
    Inimigo inimigo = jogador.getLocalizacaoAtual().getInimigo(nomeInimigo);
    if (inimigo == null) {
        System.out.println("Não existe esse NPC aqui.");
        return;
    }

    // Jogador ataca o inimigo
    jogador.atacar(inimigo);
    System.out.println("Vida do inimigo " + inimigo.getNome() + ": " + inimigo.getVida());

    // Verifica se o inimigo ainda está vivo para contra-atacar
    if (inimigo.isVivo()) {
        inimigo.atacar(jogador);
        System.out.println("O inimigo " + inimigo.getNome() + " te atacou e causou " + inimigo.getDano() + " de dano.");
        System.out.println("Sua vida atual é: " + jogador.getVidaJogador());

        if (jogador.getVidaJogador() <= 0) {
            encerrarJogo("Você foi derrotado pelo inimigo " + inimigo.getNome() + ".");
        }
    } else {
        System.out.println("Você derrotou " + inimigo.getNome() + ".");
        List<Item> itensDrop = inimigo.getItensDrop();
        for (Item item : itensDrop) {
            jogador.getLocalizacaoAtual().adicionarItem(item);
            System.out.println("O inimigo " + inimigo.getNome() + " dropou " + item.getNome() + ".");
        }
        jogador.getLocalizacaoAtual().removerInimigo(nomeInimigo);
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
            if (itemProcurado.getNome().equals(jogador.getArmaAtual().getNome())) {
                jogador.setArmaAtual(new Mao("Mão", 1.0, "mãos com socos fortes.", infinito));
            } else if (itemProcurado.getNome().equals(jogador.getArmaduraAtual().getNome())) {
                jogador.setArmaduraAtual(new Armadura("Roupa velha", "uma roupa rasgada e suja", 5, 0));
            } else if (itemProcurado.getNome().equals(jogador.getAcessorioAtual().getNome())) {
                jogador.setAcessorioAtual(new Acessorio("Pulseira elegante", "uma bela pulseira que você ganhou de sua tia Gilda", "de te deixar feliz"));
            }
            System.out.println("Você largou " + nomeItem + " no chão.");
        } else {
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
            } else if (itemProcurado.getNome().equals(jogador.getArmaduraAtual().getNome())) {
                jogador.setArmaduraAtual(new Armadura("Roupa velha", "uma roupa rasgada e suja", 5, 0));
                System.out.println("Você desequipou " + nomeItem + ".");
            } else if (itemProcurado.getNome().equals(jogador.getAcessorioAtual().getNome())) {
                jogador.setAcessorioAtual(new Acessorio("Pulseira elegante", "uma bela pulseira que você ganhou de sua tia Gilda", "de te deixar feliz"));
                System.out.println("Você desequipou " + nomeItem + ".");
            } else {
                System.out.println("Item não equipado");
            }
        } else {
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

        StringBuilder mensagem = new StringBuilder();

        if (armaAtual != null) {
            Equipavel equipavelArma = (Equipavel) armaAtual;
            mensagem.append(equipavelArma.equipado(jogador)).append("\n");
        }

        if (armaduraAtual != null) {
            Equipavel equipavelArmadura = (Equipavel) armaduraAtual;
            mensagem.append(equipavelArmadura.equipado(jogador)).append("\n");
        }

        if (acessorioAtual != null) {
            Equipavel equipavelAcessorio = (Equipavel) acessorioAtual;
            mensagem.append(equipavelAcessorio.equipado(jogador)).append("\n");
        }

        System.out.println(mensagem.toString());
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
            } else {
                System.out.println("Sua classe não permite equipar esse item.");
            }
        } else {
            System.out.println("O item " + nomeItem + " não pode ser equipado.");
        }
    }

    private void irParaAmbiente(Comando comando) {
        if (!comando.temSegundaPalavra()) {
            System.out.println("Ir pra onde?");
            return;
        }
        Direcao direcao = Direcao.pelaString(comando.getSegundaPalavra());
        Ambiente ambienteAtual = jogador.getLocalizacaoAtual();
        Ambiente proximoAmbiente = ambienteAtual.getSaida(direcao);
        if (proximoAmbiente == ambienteAtual) {
            System.out.println(proximoAmbiente.getMotivo(direcao));
        } else if (proximoAmbiente == null) {
            System.out.println("Não há passagem!");
        } else {
            jogador.setLocalizacaoAtual(proximoAmbiente);
            if (jogador.getLocalizacaoAtual().isToxico() && !jogador.getAcessorioAtual().getEfeito().equals("proteger")) {
                iniciarControleAmbienteToxico();
            }
            if (jogador.getLocalizacaoAtual().getInimigos().size() > 0) {
                List<Inimigo> inimigos = jogador.getLocalizacaoAtual().getInimigos();
                System.out.println("Você encontrou um inimigo!");
                for (Inimigo inimigo : inimigos) {
                    System.out.println("Nome: " + inimigo.getNome() + " Vida: " + inimigo.getVida());
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
            if (saidaBloqueada != null) {
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
            System.out.println(legivel.lerCarta(jogador));
        } else {
            System.out.println("O item " + nomeItem + " não pode ser lido.");
        }
    }

    private void imprimirLocalizacaoAtual() {
        System.out.println(jogador.getLocalizacaoAtual().getDescricaoPequena(jogador));
        System.out.println();
    }

    private void imprimirDescricaoLonga() {
        System.out.println(jogador.getLocalizacaoAtual().getDescricaoLonga(jogador));
        System.out.println();
    }

    private boolean sair(Comando comando) {
        if (comando.temSegundaPalavra()) {
            System.out.println("Sair o que?");
            return false;
        }
        return true; 
    }

    private void verificarAmbienteToxico() {
        boolean ambienteToxicoAtual = jogador.getLocalizacaoAtual().isToxico(); 
        if (ambienteToxicoAtual && !estavaToxico) {
            iniciarControleAmbienteToxico(); 
        } else if (!ambienteToxicoAtual && estavaToxico) {
            pararControleAmbienteToxico();
        }
        estavaToxico = ambienteToxicoAtual; 
    }

    private void iniciarControleAmbienteToxico() {
        if (timer != null) {
            timer.cancel(); 
        }
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                if (jogador.getLocalizacaoAtual().isToxico() && !jogador.getAcessorioAtual().getEfeito().equals("proteger")) {
                    jogador.perderVida(20);
                    System.out.println("Você está em um ambiente tóxico e perdeu 20 de vida! Em 25 segundos, perderá mais 20 de vida.");
                    System.out.println("Vida atual: " + jogador.getVidaJogador());
                    if (jogador.getVidaJogador() <= 0) {
                        encerrarJogo("Você morreu devido à toxicidade do ambiente!");
                    }
                }
            }
        };
        timer.scheduleAtFixedRate(task, 25000, 25000); 
    }

    private void pararControleAmbienteToxico() {
        if (timer != null) {
            timer.cancel();
        }
    }

    private void encerrarJogo(String mensagem) {
        System.out.println(mensagem);
       processarComando(new Comando(PalavraDeComando.SAIR, null)); 
    }
}
