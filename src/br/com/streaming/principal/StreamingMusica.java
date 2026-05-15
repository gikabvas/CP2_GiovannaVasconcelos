package br.com.streaming.principal;

import br.com.streaming.modelo.*;
import br.com.streaming.servico.GeradorRecomendacoes;

import java.util.ArrayList;
import java.util.Scanner;

// classe principal — controla os menus e conecta tudo
// uso ArrayList<Usuario> pra guardar Free e Premium no mesmo lugar (polimorfismo)
public class StreamingMusica {

    private static ArrayList<Usuario> usuarios = new ArrayList<>();
    private static Usuario            usuarioLogado = null;
    private static ArrayList<Musica>  catalogo = new ArrayList<>();

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        adicionarMusicasTeste();
        menuInicial();
        scanner.close();
    }

    // menu antes do login — criar conta, logar ou ver usuarios
    private static void menuInicial() {
        int opcao;
        do {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║   🎵 SISTEMA DE STREAMING    ║");
            System.out.println("╚══════════════════════════════╝");
            System.out.println("  1. Criar novo usuário");
            System.out.println("  2. Login");
            System.out.println("  3. Listar usuários");
            System.out.println("  0. Sair");
            System.out.print("Escolha: ");
            opcao = lerOpcao();

            switch (opcao) {
                case 1:  criarUsuario();   break;
                case 2:  fazerLogin();     break;
                case 3:  listarUsuarios(); break;
                case 0:  System.out.println("\nAté logo! 👋"); break;
                default: System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void criarUsuario() {
        System.out.println("\n=== CRIAR USUÁRIO ===");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        // nao deixa cadastrar o mesmo email duas vezes
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                System.out.println("❌ Email já cadastrado.");
                return;
            }
        }

        System.out.println("\nTipo de conta:");
        System.out.println("  1. Free (Gratuito)");
        System.out.println("  2. Premium (Pago)");
        System.out.print("Escolha: ");
        int tipo = lerOpcao();

        try {
            if (tipo == 2) {
                System.out.println("\nEscolha o plano:");
                System.out.println("  1. Mensal  (R$ 19,90)");
                System.out.println("  2. Anual   (R$ 199,00)");
                System.out.println("  3. Familiar (R$ 29,90)");
                System.out.print("Escolha: ");

                String plano;
                switch (lerOpcao()) {
                    case 2:  plano = "Anual";    break;
                    case 3:  plano = "Familiar"; break;
                    default: plano = "Mensal";   break;
                }
                // upcasting implicito: UsuarioPremium -> Usuario
                usuarios.add(new UsuarioPremium(nome, email, plano));
                System.out.println("✅ Conta Premium (" + plano + ") criada com sucesso!");

            } else {
                // upcasting implicito: UsuarioFree -> Usuario
                usuarios.add(new UsuarioFree(nome, email));
                System.out.println("✅ Conta Free criada com sucesso!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void fazerLogin() {
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado. Crie um primeiro!");
            return;
        }
        listarUsuarios();
        System.out.print("Escolha o usuário: ");
        int idx = lerOpcao() - 1;
        if (idx < 0 || idx >= usuarios.size()) {
            System.out.println("Índice inválido.");
            return;
        }

        // a variavel é do tipo base Usuario, mas o objeto pode ser Free ou Premium
        usuarioLogado = usuarios.get(idx);
        String tipo = (usuarioLogado instanceof UsuarioPremium) ? "Premium 💎" : "Free 🆓";
        System.out.println("✅ Login: " + usuarioLogado.getNome() + " (" + tipo + ")");

        menuPrincipal();
        usuarioLogado = null; // desloga ao sair do menu
    }

    // usa instanceof pra exibir detalhes especificos de cada tipo
    private static void listarUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }
        System.out.println("\nUsuários cadastrados:");
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario u = usuarios.get(i);

            if (u instanceof UsuarioPremium) {
                UsuarioPremium premium = (UsuarioPremium) u; // downcasting
                System.out.printf("  %d. %s  (Premium — %s | %d downloads)%n",
                        (i + 1), u.getNome(), premium.getPlano(), premium.getTamanhoBaixados());

            } else if (u instanceof UsuarioFree) {
                UsuarioFree free = (UsuarioFree) u; // downcasting
                System.out.printf("  %d. %s  (Free — playlists: %d/3)%n",
                        (i + 1), u.getNome(), free.getTotalPlaylists());
            }
        }
    }

    private static void menuPrincipal() {
        int opcao;
        do {
            exibirMenu();
            opcao = lerOpcao();
            processarOpcao(opcao);
        } while (opcao != 0);

        System.out.println("\nAté logo, " + usuarioLogado.getNome() + "! 👋");
    }

    // polimorfismo no menu: mostra opcoes diferentes conforme o tipo do usuario logado
    private static void exibirMenu() {
        if (usuarioLogado instanceof UsuarioPremium) {
            exibirMenuPremium();
        } else {
            exibirMenuFree();
        }
    }

    private static void exibirMenuFree() {
        System.out.println("\n╔══════════════════════════════════╗");
        System.out.printf( "║  🆓 FREE  —  %-20s║%n", usuarioLogado.getNome());
        System.out.println("╚══════════════════════════════════╝");
        System.out.println("  1. Reproduzir música");
        System.out.println("  2. Ver histórico");
        System.out.println("  3. Criar playlist (máx. 3)");
        System.out.println("  4. Gerenciar playlists");
        System.out.println("  5. Listar músicas");
        System.out.println("  6. Cadastrar música");
        System.out.println("  7. Buscar música");
        System.out.println("  8. Minhas estatísticas");
        System.out.println("  9. Playlists automáticas");
        System.out.println("  0. Logoff");
        System.out.print("Opção: ");
    }

    private static void exibirMenuPremium() {
        System.out.println("\n╔══════════════════════════════════╗");
        System.out.printf( "║  💎 PREMIUM  —  %-17s║%n", usuarioLogado.getNome());
        System.out.println("╚══════════════════════════════════╝");
        System.out.println("  1. Reproduzir música (Alta Qualidade)");
        System.out.println("  2. Ver histórico");
        System.out.println("  3. Criar playlist (ilimitado)");
        System.out.println("  4. Gerenciar playlists");
        System.out.println("  5. Listar músicas");
        System.out.println("  6. Cadastrar música");
        System.out.println("  7. Buscar música");
        System.out.println("  8. Baixar / Remover download");
        System.out.println("  9. Playlists automáticas");
        System.out.println("  10. Ver músicas baixadas");
        System.out.println("  11. Estatísticas do sistema");
        System.out.println("  0. Logoff");
        System.out.print("Opção: ");
    }

    private static void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:  reproduzirMusica();              break;
            case 2:  usuarioLogado.exibirHistorico(); break;
            case 3:  criarPlaylist();                 break;
            case 4:  gerenciarPlaylists();            break;
            case 5:  listarMusicas();                 break;
            case 6:  cadastrarMusica();               break;
            case 7:  buscarMusica();                  break;
            case 8:  processarOpcao8();               break;
            case 9:  menuPlaylistsAutomaticas();      break;
            case 10: processarOpcao10();              break;
            case 11: processarOpcao11();              break;
            case 0:                                   break;
            default: System.out.println("Opção inválida.");
        }
    }

    // opcao 8 faz coisas diferentes dependendo do tipo de usuario
    private static void processarOpcao8() {
        if (usuarioLogado instanceof UsuarioPremium) {
            menuDownload((UsuarioPremium) usuarioLogado); // downcasting necessario aqui
        } else {
            exibirEstatisticasPessoais();
        }
    }

    private static void processarOpcao10() {
        if (usuarioLogado instanceof UsuarioPremium) {
            ((UsuarioPremium) usuarioLogado).listarMusicasBaixadas();
        } else {
            System.out.println("Opção inválida.");
        }
    }

    private static void processarOpcao11() {
        if (usuarioLogado instanceof UsuarioPremium) {
            exibirEstatisticasSistema();
        } else {
            System.out.println("Opção inválida.");
        }
    }

    private static void reproduzirMusica() {
        if (catalogo.isEmpty()) { System.out.println("Catálogo vazio."); return; }
        listarMusicas();
        System.out.print("Escolha a música: ");
        int idx = lerOpcao() - 1;
        if (idx < 0 || idx >= catalogo.size()) { System.out.println("Índice inválido."); return; }

        // polimorfismo: chama reproduzirMusica() da versao correta (Free ou Premium)
        usuarioLogado.reproduzirMusica(catalogo.get(idx));
    }

    private static void cadastrarMusica() {
        try {
            System.out.print("Título: ");
            String titulo = scanner.nextLine();

            System.out.print("Artista: ");
            String artista = scanner.nextLine();

            System.out.print("Duração (segundos, 1–3599): ");
            int duracao = Integer.parseInt(scanner.nextLine());

            String[] generos = Musica.getGenerosValidos();
            System.out.println("Gênero:");
            for (int i = 0; i < generos.length; i++) {
                System.out.println("  " + (i + 1) + ". " + generos[i]);
            }
            System.out.print("Escolha: ");
            int g = lerOpcao();
            if (g < 1 || g > generos.length) { System.out.println("Gênero inválido."); return; }

            catalogo.add(new Musica(titulo, artista, duracao, generos[g - 1]));
            System.out.println("✅ Música cadastrada!");

        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void listarMusicas() {
        if (catalogo.isEmpty()) { System.out.println("Catálogo vazio."); return; }
        System.out.println("\n--- CATÁLOGO ---");
        for (int i = 0; i < catalogo.size(); i++) {
            System.out.print("  " + (i + 1) + ". ");
            catalogo.get(i).exibir();
        }
    }

    private static void buscarMusica() {
        System.out.print("Buscar por título ou artista: ");
        String busca = scanner.nextLine();
        boolean encontrou = false;
        for (Musica m : catalogo) {
            if (m.contemTitulo(busca) || m.contemArtista(busca)) {
                m.exibir();
                encontrou = true;
            }
        }
        if (!encontrou) System.out.println("Nenhuma música encontrada.");
    }

    private static void criarPlaylist() {
        System.out.print("Nome da playlist: ");
        String nome = scanner.nextLine();
        try {
            // polimorfismo: Free vai checar o limite, Premium cria direto
            usuarioLogado.criarPlaylist(nome);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void gerenciarPlaylists() {
        int op;
        do {
            System.out.println("\n--- GERENCIAR PLAYLISTS ---");
            System.out.println("  1. Listar playlists");
            System.out.println("  2. Adicionar música");
            System.out.println("  3. Remover música");
            System.out.println("  4. Ver detalhes");
            System.out.println("  5. Reproduzir playlist");
            System.out.println("  0. Voltar");
            System.out.print("Opção: ");
            op = lerOpcao();
            switch (op) {
                case 1: usuarioLogado.listarPlaylists(); break;
                case 2: adicionarMusicaPlaylist();        break;
                case 3: removerMusicaPlaylist();          break;
                case 4: detalhesPlaylist();               break;
                case 5: reproduzirPlaylist();             break;
            }
        } while (op != 0);
    }

    private static void adicionarMusicaPlaylist() {
        usuarioLogado.listarPlaylists();
        System.out.print("Escolha a playlist: ");
        Playlist pl = usuarioLogado.getPlaylist(lerOpcao() - 1);
        if (pl == null) return;
        listarMusicas();
        System.out.print("Escolha a música: ");
        int m = lerOpcao() - 1;
        if (m >= 0 && m < catalogo.size()) {
            pl.adicionarMusica(catalogo.get(m));
            System.out.println("✅ Música adicionada!");
        } else {
            System.out.println("Índice inválido.");
        }
    }

    private static void removerMusicaPlaylist() {
        usuarioLogado.listarPlaylists();
        System.out.print("Escolha a playlist: ");
        Playlist pl = usuarioLogado.getPlaylist(lerOpcao() - 1);
        if (pl == null) return;
        pl.listarMusicas();
        System.out.print("Número da música a remover: ");
        pl.removerMusica(lerOpcao() - 1);
    }

    private static void detalhesPlaylist() {
        usuarioLogado.listarPlaylists();
        System.out.print("Escolha a playlist: ");
        Playlist pl = usuarioLogado.getPlaylist(lerOpcao() - 1);
        if (pl == null) return;

        System.out.println("\n--- " + pl.getNome() + " ---");
        System.out.println("Descrição: " + pl.getDescricao());
        System.out.println("Músicas:   " + pl.getQuantidadeMusicas());
        System.out.println("Duração:   " + pl.getDuracaoFormatada());
        pl.listarMusicas();
    }

    private static void reproduzirPlaylist() {
        usuarioLogado.listarPlaylists();
        System.out.print("Escolha a playlist: ");
        Playlist pl = usuarioLogado.getPlaylist(lerOpcao() - 1);
        if (pl == null) return;
        // polimorfismo via interface Reproduzivel — chama o metodo do tipo real da playlist
        pl.reproduzir();
    }

    private static void menuPlaylistsAutomaticas() {
        System.out.println("\n=== PLAYLISTS AUTOMÁTICAS ===");
        System.out.println("  1. Top Mais Tocadas");
        System.out.println("  2. Recomendadas para Você");
        System.out.println("  3. Adicionadas Recentemente");
        System.out.println("  0. Voltar");
        System.out.print("Escolha: ");

        String criterio;
        switch (lerOpcao()) {
            case 1:  criterio = "top";          break;
            case 2:  criterio = "recomendadas"; break;
            case 3:  criterio = "recentes";     break;
            default: return;
        }

        if (catalogo.isEmpty()) { System.out.println("Catálogo vazio."); return; }

        try {
            // GeradorRecomendacoes monta a playlist — só adiciono no usuario aqui
            Playlist pl = GeradorRecomendacoes.gerar(criterio, catalogo);
            usuarioLogado.adicionarPlaylist(pl);
            System.out.println("✅ Playlist \"" + pl.getNome()
                    + "\" criada com " + pl.getQuantidadeMusicas() + " músicas!");
            pl.reproduzir();
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void menuDownload(UsuarioPremium premium) {
        System.out.println("\n--- DOWNLOADS ---");
        System.out.println("  1. Baixar música");
        System.out.println("  2. Remover download");
        System.out.println("  0. Voltar");
        System.out.print("Opção: ");

        switch (lerOpcao()) {
            case 1:
                listarMusicas();
                System.out.print("Escolha a música para baixar: ");
                int idx = lerOpcao() - 1;
                if (idx >= 0 && idx < catalogo.size()) {
                    premium.baixar(catalogo.get(idx)); // metodo da interface Baixavel
                } else {
                    System.out.println("Índice inválido.");
                }
                break;

            case 2:
                premium.listarMusicasBaixadas();
                System.out.print("Escolha o download a remover: ");
                int idxR = lerOpcao() - 1;
                ArrayList<Musica> baixadas = premium.getMusicasBaixadas();
                if (idxR >= 0 && idxR < baixadas.size()) {
                    premium.removerDownload(baixadas.get(idxR)); // metodo da interface Baixavel
                } else {
                    System.out.println("Índice inválido.");
                }
                break;
        }
    }

    private static void exibirEstatisticasPessoais() {
        System.out.println("\n--- MINHAS ESTATÍSTICAS ---");
        System.out.println("Usuário:          " + usuarioLogado.getNome());
        System.out.println("Email:            " + usuarioLogado.getEmail());
        System.out.println("Reproduções:      " + usuarioLogado.getTotalReproducoes());
        System.out.println("Playlists:        " + usuarioLogado.getTotalPlaylists());
        System.out.println("Músicas catálogo: " + catalogo.size());

        // so exibe esses dados se for Free — usa instanceof + downcasting
        if (usuarioLogado instanceof UsuarioFree) {
            UsuarioFree free = (UsuarioFree) usuarioLogado;
            System.out.println("Playlists livres: " + free.getPlaylistsDisponiveis() + "/3");
            System.out.println("Anúncios vistos:  " + free.getAnunciosExibidos());
        }
    }

    // percorre o ArrayList polimorfico e separa os dados por tipo de usuario
    private static void exibirEstatisticasSistema() {
        System.out.println("\n=== ESTATÍSTICAS DO SISTEMA ===");

        int totalFree = 0, totalPremium = 0;
        int repFree = 0, repPremium = 0;
        int anuncios = 0, downloads = 0;

        for (Usuario u : usuarios) {
            if (u instanceof UsuarioPremium) {
                UsuarioPremium p = (UsuarioPremium) u;
                totalPremium++;
                repPremium += u.getTotalReproducoes();
                downloads  += p.getTamanhoBaixados();

            } else if (u instanceof UsuarioFree) {
                UsuarioFree f = (UsuarioFree) u;
                totalFree++;
                repFree  += u.getTotalReproducoes();
                anuncios += f.getAnunciosExibidos();
            }
        }

        int totalU = totalFree + totalPremium;
        int totalR = repFree + repPremium;

        System.out.printf("Usuários: %d  (Free: %d | Premium: %d)%n", totalU, totalFree, totalPremium);
        System.out.printf("Reproduções: %d%n", totalR);
        if (totalR > 0) {
            System.out.printf("  Free: %d (%.0f%%) | Premium: %d (%.0f%%)%n",
                    repFree,    repFree    * 100.0 / totalR,
                    repPremium, repPremium * 100.0 / totalR);
        }
        System.out.println("Anúncios exibidos: " + anuncios);
        System.out.println("Downloads totais:  " + downloads);
        System.out.println("Músicas catálogo:  " + catalogo.size());
    }

    private static int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    // musicas iniciais so pra nao comecar com catalogo vazio na hora de testar
    private static void adicionarMusicasTeste() {
        catalogo.add(new Musica("Bohemian Rhapsody", "Queen",           354, "Rock"));
        catalogo.add(new Musica("Billie Jean",        "Michael Jackson", 293, "Pop"));
        catalogo.add(new Musica("So What",            "Miles Davis",     560, "Jazz"));
        catalogo.add(new Musica("Lose Yourself",      "Eminem",          326, "Hip-Hop"));
        catalogo.add(new Musica("Für Elise",          "Beethoven",       180, "Clássica"));
        catalogo.add(new Musica("Strobe",             "deadmau5",        600, "Eletrônica"));
    }
}