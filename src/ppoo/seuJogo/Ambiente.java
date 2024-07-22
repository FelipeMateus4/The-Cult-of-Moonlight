package ppoo.seuJogo;

import java.util.*;

/**
 * Classe Ambiente - um ambiente em um jogo adventure.
 *
 * Esta classe é parte da aplicação "World of Zuul". "World of Zuul" é um jogo
 * de aventura muito simples, baseado em texto.
 *
 * Um "Ambiente" representa uma localização no cenário do jogo. Ele é conectado
 * aos outros ambientes através de saídas. As saídas são nomeadas como norte,
 * sul, leste e oeste. Para cada direção, o ambiente guarda uma referência para
 * o ambiente vizinho, ou null se não há saída naquela direção.
 * 
 * @author Michael Kölling and David J. Barnes (traduzido e adaptado por Julio
 *         César Alves)
 */
public class Ambiente {
    // descrição do ambiente
    private String descricao;
    // ambientes vizinhos de acordo com a direção
    private HashMap<Direcao, Saida> saidas;
    private ArrayList<Item> ItemAmbiente;
    private boolean escuro;
    private boolean toxico;


    /**
     * Cria um ambiente com a "descricao" passada. Inicialmente, ele não tem saidas.
     * "descricao" eh algo como "uma cozinha" ou "um jardim aberto".
     * 
     * @param descricao A descrição do ambiente.
     */
    public Ambiente(String descricao, boolean escuro, boolean toxico) {
        this.descricao = descricao;
        saidas = new HashMap<>();
        ItemAmbiente = new ArrayList<>();
        this.escuro = escuro;
        this.toxico = toxico;
    }

    public Ambiente(String descricao, boolean escuro, boolean toxico, ArrayList<Item> ItemAmbiente) {
        this(descricao, escuro, toxico);
        this.ItemAmbiente = ItemAmbiente;
    }

    public Saida getSaidaBloqueada(String nomeItem) {
        for (Saida saida : saidas.values()) {
            if (saida.estaTrancado() && saida.getChave().equals(nomeItem)) {
                return saida;
            }
        }
        return null;
    }

    /**
     * Define uma saída do ambiente.
     * 
     * @param direcao A direção daquela saída.
     * @param saida   O ambiente para o qual a direção leva.
     */
    public void ajustarSaida(Direcao direcao, Ambiente saida) {
        saidas.put(direcao, new Saida(saida));
    }

    public void ajustarSaidaBloqueada(Direcao direcao, Ambiente saida, String chave, String motivo) {
        saidas.put(direcao, new Saida(saida, true, chave, motivo));
    }

    /**
     * @return A descrição do ambiente.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna uma saída do ambiente, dada uma direção (null se não existir)
     * 
     * @param direcao Direção à qual a saída se refere
     * @return Ambiente de saída naquela direção
     */
    public Ambiente getSaida(Direcao direcao) {
        Saida saida = saidas.get(direcao);
        if (saida != null && !saida.estaTrancado()) {
            return saida.getDestino();
        } else if (saida != null && saida.estaTrancado()){
            return this;
        } else {
            return null;
        }
    }

    /**
     * Texto montado com todas as saídas disponíveis
     * 
     * @return Texto com as saídas
     */
    public String direcoesDeSaida() {
        String texto = "";
        for (Direcao direcao : saidas.keySet()) {
            texto += direcao + " ";
        }
        return texto;
    }

    /**
     * Retorna uma descrição longa do ambiente. A ideia é que, quando o ambiente
     * evoluir e tiver mais coisas (como itens ou monstros) não seja necessário
     * alterar a classe Jogo para informar o que existe no ambiente.
     * 
     * @return Uma descrição longa do ambiente, incluindo saídas.
     */
    public String getDescricaoLonga(Jogador jogador) {
        String desc = "Você está " + getDescricao() + "\n";
        if (!escuro || jogador.getAcessorioAtual().getEfeito().equals("iluminar")) {
            if (temItem()) {
                desc += "Você avistou ";
                for (int i = 0; i < ItemAmbiente.size(); i++){
                    if (i > 0 && i == ItemAmbiente.size() - 1) {
                        desc += " e ";
                    }
                    else if (i > 0 && i < ItemAmbiente.size()) {
                        desc += ", ";
                    }
                    desc += ItemAmbiente.get(i).getDescricao() + " cujo nome é " + ItemAmbiente.get(i).getNome();
                }
                desc += "." + "\n";
            }
            else {
                desc += "Nao há nada de especial aqui.\n";
            }
            desc += "Saídas: " + direcoesDeSaida();
            return desc;
        }
        if (escuro && !jogador.getAcessorioAtual().getEfeito().equals("iluminar")) {
            desc += "Você não consegue ver nada, está escuro demais." + "\n";
        }
        if (toxico && !jogador.getAcessorioAtual().getEfeito().equals("proteger")) {
            desc += "O ambiente é tóxico, você não pode ficar aqui." + "\n";
        }
        return desc;
    }

    public String getDescricaoPequena(Jogador jogador) {
        String desc = "Você está " + getDescricao() + "\n";
        desc += "Saídas: " + direcoesDeSaida();
        if (toxico && !jogador.getAcessorioAtual().getEfeito().equals("proteger")) {
            desc += "\n" + "O ambiente é tóxico, você não pode ficar aqui.";
        }
        return desc;
    }

    public boolean temItem() {
        return !ItemAmbiente.isEmpty();
    }

    public List<Item> getitens() {
        return Collections.unmodifiableList(ItemAmbiente);
    }

    public Item coletarItemAmbiente(String nome){
        Item aux = null;
        for (int i = 0; i < ItemAmbiente.size(); i++) {
            if (ItemAmbiente.get(i).getNome().equals(nome)) {
                aux = ItemAmbiente.get(i);
                ItemAmbiente.remove(i);
            }
        }
        return aux;
    }

    public void removerItem(Item item) {
        ItemAmbiente.remove(item);
    }

    public void largarItem(Item item) {
        ItemAmbiente.add(item);
    }
    public String getMotivo(Direcao direcao) {
        Saida saida = saidas.get(direcao);
        String motivo =  saida.getMotivoBloqueio();
        return motivo;
    }

    public boolean getEscuro() {
        return escuro;
    }
    public void setClarear() {
        escuro = false;
    }

    public void setEscurecer() {
        escuro = true;
    }

    public boolean getToxico() {
        return toxico;
    }
}
