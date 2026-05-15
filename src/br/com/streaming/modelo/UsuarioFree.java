package br.com.streaming.modelo;

// usuario do plano gratuito
// tem limite de 3 playlists e leva anuncio a cada 3 musicas reproduzidas
public class UsuarioFree extends Usuario {

    // limite fixo de playlists pra conta free
    private static final int MAX_PLAYLISTS = 3;

    private int contadorReproducoes;

    public UsuarioFree(String nome, String email) {
        super(nome, email);
        this.contadorReproducoes = 0;
    }

    // sobrescrevo aqui pra inserir o anuncio a cada 3 reproducoes
    // depois chamo o super pra nao perder a logica de historico
    @Override
    public void reproduzirMusica(Musica musica) {
        contadorReproducoes++;
        if (contadorReproducoes % 3 == 0) {
            exibirAnuncio();
        }
        super.reproduzirMusica(musica);
    }

    // sobrescrevo pra checar o limite antes de criar
    @Override
    public void criarPlaylist(String nome) {
        if (playlists.size() >= MAX_PLAYLISTS) {
            System.out.println("❌ Limite de " + MAX_PLAYLISTS + " playlists atingido!");
            System.out.println("💎 Faça upgrade para Premium e tenha playlists ilimitadas!");
            return;
        }
        super.criarPlaylist(nome);
    }

    private void exibirAnuncio() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("📢 ANÚNCIO: Assine Premium e ouça sem interrupções!");
        System.out.println("=".repeat(50) + "\n");
    }

    public int getContadorReproducoes()  { return contadorReproducoes; }
    public int getPlaylistsDisponiveis() { return MAX_PLAYLISTS - playlists.size(); }

    // quantidade de anuncios que o usuario ja viu
    public int getAnunciosExibidos()     { return contadorReproducoes / 3; }
}