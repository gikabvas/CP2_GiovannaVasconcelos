package br.com.streaming.modelo;

import br.com.streaming.servico.Reproduzivel;

// classe abstrata que serve de base pra Musica e Playlist
// coloquei aqui o estado de reproducao (emReproducao, pausado) porque
// tanto musica quanto playlist precisam disso, entao faz sentido compartilhar
public abstract class ItemReproducao implements Reproduzivel {

    protected boolean emReproducao;
    protected boolean pausado;

    public ItemReproducao() {
        this.emReproducao = false;
        this.pausado      = false;
    }

    // pausar e parar sao iguais pra musica e playlist, entao implementei aqui
    // se alguma subclasse precisar de comportamento diferente, pode sobrescrever

    @Override
    public void pausar() {
        if (emReproducao) {
            pausado      = true;
            emReproducao = false;
            System.out.println("⏸  Pausado.");
        } else {
            System.out.println("ℹ️  Nao esta em reproducao.");
        }
    }

    @Override
    public void parar() {
        emReproducao = false;
        pausado      = false;
        System.out.println("⏹  Parado.");
    }

    // cada subclasse define como reproduz e qual a duracao — obrigatorio implementar
    @Override
    public abstract void reproduzir();

    @Override
    public abstract int getDuracaoTotal();

    public boolean isEmReproducao() { return emReproducao; }
    public boolean isPausado()      { return pausado; }
}