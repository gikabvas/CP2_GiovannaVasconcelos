import java.util.ArrayList;
import java.util.Scanner;

public class StreamingMusica {

    static ArrayList<Musica> musicas = new ArrayList<>();
    static Usuario usuario; // será UsuarioFree ou UsuarioPremium

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        adicionarMusicasTeste();
        criarUsuario(); // pergunta nome, email e tipo de conta

        int opcao;
        do {
            exibirMenu();
            opcao = lerOpcao();
            processarOpcao(opcao);
        } while (opcao != 0);

        System.out.println("\nAté logo, " + usuario.getNome() + "!");
        scanner.close();
    }

    // =========================================================
    // CRIAÇÃO DO USUÁRIO
    // =========================================================

    public static void criarUsuario() {
        System.out.println("=== BEM-VINDO AO STREAMING ===");

        System.out.print("Digite seu nome: ");
        String nome = scanner.nextLine();

        System.out.print("Digite seu email: ");
        String email = scanner.nextLine();

        System.out.println("\nEscolha o tipo de conta:");
        System.out.println("1. Free (Gratuito)");
        System.out.println("2. Premium (Pago)");
        System.out.print("Escolha: ");
        int tipo = lerOpcao();

        if (tipo == 2) {
            System.out.println("\nEscolha o plano Premium:");
            System.out.println("1. Mensal  (R$ 19,90)");
            System.out.println("2. Anual   (R$ 199,00)");
            System.out.println("3. Familiar (R$ 29,90)");
            System.out.print("Escolha: ");
            int planoOpcao = lerOpcao();

            String plano;
            switch (planoOpcao) {
                case 2: plano = "Anual"; break;
                case 3: plano = "Familiar"; break;
                default: plano = "Mensal"; break;
            }

            usuario = new UsuarioPremium(nome, email, plano);
            System.out.println("\n✅ Conta Premium (" + plano + ") criada com sucesso!");

        } else {
            usuario = new UsuarioFree(nome, email);
            System.out.println("\n✅ Conta Free criada com sucesso!");
        }
    }

    // =========================================================
    // MENU
    // =========================================================

    public static void exibirMenu() {
        if (usuario instanceof UsuarioPremium) {
            exibirMenuPremium();
        } else {
            exibirMenuFree();
        }
    }

    public static void exibirMenuFree() {
        System.out.println("\n=== MENU (🆓 FREE) — " + usuario.getNome() + " ===");
        System.out.println("1. Reproduzir música");
        System.out.println("2. Ver histórico");
        System.out.println("3. Criar playlist (máx. 3)");
        System.out.println("4. Gerenciar playlists");
        System.out.println("5. Listar músicas");
        System.out.println("6. Cadastrar música");
        System.out.println("7. Buscar música");
        System.out.println("8. 💎 Estatísticas");
        System.out.println("0. Sair");
        System.out.print("Opção: ");
    }

    public static void exibirMenuPremium() {
        System.out.println("\n=== MENU (💎 PREMIUM) — " + usuario.getNome() + " ===");
        System.out.println("1. Reproduzir música (Alta Qualidade)");
        System.out.println("2. Ver histórico");
        System.out.println("3. Criar playlist (ilimitado)");
        System.out.println("4. Gerenciar playlists");
        System.out.println("5. Listar músicas");
        System.out.println("6. Cadastrar música");
        System.out.println("7. Buscar música");
        System.out.println("8. Baixar música");
        System.out.println("9. Ver músicas baixadas");
        System.out.println("0. Sair");
        System.out.print("Opção: ");
    }

    public static int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    public static void processarOpcao(int opcao) {
        switch (opcao) {
            case 1: reproduzirMusica(); break;
            case 2: usuario.exibirHistorico(); break;
            case 3: criarPlaylist(); break;
            case 4: gerenciarPlaylists(); break;
            case 5: listarMusicas(); break;
            case 6: cadastrarMusica(); break;
            case 7: buscarMusica(); break;
            case 8: processarOpcao8(); break;
            case 9: processarOpcao9(); break;
            case 0: break;
            default: System.out.println("Opção inválida.");
        }
    }

    // Opção 8: estatísticas (Free) ou baixar música (Premium)
    public static void processarOpcao8() {
        if (usuario instanceof UsuarioPremium) {
            baixarMusica((UsuarioPremium) usuario);
        } else {
            exibirEstatisticas();
        }
    }

    // Opção 9: apenas Premium
    public static void processarOpcao9() {
        if (usuario instanceof UsuarioPremium) {
            ((UsuarioPremium) usuario).listarMusicasBaixadas();
        } else {
            System.out.println("Opção inválida.");
        }
    }

    // =========================================================
    // REPRODUÇÃO
    // =========================================================

    public static void reproduzirMusica() {
        if (musicas.isEmpty()) {
            System.out.println("Nenhuma música cadastrada.");
            return;
        }
        listarMusicas();
        System.out.print("Escolha a música: ");
        int indice = lerOpcao() - 1;
        if (indice < 0 || indice >= musicas.size()) {
            System.out.println("Índice inválido.");
            return;
        }
        usuario.reproduzirMusica(musicas.get(indice));
    }

    // =========================================================
    // MÚSICAS
    // =========================================================

    public static void cadastrarMusica() {
        try {
            System.out.print("Título: ");
            String titulo = scanner.nextLine();

            System.out.print("Artista: ");
            String artista = scanner.nextLine();

            System.out.print("Duração (em segundos, entre 1 e 3599): ");
            int duracao = Integer.parseInt(scanner.nextLine());

            String[] generos = Musica.getGenerosValidos();
            System.out.println("Escolha o gênero:");
            for (int i = 0; i < generos.length; i++) {
                System.out.println((i + 1) + ". " + generos[i]);
            }
            int g = Integer.parseInt(scanner.nextLine());

            if (g < 1 || g > generos.length) {
                System.out.println("Gênero inválido.");
                return;
            }

            Musica m = new Musica(titulo, artista, duracao, generos[g - 1]);
            musicas.add(m);
            System.out.println("✅ Música cadastrada!");

        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar música: " + e.getMessage());
        }
    }

    public static void listarMusicas() {
        if (musicas.isEmpty()) {
            System.out.println("Nenhuma música cadastrada.");
            return;
        }
        for (int i = 0; i < musicas.size(); i++) {
            System.out.print((i + 1) + ". ");
            musicas.get(i).exibir();
        }
    }

    public static void buscarMusica() {
        System.out.print("Buscar: ");
        String busca = scanner.nextLine();
        boolean encontrou = false;
        for (Musica m : musicas) {
            if (m.contemTitulo(busca) || m.contemArtista(busca)) {
                m.exibir();
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhuma música encontrada.");
        }
    }

    // =========================================================
    // PLAYLISTS
    // =========================================================

    public static void criarPlaylist() {
        try {
            System.out.print("Nome da playlist: ");
            String nome = scanner.nextLine();
            usuario.criarPlaylist(nome);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public static void gerenciarPlaylists() {
        int op;
        do {
            System.out.println("\n--- GERENCIAR PLAYLISTS ---");
            System.out.println("1. Listar playlists");
            System.out.println("2. Adicionar música a playlist");
            System.out.println("3. Remover música de playlist");
            System.out.println("4. Ver detalhes de playlist");
            System.out.println("0. Voltar");
            op = lerOpcao();
            switch (op) {
                case 1: usuario.listarPlaylists(); break;
                case 2: adicionarMusicaPlaylist(); break;
                case 3: removerMusicaPlaylist(); break;
                case 4: detalhesPlaylist(); break;
            }
        } while (op != 0);
    }

    public static void adicionarMusicaPlaylist() {
        usuario.listarPlaylists();
        System.out.print("Escolha a playlist: ");
        Playlist pl = usuario.getPlaylist(lerOpcao() - 1);
        if (pl == null) return;

        listarMusicas();
        System.out.print("Escolha a música: ");
        int m = lerOpcao() - 1;
        if (m >= 0 && m < musicas.size()) {
            pl.adicionarMusica(musicas.get(m));
            System.out.println("✅ Música adicionada!");
        } else {
            System.out.println("Índice inválido.");
        }
    }

    public static void removerMusicaPlaylist() {
        usuario.listarPlaylists();
        System.out.print("Escolha a playlist: ");
        Playlist pl = usuario.getPlaylist(lerOpcao() - 1);
        if (pl == null) return;

        pl.listarMusicas();
        System.out.print("Número da música a remover: ");
        pl.removerMusica(lerOpcao() - 1);
    }

    public static void detalhesPlaylist() {
        usuario.listarPlaylists();
        System.out.print("Escolha a playlist: ");
        Playlist pl = usuario.getPlaylist(lerOpcao() - 1);
        if (pl == null) return;

        System.out.println("\n--- " + pl.getNome() + " ---");
        pl.listarMusicas();
        System.out.println("Total de músicas: " + pl.getQuantidadeMusicas());
        System.out.println("Duração total:    " + pl.getDuracaoTotal() + "s");
    }

    // =========================================================
    // DOWNLOAD (apenas Premium)
    // =========================================================

    public static void baixarMusica(UsuarioPremium premium) {
        if (musicas.isEmpty()) {
            System.out.println("Nenhuma música cadastrada.");
            return;
        }
        listarMusicas();
        System.out.print("Escolha a música para baixar: ");
        int indice = lerOpcao() - 1;
        if (indice < 0 || indice >= musicas.size()) {
            System.out.println("Índice inválido.");
            return;
        }
        premium.baixarMusica(musicas.get(indice));
    }

    // =========================================================
    // ESTATÍSTICAS
    // =========================================================

    public static void exibirEstatisticas() {
        System.out.println("\n--- ESTATÍSTICAS ---");
        System.out.println("Usuário:        " + usuario.getNome());
        System.out.println("Email:          " + usuario.getEmail());
        System.out.println("Total músicas:  " + musicas.size());
        System.out.println("Total playlists:" + usuario.getTotalPlaylists());
    }

    // =========================================================
    // DADOS DE TESTE
    // =========================================================

    public static void adicionarMusicasTeste() {
        musicas.add(new Musica("Bohemian Rhapsody", "Queen", 354, "Rock"));
        musicas.add(new Musica("Billie Jean", "Michael Jackson", 293, "Pop"));
        musicas.add(new Musica("So What", "Miles Davis", 560, "Jazz"));
        musicas.add(new Musica("Lose Yourself", "Eminem", 326, "Hip-Hop"));
    }
}