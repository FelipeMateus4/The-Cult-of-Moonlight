package ppoo.seuJogo;

public class Npc {
    private String nome;
    private String descricao;
    private String dialogo;
    private Item item;

    public Npc(String nome, String descricao, String dialogo, Item item) {
        this.nome = nome;
        this.descricao = descricao;
        this.dialogo = dialogo;
        this.item = item;
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

    public Item getItem() {
        return item;
    }

    public void removerItem() {
        this.item = null;
    }

}