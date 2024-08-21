package ppoo.seuJogo.Inimigo;

import java.util.List;
import java.util.Random;

import ppoo.seuJogo.Equipaveis.Item;

public class InimigoComum extends Inimigo {
    Random random = new Random();

    public InimigoComum(String nome, String descricao, double vida, double dano, int pontos, List<Item> itensDrop) {
        super(nome, descricao, vida, dano, pontos, itensDrop);
    }

    @Override
    public double escolherAtaque() {
        int tipoAtaque = random.nextInt(3);

        double danoCalculado;
        switch (tipoAtaque) {
            case 0:
                danoCalculado = getDano() * 0.7; // Ataque rápido
                break;
            case 1:
                danoCalculado = getDano() * 1.1; // Ataque forte
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
                return 1.0;
            case "adaga":
                return 1.0;
            case "cajado":
                return 1.0;
            default:
                return 1.0;
        }
    }
}
