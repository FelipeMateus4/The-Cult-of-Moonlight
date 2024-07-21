package ppoo.seuJogo;

public class Acessorio extends Item {
    private String efeito;

    Acessorio (String nome, String descricao, String efeito) {
        super(nome, descricao);
        this.efeito =  efeito;
    }
    public String getEfeito() {
        return efeito;
    }
}
