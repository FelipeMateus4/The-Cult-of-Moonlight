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
    if (descricao.toLowerCase().contains("escuro")) {
        ambiente = new AmbienteEscuro(descricao);
    } else if (descricao.toLowerCase().contains("toxico")) {
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
        String efeito = partes[4];
        Item item = null;
        switch (tipo) {
            case "Acessorio":
                item = new Acessorio(nome, descricao, efeito);
                break;
            case "Espada":
                item = new Espada(nome, Double.parseDouble(partes[5]), descricao, Integer.parseInt(partes[6]));
                break;
            case "Pocao":
                item = new Pocao(nome, descricao, Double.parseDouble(partes[5]), Integer.parseInt(partes[6]));
                break;
            // Adicione outros tipos de itens aqui
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
        Inimigo inimigo = new Inimigo(nome, descricao, 100, 10, new ArrayList<>()); // Exemplo de valores para vida e dano
        inimigos.add(inimigo);
        ambientes.get(ambienteInicial).adicionarInimigo(inimigo);
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
