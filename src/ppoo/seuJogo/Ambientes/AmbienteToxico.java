package ppoo.seuJogo.Ambientes;

import java.util.ArrayList;

import ppoo.seuJogo.Jogador;
import ppoo.seuJogo.Equipaveis.Item;

public class AmbienteToxico extends Ambiente {
    private boolean toxico;

    public AmbienteToxico(String descricao, String caminhoImagem) {
        super(descricao, caminhoImagem);
        this.toxico = true;
    }

    public AmbienteToxico(String descricao, String caminhoImagem, ArrayList<Item> ItemAmbiente) {
        super(descricao, caminhoImagem, ItemAmbiente);
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
