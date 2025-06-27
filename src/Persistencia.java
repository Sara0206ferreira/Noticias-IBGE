
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Persistencia {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    
    public static void salvar(List<Noticia> lista, String nomeArquivo) {
        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            System.err.println("Erro ao salvar a lista em " + nomeArquivo + ": " + e.getMessage());
        }
    }

    
    public static List<Noticia> carregarLista(String nomeArquivo) {
        try (FileReader reader = new FileReader(nomeArquivo)) {
            Type tipoLista = new TypeToken<ArrayList<Noticia>>() {}.getType();
            List<Noticia> lista = gson.fromJson(reader, tipoLista);
            if (lista == null) {
                return new ArrayList<>();
            }
            return lista;
        } catch (IOException e) {
            return new ArrayList<>(); 
        }
    }

    
    public static Usuario carregarUsuario(String nomeArquivo) {
        try (FileReader reader = new FileReader(nomeArquivo)) {
            Usuario usuario = gson.fromJson(reader, Usuario.class);
            if (usuario == null) {
                return new Usuario(""); 
            }
            return usuario;
        } catch (IOException e) {
            return new Usuario(""); 
        }
    }

   
    public static void salvarUsuario(Usuario usuario, String nomeArquivo) {
        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            gson.toJson(usuario, writer);
        } catch (IOException e) {
            System.err.println("Erro ao salvar usuário em " + nomeArquivo + ": " + e.getMessage());
        }
    }
}