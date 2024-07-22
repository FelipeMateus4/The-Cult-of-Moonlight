package ppoo.seuJogo;

import java.util.*;

public class Ambiente implements AmbienteModificado{
    private String descricao;
    private HashMap<Direcao, Saida> saidas;
    private ArrayList<Item> ItemAmbiente;

    public Ambiente(String descricao) {
        this.descricao = descricao;
        this.saidas = new HashMap<>();
        this.ItemAmbiente = new ArrayList<>();
    }

    public Ambiente(String descricao, ArrayList<Item> ItemAmbiente) {
        this(descricao);
        this.ItemAmbiente = ItemAmbiente;
    }

    public Saida getSaidaBloqueada(String nomeItem) {
        for (Saida saida : saidas.values()) {
            if (saida.estaTrancado() && saida.getChave().equals(nomeItem)) {
                return saida;
            }
        }
        return null;
    }

    public void ajustarSaida(Direcao direcao, Ambiente saida) {
        saidas.put(direcao, new Saida(saida));
    }

    public void ajustarSaidaBloqueada(Direcao direcao, Ambiente saida, String chave, String motivo) {
        saidas.put(direcao, new Saida(saida, true, chave, motivo));
    }

    public String getDescricao() {
        return descricao;
    }

    public Ambiente getSaida(Direcao direcao) {
        Saida saida = saidas.get(direcao);
        if (saida != null && !saida.estaTrancado()) {
            return saida.getDestino();
        } else if (saida != null && saida.estaTrancado()){
            return this;
        } else {
            return null;
        }
    }

    public String direcoesDeSaida() {
        String texto = "";
        for (Direcao direcao : saidas.keySet()) {
            texto += direcao + " ";
        }
        return texto;
    }

    public String getDescricaoLonga(Jogador jogador) {
        String desc = "Você está " + getDescricao() + "\n";
        if (temItem()) {
            desc += "Você avistou ";
            for (int i = 0; i < ItemAmbiente.size(); i++){
                if (i > 0 && i == ItemAmbiente.size() - 1) {
                    desc += " e ";
                }
                else if (i > 0 && i < ItemAmbiente.size()) {
                    desc += ", ";
                }
                desc += ItemAmbiente.get(i).getDescricao() + " cujo nome é " + ItemAmbiente.get(i).getNome();
            }
            desc += "." + "\n";
        }
        else {
            desc += "Nao há nada de especial aqui.\n";
        }
        desc += "Saídas: " + direcoesDeSaida();
        return desc;
    }

    public String getDescricaoPequena(Jogador jogador) {
        String desc = "Você está " + getDescricao() + "\n";
        desc += "Saídas: " + direcoesDeSaida();
        return desc;
    }

    public boolean temItem() {
        return !ItemAmbiente.isEmpty();
    }

    public List<Item> getitens() {
        return Collections.unmodifiableList(ItemAmbiente);
    }

    public void removerItemAmbiente(String nome){
        for (int i = 0; i < ItemAmbiente.size(); i++) {
            if (ItemAmbiente.get(i).getNome().equals(nome)) {
                ItemAmbiente.remove(i);
            }
        }
    }

    public Item getItem(String nome) {
        for (Item item : ItemAmbiente) {
            if (item.getNome().equals(nome)) {
                return item;
            }
        }
        return null;
    }

    public void largarItem(Item item) {
        ItemAmbiente.add(item);
    }

    public String getMotivo(Direcao direcao) {
        Saida saida = saidas.get(direcao);
        String motivo =  saida.getMotivoBloqueio();
        return motivo;
    }
    @Override
    public boolean isToxico() {
        return false;
    }

    @Override
    public boolean isEscuro() {
        return false;
    }
}
