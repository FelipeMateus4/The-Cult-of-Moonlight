package ppoo.seuJogo;

public class Npc {
    private String nome;
    private String descricao;
    private String dialogo;

    public Npc(String nome, String descricao, String dialogo) {
        this.nome = nome;
        this.descricao = descricao;
        this.dialogo = dialogo;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getDialogo() {
        return dialogo;
    }
}