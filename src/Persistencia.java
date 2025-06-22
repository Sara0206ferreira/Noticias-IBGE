import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Persistencia {
    private static final Gson gson = new Gson();

    public static void salvar(List<Noticia> lista, String arquivo) {
        try (FileWriter writer = new FileWriter(arquivo)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivo " + arquivo + ": " + e.getMessage());
        }
    }

    public static List<Noticia> carregarLista(String arquivo) {
        List<Noticia> lista = new ArrayList<>();
        try (FileReader reader = new FileReader(arquivo)) {
            Type listType = new TypeToken<List<Noticia>>() {}.getType();
            lista = gson.fromJson(reader, listType);
        } catch (IOException e) {
            System.out.println("Arquivo " + arquivo + " não encontrado. Criando vazio.");
        }
        return lista;
    }
}