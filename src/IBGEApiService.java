
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class IBGEApiService {
    private static final String API_BASE_URL = "https://servicodados.ibge.gov.br/api/v3/noticias/";

   
    public static List<Noticia> buscarNoticias(String palavraChave) {
        List<Noticia> noticias = new ArrayList<>();
        HttpURLConnection conexao = null;

        try {
            String urlCompleta;

            if (palavraChave != null && !palavraChave.trim().isEmpty()) {
                String palavraChaveCodificada = URLEncoder.encode(palavraChave, StandardCharsets.UTF_8.toString());
                urlCompleta = API_BASE_URL + "?busca=" + palavraChaveCodificada;
                System.out.println("Buscando por palavra-chave: " + urlCompleta);
            } else {
                urlCompleta = API_BASE_URL + "?qtd=10"; 
                System.out.println("Buscando notícias recentes: " + urlCompleta);
            }

            URL url = new URL(urlCompleta);
            conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("GET");

            int responseCode = conexao.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
                StringBuilder resposta = new StringBuilder();
                String linha;

                while ((linha = reader.readLine()) != null) {
                    resposta.append(linha);
                }
                reader.close();

                Gson gson = new Gson();
                JsonElement jsonElement = gson.fromJson(resposta.toString(), JsonElement.class);

                JsonArray arrayNoticias = null;

               
                if (jsonElement.isJsonArray()) {
                    arrayNoticias = jsonElement.getAsJsonArray();
                } else if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    if (jsonObject.has("items")) {
                        arrayNoticias = jsonObject.getAsJsonArray("items");
                    } else if (jsonObject.has("noticias")) {
                        arrayNoticias = jsonObject.getAsJsonArray("noticias");
                    }
                }

                if (arrayNoticias != null) {
                    for (int i = 0; i < arrayNoticias.size(); i++) {
                        JsonObject obj = arrayNoticias.get(i).getAsJsonObject();
                        Noticia noticia = new Noticia(
                                obj.has("titulo") ? obj.get("titulo").getAsString() : "Sem Título",
                                obj.has("introducao") ? obj.get("introducao").getAsString() : "Sem Introdução",
                                obj.has("data_publicacao") ? obj.get("data_publicacao").getAsString() : "Data Indisponível",
                                obj.has("link") ? obj.get("link").getAsString() : "Link Indisponível",
                                obj.has("tipo") ? obj.get("tipo").getAsString() : "Tipo Indisponível",
                                obj.has("fonte") ? obj.get("fonte").getAsString() : "Fonte Indisponível"
                        );
                        noticias.add(noticia);
                    }
                } else {
                    System.out.println("Formato de resposta da API do IBGE inesperado ou sem array de notícias.");
                }

            } else {
                System.out.println("Erro na requisição à API do IBGE. Código de resposta: " + responseCode);
            }

        } catch (JsonParseException e) {
            System.out.println("Erro de parsing JSON ao buscar notícias da API do IBGE: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao buscar notícias da API do IBGE: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conexao != null) {
                conexao.disconnect();
            }
        }

        return noticias;
    }

  
    public static List<Noticia> buscarNoticiasPorData(String dataBusca) {
        List<Noticia> noticias = new ArrayList<>();
        HttpURLConnection conexao = null;

        try {
            String dataCodificada = URLEncoder.encode(dataBusca, StandardCharsets.UTF_8.toString());
            String urlCompleta = API_BASE_URL + "?data_publicacao=" + dataCodificada;
            System.out.println("Buscando por data: " + urlCompleta);

            URL url = new URL(urlCompleta);
            conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("GET");

            int responseCode = conexao.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
                StringBuilder resposta = new StringBuilder();
                String linha;

                while ((linha = reader.readLine()) != null) {
                    resposta.append(linha);
                }
                reader.close();

                Gson gson = new Gson();
                JsonElement jsonElement = gson.fromJson(resposta.toString(), JsonElement.class);

                JsonArray arrayNoticias = null;

                if (jsonElement.isJsonArray()) {
                    arrayNoticias = jsonElement.getAsJsonArray();
                } else if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    if (jsonObject.has("items")) {
                        arrayNoticias = jsonObject.getAsJsonArray("items");
                    } else if (jsonObject.has("noticias")) {
                        arrayNoticias = jsonObject.getAsJsonArray("noticias");
                    }
                }

                if (arrayNoticias != null) {
                    for (int i = 0; i < arrayNoticias.size(); i++) {
                        JsonObject obj = arrayNoticias.get(i).getAsJsonObject();
                        Noticia noticia = new Noticia(
                                obj.has("titulo") ? obj.get("titulo").getAsString() : "Sem Título",
                                obj.has("introducao") ? obj.get("introducao").getAsString() : "Sem Introdução",
                                obj.has("data_publicacao") ? obj.get("data_publicacao").getAsString() : "Data Indisponível",
                                obj.has("link") ? obj.get("link").getAsString() : "Link Indisponível",
                                obj.has("tipo") ? obj.get("tipo").getAsString() : "Tipo Indisponível",
                                obj.has("fonte") ? obj.get("fonte").getAsString() : "Fonte Indisponível"
                        );
                        noticias.add(noticia);
                    }
                } else {
                    System.out.println("Formato de resposta da API do IBGE inesperado ou sem array de notícias para a busca por data.");
                }

            } else {
                System.out.println("Erro na requisição à API do IBGE por data. Código de resposta: " + responseCode);
            }

        } catch (JsonParseException e) {
            System.out.println("Erro de parsing JSON ao buscar notícias por data da API do IBGE: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao buscar notícias por data da API do IBGE: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conexao != null) {
                conexao.disconnect();
            }
        }
        return noticias;
    }
}