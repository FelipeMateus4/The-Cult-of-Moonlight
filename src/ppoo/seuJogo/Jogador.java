package ppoo.seuJogo;

import java.util.*;

public class Jogador {
    private String nome;
    private String classe;
    private Double vida;
    private Arma armaAtual;
    private Armadura armaduraAtual;
    private Ambiente localizacaoAtual;
    private Map<String, Item> mochila;
    private Acessorio acessorioAtual;
    private static final int LIMITE_MOCHILA = 8;

    public Jogador(String nome, String classe, Double vida, Arma armaAtual, Armadura armaduraAtual, Acessorio acessorioAtual, Ambiente localizacaoAtual) {
        this.nome = nome;
        this.classe = classe;
        this.vida = vida;
        this.armaAtual = armaAtual;
        this.armaduraAtual = armaduraAtual;
        this.acessorioAtual = acessorioAtual;
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

    public Arma getArmaAtual() {
        return armaAtual;
    }

    public Armadura getArmaduraAtual() {
        return armaduraAtual;
    }

    public void setVidaJogador(Double vida) {
        this.vida = vida;
    }

    public void setArmaAtual(Arma armaAtual) {
        this.armaAtual = armaAtual;
    }

    public void setArmaduraAtual(Armadura armaduraAtual) {
        this.armaduraAtual = armaduraAtual;
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

    public boolean adicionarItem(Item item) {
        if (mochila.size() >= LIMITE_MOCHILA) {
            return false;
        }
        mochila.put(item.getNome(), item);
        return true;
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

    public double beber(Pocao pocao) {
        if (vida + pocao.getVidaDada() > 100) {
            vida = 100.0;
        }
        else {
            vida += pocao.getVidaDada();
        }
        System.out.println(vida);
        return vida;
    }

    public Acessorio getAcessorioAtual() {
        return acessorioAtual;
    }

    public void setAcessorioAtual(Acessorio acessorioAtual) {
        this.acessorioAtual =  acessorioAtual;
    }

    public void perderVida(double quantidade) {
        this.vida -= quantidade;
        if (this.vida < 0) {
            this.vida = 0.0;
        }
    }

}