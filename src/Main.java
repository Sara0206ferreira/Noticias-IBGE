
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Noticia> noticiasCarregadasAtualmente = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Usuario usuario;

        
        usuario = Persistencia.carregarUsuario("usuario.json");
        if (usuario.getNome().isEmpty()) {
            System.out.print("Digite seu nome ou apelido: ");
            usuario.setNome(scanner.nextLine());
            Persistencia.salvarUsuario(usuario, "usuario.json");
        }
        System.out.println("=== Blog de Notícias IBGE ===");
        System.out.println("Usuário: " + usuario.getNome());

        List<Noticia> noticiasFavoritas = Persistencia.carregarLista("favoritos.json");
        List<Noticia> noticiasLidas = Persistencia.carregarLista("lidas.json");
        List<Noticia> noticiasParaLerDepois = Persistencia.carregarLista("para_ler_depois.json");
        

        
        noticiasCarregadasAtualmente = IBGEApiService.buscarNoticias(null);

        int opcao;
        do {
            System.out.println("\n--- Menu Principal ---");
            System.out.println("1. Buscar notícias (por título/palavra-chave ou recentes)");
            System.out.println("2. Buscar notícias por data (AAAA-MM-DD)"); 
            System.out.println("3. Exibir todas as notícias carregadas");
            System.out.println("4. Exibir listas (Favoritos, Lidas, Para ler depois)");
            System.out.println("5. Ordenar listas");
            System.out.println("6. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Digite a palavra-chave para buscar (deixe em branco para as 10 mais recentes): ");
                    String palavraChave = scanner.nextLine();

                    noticiasCarregadasAtualmente = IBGEApiService.buscarNoticias(palavraChave);

                    if (noticiasCarregadasAtualmente.isEmpty()) {
                        System.out.println("Nenhuma notícia encontrada com essa palavra-chave ou recentes.");
                    } else {
                        System.out.println("\n--- Notícias Carregadas ---");
                        exibirNoticias(noticiasCarregadasAtualmente);
                        interagirComNoticias(scanner, noticiasCarregadasAtualmente, noticiasFavoritas, noticiasLidas, noticiasParaLerDepois);
                    }
                    break;
                case 2:
                    System.out.print("Digite a data para buscar (AAAA-MM-DD): ");
                    String dataBusca = scanner.nextLine();

                    
                    noticiasCarregadasAtualmente = IBGEApiService.buscarNoticiasPorData(dataBusca);

                    if (noticiasCarregadasAtualmente.isEmpty()) {
                        System.out.println("Nenhuma notícia encontrada para a data " + dataBusca + ".");
                    } else {
                        System.out.println("\n--- Notícias Carregadas para " + dataBusca + " ---");
                        exibirNoticias(noticiasCarregadasAtualmente);
                        interagirComNoticias(scanner, noticiasCarregadasAtualmente, noticiasFavoritas, noticiasLidas, noticiasParaLerDepois);
                    }
                    break;
                case 3:
                    System.out.println("\n--- Exibindo Notícias Carregadas Atualmente ---");
                    if (noticiasCarregadasAtualmente.isEmpty()) {
                        System.out.println("Nenhuma notícia foi carregada ainda. Use a opção 1 para carregar.");
                    } else {
                        exibirNoticias(noticiasCarregadasAtualmente);
                        interagirComNoticias(scanner, noticiasCarregadasAtualmente, noticiasFavoritas, noticiasLidas, noticiasParaLerDepois);
                    }
                    break;
                case 4:
                    System.out.println("\n--- Listas Salvas ---");
                    System.out.println("1. Favoritas");
                    System.out.println("2. Lidas");
                    System.out.println("3. Para ler depois");
                    System.out.print("Escolha qual lista exibir: ");
                    int subOpcaoLista = scanner.nextInt();
                    scanner.nextLine();

                    switch (subOpcaoLista) {
                        case 1:
                            if (noticiasFavoritas.isEmpty()) {
                                System.out.println("Sua lista de favoritos está vazia.");
                            } else {
                                exibirNoticias(noticiasFavoritas);
                                interagirComNoticias(scanner, noticiasFavoritas, noticiasFavoritas, noticiasLidas, noticiasParaLerDepois);
                            }
                            break;
                        case 2:
                            if (noticiasLidas.isEmpty()) {
                                System.out.println("Sua lista de notícias lidas está vazia.");
                            } else {
                                exibirNoticias(noticiasLidas);
                                interagirComNoticias(scanner, noticiasLidas, noticiasFavoritas, noticiasLidas, noticiasParaLerDepois);
                            }
                            break;
                        case 3:
                            if (noticiasParaLerDepois.isEmpty()) {
                                System.out.println("Sua lista de notícias para ler depois está vazia.");
                            } else {
                                exibirNoticias(noticiasParaLerDepois);
                                interagirComNoticias(scanner, noticiasParaLerDepois, noticiasFavoritas, noticiasLidas, noticiasParaLerDepois);
                            }
                            break;
                        default:
                            System.out.println("Opção de lista inválida.");
                    }
                    break;
                case 5:
                    System.out.println("\n--- Ordenar Listas ---");
                    System.out.println("1. Ordenar Notícias Carregadas Atualmente");
                    System.out.println("2. Ordenar Favoritas");
                    System.out.println("3. Ordenar Lidas");
                    System.out.println("4. Ordenar Para ler depois");
                    System.out.print("Escolha qual lista ordenar: ");
                    int subOpcaoOrdenar = scanner.nextInt();
                    scanner.nextLine();

                    switch (subOpcaoOrdenar) {
                        case 1:
                            if (noticiasCarregadasAtualmente.isEmpty()) {
                                System.out.println("A lista de notícias carregadas está vazia para ordenar.");
                            } else {
                                ordenarListasMenu(scanner, noticiasCarregadasAtualmente, "Notícias Carregadas Atualmente");
                            }
                            break;
                        case 2:
                            if (noticiasFavoritas.isEmpty()) {
                                System.out.println("A lista de favoritos está vazia para ordenar.");
                            } else {
                                ordenarListasMenu(scanner, noticiasFavoritas, "Favoritas");
                            }
                            break;
                        case 3:
                            if (noticiasLidas.isEmpty()) {
                                System.out.println("A lista de lidas está vazia para ordenar.");
                            } else {
                                ordenarListasMenu(scanner, noticiasLidas, "Lidas");
                            }
                            break;
                        case 4:
                            if (noticiasParaLerDepois.isEmpty()) {
                                System.out.println("A lista 'Para ler depois' está vazia para ordenar.");
                            } else {
                                ordenarListasMenu(scanner, noticiasParaLerDepois, "Para ler depois");
                            }
                            break;
                        default:
                            System.out.println("Opção de ordenação inválida.");
                    }
                    break;
                case 6:
                    System.out.println("Saindo do programa. Até logo, " + usuario.getNome() + "!");
                    Persistencia.salvarUsuario(usuario, "usuario.json");
                    Persistencia.salvar(noticiasFavoritas, "favoritos.json");
                    Persistencia.salvar(noticiasLidas, "lidas.json");
                    Persistencia.salvar(noticiasParaLerDepois, "para_ler_depois.json");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 6);

        scanner.close();
    }

    
    private static void exibirNoticias(List<Noticia> lista) {
        if (lista.isEmpty()) {
            System.out.println("Nenhuma notícia para exibir.");
            return;
        }
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + ". " + lista.get(i).getTitulo() + " (" + lista.get(i).getDataPublicacao() + ")");
        }
    }

    
    private static void interagirComNoticias(Scanner scanner, List<Noticia> listaAtual, List<Noticia> favoritos, List<Noticia> lidas, List<Noticia> paraLerDepois) {
        if (listaAtual.isEmpty()) {
            return;
        }
        System.out.println("\n--- Interagir com Notícias ---");
        System.out.println("1. Ver detalhes de uma notícia");
        System.out.println("2. Adicionar aos favoritos");
        System.out.println("3. Marcar como lida");
        System.out.println("4. Adicionar para ler depois");
        System.out.println("0. Voltar ao menu principal");
        System.out.print("Escolha uma opção: ");
        int opcaoInteracao = scanner.nextInt();
        scanner.nextLine(); 

        int indiceNoticia;
        Noticia noticiaSelecionada;

        switch (opcaoInteracao) {
            case 1:
                System.out.print("Digite o número da notícia para ver detalhes: ");
                indiceNoticia = scanner.nextInt();
                scanner.nextLine();
                if (indiceNoticia > 0 && indiceNoticia <= listaAtual.size()) {
                    noticiaSelecionada = listaAtual.get(indiceNoticia - 1);
                    System.out.println("\n--- Detalhes da Notícia ---");
                    System.out.println("Título: " + noticiaSelecionada.getTitulo());
                    System.out.println("Introdução: " + noticiaSelecionada.getIntroducao());
                    System.out.println("Data: " + noticiaSelecionada.getDataPublicacao());
                    System.out.println("Tipo: " + noticiaSelecionada.getTipo());
                    System.out.println("Fonte: " + noticiaSelecionada.getFonte());
                    System.out.println("Link: " + noticiaSelecionada.getLink());
                } else {
                    System.out.println("Número de notícia inválido.");
                }
                break;
            case 2:
                System.out.print("Digite o número da notícia para adicionar aos favoritos: ");
                indiceNoticia = scanner.nextInt();
                scanner.nextLine();
                if (indiceNoticia > 0 && indiceNoticia <= listaAtual.size()) {
                    noticiaSelecionada = listaAtual.get(indiceNoticia - 1);
                    if (!favoritos.contains(noticiaSelecionada)) {
                        favoritos.add(noticiaSelecionada);
                        System.out.println("Notícia adicionada aos favoritos!");
                    } else {
                        System.out.println("Esta notícia já está nos favoritos.");
                    }
                } else {
                    System.out.println("Número de notícia inválido.");
                }
                break;
            case 3:
                System.out.print("Digite o número da notícia para marcar como lida: ");
                indiceNoticia = scanner.nextInt();
                scanner.nextLine();
                if (indiceNoticia > 0 && indiceNoticia <= listaAtual.size()) {
                    noticiaSelecionada = listaAtual.get(indiceNoticia - 1);
                    if (!lidas.contains(noticiaSelecionada)) {
                        lidas.add(noticiaSelecionada);
                        System.out.println("Notícia marcada como lida!");
                    } else {
                        System.out.println("Esta notícia já foi marcada como lida.");
                    }
                } else {
                    System.out.println("Número de notícia inválido.");
                }
                break;
            case 4:
                System.out.print("Digite o número da notícia para adicionar para ler depois: ");
                indiceNoticia = scanner.nextInt();
                scanner.nextLine();
                if (indiceNoticia > 0 && indiceNoticia <= listaAtual.size()) {
                    noticiaSelecionada = listaAtual.get(indiceNoticia - 1);
                    if (!paraLerDepois.contains(noticiaSelecionada)) {
                        paraLerDepois.add(noticiaSelecionada);
                        System.out.println("Notícia adicionada para ler depois!");
                    } else {
                        System.out.println("Esta notícia já está na lista 'Para ler depois'.");
                    }
                } else {
                    System.out.println("Número de notícia inválido.");
                }
                break;
            case 0:
                
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }

    private static void ordenarListasMenu(Scanner scanner, List<Noticia> lista, String nomeLista) {
        System.out.println("\n--- Ordenar " + nomeLista + " ---");
        System.out.println("1. Por Título");
        System.out.println("2. Por Data");
        System.out.println("3. Por Tipo");
        System.out.print("Escolha o critério de ordenação: ");
        int criterio = scanner.nextInt();
        scanner.nextLine();

        switch (criterio) {
            case 1:
                Ordenador.ordenarPorTitulo(lista);
                System.out.println("Lista " + nomeLista + " ordenada por título.");
                break;
            case 2:
                Ordenador.ordenarPorData(lista);
                System.out.println("Lista " + nomeLista + " ordenada por data.");
                break;
            case 3:
                Ordenador.ordenarPorTipo(lista);
                System.out.println("Lista " + nomeLista + " ordenada por tipo.");
                break;
            default:
                System.out.println("Opção inválida.");
        }
        exibirNoticias(lista); 
    }
}