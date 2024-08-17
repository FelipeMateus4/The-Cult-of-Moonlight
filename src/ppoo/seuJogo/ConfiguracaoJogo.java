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
    private List<Item> itensSemAmbiente = new ArrayList<>();

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
        if (partes.length < 3) {
            throw new IllegalArgumentException("Configuração inválida para ambiente: " + linha);
        }
        String nome = partes[0];
        String descricao = partes[1];
        String caminhoImagem = partes[2];
        String[] saidas = Arrays.copyOfRange(partes, 3, partes.length);

        Ambiente ambiente;
        boolean isEscuro = false;
        boolean isToxico = false;

        for (int i = 3; i < partes.length; i++) {
            if (partes[i].equalsIgnoreCase("Escuro")) {
                isEscuro = true;
            }
            if (partes[i].equalsIgnoreCase("Toxico")) {
                isToxico = true;
            }
        }

        if (isEscuro) {
            ambiente = new AmbienteEscuro(descricao, caminhoImagem);
        } else if (isToxico) {
            ambiente = new AmbienteToxico(descricao, caminhoImagem);
        } else {
            ambiente = new Ambiente(descricao, caminhoImagem);
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

                    String motivoBloqueio = (direcaoAmbienteChave.length > 2) ? direcaoAmbienteChave[2] : "A saída está trancada.";

                    saidasPendentes.add(new String[]{nome, direcao, ambienteDestino, chave, motivoBloqueio});
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
            String chave = saida.length >= 4 ? saida[3] : null;
            String motivoBloqueio = saida.length == 5 ? saida[4] : null;

            Ambiente ambiente = ambientes.get(nomeAmbiente);
            Ambiente ambienteSaida = ambientes.get(nomeAmbienteSaida);

            if (ambiente != null && ambienteSaida != null) {
                try {
                    Direcao direcao = Direcao.valueOf(direcaoString.toUpperCase());
                    if (chave != null) {
                        ambiente.ajustarSaidaBloqueada(direcao, ambienteSaida, chave, motivoBloqueio);
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
        if (partes.length < 6) {
            throw new IllegalArgumentException("Configuração inválida para item: " + linha);
        }
        String nome = partes[0];
        String descricao = partes[1];
        String tipo = partes[2];
        String ambienteInicial = partes[3].equals("null") ? null : partes[3];
        String caminhoImagem = partes[4];
        Item item = null;
        
        switch (tipo) {
            case "Acessorio":
                String efeito = partes[5];
                item = new Acessorio(nome, descricao, efeito, caminhoImagem);
                break;
            case "Espada":
                double danoEspada = Double.parseDouble(partes[5]);
                int durabilidadeEspada = Integer.parseInt(partes[6]);
                item = new Espada(nome, danoEspada, descricao, durabilidadeEspada, caminhoImagem);
                break;
            case "Pocao":
                double recuperacao = Double.parseDouble(partes[5]);
                int usos = Integer.parseInt(partes[6]);
                item = new Pocao(nome, descricao, recuperacao, usos, caminhoImagem);
                break;
            case "Consumivel":
                int usosConsumivel = Integer.parseInt(partes[5]);
                item = new Consumivel(nome, descricao, usosConsumivel, caminhoImagem);
                break;
            case "Carta":
                String textoCarta = partes[5];
                item = new Carta(nome, descricao, textoCarta, caminhoImagem);
                break;
            case "Armadura":
                double defesaFisica = Double.parseDouble(partes[5]);
                double defesaMagica = Double.parseDouble(partes[6]);
                item = new Armadura(nome, descricao, defesaFisica, defesaMagica, caminhoImagem);
                break;
            case "Adaga":
                double danoAdaga = Double.parseDouble(partes[5]);
                int durabilidadeAdaga = Integer.parseInt(partes[6]);
                item = new Adaga(nome, danoAdaga, descricao, durabilidadeAdaga, caminhoImagem);
                break;
            case "Cajado":
                double danoCajado = Double.parseDouble(partes[5]);
                int durabilidadeCajado = Integer.parseInt(partes[6]);
                item = new Cajado(nome, danoCajado, descricao, durabilidadeCajado, caminhoImagem);
                break;
            default:
                throw new IllegalArgumentException("Tipo de item desconhecido: " + tipo);
        }

        if (ambienteInicial != null && ambientes.containsKey(ambienteInicial)) {
            ambientes.get(ambienteInicial).adicionarItem(item);
        } else {
            itensSemAmbiente.add(item);
        }
        itens.add(item);
    }

    private void adicionarPersonagem(String linha) throws IllegalArgumentException {
        String[] partes = linha.split("\\|");
        if (partes.length < 5) {
            throw new IllegalArgumentException("Configuração inválida para personagem: " + linha);
        }
        String nome = partes[0];
        String descricao = partes[1];
        String dialogo = partes[2];
        String ambienteInicial = partes[3];
        String itemNome = partes[4];
        
        Item item = null;
        if (!itemNome.equals("null")) {
            item = encontrarItemPorNome(itemNome);
            if (item == null) {
                throw new IllegalArgumentException("Item não encontrado: " + itemNome);
            }
        }
        
        Npc npc = new Npc(nome, descricao, dialogo, item);
        npcs.add(npc);
        Ambiente ambiente = ambientes.get(ambienteInicial);
        if (ambiente != null) {
            ambiente.adicionarNpc(npc);
        } else {
            System.out.println("Erro: Ambiente " + ambienteInicial + " não encontrado.");
        }
    }

    private void adicionarInimigo(String linha) throws IllegalArgumentException {
        String[] partes = linha.split("\\|");
        if (partes.length < 5) {
            throw new IllegalArgumentException("Configuração inválida para inimigo: " + linha);
        }
    
        String tipoInimigo = partes[0];
        String nome = partes[1];
        String descricao = partes[2];
        String ambienteInicial = partes[3];
        Double vida = Double.parseDouble(partes[4]);
        Double dano = Double.parseDouble(partes[5]);
        int pontos = Integer.parseInt(partes[6]);
    
        List<Item> itensDropados = new ArrayList<>();
        if (partes.length > 7) {
            String[] itensNomes = partes[7].split(",");
            for (String itemNome : itensNomes) {
                Item item = encontrarItemPorNome(itemNome.trim());
                if (item != null) {
                    itensDropados.add(item);
                } else {
                    System.out.println("Item não encontrado: " + itemNome);
                }
            }
        }
        
        Inimigo inimigo = InimigoFactory.criarInimigo(tipoInimigo, nome, descricao, vida, dano, pontos, itensDropados);
    
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
