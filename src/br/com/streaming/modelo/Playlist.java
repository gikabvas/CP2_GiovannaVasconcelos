package br.com.streaming.modelo;

import br.com.streaming.util.FormatadorTempo;
import br.com.streaming.util.Validador;

import java.util.ArrayList;

// representa uma playlist de musicas
// herda de ItemReproducao assim como Musica — os dois sao "reproduziveis"
public class Playlist extends ItemReproducao {

    protected String            nome;
    protected String            descricao;
    protected ArrayList<Musica> musicas;

    public Playlist() {
        this("Sem nome", "Sem descrição");
    }

    public Playlist(String nome) {
        this(nome, "Sem descrição");
    }

    public Playlist(String nome, String descricao) {
        super();
        setNome(nome);
        setDescricao(descricao);
        this.musicas = new ArrayList<>();
    }

    public String getNome()      { return nome; }
    public String getDescricao() { return descricao; }

    // retorna copia da lista pra nao deixar alguem mexer direto no ArrayList interno
    public ArrayList<Musica> getMusicas() {
        return new ArrayList<>(musicas);
    }

    public void setNome(String nome) {
        Validador.exigirTexto(nome, "Nome da playlist");
        this.nome = nome.trim();
    }

    public void setDescricao(String descricao) {
        Validador.exigirNaoNulo(descricao, "Descrição");
        this.descricao = descricao.trim();
    }

    // quando reproduz a playlist, mostra todas as musicas em sequencia
    @Override
    public void reproduzir() {
        emReproducao = true;
        pausado      = false;
        System.out.println("🎵 Reproduzindo playlist: " + nome);
        if (musicas.isEmpty()) {
            System.out.println("  (playlist vazia)");
            return;
        }
        for (Musica m : musicas) {
            System.out.println("  ▶ " + m.getTitulo() + " — " + m.getArtista());
        }
    }

    @Override
    public int getDuracaoTotal() {
        int total = 0;
        for (Musica m : musicas) {
            total += m.getDuracaoSegundos();
        }
        return total;
    }

    // final nesses metodos porque a logica de adicionar/remover nao pode mudar nas subclasses
    public final void adicionarMusica(Musica musica) {
        Validador.exigirNaoNulo(musica, "Música");
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
            System.out.println("  Playlist vazia.");
            return;
        }
        for (int i = 0; i < musicas.size(); i++) {
            System.out.print("  " + (i + 1) + ". ");
            musicas.get(i).exibir();
        }
    }

    public final int getQuantidadeMusicas() { return musicas.size(); }

    public String getDuracaoFormatada() {
        return FormatadorTempo.formatarLongo(getDuracaoTotal());
    }
}