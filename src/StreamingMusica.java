import java.util.ArrayList;
import java.util.Scanner;

public class StreamingMusica {

    // Lista polimórfica: pode conter UsuarioFree e UsuarioPremium
    static ArrayList<Usuario> usuarios = new ArrayList<>();
    static Usuario usuarioLogado = null;

    // Catálogo geral de músicas do sistema
    static ArrayList<Musica> musicas = new ArrayList<>();

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        adicionarMusicasTeste();
        menuInicial();

        scanner.close();
    }

    // =========================================================
    // MENU INICIAL (antes do login)
    // =========================================================

    public static void menuInicial() {
        int opcao;
        do {
            System.out.println("\n=== SISTEMA DE STREAMING ===");
            System.out.println("1. Criar novo usuário");
            System.out.println("2. Login");
            System.out.println("3. Listar usuários");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opcao = lerOpcao();

            switch (opcao) {
                case 1: criarUsuario(); break;
                case 2: fazerLogin(); break;
                case 3: listarUsuarios(); break;
                case 0: System.out.println("\nAté logo! 👋"); break;
                default: System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    // =========================================================
    // CRIAÇÃO DO USUÁRIO (sistema multi-usuário)
    // =========================================================

    public static void criarUsuario() {
        System.out.println("\n=== CRIAR USUÁRIO ===");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        // Verifica se email já está cadastrado — usa polimorfismo na iteração
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                System.out.println("❌ Email já cadastrado.");
                return;
            }
        }

        System.out.println("\nTipo de conta:");
        System.out.println("1. Free (Gratuito)");
        System.out.println("2. Premium (Pago)");
        System.out.print("Escolha: ");
        int tipo = lerOpcao();

        try {
            if (tipo == 2) {
                System.out.println("\nEscolha o plano Premium:");
                System.out.println("1. Mensal  (R$ 19,90)");
                System.out.println("2. Anual   (R$ 199,00)");
                System.out.println("3. Familiar (R$ 29,90)");
                System.out.print("Escolha: ");
                int planoOpcao = lerOpcao();

                String plano;
                switch (planoOpcao) {
                    case 2:  plano = "Anual";     break;
                    case 3:  plano = "Familiar";  break;
                    default: plano = "Mensal";    break;
                }

                // Upcasting implícito: UsuarioPremium → Usuario (ArrayList polimórfico)
                usuarios.add(new UsuarioPremium(nome, email, plano));
                System.out.println("✅ Conta Premium (" + plano + ") criada com sucesso!");

            } else {
                // Upcasting implícito: UsuarioFree → Usuario
                usuarios.add(new UsuarioFree(nome, email));
                System.out.println("✅ Conta Free criada com sucesso!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao criar usuário: " + e.getMessage());
        }
    }

    // =========================================================
    // LOGIN
    // =========================================================

    public static void fazerLogin() {
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado. Crie um primeiro!");
            return;
        }

        listarUsuarios();
        System.out.print("Escolha o usuário: ");
        int indice = lerOpcao() - 1;

        if (indice < 0 || indice >= usuarios.size()) {
            System.out.println("Índice inválido.");
            return;
        }

        // Polimorfismo: a referência é do tipo base Usuario,
        // mas o objeto real pode ser UsuarioFree ou UsuarioPremium
        usuarioLogado = usuarios.get(indice);

        String tipo = (usuarioLogado instanceof UsuarioPremium) ? "Premium 💎" : "Free 🆓";
        System.out.println("✅ Login realizado: " + usuarioLogado.getNome() + " (" + tipo + ")");

        menuPrincipal();
        usuarioLogado = null; // desloga ao retornar
    }

    // =========================================================
    // LISTAR USUÁRIOS (com instanceof e casting)
    // =========================================================

    public static void listarUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }
        System.out.println("\nUsuários cadastrados:");
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario u = usuarios.get(i);

            // instanceof para determinar o tipo real e exibir detalhes específicos
            if (u instanceof UsuarioPremium) {
                UsuarioPremium premium = (UsuarioPremium) u; // downcasting
                System.out.println((i + 1) + ". " + u.getNome()
                        + " (Premium — Plano: " + premium.getPlano() + ")");
            } else if (u instanceof UsuarioFree) {
                UsuarioFree free = (UsuarioFree) u; // downcasting
                System.out.println((i + 1) + ". " + u.getNome()
                        + " (Free — Playlists: " + free.getTotalPlaylists() + "/3)");
            }
        }
    }

    // =========================================================
    // MENU PRINCIPAL (após login)
    // =========================================================

    public static void menuPrincipal() {
        int opcao;
        do {
            exibirMenu();
            opcao = lerOpcao();
            processarOpcao(opcao);
        } while (opcao != 0);

        System.out.println("\nAté logo, " + usuarioLogado.getNome() + "!");
    }

    public static void exibirMenu() {
        // Polimorfismo: exibe menu diferente conforme o tipo real do usuário
        if (usuarioLogado instanceof UsuarioPremium) {
            exibirMenuPremium();
        } else {
            exibirMenuFree();
        }
    }

    public static void exibirMenuFree() {
        System.out.println("\n=== MENU (🆓 FREE) — " + usuarioLogado.getNome() + " ===");
        System.out.println("1. Reproduzir música");
        System.out.println("2. Ver histórico");
        System.out.println("3. Criar playlist (máx. 3)");
        System.out.println("4. Gerenciar playlists");
        System.out.println("5. Listar músicas");
        System.out.println("6. Cadastrar música");
        System.out.println("7. Buscar música");
        System.out.println("8. Estatísticas");
        System.out.println("9. Playlists Automáticas");
        System.out.println("0. Sair (Logoff)");
        System.out.print("Opção: ");
    }

    public static void exibirMenuPremium() {
        System.out.println("\n=== MENU (💎 PREMIUM) — " + usuarioLogado.getNome() + " ===");
        System.out.println("1. Reproduzir música (Alta Qualidade)");
        System.out.println("2. Ver histórico");
        System.out.println("3. Criar playlist (ilimitado)");
        System.out.println("4. Gerenciar playlists");
        System.out.println("5. Listar músicas");
        System.out.println("6. Cadastrar música");
        System.out.println("7. Buscar música");
        System.out.println("8. Baixar música");
        System.out.println("9. Playlists Automáticas");
        System.out.println("10. Ver músicas baixadas");
        System.out.println("11. Estatísticas do sistema");
        System.out.println("0. Sair (Logoff)");
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
            case 1:  reproduzirMusica();                    break;
            case 2:  usuarioLogado.exibirHistorico();       break;
            case 3:  criarPlaylist();                       break;
            case 4:  gerenciarPlaylists();                  break;
            case 5:  listarMusicas();                       break;
            case 6:  cadastrarMusica();                     break;
            case 7:  buscarMusica();                        break;
            case 8:  processarOpcao8();                     break;
            case 9:  menuPlaylistsAutomaticas();            break;
            case 10: processarOpcao10();                    break;
            case 11: processarOpcao11();                    break;
            case 0:                                         break;
            default: System.out.println("Opção inválida."); break;
        }
    }

    // Opção 8: download (Premium) ou estatísticas pessoais (Free)
    public static void processarOpcao8() {
        if (usuarioLogado instanceof UsuarioPremium) {
            // Downcasting para acessar método específico de UsuarioPremium
            baixarMusica((UsuarioPremium) usuarioLogado);
        } else {
            exibirEstatisticasPessoais();
        }
    }

    // Opção 10: apenas Premium — músicas baixadas
    public static void processarOpcao10() {
        if (usuarioLogado instanceof UsuarioPremium) {
            ((UsuarioPremium) usuarioLogado).listarMusicasBaixadas(); // downcasting
        } else {
            System.out.println("Opção inválida.");
        }
    }

    // Opção 11: apenas Premium — estatísticas globais do sistema
    public static void processarOpcao11() {
        if (usuarioLogado instanceof UsuarioPremium) {
            exibirEstatisticasSistema();
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
        // Polimorfismo em ação: chama a versão correta do método conforme o tipo real
        usuarioLogado.reproduzirMusica(musicas.get(indice));
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
            System.out.print("Gênero: ");
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
        System.out.print("Buscar por título ou artista: ");
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
            // Polimorfismo: chama a versão correta de criarPlaylist()
            usuarioLogado.criarPlaylist(nome);
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
            System.out.println("5. Reproduzir playlist");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            op = lerOpcao();
            switch (op) {
                case 1: usuarioLogado.listarPlaylists();  break;
                case 2: adicionarMusicaPlaylist();         break;
                case 3: removerMusicaPlaylist();           break;
                case 4: detalhesPlaylist();                break;
                case 5: reproduzirPlaylist();              break;
            }
        } while (op != 0);
    }

    public static void adicionarMusicaPlaylist() {
        usuarioLogado.listarPlaylists();
        System.out.print("Escolha a playlist: ");
        Playlist pl = usuarioLogado.getPlaylist(lerOpcao() - 1);
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
        usuarioLogado.listarPlaylists();
        System.out.print("Escolha a playlist: ");
        Playlist pl = usuarioLogado.getPlaylist(lerOpcao() - 1);
        if (pl == null) return;

        pl.listarMusicas();
        System.out.print("Número da música a remover: ");
        pl.removerMusica(lerOpcao() - 1);
    }

    public static void detalhesPlaylist() {
        usuarioLogado.listarPlaylists();
        System.out.print("Escolha a playlist: ");
        Playlist pl = usuarioLogado.getPlaylist(lerOpcao() - 1);
        if (pl == null) return;

        System.out.println("\n--- " + pl.getNome() + " ---");

        // instanceof para verificar se é automática e exibir info extra
        if (pl instanceof PlaylistAutomatica) {
            PlaylistAutomatica auto = (PlaylistAutomatica) pl; // downcasting
            System.out.println("Tipo: Automática | Critério: " + auto.getCriterio());
        } else {
            System.out.println("Tipo: Personalizada");
        }

        System.out.println("Descrição: " + pl.getDescricao());
        pl.listarMusicas();
        System.out.println("Total de músicas: " + pl.getQuantidadeMusicas());
        System.out.println("Duração total:    " + pl.getDuracaoTotal() + "s");
    }

    // Reproduz a playlist — polimorfismo: chama reproduzir() da subclasse correta
    public static void reproduzirPlaylist() {
        usuarioLogado.listarPlaylists();
        System.out.print("Escolha a playlist: ");
        Playlist pl = usuarioLogado.getPlaylist(lerOpcao() - 1);
        if (pl == null) return;

        pl.reproduzir(); // polimorfismo: PlaylistAutomatica ou Playlist base
    }

    // =========================================================
    // PLAYLISTS AUTOMÁTICAS
    // =========================================================

    public static void menuPlaylistsAutomaticas() {
        System.out.println("\n=== PLAYLISTS AUTOMÁTICAS ===");
        System.out.println("1. Top Mais Tocadas");
        System.out.println("2. Recomendadas para Você");
        System.out.println("3. Adicionadas Recentemente");
        System.out.println("0. Voltar");
        System.out.print("Escolha: ");
        int opcao = lerOpcao();

        String criterio;
        String nomePlaylist;

        switch (opcao) {
            case 1:
                criterio = "top";
                nomePlaylist = "Top Mais Tocadas";
                break;
            case 2:
                criterio = "recomendadas";
                nomePlaylist = "Recomendadas para Você";
                break;
            case 3:
                criterio = "recentes";
                nomePlaylist = "Adicionadas Recentemente";
                break;
            default:
                return;
        }

        System.out.println("🤖 Gerando playlist \"" + nomePlaylist + "\"...");

        // Cria a PlaylistAutomatica e popula com músicas do catálogo
        PlaylistAutomatica auto = new PlaylistAutomatica(nomePlaylist, criterio);
        auto.atualizar(musicas); // preenche com base no critério

        // Upcasting implícito ao adicionar no ArrayList<Playlist> do usuário
        usuarioLogado.adicionarPlaylist(auto);

        System.out.println("✅ Playlist criada com " + auto.getQuantidadeMusicas() + " músicas!");
        auto.reproduzir();
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

    // Estatísticas pessoais do usuário logado
    public static void exibirEstatisticasPessoais() {
        System.out.println("\n--- MINHAS ESTATÍSTICAS ---");
        System.out.println("Usuário:            " + usuarioLogado.getNome());
        System.out.println("Email:              " + usuarioLogado.getEmail());
        System.out.println("Total de músicas:   " + musicas.size());
        System.out.println("Total de playlists: " + usuarioLogado.getTotalPlaylists());
        System.out.println("Total reproduções:  " + usuarioLogado.getTotalReproducoes());

        // instanceof para acessar dados específicos do tipo de usuário
        if (usuarioLogado instanceof UsuarioFree) {
            UsuarioFree free = (UsuarioFree) usuarioLogado; // downcasting
            System.out.println("Playlists restantes:" + free.getPlaylitsDisponiveis() + "/3");
        }
    }

    // Estatísticas globais do sistema — percorre o ArrayList polimórfico
    public static void exibirEstatisticasSistema() {
        System.out.println("\n=== ESTATÍSTICAS DO SISTEMA ===");

        int totalFree = 0;
        int totalPremium = 0;
        int reproducoesFree = 0;
        int reproducoesPremium = 0;
        int anunciosExibidos = 0;
        int totalBaixadas = 0;

        // Itera sobre o ArrayList polimórfico usando instanceof para dados específicos
        for (Usuario u : usuarios) {
            if (u instanceof UsuarioPremium) {
                UsuarioPremium premium = (UsuarioPremium) u; // downcasting
                totalPremium++;
                reproducoesPremium += u.getTotalReproducoes();
                totalBaixadas += premium.getTotalBaixadas();

            } else if (u instanceof UsuarioFree) {
                UsuarioFree free = (UsuarioFree) u; // downcasting
                totalFree++;
                reproducoesFree += u.getTotalReproducoes();
                anunciosExibidos += free.getContadorReproducoes() / 3;
            }
        }

        int totalUsuarios = totalFree + totalPremium;
        int totalReproducoes = reproducoesFree + reproducoesPremium;

        System.out.println("Total de usuários:  " + totalUsuarios);
        System.out.printf("  - Free:           %d usuários%n", totalFree);
        System.out.printf("  - Premium:        %d usuários%n", totalPremium);

        System.out.println("\nReproduções totais: " + totalReproducoes);
        if (totalReproducoes > 0) {
            System.out.printf("  - Free:           %d reproduções (%.0f%%)%n",
                    reproducoesFree, (reproducoesFree * 100.0 / totalReproducoes));
            System.out.printf("  - Premium:        %d reproduções (%.0f%%)%n",
                    reproducoesPremium, (reproducoesPremium * 100.0 / totalReproducoes));
        }

        System.out.println("\nAnúncios exibidos:  " + anunciosExibidos);
        System.out.println("Músicas baixadas:   " + totalBaixadas);
        System.out.println("Músicas no catálogo:" + musicas.size());
    }

    // =========================================================
    // DADOS DE TESTE
    // =========================================================

    public static void adicionarMusicasTeste() {
        musicas.add(new Musica("Bohemian Rhapsody", "Queen", 354, "Rock"));
        musicas.add(new Musica("Billie Jean", "Michael Jackson", 293, "Pop"));
        musicas.add(new Musica("So What", "Miles Davis", 560, "Jazz"));
        musicas.add(new Musica("Lose Yourself", "Eminem", 326, "Hip-Hop"));
        musicas.add(new Musica("Für Elise", "Beethoven", 180, "Clássica"));
        musicas.add(new Musica("Strobe", "deadmau5", 600, "Eletrônica"));
    }
}