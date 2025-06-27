
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects; 

public class Ordenador {
    public static void ordenarPorTitulo(List<Noticia> lista) {
        Collections.sort(lista, new Comparator<Noticia>() {
            @Override
            public int compare(Noticia n1, Noticia n2) {
                
                String titulo1 = n1.getTitulo();
                String titulo2 = n2.getTitulo();

                if (titulo1 == null && titulo2 == null) {
                    return 0;
                }
                if (titulo1 == null) {
                    return 1;
                }
                if (titulo2 == null) {
                    return -1; 
                return titulo1.compareToIgnoreCase(titulo2);
            }
        });
    }

    
    public static void ordenarPorData(List<Noticia> lista) {
        Collections.sort(lista, new Comparator<Noticia>() {
            @Override
            public int compare(Noticia n1, Noticia n2) {
                String data1 = n1.getDataPublicacao();
                String data2 = n2.getDataPublicacao();

                
                if (data1 == null && data2 == null) {
                    return 0;
                }
                
                if (data1 == null) {
                    return 1;
                }
                
                if (data2 == null) {
                    return -1; 
                }

                
                return data2.compareTo(data1);
            }
        });
    }

   
    public static void ordenarPorTipo(List<Noticia> lista) {
        Collections.sort(lista, new Comparator<Noticia>() {
            @Override
            public int compare(Noticia n1, Noticia n2) {
                
                String tipo1 = n1.getTipo();
                String tipo2 = n2.getTipo();

                if (tipo1 == null && tipo2 == null) {
                    return 0;
                }
                if (tipo1 == null) {
                    return 1; 
                }
                if (tipo2 == null) {
                    return -1; 
                }
                return tipo1.compareToIgnoreCase(tipo2);
            }
        });
    }
}