package ppoo.seuJogo;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Inimigo extends Individuo {
    private double vida;
    private double dano;
    private boolean vivo;
    private Timer ataqueTimer;
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
    Random random = new Random();
    int delayInicial = 5000 + random.nextInt(20000); // Gera um valor entre 5000 e 25000 milissegundos (5 a 25 segundos)
    scheduleNextAttack(jogador, random, delayInicial);
    }

    private void scheduleNextAttack(Jogador jogador, Random random, int delay) {
        ataqueTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (vivo) {
                    atacar(jogador);
                    if (!jogador.getVivo()) {
                        System.out.println("Você foi derrotado pelo inimigo " + getNome() + ".");
                        System.exit(0); // Encerra o jogo se o jogador morrer
                    }
                    System.out.println("sua vida atual é: " + jogador.getVidaJogador());
                    // Programa o próximo ataque com uma duração aleatória
                    int nextDelay = 5000 + random.nextInt(20000); // Gera um valor entre 5000 e 25000 milissegundos (5 a 25 segundos)
                    scheduleNextAttack(jogador, random, nextDelay);
                } else {
                    ataqueTimer.cancel();;
                }
            }
        }, delay);
    }

    public void pararAtaque() {
        if (ataqueTimer != null) {
            ataqueTimer.cancel();
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
