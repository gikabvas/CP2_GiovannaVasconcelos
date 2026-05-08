class Musica {

    private static final String[] GENEROS_VALIDOS = {"Pop", "Rock", "Jazz", "Eletrônica", "Hip-Hop", "Clássica"};

    private String titulo;
    private String artista;
    private int duracaoSegundos;
    private String genero;

    // Construtor padrão — chama o parametrizado com valores default
    public Musica() {
        this("Sem título", "Desconhecido", 1, "Pop");
    }

    // Construtor parametrizado — valida os dados ao criar o objeto
    public Musica(String titulo, String artista, int duracaoSegundos, String genero) {
        setTitulo(titulo);
        setArtista(artista);
        setDuracaoSegundos(duracaoSegundos);
        setGenero(genero);
    }

    // Getters
    public String getTitulo() {
        return titulo;
    }

    public String getArtista() {
        return artista;
    }

    public int getDuracaoSegundos() {
        return duracaoSegundos;
    }

    public String getGenero() {
        return genero;
    }

    public static String[] getGenerosValidos() {
        return GENEROS_VALIDOS;
    }

    // Setters com validação
    public void setTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Título não pode ser nulo ou vazio.");
        }
        this.titulo = titulo.trim();
    }

    public void setArtista(String artista) {
        if (artista == null || artista.isBlank()) {
            throw new IllegalArgumentException("Artista não pode ser nulo ou vazio.");
        }
        this.artista = artista.trim();
    }

    public void setDuracaoSegundos(int duracaoSegundos) {
        if (duracaoSegundos <= 0) {
            throw new IllegalArgumentException("Duração deve ser maior que 0.");
        }
        if (duracaoSegundos >= 3600) {
            throw new IllegalArgumentException("Duração deve ser menor que 3600 segundos (1 hora).");
        }
        this.duracaoSegundos = duracaoSegundos;
    }

    public void setGenero(String genero) {
        if (genero == null || genero.isBlank()) {
            throw new IllegalArgumentException("Gênero não pode ser nulo ou vazio.");
        }
        for (String g : GENEROS_VALIDOS) {
            if (g.equalsIgnoreCase(genero.trim())) {
                this.genero = g; // armazena sempre no formato padrão (ex: "Rock")
                return;
            }
        }
        throw new IllegalArgumentException("Gênero inválido. Escolha entre: Pop, Rock, Jazz, Eletrônica, Hip-Hop, Clássica.");
    }

    // Métodos
    public void exibir() {
        System.out.printf("Título: %s | Artista: %s | Duração: %s | Gênero: %s%n",
                titulo, artista, getDuracaoFormatada(), genero);
    }

    public String getDuracaoFormatada() {
        int min = duracaoSegundos / 60;
        int seg = duracaoSegundos % 60;
        return String.format("%d:%02d", min, seg);
    }

    public boolean contemTitulo(String busca) {
        return titulo.toLowerCase().contains(busca.toLowerCase());
    }

    public boolean contemArtista(String busca) {
        return artista.toLowerCase().contains(busca.toLowerCase());
    }
}