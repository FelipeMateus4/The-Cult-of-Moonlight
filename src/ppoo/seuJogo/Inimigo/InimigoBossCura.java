package ppoo.seuJogo.Inimigo;

import java.util.List;
import java.util.Random;

import ppoo.seuJogo.Jogador;
import ppoo.seuJogo.Equipaveis.Item;


public class InimigoBossCura extends Inimigo {
    Random random = new Random();

    public InimigoBossCura(String nome, String descricao, double vida, double dano, int pontos, List<Item> itensDrop) {
        super(nome, descricao, vida, dano, pontos, itensDrop);
        setBoss(true);
    }

    @Override
    public double escolherAtaque() {
        int tipoAtaque = random.nextInt(4);

        double danoCalculado;
        switch (tipoAtaque) {
            case 0:
                danoCalculado = getDano() * 0.8; // Ataque rápido
                break;
            case 1:
                danoCalculado = getDano() * 1.3; // Ataque forte
                break;
            case 2:
                danoCalculado = getDano(); // Ataque normal
                break;
            case 3:
                danoCalculado = getDano() * 1.8; // Ataque crítico
                break;
            default:
                danoCalculado = getDano();
                break;
        }
        return danoCalculado;
    }

    @Override
    public double atacar(Jogador jogador) {
        double danoAplicado = 0.0;
        if (isVivo()) {
            danoAplicado = escolherAtaque();
            adicionarVida(danoAplicado * 0.5);
        }
        return danoAplicado;
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
    
}