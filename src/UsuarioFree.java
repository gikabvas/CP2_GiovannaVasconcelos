class UsuarioFree extends Usuario {

    private static final int MAX_PLAYLISTS = 3;
    private int contadorReproducoes;

    public UsuarioFree(String nome, String email) {
        super(nome, email); // chama o construtor da superclasse
        this.contadorReproducoes = 0;
    }

    // Sobrescrita: incrementa contador e exibe anúncio a cada 3 músicas
    @Override
    public void reproduzirMusica(Musica musica) {
        contadorReproducoes++;

        if (contadorReproducoes % 3 == 0) {
            exibirAnuncio();
        }

        super.reproduzirMusica(musica); // chama o método da superclasse
    }

    // Sobrescrita: cria playlist respeitando o limite de 3
    @Override
    public void criarPlaylist(String nome) {
        if (playlists.size() >= MAX_PLAYLISTS) {
            System.out.println("❌ Limite de " + MAX_PLAYLISTS + " playlists atingido!");
            System.out.println("💎 Faça upgrade para Premium e tenha playlists ilimitadas!");
            return;
        }
        super.criarPlaylist(nome);
    }

    // Exibe anúncio entre músicas
    private void exibirAnuncio() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("📢 ANÚNCIO: Assine Premium e ouça sem interrupções!");
        System.out.println("=".repeat(50) + "\n");
    }

    public int getContadorReproducoes() {
        return contadorReproducoes;
    }

    // Retorna quantas playlists ainda podem ser criadas
    public int getPlaylitsDisponiveis() {
        return MAX_PLAYLISTS - playlists.size();
    }
}