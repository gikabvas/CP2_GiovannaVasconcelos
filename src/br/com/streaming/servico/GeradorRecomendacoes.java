package br.com.streaming.servico;

import br.com.streaming.modelo.Musica;
import br.com.streaming.modelo.Playlist;

import java.util.ArrayList;

// gera playlists automaticas com base em um criterio escolhido pelo usuario
// separei numa classe propria pra nao deixar o StreamingMusica gigante
// criterios aceitos: "top", "recomendadas", "recentes"
public class GeradorRecomendacoes {

    // nao faz sentido instanciar essa classe, todos os metodos sao estaticos
    private GeradorRecomendacoes() {}

    public static Playlist gerar(String criterio, ArrayList<Musica> catalogo) {
        if (criterio == null || criterio.isBlank()) {
            throw new IllegalArgumentException("Criterio nao pode ser vazio.");
        }
        if (catalogo == null || catalogo.isEmpty()) {
            throw new IllegalArgumentException("Catalogo nao pode ser vazio.");
        }

        String nome;
        switch (criterio.toLowerCase()) {
            case "top":          nome = "Top Mais Tocadas";         break;
            case "recomendadas": nome = "Recomendadas para Voce";   break;
            case "recentes":     nome = "Adicionadas Recentemente"; break;
            default:
                throw new IllegalArgumentException("Criterio invalido. Use: top, recomendadas ou recentes.");
        }

        Playlist playlist = new Playlist(nome, "Gerada automaticamente - criterio: " + criterio);

        ArrayList<Musica> selecionadas = selecionar(criterio.toLowerCase(), catalogo);
        for (Musica m : selecionadas) {
            playlist.adicionarMusica(m);
        }

        return playlist;
    }

    private static ArrayList<Musica> selecionar(String criterio, ArrayList<Musica> catalogo) {
        ArrayList<Musica> resultado = new ArrayList<>();
        int limite = Math.min(5, catalogo.size());

        if (criterio.equals("top")) {
            for (int i = 0; i < limite; i++) {
                resultado.add(catalogo.get(i));
            }
        } else if (criterio.equals("recomendadas")) {
            for (int i = 0; i < limite; i++) {
                if (i % 2 == 0) {
                    resultado.add(catalogo.get(i));
                } else {
                    resultado.add(catalogo.get(catalogo.size() - 1 - i));
                }
            }
        } else if (criterio.equals("recentes")) {
            for (int i = catalogo.size() - 1; i >= catalogo.size() - limite; i--) {
                resultado.add(catalogo.get(i));
            }
        }

        return resultado;
    }
}