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

    public String getTitulo() { return titulo; }
    public String getIntroducao() { return introducao; }
    public String getDataPublicacao() { return dataPublicacao; }
    public String getLink() { return link; }
    public String getTipo() { return tipo; }
    public String getFonte() { return fonte; }

    @Override
    public String toString() {
        return "Título: " + titulo + "\nIntrodução: " + introducao + "\nData: " + dataPublicacao +
               "\nLink: " + link + "\nTipo: " + tipo + "\nFonte: " + fonte + "\n";
    }
}

