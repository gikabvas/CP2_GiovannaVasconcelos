import java.util.ArrayList;

public class Playlist {

    String nome;
    ArrayList<Musica> musicas = new ArrayList<>();

    public Playlist(String nome) {
        this.nome = nome;
    }

    public void adicionarMusica(Musica musica) {
        musicas.add(musica);
    }

    public void removerMusica(int indice) {
        if (indice >= 0 && indice < musicas.size()) {
            musicas.remove(indice);
        } else {
            System.out.println("Índice inválido!");
        }
    }

    public void listarMusicas() {
        for (Musica m : musicas) {
            m.exibir();
        }
    }

    public int getDuracaoTotal() {
        int total = 0;
        for (Musica m : musicas) {
            total += m.duracaoSegundos;
        }
        return total;
    }

    public int getQuantidadeMusicas() {
        return musicas.size();
    }
}