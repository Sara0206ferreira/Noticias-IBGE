
import java.util.Objects;

public class Noticia {
    private String titulo;
    private String introducao;
    private String dataPublicacao; 
    private String link;
    private String tipo;
    private String fonte;

   
    public Noticia(String titulo, String introducao, String dataPublicacao, String link, String tipo, String fonte) {
        this.titulo = titulo;
        this.introducao = introducao;
        this.dataPublicacao = dataPublicacao;
        this.link = link;
        this.tipo = tipo;
        this.fonte = fonte;
    }

   
    public String getTitulo() {
        return titulo;
    }

    public String getIntroducao() {
        return introducao;
    }

    public String getDataPublicacao() {
        return dataPublicacao;
    }

    public String getLink() {
        return link;
    }

    public String getTipo() {
        return tipo;
    }

    public String getFonte() {
        return fonte;
    }

    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setIntroducao(String introducao) {
        this.introducao = introducao;
    }

    public void setDataPublicacao(String dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setFonte(String fonte) {
        this.fonte = fonte;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Noticia noticia = (Noticia) o;
        return Objects.equals(link, noticia.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link);
    }

    @Override
    public String toString() {
        return "Noticia{" +
               "titulo='" + titulo + '\'' +
               ", dataPublicacao='" + dataPublicacao + '\'' +
               ", link='" + link + '\'' +
               '}';
    }
}   