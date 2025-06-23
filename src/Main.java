import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        IBGENoticiaService service = new IBGENoticiaService();

        System.out.print("Digite seu nome ou apelido: ");
        String nomeUsuario = scanner.nextLine();
        Usuario usuario = new Usuario(nomeUsuario);

        List<Noticia> favoritos = Persistencia.carregarLista("favoritos.json");
        List<Noticia> lidas = Persistencia.carregarLista("lidas.json");
        List<Noticia> paraLerDepois = Persistencia.carregarLista("para_ler_depois.json");
        List<Noticia> noticiasCarregadas = new ArrayList<>();

        while (true) {
            System.out.println("\n=== Blog de Notícias IBGE ===");
            System.out.println("Usuário: " + usuario.getNome());
            System.out.println("1. Buscar notícias por título");
            System.out.println("2. Buscar notícias por data");
            System.out.println("3. Exibir todas as notícias carregadas");
            System.out.println("4. Exibir listas (Favoritos, Lidas, Para ler depois)");
            System.out.println("5. Sair");
            System.out.println("6. Buscar todas as notícias (sem filtro)");  
            System.out.print("Escolha uma opção: ");

            String opcaoStr = scanner.nextLine();
            int opcao;

            try {
                opcao = Integer.parseInt(opcaoStr);
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
                continue;
            }

            switch (opcao) {
                case 1:
                    System.out.print("Digite o título para buscar: ");
                    String tituloBusca = scanner.nextLine();
                    List<Noticia> resultadosTitulo = service.buscarPorTitulo(tituloBusca);
                    noticiasCarregadas.addAll(resultadosTitulo);
                    if (resultadosTitulo.isEmpty()) {
                        System.out.println("Nenhuma notícia encontrada com esse título.");
                    } else {
                        resultadosTitulo.forEach(System.out::println);
                    }
                    break;

                case 2:
                    System.out.print("Digite a data (AAAA-MM-DD): ");
                    String dataBusca = scanner.nextLine();
                    List<Noticia> resultadosData = service.buscarPorData(dataBusca);
                    noticiasCarregadas.addAll(resultadosData);
                    if (resultadosData.isEmpty()) {
                        System.out.println("Nenhuma notícia encontrada para essa data.");
                    } else {
                        resultadosData.forEach(System.out::println);
                    }
                    break;

                case 3:
                    if (noticiasCarregadas.isEmpty()) {
                        System.out.println("Nenhuma notícia carregada ainda. Use as opções 1, 2 ou 6 para buscar.");
                    } else {
                        System.out.println("\n--- Notícias Carregadas ---");
                        for (int i = 0; i < noticiasCarregadas.size(); i++) {
                            System.out.println("[" + i + "] " + noticiasCarregadas.get(i).getTitulo());
                        }

                        System.out.print("\nDigite o número da notícia para ver detalhes e opções, ou -1 para voltar: ");
                        int escolha = scanner.nextInt();
                        scanner.nextLine();

                        if (escolha >= 0 && escolha < noticiasCarregadas.size()) {
                            Noticia selecionada = noticiasCarregadas.get(escolha);
                            System.out.println("\nDetalhes da Notícia:");
                            System.out.println(selecionada);

                            System.out.println("\nO que deseja fazer?");
                            System.out.println("1. Adicionar aos favoritos");
                            System.out.println("2. Marcar como lida");
                            System.out.println("3. Adicionar em 'Para ler depois'");
                            System.out.println("4. Voltar");

                            int acao = scanner.nextInt();
                            scanner.nextLine();

                            switch (acao) {
                                case 1:
                                    favoritos.add(selecionada);
                                    Persistencia.salvar(favoritos, "favoritos.json");
                                    System.out.println("Adicionado aos favoritos!");
                                    break;
                                case 2:
                                    lidas.add(selecionada);
                                    Persistencia.salvar(lidas, "lidas.json");
                                    System.out.println("Marcada como lida!");
                                    break;
                                case 3:
                                    paraLerDepois.add(selecionada);
                                    Persistencia.salvar(paraLerDepois, "para_ler_depois.json");
                                    System.out.println("Adicionado em 'Para ler depois'!");
                                    break;
                                case 4:
                                    System.out.println("Voltando ao menu...");
                                    break;
                                default:
                                    System.out.println("Opção inválida.");
                            }
                        }
                    }
                    break;

                case 4:
                    System.out.println("\n--- Favoritos ---");
                    if (favoritos.isEmpty()) {
                        System.out.println("Nenhuma notícia nos favoritos.");
                    } else {
                        favoritos.forEach(System.out::println);
                    }

                    System.out.println("\n--- Lidas ---");
                    if (lidas.isEmpty()) {
                        System.out.println("Nenhuma notícia marcada como lida.");
                    } else {
                        lidas.forEach(System.out::println);
                    }

                    System.out.println("\n--- Para ler depois ---");
                    if (paraLerDepois.isEmpty()) {
                        System.out.println("Nenhuma notícia na lista 'Para ler depois'.");
                    } else {
                        paraLerDepois.forEach(System.out::println);
                    }
                    break;

                case 5:
                    System.out.println("Saindo... Até mais, " + usuario.getNome() + "!");
                    scanner.close();
                    return;

                case 6:
                    System.out.println("Buscando todas as notícias do IBGE...");
                    List<Noticia> todasNoticias = service.buscarTodasNoticias();
                    noticiasCarregadas.addAll(todasNoticias);
                    if (todasNoticias.isEmpty()) {
                        System.out.println("Nenhuma notícia encontrada.");
                    } else {
                        todasNoticias.forEach(System.out::println);
                    }
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
