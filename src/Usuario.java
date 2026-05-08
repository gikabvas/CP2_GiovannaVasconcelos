import java.util.ArrayList;

class Usuario {

    protected String nome;
    protected String email;
    protected ArrayList<Playlist> playlists;
    protected ArrayList<Musica> historicoReproducao;

    // Construtor parametrizado
    public Usuario(String nome, String email) {
        setNome(nome);
        setEmail(email);
        this.playlists = new ArrayList<>();
        this.historicoReproducao = new ArrayList<>();
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    // Retorna cópia defensiva das playlists (acessível via polimorfismo + instanceof)
    public ArrayList<Playlist> getPlaylists() {
        return new ArrayList<>(playlists);
    }

    // Retorna cópia defensiva do histórico
    public ArrayList<Musica> getHistoricoReproducao() {
        return new ArrayList<>(historicoReproducao);
    }

    // Setters com validação — marcados como final para não serem sobrescritos
    public final void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do usuário não pode ser nulo ou vazio.");
        }
        this.nome = nome.trim();
    }

    public final void setEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email não pode ser nulo ou vazio.");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Email inválido: deve conter '@'.");
        }
        this.email = email.trim();
    }

    // Reproduz uma música e adiciona ao histórico (pode ser sobrescrito nas subclasses)
    public void reproduzirMusica(Musica musica) {
        System.out.println("🎵 Reproduzindo: " + musica.getTitulo());
        historicoReproducao.add(musica);
    }

    // Exibe todo o histórico de reprodução — final, comportamento igual para todos
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

    // Cria e adiciona uma playlist (pode ser sobrescrito nas subclasses)
    public void criarPlaylist(String nome) {
        Playlist p = new Playlist(nome);
        playlists.add(p);
        System.out.println("✅ Playlist \"" + nome + "\" criada!");
    }

    public final void adicionarPlaylist(Playlist playlist) {
        if (playlist == null) {
            throw new IllegalArgumentException("Não é possível adicionar uma playlist nula.");
        }
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
            String tipo = (p instanceof PlaylistAutomatica) ? " [Automática]" : "";
            System.out.println((i + 1) + ". " + p.getNome() + tipo
                    + " (" + p.getQuantidadeMusicas() + " músicas)");
        }
    }

    public final int getTotalPlaylists() {
        return playlists.size();
    }

    // Retorna o total de reproduções do usuário
    public final int getTotalReproducoes() {
        return historicoReproducao.size();
    }
}