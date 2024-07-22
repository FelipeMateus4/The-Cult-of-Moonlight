package ppoo.seuJogo;

import java.util.ArrayList;

public class AmbienteEscuro extends Ambiente {

    public AmbienteEscuro(String descricao, String tipoAmbiente) {
        super(descricao, tipoAmbiente);
    }

    public AmbienteEscuro(String descricao, ArrayList<Item> ItemAmbiente, String tipoAmbiente) {
        super(descricao, ItemAmbiente, tipoAmbiente);
    }

    @Override
    public String getDescricaoLonga() {
        return "Você está em um ambiente escuro. Você precisa de algo para iluminar o caminho.";
    }

    @Override
    public String getDescricaoPequena() {
        return "Você está em um ambiente escuro. Você precisa de algo para iluminar o caminho.";
    }

}
