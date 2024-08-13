package ppoo.seuJogo;

public class Inimigo extends Individuo {
    private double vida;
    private double dano;
    private boolean vivo;

    public Inimigo(String nome, String descricao, double vida, double dano) {
        super(nome, descricao);
        this.vida = vida;
        this.dano = dano;
        this.vivo = true;
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
            jogador.setVidaJogador(jogador.getVidaJogador() - dano);
            System.out.println("O inimigo " + getNome() + " te atacou e causou " + dano + " de dano.");
            if (jogador.getVidaJogador() <= 0) {
                jogador.setMorto();
            }
        }
    }

    public double getDanoMultiplicador(String weaponType) {
        switch (weaponType) {
            case "espada":
                return 1.0; // Exemplo de multiplicador para espada
            case "adaga":
                return 0.7; // Exemplo de multiplicador para adaga
            case "cajado":
                return 1.5; // Exemplo de multiplicador para cajado
            default:
                return 1.0;
        }
    }
}
