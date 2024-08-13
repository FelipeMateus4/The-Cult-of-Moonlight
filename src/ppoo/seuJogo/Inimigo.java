package ppoo.seuJogo;

import java.util.Timer;
import java.util.TimerTask;

public class Inimigo extends Individuo {
    private double vida;
    private double dano;
    private boolean vivo;
    private Timer ataqueTimer;

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
        if (!vivo && ataqueTimer != null) {
            ataqueTimer.cancel();  // Para os ataques periódicos se o inimigo morrer
        }
    }

    public void atacar(Jogador jogador) {
        if (vivo) {
            jogador.perderVida(dano);
            System.out.println("O inimigo " + getNome() + " te atacou e causou " + dano + " de dano.");
            if (jogador.getVidaJogador() <= 0) {
                jogador.setMorto();
            }
        }
    }

    public void iniciarAtaquesPeriodicos(Jogador jogador) {
        ataqueTimer = new Timer();
        ataqueTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (vivo) {
                    atacar(jogador);  // Chama o método atacar automaticamente a cada 5 segundos
                } else {
                    ataqueTimer.cancel();  // Cancela o timer se o inimigo não estiver mais vivo
                }
            }
        }, 5000, 5000);  // 5000 milissegundos = 5 segundos
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
