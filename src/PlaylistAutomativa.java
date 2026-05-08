import java.util.ArrayList;

class PlaylistAutomatica extends Playlist {

    private String criterio; // "top", "recomendadas", "recentes"

    // Construtor parametrizado
    public PlaylistAutomatica(String nome, String criterio) {
        super(nome, "Playlist gerada automaticamente pelo sistema");
        setCriterio(criterio);
    }

    // Getter e Setter
    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        if (criterio == null || criterio.isBlank()) {
            throw new IllegalArgumentException("Critério não pode ser nulo ou vazio.");
        }
        String c = criterio.trim().toLowerCase();
        if (!c.equals("top") && !c.equals("recomendadas") && !c.equals("recentes")) {
            throw new IllegalArgumentException("Critério inválido. Use: top, recomendadas ou recentes.");
        }
        this.criterio = c;
    }

    // Sobrescrita: reproduz com informações extras sobre o critério
    @Override
    public void reproduzir() {
        System.out.println("🤖 Playlist Automática: " + nome);
        System.out.println("📊 Critério: " + getCriterioLabel());
        super.reproduzir();
    }

    // Atualiza as músicas da playlist com base no critério e na lista geral
    public void atualizar(ArrayList<Musica> todasMusicas) {
        musicas.clear();

        if (todasMusicas == null || todasMusicas.isEmpty()) {
            System.out.println("⚠️  Nenhuma música disponível para gerar a playlist.");
            return;
        }

        switch (criterio) {
            case "top":
                // Simula as "mais tocadas": pega até 5 músicas (em um sistema real,
                // seria ordenado por contagem de reproduções)
                int limiteTop = Math.min(5, todasMusicas.size());
                for (int i = 0; i < limiteTop; i++) {
                    musicas.add(todasMusicas.get(i));
                }
                break;

            case "recomendadas":
                // Simula recomendações: pega músicas em ordem inversa (variedade)
                for (int i = todasMusicas.size() - 1; i >= 0; i--) {
                    musicas.add(todasMusicas.get(i));
                    if (musicas.size() >= 5) break;
                }
                break;

            case "recentes":
                // Simula recentes: pega as últimas músicas adicionadas ao catálogo
                int limiteRecentes = Math.min(5, todasMusicas.size());
                for (int i = todasMusicas.size() - 1; i >= todasMusicas.size() - limiteRecentes; i--) {
                    musicas.add(todasMusicas.get(i));
                }
                break;
        }
    }

    // Retorna o rótulo amigável do critério
    private String getCriterioLabel() {
        switch (criterio) {
            case "top":         return "Top Mais Tocadas";
            case "recomendadas": return "Recomendadas para Você";
            case "recentes":    return "Adicionadas Recentemente";
            default:            return criterio;
        }
    }
}