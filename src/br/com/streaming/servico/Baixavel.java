package br.com.streaming.servico;

import br.com.streaming.modelo.Musica;

// só o usuario premium pode baixar musicas, por isso essa interface
// fica separada de Reproduzivel — nem todo usuario vai implementar isso
public interface Baixavel {
    void baixar(Musica musica);
    void removerDownload(Musica musica);
    boolean estaBaixada(Musica musica);
    int getTamanhoBaixados();
}