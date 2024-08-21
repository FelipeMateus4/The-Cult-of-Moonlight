package ppoo.seuJogo;

import ppoo.baseJogo.InterfaceUsuario;
import ppoo.seuJogo.Ambientes.Ambiente;
import ppoo.seuJogo.Equipaveis.Acessorio;
import ppoo.seuJogo.Equipaveis.Arma;
import ppoo.seuJogo.Equipaveis.Armadura;
import ppoo.seuJogo.Equipaveis.Item;
import ppoo.seuJogo.Equipaveis.Mao;
import ppoo.seuJogo.Inimigo.Inimigo;
import ppoo.seuJogo.NPCS.Npc;
import ppoo.seuJogo.interfaces.Bebivel;
import ppoo.seuJogo.interfaces.Equipavel;
import ppoo.seuJogo.interfaces.Legivel;

import java.io.IOException;
import java.util.*;

public class Jogo {
    private Analisador analisador;
    private Jogador jogador;
    private Map<String, Ambiente> ambientes;
    private InterfaceUsuario interfaceUsuario;
    private int infinito = Integer.MAX_VALUE;
    private Timer timer;
    private TimerTask task;
    private boolean estavaToxico;
    private boolean terminado;

    public Jogo(InterfaceUsuario interfaceUsuario, String caminhoConfiguracao) {
        this.interfaceUsuario = interfaceUsuario;
        try {
            ConfiguracaoJogo configuracao = new ConfiguracaoJogo(caminhoConfiguracao);
            ambientes = configuracao.getAmbientes();
            analisador = new Analisador(interfaceUsuario);
            Ambiente ambienteInicial = configuracao.getAmbienteInicial();
            if (ambienteInicial == null) {
                throw new RuntimeException("Nenhum ambiente inicial encontrado na configuração.");
            }
            jogador = new Jogador(configuracao.getNomeJogador(), configuracao.getClasseJogador(), 100.0, 
                    new Mao("Mão", 1.0, "Somente sua mão.", Integer.MAX_VALUE, "imagens\\mao.jpeg"),
                    new Armadura("Roupa velha", "Trapos rasgados e sujos", 5, "imagens\\roupaVelha.jpeg"),
                    new Acessorio("Medalhão antigo", "um medalhão simples encontrado pelo caminho", "de não deixar a história se perder", "imagens\\medalhao.jpeg"),
                    ambienteInicial);

            // Notifica a interface sobre o ambiente inicial
            interfaceUsuario.ambienteAtualMudou(jogador.getLocalizacaoAtual());
        } catch (IOException e) {
            interfaceUsuario.exibirMensagem("Erro ao carregar o arquivo de configuração: " + e.getMessage());
            e.printStackTrace();
        } catch (RuntimeException e) {
            interfaceUsuario.exibirMensagem("Erro na configuração do jogo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void jogar() {
        if (jogador == null) {
            interfaceUsuario.exibirMensagem("Erro: O jogo não pode ser iniciado sem um jogador.");
            return;
        }
        imprimirBoasVindas();
        verificacaoInicial();
        terminado = false;
        while (!terminado) {
            Comando comando = analisador.pegarComando();
            terminado = processarComando(comando);
            verificarAmbienteToxico();
        }
        if (timer != null) {
            timer.cancel();
        }
    }

    private void imprimirBoasVindas() {
        interfaceUsuario.exibirMensagem("\nBem-vindo à vila de Moonlight");
        interfaceUsuario.exibirMensagem("Você foi contratado para descobrir o que ou quem está causando a onda de assassinatos na cidade.");
        interfaceUsuario.exibirMensagem("Digite 'ajuda' se você precisar de ajuda.\n");
        imprimirLocalizacaoAtual();
    }

    private boolean processarComando(Comando comando) {
        boolean querSair = false;
        if (comando.ehDesconhecido()) {
            interfaceUsuario.exibirMensagem("Eu não entendi o que você disse...");
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
        interfaceUsuario.exibirMensagem(jogador.getItensCarregados());
        jogador.getItens().forEach(interfaceUsuario::jogadorPegouItem);
    }

    private void imprimirAjuda() {
        interfaceUsuario.exibirMensagem("Suas palavras de comando são:");
        interfaceUsuario.exibirMensagem("   " + analisador.getComandosValidos());
    }

    private void observar(Comando comando) {
        if (comando.temSegundaPalavra()) {
            interfaceUsuario.exibirMensagem("Não é possível observar detalhes de alguma coisa");
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
            interfaceUsuario.exibirMensagem("Você encontrou um inimigo!");
            for (Inimigo inimigo : inimigos) {
                interfaceUsuario.exibirMensagem("Nome: " + inimigo.getNome() + " Vida: " + inimigo.getVida());
            }
        }
        if (jogador.getLocalizacaoAtual().isEscuro() && !jogador.getAcessorioAtual().getEfeito().equals("iluminar")) {
            interfaceUsuario.exibirMensagem("Está escuro aqui. Você não consegue ver nada.");
        }
    }

    private void pegar(Comando comando) {
        if (!comando.temSegundaPalavra()) {
            interfaceUsuario.exibirMensagem("Pegar o que?");
            return;
        }
        String nomeItem = comando.getSegundaPalavra();
        Item item = jogador.getLocalizacaoAtual().getItem(nomeItem);
        if (jogador.getLocalizacaoAtual().isEscuro() && !jogador.getAcessorioAtual().getEfeito().equals("iluminar")) {
            interfaceUsuario.exibirMensagem("Você não consegue pegar nenhum item, pois está escuro");
        } else if (item == null) {
            interfaceUsuario.exibirMensagem("Não existe esse item aqui.");
        } else {
            boolean verificar = jogador.adicionarItem(item);
            if (verificar) {
                jogador.getLocalizacaoAtual().removerItemAmbiente(nomeItem);
                interfaceUsuario.exibirMensagem("Item coletado.");
                interfaceUsuario.jogadorPegouItem(item); // Exibe a imagem do item na UI
            } else {
                interfaceUsuario.exibirMensagem("Mochila cheia. Remova um item com o comando largar para adicionar outro.");
            }
        }
    }

public void atacar(Comando comando) {
    if (!comando.temSegundaPalavra()) {
        interfaceUsuario.exibirMensagem("Atacar o que?");
        return;
    }
    
    String nomeInimigo = comando.getSegundaPalavra();
    Inimigo inimigo = jogador.getLocalizacaoAtual().getInimigo(nomeInimigo);
    
    if (inimigo == null) {
        interfaceUsuario.exibirMensagem("Não existe esse Inimigo aqui.");
        return;
    }
    // Jogador ataca o inimigo
    boolean ataque = jogador.atacar(inimigo);
    
        // Verifica se o inimigo ainda está vivo para contra-atacar
        if (inimigo.isVivo()) {
            double danoRecebido = inimigo.atacar(jogador);
            interfaceUsuario.exibirMensagem("Vida do inimigo " + inimigo.getNome() + ": " + String.format("%.2f", inimigo.getVida()));
            Armadura armadura = jogador.getArmaduraAtual();
            danoRecebido = danoRecebido - (armadura.getDefesa()/10);
            jogador.perderVida(danoRecebido);
            if (danoRecebido < 0) {
                danoRecebido = 0;
            }
            if (jogador.getVidaJogador() <= 0) {
                jogador.setMorto();
            }
            interfaceUsuario.exibirMensagem("O inimigo " + inimigo.getNome() + " te atacou e causou " + String.format("%.2f", danoRecebido) + " de dano.");
            interfaceUsuario.exibirMensagem("Sua vida atual é: " + String.format("%.2f", jogador.getVidaJogador()));
    
            // Verifica se o jogador foi derrotado
            if (jogador.getVidaJogador() <= 0) {
                encerrarJogo("Você foi derrotado pelo inimigo " + inimigo.getNome() + ".");
                return;
            }
        } else {
            // Se o inimigo foi derrotado

            interfaceUsuario.exibirMensagem("Você derrotou " + inimigo.getNome() + ".");
            jogador.adicionarPontos(inimigo.getPontos());
            interfaceUsuario.exibirMensagem("Você ganhou " + inimigo.getPontos() + " pontos.");
    
            // Inimigo dropa itens
            List<Item> itensDrop = inimigo.getItensDrop();
            for (Item item : itensDrop) {
                jogador.getLocalizacaoAtual().adicionarItem(item);
                interfaceUsuario.exibirMensagem("O inimigo " + inimigo.getNome() + " dropou " + item.getNome() + ".");
            }

            if (inimigo.isBoss()) {
                interfaceUsuario.exibirMensagem("Você derrotou o chefão final!");
                encerrarJogo("Parabéns! Você ganhou o jogo!");
                return;
            }
    
            // Remove o inimigo da localização atual
            jogador.getLocalizacaoAtual().removerInimigo(nomeInimigo);
        }
        if(!ataque) {
            interfaceUsuario.jogadorDescartouItem(jogador.getArmaAtual());
            jogador.removerItem(jogador.getArmaAtual().getNome());
            jogador.setArmaAtual(new Mao("Mão", 1.0, "mãos com socos fortes.", infinito, "imagens\\avb.jpeg"));
            interfaceUsuario.exibirMensagem("A arma quebrou. Você não pode mais atacar.");
        }
    } 
    


    private void conversar(Comando comando) {
        if (!comando.temSegundaPalavra()) {
            interfaceUsuario.exibirMensagem("Conversar com quem?");
            return;
        }
        String nomeNpc = comando.getSegundaPalavra();
        Npc npc = jogador.getLocalizacaoAtual().getNpc(nomeNpc);
        if (npc == null) {
            interfaceUsuario.exibirMensagem("Não existe esse NPC aqui.");
        } else {
            interfaceUsuario.exibirMensagem(npc.getDialogo());
        }

        if (npc.getItem() != null) {
            if (!jogador.temItem(npc.getItem().getNome())) {
                jogador.adicionarItem(npc.getItem());
                interfaceUsuario.exibirMensagem("Você recebeu o item " + npc.getItem().getNome() + " do NPC " + npc.getNome() + ".");
                interfaceUsuario.jogadorPegouItem(npc.getItem()); // Exibe a imagem do item na UI
            } else {
                interfaceUsuario.exibirMensagem("Você já possui o item " + npc.getItem().getNome() + ".");
            }
        }
    }

    private void largar(Comando comando) {
        if (!comando.temSegundaPalavra()) {
            interfaceUsuario.exibirMensagem("Largar o que?");
            return;
        }
        String nomeItem = comando.getSegundaPalavra();
        Item itemProcurado = jogador.getItemEspecifico(nomeItem);
        if (itemProcurado != null) {
            jogador.removerItem(nomeItem);
            jogador.getLocalizacaoAtual().largarItem(itemProcurado);
            interfaceUsuario.jogadorDescartouItem(itemProcurado); // Remove a imagem do item da UI
            if (itemProcurado.getNome().equals(jogador.getArmaAtual().getNome())) {
                jogador.setArmaAtual(new Mao("Mão", 1.0, "mãos com socos fortes.", infinito, "imagens\\mao.jpeg"));
            } else if (itemProcurado.getNome().equals(jogador.getArmaduraAtual().getNome())) {
                jogador.setArmaduraAtual(new Armadura("Roupa velha", "uma roupa rasgada e suja", 5, "imagens\\roupaVelha.jpeg"));
            } else if (itemProcurado.getNome().equals(jogador.getAcessorioAtual().getNome())) {
                jogador.setAcessorioAtual(new Acessorio("Medalhão antigo", "um medalhão simples encontrado pelo caminho", "de não deixar a história se perder", "imagens\\medalhao.jpeg"));
            }
            interfaceUsuario.exibirMensagem("Você largou " + nomeItem + " no chão.");
        } else {
            interfaceUsuario.exibirMensagem("Item não encontrado na mochila");
        }
    }

    private void desequipar(Comando comando) {
        if (!comando.temSegundaPalavra()) {
            interfaceUsuario.exibirMensagem("Desequipar o que?");
            return;
        }
        String nomeItem = comando.getSegundaPalavra();
        Item itemProcurado = jogador.getItemEspecifico(nomeItem);
        if (itemProcurado != null) {
            if (itemProcurado.getNome().equals(jogador.getArmaAtual().getNome())) {
                jogador.setArmaAtual(new Mao("Mão", 1.0, "mãos com socos fortes.", infinito, "imagens\\mao.jpeg"));
                interfaceUsuario.exibirMensagem("Você desequipou " + nomeItem + ".");
                interfaceUsuario.jogadorDescartouItem(itemProcurado); // Atualiza a UI
            } else if (itemProcurado.getNome().equals(jogador.getArmaduraAtual().getNome())) {
                jogador.setArmaduraAtual(new Armadura("Roupa velha", "uma roupa rasgada e suja", 5, "imagens\\roupaVelha.jpeg"));
                interfaceUsuario.exibirMensagem("Você desequipou " + nomeItem + ".");
                interfaceUsuario.jogadorDescartouItem(itemProcurado); // Atualiza a UI
            } else if (itemProcurado.getNome().equals(jogador.getAcessorioAtual().getNome())) {
                jogador.setAcessorioAtual(new Acessorio("Pulseira elegante", "uma bela pulseira que você ganhou de sua tia Gilda", "de te deixar feliz", "imagens\\medalhao.jpeg"));
                interfaceUsuario.exibirMensagem("Você desequipou " + nomeItem + ".");
                interfaceUsuario.jogadorDescartouItem(itemProcurado); // Atualiza a UI
            } else {
                interfaceUsuario.exibirMensagem("Item não equipado");
            }
        } else {
            interfaceUsuario.exibirMensagem("Item não equipado");
        }
    }

    private void beber(Comando comando) {
        if (!comando.temSegundaPalavra()) {
            interfaceUsuario.exibirMensagem("Beber o que?");
            return;
        }
        String nomeItem = comando.getSegundaPalavra();
        Item itemProcurado = jogador.getItemEspecifico(nomeItem);
        if (itemProcurado == null) {
            interfaceUsuario.exibirMensagem("O item não foi encontrado.");
            return;
        }
        if (itemProcurado instanceof Bebivel) {
            Bebivel bebivel = (Bebivel) itemProcurado;
            double vidaAntiga = jogador.getVidaJogador();
            if (jogador.getVidaJogador() == 100) {
                interfaceUsuario.exibirMensagem("Sua vida já está cheia.");
                return;
            }
            boolean oi = bebivel.beberPocao(jogador);
            double vidaNova = jogador.getVidaJogador();
            interfaceUsuario.exibirMensagem("Você recuperou " + (vidaNova - vidaAntiga) + " de vida.");
            if (oi == true) {
                interfaceUsuario.exibirMensagem("Seus usos acabaram. Você não tem mais " + itemProcurado.getNome() + " na mochila.");
                interfaceUsuario.jogadorDescartouItem(itemProcurado); // Atualiza a UI ao consumir o item
        } else {
            interfaceUsuario.exibirMensagem("O item " + nomeItem + " não pode ser bebido.");
        }
    }
    }

    private void equipado(Comando comando) {
        if (comando.temSegundaPalavra()) {
            interfaceUsuario.exibirMensagem("Não é possível equipar algo aqui. Para equipar algo, use o comando 'equipar' e o nome do item.");
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
            mensagem.append(equipavelAcessorio.equipado(jogador));
        }

        interfaceUsuario.exibirMensagem(mensagem.toString());
    }

    private void equipar(Comando comando) {
        if (!comando.temSegundaPalavra()) {
            interfaceUsuario.exibirMensagem("Equipar o que?");
            return;
        }
        String nomeItem = comando.getSegundaPalavra();
        Item itemProcurado = jogador.getItemEspecifico(nomeItem);
        if (itemProcurado == null) {
            interfaceUsuario.exibirMensagem("O item não foi encontrado.");
            return;
        }
        if (itemProcurado instanceof Equipavel) {
            Equipavel equipavel = (Equipavel) itemProcurado;
            boolean bool = equipavel.equipar(jogador);
            if (bool) {
                interfaceUsuario.exibirMensagem("Item equipado.");
            } else {
                interfaceUsuario.exibirMensagem("Sua classe não permite equipar esse item.");
            }
        } else {
            interfaceUsuario.exibirMensagem("O item " + nomeItem + " não pode ser equipado.");
        }
    }

    private void irParaAmbiente(Comando comando) {
        if (!comando.temSegundaPalavra()) {
            interfaceUsuario.exibirMensagem("Ir pra onde?");
            return;
        }
        interfaceUsuario.limparMensagens();
        Direcao direcao = Direcao.pelaString(comando.getSegundaPalavra());
        Ambiente ambienteAtual = jogador.getLocalizacaoAtual();
        Ambiente proximoAmbiente = ambienteAtual.getSaida(direcao);
        if (proximoAmbiente == ambienteAtual) {
            interfaceUsuario.exibirMensagem(proximoAmbiente.getMotivo(direcao));
        } else if (proximoAmbiente == null) {
            interfaceUsuario.exibirMensagem("Não há passagem!");
        } else {
            jogador.setLocalizacaoAtual(proximoAmbiente);
            if (jogador.getLocalizacaoAtual().isToxico() && !jogador.getAcessorioAtual().getEfeito().equals("proteger")) {
                iniciarControleAmbienteToxico();
            }
            if (jogador.getLocalizacaoAtual().getInimigos().size() > 0) {
                List<Inimigo> inimigos = jogador.getLocalizacaoAtual().getInimigos();
                interfaceUsuario.exibirMensagem("Você encontrou um inimigo!");
                for (Inimigo inimigo : inimigos) {
                    interfaceUsuario.exibirMensagem("Nome: " + inimigo.getNome() + " Vida: " + inimigo.getVida());
                }
            }
            interfaceUsuario.ambienteAtualMudou(proximoAmbiente); // Atualiza a UI com o novo ambiente
            imprimirLocalizacaoAtual();
        }
    }

    private void usar(Comando comando) {
        if (!comando.temSegundaPalavra()) {
            interfaceUsuario.exibirMensagem("Usar o que?");
            return;
        }
        String nomeItem = comando.getSegundaPalavra();
        if (jogador.temItem(nomeItem)) {
            Saida saidaBloqueada = jogador.getLocalizacaoAtual().getSaidaBloqueada(nomeItem);
            if (saidaBloqueada != null) {
                saidaBloqueada.desbloquear();
                jogador.removerItem(nomeItem);
                interfaceUsuario.exibirMensagem("A saída foi destravada.");
                interfaceUsuario.ambienteAtualMudou(jogador.getLocalizacaoAtual()); // Atualiza a UI após usar o item
            } else {
                interfaceUsuario.exibirMensagem("Esse item não pode ser usado aqui.");
            }
        } else {
            interfaceUsuario.exibirMensagem("Você não possui esse item.");
        }
    }

    private void ler(Comando comando) {
        if (!comando.temSegundaPalavra()) {
            interfaceUsuario.exibirMensagem("Ler o que?");
            return;
        }
        String nomeItem = comando.getSegundaPalavra();
        Item itemProcurado = jogador.getItemEspecifico(nomeItem);
        if (itemProcurado == null) {
            interfaceUsuario.exibirMensagem("O item não foi encontrado.");
            return;
        }
        if (itemProcurado instanceof Legivel) {
            Legivel legivel = (Legivel) itemProcurado;
            interfaceUsuario.exibirMensagem(legivel.lerCarta(jogador));
        } else {
            interfaceUsuario.exibirMensagem("O item " + nomeItem + " não pode ser lido.");
        }
    }

    private void imprimirLocalizacaoAtual() {
        interfaceUsuario.exibirMensagem(jogador.getLocalizacaoAtual().getDescricaoPequena(jogador));
        interfaceUsuario.exibirMensagem("");
    }

    private void imprimirDescricaoLonga() {
        interfaceUsuario.exibirMensagem(jogador.getLocalizacaoAtual().getDescricaoLonga(jogador));
        interfaceUsuario.exibirMensagem("");
    }

    private boolean sair(Comando comando) {
        if (comando.temSegundaPalavra()) {
            interfaceUsuario.exibirMensagem("Sair o que?");
            return false;
        }
        interfaceUsuario.exibirMensagem("Obrigado por jogar. Até mais!");
    
        try {
            Thread.sleep(2000); // Pausa para permitir que a mensagem seja vista
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Força o fechamento do programa
        System.exit(0);

        return true; // Este código nunca será alcançado, mas é uma boa prática retornar um valor.
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
                    interfaceUsuario.exibirMensagem("Você está em um ambiente tóxico e perdeu 20 de vida! Em 25 segundos, perderá mais 20 de vida.");
                    interfaceUsuario.exibirMensagem("Vida atual: " + jogador.getVidaJogador());
                    if (jogador.getVidaJogador() <= 0) {
                        jogador.setMorto();
                        encerrarJogo("Você morreu devido à toxicidade do ambiente!");
                        timer.cancel();
                    }
                }
            }
        };
        timer.scheduleAtFixedRate(task, 10000, 25000); 
    }

    private void pararControleAmbienteToxico() {
        if (timer != null) {
            timer.cancel();
        }
    }

    private void encerrarJogo(String mensagem) {
        interfaceUsuario.exibirMensagem(mensagem);
        interfaceUsuario.exibirMensagem("Você conseguiu um total de " + jogador.getPontos() + " pontos!");
        interfaceUsuario.exibirMensagem("Obrigado por jogar. Até mais!");
        if (timer != null) {
            timer.cancel();
        }
        interfaceUsuario.exibirMensagem("O jogo será encerrado em 5 segundos.");
        try {
            Thread.sleep(5000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.exit(0);
    }
}
