package br.com.streaming.modelo;

import br.com.streaming.servico.Baixavel;
import br.com.streaming.util.Validador;

import java.util.ArrayList;

// usuario premium — sem limite de playlists, sem anuncios, reproducao em alta qualidade
// implementa Baixavel porque so ele pode fazer downloads
public class UsuarioPremium extends Usuario implements Baixavel {

    private String            plano; // "Mensal", "Anual" ou "Familiar"
    private ArrayList<Musica> musicasBaixadas;

    public UsuarioPremium(String nome, String email, String plano) {
        super(nome, email);
        setPlano(plano);
        this.musicasBaixadas = new ArrayList<>();
    }

    public String getPlano() { return plano; }

    public void setPlano(String plano) {
        Validador.exigirTexto(plano, "Plano");
        this.plano = plano.trim();
    }

    // retorna copia pra nao expor o ArrayList interno
    public ArrayList<Musica> getMusicasBaixadas() {
        return new ArrayList<>(musicasBaixadas);
    }

    // sobrescrevo pra mostrar "alta qualidade" e adicionar direto no historico
    // sem passar pelo super porque nao quero o print padrao da classe base
    @Override
    public void reproduzirMusica(Musica musica) {
        Validador.exigirNaoNulo(musica, "Música");
        System.out.println("🎵 Reproduzindo em ALTA QUALIDADE: "
                + musica.getTitulo() + " — " + musica.getArtista());
        historicoReproducao.add(musica);
    }

    // metodos da interface Baixavel — obrigatorio implementar todos os quatro

    @Override
    public void baixar(Musica musica) {
        Validador.exigirNaoNulo(musica, "Música");
        if (!musicasBaixadas.contains(musica)) {
            musicasBaixadas.add(musica);
            System.out.println("⬇️  Música baixada: " + musica.getTitulo());
        } else {
            System.out.println("ℹ️  \"" + musica.getTitulo() + "\" já está baixada!");
        }
    }

    @Override
    public void removerDownload(Musica musica) {
        if (musicasBaixadas.remove(musica)) {
            System.out.println("🗑️  Download removido: " + musica.getTitulo());
        } else {
            System.out.println("ℹ️  \"" + musica.getTitulo() + "\" não está na lista de downloads.");
        }
    }

    @Override
    public boolean estaBaixada(Musica musica) {
        return musicasBaixadas.contains(musica);
    }

    @Override
    public int getTamanhoBaixados() {
        return musicasBaixadas.size();
    }

    public void listarMusicasBaixadas() {
        System.out.println("\n--- MÚSICAS BAIXADAS ---");
        if (musicasBaixadas.isEmpty()) {
            System.out.println("Nenhuma música baixada.");
            return;
        }
        for (int i = 0; i < musicasBaixadas.size(); i++) {
            System.out.print("  " + (i + 1) + ". ");
            musicasBaixadas.get(i).exibir();
        }
    }
}