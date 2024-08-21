package ppoo.seuJogo.Inimigo;

import java.util.List;

import ppoo.seuJogo.Jogador;
import ppoo.seuJogo.Equipaveis.Item;
import ppoo.seuJogo.interfaces.Individuo;

public abstract class Inimigo extends Individuo {
    private double vida;
    private double dano;
    private boolean vivo;
    private List<Item> itensDrop;
    private int pontos;
    private double vidaMaxima;
    private boolean boss;

    public Inimigo(String nome, String descricao, double vida, double dano, int pontos, List<Item> itensDrop) {
        super(nome, descricao);
        this.vida = vida;
        this.dano = dano;
        this.vivo = true;
        this.pontos = pontos;
        this.itensDrop = itensDrop;
        this.vidaMaxima = vida;
        this.boss = false;
    }

    // Getters e Setters

    public void setBoss(boolean boss) {
        this.boss = boss;
    }

    public boolean isBoss() {
        return boss;
    }

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

    public Boolean receberDano(double danoRecebido) {
        if (vivo) {
            vida -= danoRecebido;
            if (vida <= 0) {
                vivo = false;
                vida = 0;
                return true;
            }
        }
        return false;
    }

    public abstract double escolherAtaque();

    public abstract double getDanoMultiplicador(String weaponType);
}
