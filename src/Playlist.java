import java.util.ArrayList;

class Playlist {

    private String nome;
    private ArrayList<Musica> musicas;

    // Construtor padrão — chama o parametrizado com valor default
    public Playlist() {
        this("Sem nome");
    }

    // Construtor parametrizado — valida o nome e inicializa a lista
    public Playlist(String nome) {
        setNome(nome);
        this.musicas = new ArrayList<>();
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public ArrayList<Musica> getMusicas() {
        return new ArrayList<>(musicas); // cópia defensiva para proteger a lista interna
    }

    // Setter com validação
    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome da playlist não pode ser nulo ou vazio.");
        }
        this.nome = nome.trim();
    }

    // Métodos
    public void adicionarMusica(Musica musica) {
        if (musica == null) {
            throw new IllegalArgumentException("Não é possível adicionar uma música nula.");
        }
        musicas.add(musica);
    }

    public void removerMusica(int indice) {
        if (indice < 0 || indice >= musicas.size()) {
            System.out.println("Índice inválido. Deve ser entre 0 e " + (musicas.size() - 1) + ".");
            return;
        }
        musicas.remove(indice);
    }

    public void listarMusicas() {
        if (musicas.isEmpty()) {
            System.out.println("Playlist vazia.");
            return;
        }

        for (int i = 0; i < musicas.size(); i++) {
            System.out.print((i + 1) + ". ");
            musicas.get(i).exibir();
        }
    }

    public int getDuracaoTotal() {
        int total = 0;
        for (Musica m : musicas) {
            total += m.getDuracaoSegundos();
        }
        return total;
    }

    public int getQuantidadeMusicas() {
        return musicas.size();
    }
}