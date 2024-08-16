package ppoo.seuJogo;

import java.io.*;
import java.util.*;

public class ConfiguracaoJogo {
    private Map<String, Ambiente> ambientes;
    private List<Item> itens;
    private List<Npc> npcs;
    private List<Inimigo> inimigos;
    private String nomeJogador;
    private String classeJogador;
    private List<String[]> saidasPendentes;
    private Ambiente ambienteInicial;

    public ConfiguracaoJogo(String caminhoArquivo) throws IOException {
        ambientes = new HashMap<>();
        itens = new ArrayList<>();
        npcs = new ArrayList<>();
        inimigos = new ArrayList<>();
        saidasPendentes = new ArrayList<>();
        carregarConfiguracao(caminhoArquivo);
    }

    private void carregarConfiguracao(String caminhoArquivo) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo));
        String linha;
        String secao = "";
        boolean ambienteInicialDefinido = false;

        while ((linha = br.readLine()) != null) {
            linha = linha.trim();
            if (linha.isEmpty()) {
                continue;
            }
            if (linha.startsWith("[") && linha.endsWith("]")) {
                secao = linha.substring(1, linha.length() - 1);
            } else {
                try {
                    switch (secao) {
                        case "Jogador":
                            adicionarJogador(linha);
                            break;
                        case "Ambientes":
                            adicionarAmbiente(linha);
                            if (!ambienteInicialDefinido) {
                                ambienteInicialDefinido = true;
                            }
                            break;
                        case "Itens":
                            adicionarItem(linha);
                            break;
                        case "Personagens":
                            adicionarPersonagem(linha);
                            break;
                        case "Inimigos":
                            adicionarInimigo(linha);
                            break;
                        default:
                            throw new IllegalArgumentException("Seção desconhecida: " + secao);
                    }
                } catch (Exception e) {
                    System.out.println("Erro ao processar linha na seção " + secao + ": " + linha + " - " + e.getMessage());
                }
            }
        }

        System.out.println(classeJogador);
        br.close();
        ajustarSaidasPendentes();
    }

    private void adicionarJogador(String linha) throws IllegalArgumentException {
        String[] partes = linha.split("=");
        if (partes.length < 2) {
            throw new IllegalArgumentException("Configuração inválida para jogador: " + linha);
        }
        if (partes[0].equals("Nome")) {
            nomeJogador = partes[1];
        } else if (partes[0].equals("Classe")) {
            classeJogador = partes[1];
        }
    }

private void adicionarAmbiente(String linha) throws IllegalArgumentException {
    String[] partes = linha.split("\\|");
    if (partes.length < 2) {
        throw new IllegalArgumentException("Configuração inválida para ambiente: " + linha);
    }
    String nome = partes[0];
    String descricao = partes[1];
    String[] saidas = Arrays.copyOfRange(partes, 2, partes.length);

    Ambiente ambiente;
    boolean isEscuro = false;
    boolean isToxico = false;

    for (int i = 2; i < partes.length; i++) {
        if (partes[i].equalsIgnoreCase("Escuro")) {
            isEscuro = true;
        }
        if (partes[i].equalsIgnoreCase("Toxico")) {
            isToxico = true;
        }
    }

  
      if (isEscuro) {
        ambiente = new AmbienteEscuro(descricao);
    } else if (isToxico) {
        ambiente = new AmbienteToxico(descricao);
    } else {
        ambiente = new Ambiente(descricao);
    }

    ambientes.put(nome, ambiente);

    if (ambienteInicial == null) {
        ambienteInicial = ambiente;
    }

    for (String saida : saidas) {
        if (saida.contains(":")) {
            String[] direcaoAmbienteChave = saida.split(":");
            String[] direcaoAmbiente = direcaoAmbienteChave[0].split("=");
            if (direcaoAmbiente.length == 2) {
                String direcao = direcaoAmbiente[0];
                String ambienteDestino = direcaoAmbiente[1];
                String chave = direcaoAmbienteChave[1];
                saidasPendentes.add(new String[]{nome, direcao, ambienteDestino, chave});
            }
        } else {
            String[] direcaoAmbiente = saida.split("=");
            if (direcaoAmbiente.length == 2) {
                saidasPendentes.add(new String[]{nome, direcaoAmbiente[0], direcaoAmbiente[1]});
            }
        }
    }
}

