import java.util.ArrayList;

public class Usuario {

    String nome;
    ArrayList<Playlist> playlists = new ArrayList<>();

    public void criarPlaylist(String nome) {
        Playlist p = new Playlist();
        p.nome = nome;
        playlists.add(p);
    }

    public Playlist getPlaylist(int indice) {
        if (indice >= 0 && indice < playlists.size()) {
            return playlists.get(indice);
        }
        System.out.println("Playlist inválida.");
        return null;
    }

    public void listarPlaylists() {
        if (playlists.isEmpty()) {
            System.out.println("Nenhuma playlist criada.");
            return;
        }

        for (int i = 0; i < playlists.size(); i++) {
            System.out.println((i + 1) + ". " + playlists.get(i).nome);
        }
    }

    public int getTotalPlaylists() {
        return playlists.size();
    }
}