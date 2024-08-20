package ppoo.seuJogo;

import java.util.List;

public abstract class Inimigo extends Individuo {
    private double vida;
    private double dano;
    private boolean vivo;
    private List<Item> itensDrop;
    private int pontos;
    private double vidaMaxima;

    public Inimigo(String nome, String descricao, double vida, double dano, int pontos, List<Item> itensDrop) {
        super(nome, descricao);
        this.vida = vida;
        this.dano = dano;
        this.vivo = true;
        this.pontos = pontos;
        this.itensDrop = itensDrop;
        this.vidaMaxima = vida;
    }

    // Getters e Setters

    public List<Item> getItensDrop() {
        return itensDrop;
    }

    public double getVidaMaxima() {
        return vidaMaxima;
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

    public void adicionarVida(double valor) {
        if (this.vida + valor >= vidaMaxima) {
            this.vida = vidaMaxima;
            return;
        }
        this.vida += valor;
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
        }
        return danoAplicado;
    }

    public void receberDano(double danoRecebido) {
        if (vivo) {
            vida -= danoRecebido;
            if (vida <= 0) {
                vivo = false;
            }
        }
    }

    public abstract double escolherAtaque();

    public abstract double getDanoMultiplicador(String weaponType);
}
