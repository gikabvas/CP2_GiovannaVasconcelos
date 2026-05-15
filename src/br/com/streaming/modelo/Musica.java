package br.com.streaming.modelo;

import br.com.streaming.util.FormatadorTempo;
import br.com.streaming.util.Validador;

// representa uma musica do catalogo
// herda de ItemReproducao que ja implementa Reproduzivel
public class Musica extends ItemReproducao {

    // generos aceitos pelo sistema — qualquer outro valor lanca excecao no setter
    private static final String[] GENEROS_VALIDOS =
            {"Pop", "Rock", "Jazz", "Eletrônica", "Hip-Hop", "Clássica"};

    private String titulo;
    private String artista;
    private int    duracaoSegundos;
    private String genero;

    // construtor padrao, usa valores ficticios so pra nao deixar null em nada
    public Musica() {
        this("Sem título", "Desconhecido", 1, "Pop");
    }

    // construtor principal — toda validacao fica nos setters
    public Musica(String titulo, String artista, int duracaoSegundos, String genero) {
        super();
        setTitulo(titulo);
        setArtista(artista);
        setDuracaoSegundos(duracaoSegundos);
        setGenero(genero);
    }

    public String getTitulo()           { return titulo; }
    public String getArtista()          { return artista; }
    public int    getDuracaoSegundos()  { return duracaoSegundos; }
    public String getGenero()           { return genero; }
    public static String[] getGenerosValidos() { return GENEROS_VALIDOS; }

    public void setTitulo(String titulo) {
        Validador.exigirTexto(titulo, "Título");
        this.titulo = titulo.trim();
    }

    public void setArtista(String artista) {
        Validador.exigirTexto(artista, "Artista");
        this.artista = artista.trim();
    }

    public void setDuracaoSegundos(int duracaoSegundos) {
        // limite de 1 hora pra nao aceitar duracao absurda
        Validador.exigirIntervalo(duracaoSegundos, 1, 3600, "Duração");
        this.duracaoSegundos = duracaoSegundos;
    }

    public void setGenero(String genero) {
        Validador.exigirTexto(genero, "Gênero");
        for (String g : GENEROS_VALIDOS) {
            if (g.equalsIgnoreCase(genero.trim())) {
                this.genero = g; // salva sempre no formato padrao (ex: "Rock" e nao "rock")
                return;
            }
        }
        throw new IllegalArgumentException(
                "Gênero inválido. Escolha entre: Pop, Rock, Jazz, Eletrônica, Hip-Hop, Clássica.");
    }

    // implementacao obrigatoria da interface Reproduzivel (via ItemReproducao)
    @Override
    public void reproduzir() {
        emReproducao = true;
        pausado      = false;
        System.out.printf("▶  %s — %s  [%s]%n", titulo, artista, getDuracaoFormatada());
    }

    @Override
    public int getDuracaoTotal() {
        return duracaoSegundos;
    }

    // exibe as infos da musica numa linha so
    public void exibir() {
        System.out.printf("Título: %s | Artista: %s | Duração: %s | Gênero: %s%n",
                titulo, artista, getDuracaoFormatada(), genero);
    }

    public String getDuracaoFormatada() {
        return FormatadorTempo.formatarSegundos(duracaoSegundos);
    }

    // uso na busca — nao precisa ser exato, qualquer trecho do titulo/artista serve
    public boolean contemTitulo(String busca) {
        return titulo.toLowerCase().contains(busca.toLowerCase());
    }

    public boolean contemArtista(String busca) {
        return artista.toLowerCase().contains(busca.toLowerCase());
    }
}