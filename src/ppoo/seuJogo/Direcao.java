package ppoo.seuJogo;

public enum Direcao {
    NORTE("norte"),
    SUL("sul"),
    LESTE("leste"),
    OESTE("oeste"),
    BAIXO("baixo"),
    CIMA("cima"),
    SUDOESTE("sudoeste"),
    SUDESTE("sudeste"),
    NOROESTE("noroeste"),
    NORDESTE("nordeste"),
    DESCONHECIDA("?"); // usada quando a direção é inválida

    // Atributo string com a representação do enumerador como String.
    private String direcaoString;

    // Construtor privado
    private Direcao(String direcaoString) {
        this.direcaoString = direcaoString;
    }

    // Sobrescrita do método toString retornado o atributo String
    @Override
    public String toString() {
        return this.direcaoString;
    }

    // Método estático que, dada uma String, retorna o enumerador
    public static Direcao pelaString(String direcao) {
        for (Direcao d : Direcao.values()) {
            if (d.direcaoString.equals(direcao)) {
                return d;
            }
        }
        return Direcao.DESCONHECIDA;
    }
}