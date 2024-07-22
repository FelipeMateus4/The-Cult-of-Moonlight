package ppoo.seuJogo;

import java.util.ArrayList;

public class AmbienteEscuro extends Ambiente {
    private boolean escuro;

    public AmbienteEscuro(String descricao) {
        super(descricao);
        this.escuro = true;
    }

    public AmbienteEscuro(String descricao, ArrayList<Item> ItemAmbiente) {
        super(descricao, ItemAmbiente);
        this.escuro = true;
    }

    @Override
    public String getDescricaoLonga(Jogador jogador) {
        if (!escuro || jogador.getAcessorioAtual().getEfeito().equals("iluminar")) {
            return super.getDescricaoLonga(jogador);
        }
        return "Você não consegue ver nada, está escuro demais.\n";
    }

    @Override
    public String getDescricaoPequena(Jogador jogador) {
        if (!escuro || jogador.getAcessorioAtual().getEfeito().equals("iluminar")) {
            return super.getDescricaoPequena(jogador);
        }
        return "Está escuro demais, com dificuldade você cosegue ver apenas as saídas.\n" + super.getDescricaoPequena(jogador);
    }

    public boolean getEscuro() {
        return escuro;
    }

    public void setClarear() {
        escuro = false;
    }

    public void setEscurecer() {
        escuro = true;
    }

    @Override
    public boolean isToxico() {
        return false;
    }

    @Override
    public boolean isEscuro() {
        return true;
    }
}
