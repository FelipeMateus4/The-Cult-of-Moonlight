package ppoo.seuJogo;

public class Npc {
    private String nome;
    private String descricao;
    private String dialogo;
    private Ambiente ambiente;

    public Npc(String nome, String descricao, String dialogo, Ambiente ambiente) {
        this.nome = nome;
        this.descricao = descricao;
        this.dialogo = dialogo;
        this.ambiente = ambiente;
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

    public Ambiente getAmbiente() {
        return ambiente;
    }
}