package ppoo.seuJogo;

public class DanoDeAdagaStrategy implements DanoDeArmaStrategy {
    @Override
    public double calcularDano(double baseDamage, Inimigo inimigo) {
        return baseDamage * 1.5 * inimigo.getDanoMultiplicador("adaga");
    }
}
