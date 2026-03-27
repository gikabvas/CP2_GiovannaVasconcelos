public class Musica {

    String titulo;
    String artista;
    int duracaoSegundos;
    String genero;

    public Musica(String titulo, String artista, int duracaoSegundos, String genero) {
        this.titulo = titulo;
        this.artista = artista;
        this.duracaoSegundos = duracaoSegundos;
        this.genero = genero;
    }

    public void exibir() {
        System.out.printf("Título: %s | Artista: %s | Duração: %s | Gênero: %s%n",
                titulo, artista, getDuracaoFormatada(), genero);
    }

    public String getDuracaoFormatada() {
        int minutos = duracaoSegundos / 60;
        int segs = duracaoSegundos % 60;
        return String.format("%d:%02d", minutos, segs);
    }

    public boolean contemTitulo(String busca) {
        return titulo.toLowerCase().contains(busca.toLowerCase());
    }

    public boolean contemArtista(String busca) {
        return artista.toLowerCase().contains(busca.toLowerCase());
    }
}