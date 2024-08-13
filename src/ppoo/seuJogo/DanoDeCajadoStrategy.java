package ppoo.seuJogo;

public class DanoDeCajadoStrategy implements DanoDeArmaStrategy {
    @Override
    public double calcularDano(double baseDamage, Inimigo inimigo) {
        return baseDamage * 1.2 * inimigo.getDanoMultiplicador("cajado");
    }
}
