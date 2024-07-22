package ppoo.seuJogo;

import java.util.ArrayList;

public class AmbienteToxico extends Ambiente {
    private boolean toxico;

    public AmbienteToxico(String descricao) {
        super(descricao);
        this.toxico = true;
    }

    public AmbienteToxico(String descricao, ArrayList<Item> ItemAmbiente) {
        super(descricao, ItemAmbiente);
        this.toxico = true;
    }

    @Override
    public String getDescricaoLonga(Jogador jogador) {
        String desc = super.getDescricaoLonga(jogador);
        if (toxico && !jogador.getAcessorioAtual().getEfeito().equals("proteger")) {
            desc += "\nO ambiente é tóxico, você não pode ficar aqui.\n";
        }
        return desc;
    }

    @Override
    public String getDescricaoPequena(Jogador jogador) {
        String desc = super.getDescricaoPequena(jogador);
        if (toxico && !jogador.getAcessorioAtual().getEfeito().equals("proteger")) {
            desc += "\nO ambiente é tóxico, você não pode ficar aqui.";
        }
        return desc;
    }

    public boolean getToxico() {
        return toxico;
    }

    @Override
    public boolean isToxico() {
        return true;
    }

    @Override
    public boolean isEscuro() {
        return false;
    }
}
