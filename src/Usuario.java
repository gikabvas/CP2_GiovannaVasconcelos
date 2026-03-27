import java.util.ArrayList;

public class Usuario {

    String nome;
    ArrayList<Playlist> playlists = new ArrayList<>();

    public Usuario(String nome) {
        this.nome = nome;
    }

    public void criarPlaylist(String nome) {
        playlists.add(new Playlist(nome));
    }

    public Playlist getPlaylist(int indice) {
        if (indice >= 0 && indice < playlists.size()) {
            return playlists.get(indice);
        }
        System.out.println("Índice inválido!");
        return null;
    }

    public void listarPlaylists() {
        for (int i = 0; i < playlists.size(); i++) {
            System.out.println((i + 1) + ". " + playlists.get(i).nome);
        }
    }
}