package ppoo.seuJogo;

import java.util.List;
import java.util.Random;

public class Inimigo extends Individuo {
    private double vida;
    private double dano;
    private boolean vivo;
    private List<Item> itensDrop;

    public Inimigo(String nome, String descricao, double vida, double dano, List<Item> itensDrop) {
        super(nome, descricao);
        this.vida = vida;
        this.dano = dano;
        this.vivo = true;
        this.itensDrop = itensDrop;
    }

    // Getters e Setters

    public List<Item> getItensDrop() {
        return itensDrop;
    }

    public double getVida() {
        return vida;
    }

    public double getDano() {
        return dano;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVida(double vida) {
        this.vida = vida;
    }

    public void setDano(double dano) {
        this.dano = dano;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    public void atacar(Jogador jogador) {
        if (vivo) {
            double danoAplicado = escolherAtaque();
            jogador.perderVida(danoAplicado);
            if (jogador.getVidaJogador() <= 0) {
                jogador.setMorto();
            }
        }
    }

    private double escolherAtaque() {
        Random random = new Random();
        int tipoAtaque = random.nextInt(3); // 0, 1, ou 2

        switch (tipoAtaque) {
            case 0:
                return dano * 0.8; // Ataque rápido
            case 1:
                return dano * 1.5; // Ataque forte
            case 2:
                return dano; // Ataque normal
            default:
                return dano;
        }
    }

    public void receberDano(double danoRecebido) {
        if (vivo) {
            vida -= danoRecebido;
            if (vida <= 0) {
                vivo = false;
            }
        }
    }

    public double getDanoMultiplicador(String weaponType) {
        switch (weaponType) {
            case "espada":
                return 1.0;
            case "adaga":
                return 0.7;
            case "cajado":
                return 1.5;
            default:
                return 1.0;
        }
    }
}
