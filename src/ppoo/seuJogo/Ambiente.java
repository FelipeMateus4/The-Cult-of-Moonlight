package ppoo.seuJogo;

import ppoo.baseJogo.EntidadeGrafica;
import java.util.*;

public class Ambiente extends EntidadeGrafica implements AmbienteModificado {
    private String descricao;
    private HashMap<Direcao, Saida> saidas;
    private ArrayList<Item> ItemAmbiente;
    private ArrayList<Npc> NpcAmbiente;
    private ArrayList<Inimigo> InimigoAmbiente;

    public Ambiente(String descricao, String caminhoImagem) {
        super(caminhoImagem);
        this.descricao = descricao;
        this.saidas = new HashMap<>();
        this.ItemAmbiente = new ArrayList<>();
        this.NpcAmbiente = new ArrayList<>();
        this.InimigoAmbiente = new ArrayList<>();
    }

    @Override
    public String getNome() {
        return descricao;
    }

    public Ambiente(String descricao, String caminhoImagem, ArrayList<Item> ItemAmbiente) {
        this(descricao, caminhoImagem);
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
        } else if (saida != null && saida.estaTrancado()) {
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
            for (int i = 0; i < ItemAmbiente.size(); i++) {
                Item item = ItemAmbiente.get(i);
                if (item != null) {
                    if (i > 0 && i == ItemAmbiente.size() - 1) {
                        desc += " e ";
                    } else if (i > 0 && i < ItemAmbiente.size()) {
                        desc += ", ";
                    }
                    desc += item.getDescricao() + " cujo nome é " + item.getNome();
                }
            }
            desc += "." + "\n";
        }

        if (!NpcAmbiente.isEmpty()) {
            desc += "Você vê ";
            for (int i = 0; i < NpcAmbiente.size(); i++) {
                Npc npc = NpcAmbiente.get(i);
                if (npc != null) {
                    if (i > 0 && i == NpcAmbiente.size() - 1) {
                        desc += " e ";
                    } else if (i > 0 && i < NpcAmbiente.size()) {
                        desc += ", ";
                    }
                    desc += npc.getDescricao() + " cujo nome é " + npc.getNome();
                }
            }
            desc += "." + "\n";
        }

        if (!InimigoAmbiente.isEmpty()) {
            desc += "Você vê ";
            for (int i = 0; i < InimigoAmbiente.size(); i++) {
                Inimigo inimigo = InimigoAmbiente.get(i);
                if (inimigo != null) {
                    if (i > 0 && i == InimigoAmbiente.size() - 1) {
                        desc += " e ";
                    } else if (i > 0 && i < InimigoAmbiente.size()) {
                        desc += ", ";
                    }
                    desc += inimigo.getDescricao() + " cujo nome é " + inimigo.getNome();
                }
            }
            desc += "." + "\n";
        } else {
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

    public List<Inimigo> getInimigos() {
        return Collections.unmodifiableList(InimigoAmbiente);
    }

    public void removerItemAmbiente(String nome) {
        ItemAmbiente.removeIf(item -> item != null && item.getNome().equals(nome));
    }

    public Item getItem(String nome) {
        for (Item item : ItemAmbiente) {
            if (item != null && item.getNome().equals(nome)) {
                return item;
            }
        }
        return null;
    }

    public void largarItem(Item item) {
        ItemAmbiente.add(item);
    }

    public void adicionarItem(Item item) {
        ItemAmbiente.add(item);
    }

    public String getMotivo(Direcao direcao) {
        Saida saida = saidas.get(direcao);
        if (saida != null) {
            return saida.getMotivoBloqueio();
        }
        return null;
    }

    public void adicionarNpc(Npc npc) {
        NpcAmbiente.add(npc);
    }

    public Npc getNpc(String nome) {
        for (Npc npc : NpcAmbiente) {
            if (npc != null && npc.getNome().equals(nome)) {
                return npc;
            }
        }
        return null;
    }

    public void adicionarInimigo(Inimigo inimigo) {
        InimigoAmbiente.add(inimigo);
    }

    public Inimigo getInimigo(String nome) {
        for (Inimigo inimigo : InimigoAmbiente) {
            if (inimigo != null && inimigo.getNome().equals(nome)) {
                return inimigo;
            }
        }
        return null;
    }

    public void removerInimigo(String nome) {
        InimigoAmbiente.removeIf(inimigo -> inimigo != null && inimigo.getNome().equals(nome));
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
