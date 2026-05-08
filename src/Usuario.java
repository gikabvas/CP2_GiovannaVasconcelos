import java.util.ArrayList;

class Usuario {

    private String nome;
    private ArrayList<Playlist> playlists;

    // Construtor padrão — chama o parametrizado com valor default
    public Usuario() {
        this("Usuário");
    }

    // Construtor parametrizado — valida o nome e inicializa a lista
    public Usuario(String nome) {
        setNome(nome);
        this.playlists = new ArrayList<>();
    }

    // Getter
    public String getNome() {
        return nome;
    }

    // Setter com validação
    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do usuário não pode ser nulo ou vazio.");
        }
        this.nome = nome.trim();
    }

    // Métodos
    public void criarPlaylist(String nome) {
        Playlist p = new Playlist(nome);
        adicionarPlaylist(p);
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