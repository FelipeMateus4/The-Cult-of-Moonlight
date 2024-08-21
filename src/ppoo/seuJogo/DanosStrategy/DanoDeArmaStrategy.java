package ppoo.seuJogo.DanosStrategy;

import ppoo.seuJogo.Inimigo.Inimigo;

public interface DanoDeArmaStrategy {
    double calcularDano(double baseDamage, Inimigo inimigo);
}
