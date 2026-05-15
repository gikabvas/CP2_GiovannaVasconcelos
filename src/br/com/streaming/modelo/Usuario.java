package br.com.streaming.modelo;

import br.com.streaming.util.Validador;

import java.util.ArrayList;

// classe base dos usuarios — UsuarioFree e UsuarioPremium herdam daqui
// coloquei aqui tudo que é igual pros dois: historico, playlists, nome, email
public class Usuario {

    protected String              nome;
    protected String              email;
    protected ArrayList<Playlist> playlists;
    protected ArrayList<Musica>   historicoReproducao;

    public Usuario(String nome, String email) {
        setNome(nome);
        setEmail(email);
        this.playlists           = new ArrayList<>();
        this.historicoReproducao = new ArrayList<>();
    }

    public String getNome()  { return nome; }
    public String getEmail() { return email; }

    public ArrayList<Playlist> getPlaylists() {
        return new ArrayList<>(playlists);
    }

    public ArrayList<Musica> getHistoricoReproducao() {
        return new ArrayList<>(historicoReproducao);
    }

    // final nos setters de nome e email — essa validacao nao pode ser alterada nas subclasses
    public final void setNome(String nome) {
        Validador.exigirTexto(nome, "Nome do usuário");
        this.nome = nome.trim();
    }

    public final void setEmail(String email) {
        if (!Validador.emailValido(email)) {
            throw new IllegalArgumentException("Email inválido: precisa conter '@'.");
        }
        this.email = email.trim();
    }

    // esse metodo é sobrescrito em UsuarioFree (anuncio) e UsuarioPremium (alta qualidade)
    public void reproduzirMusica(Musica musica) {
        Validador.exigirNaoNulo(musica, "Música");
        musica.reproduzir();
        historicoReproducao.add(musica);
    }

    // historico é igual pra todos os tipos de usuario, nao precisa sobrescrever
    public final void exibirHistorico() {
        System.out.println("\n--- HISTÓRICO DE REPRODUÇÃO ---");
        if (historicoReproducao.isEmpty()) {
            System.out.println("Nenhuma música reproduzida ainda.");
            return;
        }
        for (int i = 0; i < historicoReproducao.size(); i++) {
            System.out.print((i + 1) + ". ");
            historicoReproducao.get(i).exibir();
        }
    }

    // UsuarioFree sobrescreve esse metodo pra checar o limite de 3 playlists
    public void criarPlaylist(String nome) {
        Playlist p = new Playlist(nome);
        playlists.add(p);
        System.out.println("✅ Playlist \"" + nome + "\" criada!");
    }

    public final void adicionarPlaylist(Playlist playlist) {
        Validador.exigirNaoNulo(playlist, "Playlist");
        playlists.add(playlist);
    }

    public final Playlist getPlaylist(int indice) {
        if (indice < 0 || indice >= playlists.size()) {
            System.out.println("Índice de playlist inválido.");
            return null;
        }
        return playlists.get(indice);
    }

    public final void listarPlaylists() {
        if (playlists.isEmpty()) {
            System.out.println("Nenhuma playlist criada.");
            return;
        }
        for (int i = 0; i < playlists.size(); i++) {
            Playlist p = playlists.get(i);
            System.out.printf("  %d. %-25s (%d músicas | %s)%n",
                    (i + 1), p.getNome(), p.getQuantidadeMusicas(), p.getDuracaoFormatada());
        }
    }

    public final int getTotalPlaylists()   { return playlists.size(); }
    public final int getTotalReproducoes() { return historicoReproducao.size(); }
}