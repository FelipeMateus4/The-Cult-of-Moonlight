package ppoo.seuJogo;

import java.util.List;
import java.util.Random;


public class Boss extends Inimigo {

    public Boss(String nome, String descricao, double vida, double dano, List<Item> itensDrop, int pontos) {
        super(nome, descricao, vida, dano, itensDrop, pontos);
    }

    @Override
    public double escolherAtaque() {
        Random random = new Random();
        int tipoAtaque = random.nextInt(6); // Gera um número aleatório entre 0, 1, ou 2

        double danoCalculado;
        switch (tipoAtaque) {
            case 0:
                danoCalculado = getDano() * 0.8; // Ataque rápido
                break;
            case 1:
                danoCalculado = getDano() * 1.8; // Ataque forte
                break;
            case 2:
                danoCalculado = getDano(); // Ataque normal
                break;
            default:
                danoCalculado = getDano();
                break;
        }
        return danoCalculado;
    }

    @Override
    public double getDanoMultiplicador(String weaponType) {
        switch (weaponType) {
            case "espada":
                return 0.5;
            case "adaga":
                return 0.5;
            case "cajado":
                return 0.5;
            default:
                return 0.5;
        }
    }

    @Override
    public double atacar(Jogador jogador) {
        double danoAplicado = 0.0;
        if (isVivo()) {
            danoAplicado = escolherAtaque();
            jogador.perderVida(danoAplicado);
            setVida(danoAplicado * 0.5);

            if (jogador.getVidaJogador() <= 0) {
                jogador.setMorto();
            }
        }
        return danoAplicado;
    }
    
}
