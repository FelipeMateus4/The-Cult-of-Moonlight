package ppoo.seuJogo;

import java.util.List;
import java.util.Random;

public class Inimigo extends Individuo {
    private double vida;
    private double dano;
    private boolean vivo;
    private List<Item> itensDrop;
    private int pontos;

    public Inimigo(String nome, String descricao, double vida, double dano, List<Item> itensDrop, int pontos) {
        super(nome, descricao);
        this.vida = vida;
        this.dano = dano;
        this.vivo = true;
        this.itensDrop = itensDrop;
        this.pontos = pontos;
    }

    // Getters e Setters

    public List<Item> getItensDrop() {
        return itensDrop;
    }

    public int getPontos() {
        return pontos;
    }

    public double getVida() {
        if (vida < 0) {
            return 0;
        }
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

    public double atacar(Jogador jogador) {
        double danoAplicado = 0.0;
        if (vivo) {
            danoAplicado = escolherAtaque();
            jogador.perderVida(danoAplicado);

            if (jogador.getVidaJogador() <= 0) {
                jogador.setMorto();
            }
        }
        return danoAplicado;
    }

    public double escolherAtaque() {
        Random random = new Random();
        int tipoAtaque = random.nextInt(3); // Gera um número aleatório entre 0, 1, ou 2

        double danoCalculado;
        switch (tipoAtaque) {
            case 0:
                danoCalculado = dano * 0.8; // Ataque rápido
                break;
            case 1:
                danoCalculado = dano * 1.5; // Ataque forte
                break;
            case 2:
                danoCalculado = dano; // Ataque normal
                break;
            default:
                danoCalculado = dano;
                break;
        }
        return danoCalculado;
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
