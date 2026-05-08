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

    // Setters com validação
    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do usuário não pode ser nulo ou vazio.");
        }
        this.nome = nome.trim();
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email não pode ser nulo ou vazio.");
        }
        this.email = email.trim();
    }

    // Reproduz uma música e adiciona ao histórico
    public void reproduzirMusica(Musica musica) {
        System.out.println("🎵 Reproduzindo: " + musica.getTitulo());
        historicoReproducao.add(musica);
    }

    // Exibe todo o histórico de reprodução
    public void exibirHistorico() {
        System.out.println("\n--- HISTÓRICO DE REPRODUÇÃO ---");
        if (historicoReproducao.isEmpty()) {
            System.out.println("Nenhuma música reproduzida ainda.");
            return;
        }
        for (Musica m : historicoReproducao) {
            m.exibir();
        }
    }

    // Cria e adiciona uma playlist (pode ser sobrescrito nas subclasses)
    public void criarPlaylist(String nome) {
        Playlist p = new Playlist(nome);
        playlists.add(p);
        System.out.println("✅ Playlist \"" + nome + "\" criada!");
    }

    public void adicionarPlaylist(Playlist playlist) {
        if (playlist == null) {
            throw new IllegalArgumentException("Não é possível adicionar uma playlist nula.");
        }
        playlists.add(playlist);
    }

    public Playlist getPlaylist(int indice) {
        if (indice < 0 || indice >= playlists.size()) {
            System.out.println("Índice de playlist inválido.");
            return null;
        }
        return playlists.get(indice);
    }

    public void listarPlaylists() {
        if (playlists.isEmpty()) {
            System.out.println("Nenhuma playlist criada.");
            return;
        }
        for (int i = 0; i < playlists.size(); i++) {
            System.out.println((i + 1) + ". " + playlists.get(i).getNome());
        }
    }

    public int getTotalPlaylists() {
        return playlists.size();
    }
}