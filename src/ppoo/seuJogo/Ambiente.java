package ppoo.seuJogo;

import java.util.HashMap;

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
    private HashMap<Direcao, Ambiente> saidas;

    /**
     * Cria um ambiente com a "descricao" passada. Inicialmente, ele não tem saidas.
     * "descricao" eh algo como "uma cozinha" ou "um jardim aberto".
     * 
     * @param descricao A descrição do ambiente.
     */
    public Ambiente(String descricao) {
        this.descricao = descricao;
        saidas = new HashMap<>();
    }

    /**
     * Define uma saída do ambiente.
     * 
     * @param direcao A direção daquela saída.
     * @param saida   O ambiente para o qual a direção leva.
     */
    public void ajustarSaida(Direcao direcao, Ambiente saida) {
        saidas.put(direcao, saida);
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
        return saidas.get(direcao);
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
    public String getDescricaoLonga() {
        String desc = "Você está " + getDescricao() + "\n";
        desc += "Saídas: " + direcoesDeSaida();
        return desc;
    }

}
