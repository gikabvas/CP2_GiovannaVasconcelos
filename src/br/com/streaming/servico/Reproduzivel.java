package br.com.streaming.servico;

// qualquer coisa que pode ser reproduzida no sistema precisa implementar isso
// por enquanto é Musica e Playlist, mas poderia ser podcast, radio, etc
public interface Reproduzivel {
    void reproduzir();
    void pausar();
    void parar();
    int getDuracaoTotal();
}