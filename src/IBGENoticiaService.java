import java.util.ArrayList;
import java.util.List;

public class IBGENoticiaService {
    private List<Noticia> noticias;

    public IBGENoticiaService() {
        this.noticias = new ArrayList<>();
        carregarNoticiasFake();
    }

    private void carregarNoticiasFake() {
        noticias.add(new Noticia(
                "IBGE divulga PIB", 
                "PIB cresceu 1,2%", 
                "2025-06-20",
                "https://ibge.gov.br/pib", 
                "Economia", 
                "IBGE"));

        noticias.add(new Noticia(
                "Censo 2025", 
                "Censo demográfico avança", 
                "2025-06-18",
                "https://ibge.gov.br/censo", 
                "População", 
                "IBGE"));
    }

    public List<Noticia> buscarPorTitulo(String termo) {
        List<Noticia> resultado = new ArrayList<>();
        for (Noticia n : noticias) {
            if (n.getTitulo().toLowerCase().contains(termo.toLowerCase())) {
                resultado.add(n);
            }
        }
        return resultado;
    }

    public List<Noticia> buscarPorData(String data) {
        List<Noticia> resultado = new ArrayList<>();
        for (Noticia n : noticias) {
            if (n.getDataPublicacao().equals(data)) {
                resultado.add(n);
            }
        }
        return resultado;
    }

    public List<Noticia> buscarTodasNoticias() {
        return noticias;
    }
}
