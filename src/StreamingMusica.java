import java.util.ArrayList;
import java.util.Scanner;

public class StreamingMusica {

    static ArrayList<Musica> musicas = new ArrayList<>();

    static final String[] GENEROS_VALIDOS = {"Pop", "Rock", "Jazz", "Eletrônica", "Hip-Hop", "Clássica"};

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        adicionarMusicasTeste();

        int opcao;
        do {
            exibirMenu();
            opcao = lerOpcao();
            processarOpcao(opcao);
        } while (opcao != 0);

        System.out.println("\n🎵 Até logo!");
        scanner.close();
    }

    public static void exibirMenu() {
        System.out.println("\n1. Cadastrar música");
        System.out.println("2. Listar músicas");
        System.out.println("3. Buscar por título");
        System.out.println("4. Buscar por artista");
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
            case 3: buscarPorTitulo(); break;
            case 4: buscarPorArtista(); break;
            case 0: break;
            default: System.out.println("❌ Opção inválida!");
        }
    }

    public static void cadastrarMusica() {
        System.out.print("Título: ");
        String titulo = scanner.nextLine();

        System.out.print("Artista: ");
        String artista = scanner.nextLine();

        int duracao;
        try {
            System.out.print("Duração (segundos): ");
            duracao = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("❌ Duração inválida!");
            return;
        }

        System.out.println("Escolha gênero:");
        for (int i = 0; i < GENEROS_VALIDOS.length; i++) {
            System.out.println((i + 1) + ". " + GENEROS_VALIDOS[i]);
        }

        int g;
        try {
            g = Integer.parseInt(scanner.nextLine());
            if (g < 1 || g > GENEROS_VALIDOS.length) {
                System.out.println("❌ Gênero inválido!");
                return;
            }
        } catch (Exception e) {
            System.out.println("❌ Entrada inválida!");
            return;
        }

        String genero = GENEROS_VALIDOS[g - 1];

        musicas.add(new Musica(titulo, artista, duracao, genero));

        System.out.println("✅ Música cadastrada com sucesso!");
    }

    public static void listarMusicas() {
        if (musicas.isEmpty()) {
            System.out.println("📭 Nenhuma música cadastrada.");
            return;
        }

        for (Musica m : musicas) {
            m.exibir();
        }
    }

    public static void buscarPorTitulo() {
        System.out.print("Buscar: ");
        String busca = scanner.nextLine().toLowerCase();

        boolean encontrou = false;

        for (Musica m : musicas) {
            if (m.contemTitulo(busca)) {
                m.exibir();
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("❌ Nenhuma música encontrada.");
        }
    }

    public static void buscarPorArtista() {
        System.out.print("Buscar: ");
        String busca = scanner.nextLine().toLowerCase();

        boolean encontrou = false;

        for (Musica m : musicas) {
            if (m.contemArtista(busca)) {
                m.exibir();
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("❌ Nenhuma música encontrada.");
        }
    }

    public static void adicionarMusicasTeste() {
        musicas.add(new Musica("Bohemian Rhapsody", "Queen", 354, "Rock"));
        musicas.add(new Musica("Billie Jean", "Michael Jackson", 293, "Pop"));
    }
}