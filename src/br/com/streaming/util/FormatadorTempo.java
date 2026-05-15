package br.com.streaming.util;

// converte segundos pra formato legivel
// usei em Musica e Playlist pra nao repetir a mesma conta nos dois lugares
public final class FormatadorTempo {

    private FormatadorTempo() {}

    // uso nas musicas — ex: 293 vira "4:53"
    public static String formatarSegundos(int totalSegundos) {
        if (totalSegundos < 0) {
            throw new IllegalArgumentException("Duracao nao pode ser negativa.");
        }
        int min = totalSegundos / 60;
        int seg = totalSegundos % 60;
        return String.format("%d:%02d", min, seg);
    }

    // uso nas playlists que podem ter duracao maior — ex: 3725 vira "1h 2m 5s"
    public static String formatarLongo(int totalSegundos) {
        if (totalSegundos < 0) {
            throw new IllegalArgumentException("Duracao nao pode ser negativa.");
        }
        int horas = totalSegundos / 3600;
        int min   = (totalSegundos % 3600) / 60;
        int seg   = totalSegundos % 60;

        if (horas > 0) {
            return String.format("%dh %dm %ds", horas, min, seg);
        } else if (min > 0) {
            return String.format("%dm %ds", min, seg);
        } else {
            return String.format("%ds", seg);
        }
    }
}