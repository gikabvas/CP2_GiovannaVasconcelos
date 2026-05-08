import java.util.ArrayList;

class Playlist {

    protected String nome;
    protected ArrayList<Musica> musicas;
    protected String descricao;

    // Construtor padrão — chama o parametrizado com valor default
    public Playlist() {
        this("Sem nome", "Sem descrição");
    }

    // Construtor com nome — chama o parametrizado completo
    public Playlist(String nome) {
        this(nome, "Sem descrição");
    }

    // Construtor parametrizado completo
    public Playlist(String nome, String descricao) {
        setNome(nome);
        setDescricao(descricao);
        this.musicas = new ArrayList<>();
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public ArrayList<Musica> getMusicas() {
        return new ArrayList<>(musicas); // cópia defensiva para proteger a lista interna
    }

    // Setters com validação
    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome da playlist não pode ser nulo ou vazio.");
        }
        this.nome = nome.trim();
    }

    public void setDescricao(String descricao) {
        if (descricao == null) {
            throw new IllegalArgumentException("Descrição não pode ser nula.");
        }
        this.descricao = descricao.trim();
    }

    // Reproduz todas as músicas da playlist (pode ser sobrescrito nas subclasses)
    public void reproduzir() {
        System.out.println("🎵 Reproduzindo playlist: " + nome);
        if (musicas.isEmpty()) {
            System.out.println("  (playlist vazia)");
            return;
        }
        for (Musica m : musicas) {
            System.out.println("  ▶ " + m.getTitulo() + " — " + m.getArtista());
        }
    }

    // Métodos finais — não podem ser sobrescritos, lógica crítica de gerenciamento
    public final void adicionarMusica(Musica musica) {
        if (musica == null) {
            throw new IllegalArgumentException("Não é possível adicionar uma música nula.");
        }
        musicas.add(musica);
    }

    public final void removerMusica(int indice) {
        if (indice < 0 || indice >= musicas.size()) {
            System.out.println("Índice inválido. Deve ser entre 0 e " + (musicas.size() - 1) + ".");
            return;
        }
        musicas.remove(indice);
    }

    public final void listarMusicas() {
        if (musicas.isEmpty()) {
            System.out.println("Playlist vazia.");
            return;
        }
        for (int i = 0; i < musicas.size(); i++) {
            System.out.print((i + 1) + ". ");
            musicas.get(i).exibir();
        }
    }

    public final int getDuracaoTotal() {
        int total = 0;
        for (Musica m : musicas) {
            total += m.getDuracaoSegundos();
        }
        return total;
    }

    public final int getQuantidadeMusicas() {
        return musicas.size();
    }
}