package ppoo.seuJogo;

import java.util.*;

public class Jogador {
    private String nome;
    private String classe;
    private Double vida;
    private Ambiente localizacaoAtual;
    private Map<String, Item> mochila;

    public Jogador(String nome, String classe, Double vida, Ambiente localizacaoAtual) {
        this.nome = nome;
        this.classe = classe;
        this.vida = vida;
        this.localizacaoAtual = localizacaoAtual;
        this.mochila = new HashMap<>();
    }

    public String getNomeJogador() {
        return nome;
    }

    public String getClasseJogador() {
        return classe;
    }

    public Double getVidaJogador() {
        return vida;
    }

    public Ambiente getLocalizacaoAtual() {
        return localizacaoAtual;
    }

    public boolean temItem(String nomeItem) {
        return mochila.containsKey(nomeItem);
    }

    public void setLocalizacaoAtual(Ambiente localizacaoAtual) {
        this.localizacaoAtual = localizacaoAtual;
    }

    public void adicionarItem(Item item) {
        mochila.put(item.getNome(), item);
    }

    public Item removerItem(String nomeItem) {
        return mochila.remove(nomeItem);
    }

    public String getItensCarregados() {
        if (mochila.isEmpty()) {
            return "Mochila vazia!";
        }

        String itens = "Itens na mochila: ";
        for (String nomeItem : mochila.keySet()) {
            itens += nomeItem + ", ";
        }

        // Remove a última vírgula e espaço e adiciona um ponto final
        itens = itens.substring(0, itens.length() - 2) + ".";

        return itens;
    }

    public Item getItemEspecifico(String nome) {
        Item aux = null;
        aux = mochila.get(nome);
        return aux;
    }
}
