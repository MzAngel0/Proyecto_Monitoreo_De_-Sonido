package persistence;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import model.Project;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Guarda y carga la lista plana de proyectos en un archivo JSON. */
public class ProjectRepository {

    private final Path file;
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class,
                    (JsonSerializer<LocalDate>) (date, type, ctx) -> new JsonPrimitive(date.toString()))
            .registerTypeAdapter(LocalDate.class,
                    (JsonDeserializer<LocalDate>) (json, type, ctx) -> LocalDate.parse(json.getAsString()))
            .create();

    public ProjectRepository(Path file) {
        this.file = file;
    }

    public void save(List<Project> projects) throws IOException {
        Files.writeString(file, gson.toJson(projects));
    }

    public List<Project> load() throws IOException {
        if (!Files.exists(file)) {
            return new ArrayList<>();
        }
        Type listType = new TypeToken<List<Project>>() {}.getType();
        List<Project> projects = gson.fromJson(Files.readString(file), listType);
        return projects != null ? projects : new ArrayList<>();
    }
}
