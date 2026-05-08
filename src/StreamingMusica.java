import java.util.ArrayList;
import java.util.Scanner;

public class StreamingMusica {

    static ArrayList<Musica> musicas = new ArrayList<>();
    static Usuario usuario = new Usuario("Victor");

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        adicionarMusicasTeste();

        int opcao;

        do {
            exibirMenu();
            opcao = lerOpcao();
            processarOpcao(opcao);
        } while (opcao != 0);

        System.out.println("\nAté logo!");
        scanner.close();
    }

    // MENU
    public static void exibirMenu() {
        System.out.println("\n=== SISTEMA ===");
        System.out.println("1. Cadastrar música");
        System.out.println("2. Listar músicas");
        System.out.println("3. Buscar música");
        System.out.println("4. Criar playlist");
        System.out.println("5. Gerenciar playlists");
        System.out.println("6. Estatísticas");
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
            case 1: cadastrarMusica(); break;
            case 2: listarMusicas(); break;
            case 3: buscarMusica(); break;
            case 4: criarPlaylist(); break;
            case 5: gerenciarPlaylists(); break;
            case 6: exibirEstatisticas(); break;
            case 0: break;
            default: System.out.println("Opção inválida.");
        }
    }

    // MÚSICA
    public static void cadastrarMusica() {
        try {
            System.out.print("Título: ");
            String titulo = scanner.nextLine();

            System.out.print("Artista: ");
            String artista = scanner.nextLine();

            System.out.print("Duração (em segundos, entre 1 e 3599): ");
            int duracao = Integer.parseInt(scanner.nextLine());

            String[] generos = Musica.getGenerosValidos();
            System.out.println("Escolha gênero:");
            for (int i = 0; i < generos.length; i++) {
                System.out.println((i + 1) + ". " + generos[i]);
            }

            int g = Integer.parseInt(scanner.nextLine());

            if (g < 1 || g > generos.length) {
                System.out.println("Gênero inválido.");
                return;
            }

            String genero = generos[g - 1];

            Musica m = new Musica(titulo, artista, duracao, genero);
            musicas.add(m);
            System.out.println("Música cadastrada!");

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

    // PLAYLIST
    public static void criarPlaylist() {
        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            usuario.criarPlaylist(nome);
            System.out.println("Playlist criada!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao criar playlist: " + e.getMessage());
        }
    }

    public static void gerenciarPlaylists() {
        int op;

        do {
            System.out.println("\n1. Listar");
            System.out.println("2. Adicionar música");
            System.out.println("3. Remover música");
            System.out.println("4. Detalhes");
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
        System.out.print("Escolha playlist: ");
        int p = Integer.parseInt(scanner.nextLine()) - 1;

        Playlist pl = usuario.getPlaylist(p);
        if (pl == null) return;

        listarMusicas();
        System.out.print("Escolha música: ");
        int m = Integer.parseInt(scanner.nextLine()) - 1;

        if (m >= 0 && m < musicas.size()) {
            pl.adicionarMusica(musicas.get(m));
            System.out.println("Adicionada!");
        } else {
            System.out.println("Música inválida.");
        }
    }

    public static void removerMusicaPlaylist() {
        usuario.listarPlaylists();
        System.out.print("Escolha playlist: ");
        int p = Integer.parseInt(scanner.nextLine()) - 1;

        Playlist pl = usuario.getPlaylist(p);
        if (pl == null) return;

        pl.listarMusicas();
        System.out.print("Índice: ");
        int m = Integer.parseInt(scanner.nextLine()) - 1;

        pl.removerMusica(m);
    }

    public static void detalhesPlaylist() {
        usuario.listarPlaylists();
        System.out.print("Escolha playlist: ");
        int p = Integer.parseInt(scanner.nextLine()) - 1;

        Playlist pl = usuario.getPlaylist(p);
        if (pl == null) return;

        pl.listarMusicas();

        System.out.println("Total músicas: " + pl.getQuantidadeMusicas());
        System.out.println("Duração total: " + pl.getDuracaoTotal() + "s");
    }

    // ESTATÍSTICAS
    public static void exibirEstatisticas() {
        System.out.println("Usuário: " + usuario.getNome());
        System.out.println("Total músicas: " + musicas.size());
        System.out.println("Total playlists: " + usuario.getTotalPlaylists());
    }

    // TESTE
    public static void adicionarMusicasTeste() {
        Musica m1 = new Musica("Bohemian Rhapsody", "Queen", 354, "Rock");
        Musica m2 = new Musica("Billie Jean", "Michael Jackson", 293, "Pop");

        musicas.add(m1);
        musicas.add(m2);
    }
}