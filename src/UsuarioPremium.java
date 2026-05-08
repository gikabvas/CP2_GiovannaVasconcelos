import java.util.ArrayList;

class UsuarioPremium extends Usuario {

    private String plano; // "Mensal", "Anual" ou "Familiar"
    private ArrayList<Musica> musicasBaixadas;

    public UsuarioPremium(String nome, String email, String plano) {
        super(nome, email); // chama o construtor da superclasse
        setPlano(plano);
        this.musicasBaixadas = new ArrayList<>();
    }

    // Getters e Setters
    public String getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        if (plano == null || plano.isBlank()) {
            throw new IllegalArgumentException("Plano não pode ser nulo ou vazio.");
        }
        this.plano = plano.trim();
    }

    // Sobrescrita: reproduz em alta qualidade (sem anúncios, sem limite)
    @Override
    public void reproduzirMusica(Musica musica) {
        System.out.println("🎵 Reproduzindo em ALTA QUALIDADE: " + musica.getTitulo());
        historicoReproducao.add(musica); // acessa o atributo protected da superclasse
    }

    // Baixa uma música se ainda não foi baixada
    public void baixarMusica(Musica musica) {
        if (!musicasBaixadas.contains(musica)) {
            musicasBaixadas.add(musica);
            System.out.println("⬇️  Música baixada: " + musica.getTitulo());
        } else {
            System.out.println("ℹ️  Música \"" + musica.getTitulo() + "\" já está baixada!");
        }
    }

    // Lista todas as músicas baixadas
    public void listarMusicasBaixadas() {
        System.out.println("\n--- MÚSICAS BAIXADAS ---");
        if (musicasBaixadas.isEmpty()) {
            System.out.println("Nenhuma música baixada.");
            return;
        }
        for (int i = 0; i < musicasBaixadas.size(); i++) {
            System.out.print((i + 1) + ". ");
            musicasBaixadas.get(i).exibir();
        }
    }

    public int getTotalBaixadas() {
        return musicasBaixadas.size();
    }
}