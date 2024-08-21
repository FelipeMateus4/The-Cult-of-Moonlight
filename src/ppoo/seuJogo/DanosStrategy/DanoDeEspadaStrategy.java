package ppoo.seuJogo.DanosStrategy;

import ppoo.seuJogo.Inimigo.Inimigo;

public class DanoDeEspadaStrategy implements DanoDeArmaStrategy {
    @Override
    public double calcularDano(double baseDamage, Inimigo inimigo) {
        return baseDamage * 1.2 * inimigo.getDanoMultiplicador("espada");
    }
}