private void ajustarSaidasPendentes() {
    for (String[] saida : saidasPendentes) {
        String nomeAmbiente = saida[0];
        String direcaoString = saida[1];
        String nomeAmbienteSaida = saida[2];
        String chave = saida.length == 4 ? saida[3] : null;

        Ambiente ambiente = ambientes.get(nomeAmbiente);
        Ambiente ambienteSaida = ambientes.get(nomeAmbienteSaida);

        if (ambiente != null && ambienteSaida != null) {
            try {
                Direcao direcao = Direcao.valueOf(direcaoString.toUpperCase());
                if (chave != null) {
                    ambiente.ajustarSaidaBloqueada(direcao, ambienteSaida, chave, "A saída está trancada.");
                } else {
                    ambiente.ajustarSaida(direcao, ambienteSaida);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: Direção " + direcaoString + " inválida.");
            }
        } else {
            if (ambiente == null) {
                System.out.println("Erro: Ambiente " + nomeAmbiente + " não encontrado.");
            }
            if (ambienteSaida == null) {
                System.out.println("Erro: Ambiente de saída " + nomeAmbienteSaida + " não encontrado.");
            }
        }
    }
}

private void adicionarItem(String linha) throws IllegalArgumentException {
    String[] partes = linha.split("\\|");
    if (partes.length < 5) {
        throw new IllegalArgumentException("Configuração inválida para item: " + linha);
    }
    String nome = partes[0];
    String descricao = partes[1];
    String tipo = partes[2];
    String ambienteInicial = partes[3];
    Item item = null;
    
    switch (tipo) {
        case "Acessorio":
            String efeito = partes[4];
            item = new Acessorio(nome, descricao, efeito);
            break;
        case "Espada":
            double danoEspada = Double.parseDouble(partes[4]);
            int durabilidadeEspada = Integer.parseInt(partes[5]);
            item = new Espada(nome, danoEspada, descricao, durabilidadeEspada);
            break;
        case "Pocao":
            double recuperacao = Double.parseDouble(partes[4]);
            int usos = Integer.parseInt(partes[5]);
            item = new Pocao(nome, descricao, recuperacao, usos);
            break;
        case "Consumivel":
            int usosConsumivel = Integer.parseInt(partes[4]);
            item = new Consumivel(nome, descricao, usosConsumivel);
            break;
        case "Carta":
            String textoCarta = partes[4];
            item = new Carta(nome, descricao, textoCarta);
            break;
        case "Armadura":
            double defesaFisica = Double.parseDouble(partes[4]);
            double defesaMagica = Double.parseDouble(partes[5]);
            item = new Armadura(nome, descricao, defesaFisica, defesaMagica);
            break;
        case "Adaga":
            double danoAdaga = Double.parseDouble(partes[4]);
            int durabilidadeAdaga = Integer.parseInt(partes[5]);
            item = new Adaga(nome, danoAdaga, descricao, durabilidadeAdaga);
            break;
        default:
            throw new IllegalArgumentException("Tipo de item desconhecido: " + tipo);
    }

    itens.add(item);
    ambientes.get(ambienteInicial).adicionarItem(item);
}


    private void adicionarPersonagem(String linha) throws IllegalArgumentException {
        String[] partes = linha.split("\\|");
        if (partes.length < 4) {
            throw new IllegalArgumentException("Configuração inválida para personagem: " + linha);
        }
        String nome = partes[0];
        String descricao = partes[1];
        String dialogo = partes[2];
        String ambienteInicial = partes[3];
        Npc npc = new Npc(nome, descricao, dialogo);
        npcs.add(npc);
        ambientes.get(ambienteInicial).adicionarNpc(npc);
    }

    private void adicionarInimigo(String linha) throws IllegalArgumentException {
        String[] partes = linha.split("\\|");
        if (partes.length < 3) {
            throw new IllegalArgumentException("Configuração inválida para inimigo: " + linha);
        }
    
        String nome = partes[0];
        String descricao = partes[1];
        String ambienteInicial = partes[2];
        Double vida = Double.parseDouble(partes[3]);
        Double dano = Double.parseDouble(partes[4]);
    
        List<Item> itensDropados = new ArrayList<>();
        if (partes.length > 5) {
            String[] itensNomes = partes[5].split(",");
            for (String itemNome : itensNomes) {
                Item item = encontrarItemPorNome(itemNome.trim());
                if (item != null) {
                    itensDropados.add(item);
                } else {
                    System.out.println("Item não encontrado: " + itemNome);
                }
            }
        }
    
        Inimigo inimigo = new Inimigo(nome, descricao, vida, dano, itensDropados);
        inimigos.add(inimigo);
        ambientes.get(ambienteInicial).adicionarInimigo(inimigo);
    }
    

    private Item encontrarItemPorNome(String nome) {
        for (Item item : itens) {
            if (item.getNome().equalsIgnoreCase(nome)) {
                return item;
            }
        }
        return null;
    }

    public Map<String, Ambiente> getAmbientes() {
        return ambientes;
    }

    public List<Item> getItens() {
        return itens;
    }

    public List<Npc> getNpcs() {
        return npcs;
    }

    public List<Inimigo> getInimigos() {
        return inimigos;    
    }

    public String getNomeJogador() {
        return nomeJogador;
    }

    public String getClasseJogador() {
        return classeJogador;
    }

    public Ambiente getAmbienteInicial() {
        return ambienteInicial;
    }
}
