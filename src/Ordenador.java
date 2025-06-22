import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Ordenador {
    public static void ordenarPorTitulo(List<Noticia> lista) {
        Collections.sort(lista, Comparator.comparing(Noticia::getTitulo));
    }

    public static void ordenarPorData(List<Noticia> lista) {
        Collections.sort(lista, Comparator.comparing(Noticia::getDataPublicacao));
    }

    public static void ordenarPorTipo(List<Noticia> lista) {
        Collections.sort(lista, Comparator.comparing(Noticia::getTipo));
    }
}
