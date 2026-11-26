package controller;

import model.NoiseVariable;
import model.Project;
import model.ProjectStatus;
import persistence.ProjectRepository;
import view.ConsoleView;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/*
 * Conecta la vista con la lista de proyectos.
 * Decide qué hacer según la opción del menú. No lee ni imprime nada por sí mismo:
 * todo lo que se pregunta o se muestra pasa por ConsoleView.
 */
public class ProjectController {

    private final ProjectList projects = new ProjectList();
    private final ConsoleView view;
    private final ProjectRepository repository;

    public ProjectController(ConsoleView view, ProjectRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    /** Ciclo principal: muestra el menú y ejecuta la opción elegida hasta que el usuario sale. */
    public void run() {
        loadProjects();   // al arrancar: del archivo JSON a la lista ligada
        int option;
        do {
            option = view.showMainMenu();
            switch (option) {
                case 1 -> createProject();
                case 2 -> startMeasurement();
                case 3 -> registerMeasurement();
                case 4 -> searchProject();
                case 5 -> view.showProjectList(projects.getAllForward(), "Proyectos (Inicio -> Fin)");
                case 6 -> view.showProjectList(projects.getAllBackward(), "Proyectos (Fin -> Inicio)");
                case 7 -> filterByStatus();
                case 8 -> deleteProject();
                case 0 -> {
                    saveProjects();   // al salir: de la lista ligada al archivo JSON
                    view.showMessage("Hasta luego");
                }
                default -> view.showMessage("Opción inválida");
            }
        } while (option != 0);
    }

    /** Opción 1: pide los datos, valida las reglas del negocio y agrega el proyecto. */
    private void createProject() {
        String id = view.askProjectId();
        if (id.isEmpty()) {
            view.showMessage("El ID no puede estar vacío");
            return;
        }
        // Se revisa el ID antes de pedir el resto de datos.
        if (projects.findById(id) != null) {
            view.showMessage("Ese ID ya existe");
            return;
        }

        String name = view.askProjectName();
        String client = view.askClient();
        String address = view.askAddress();
        NoiseVariable variable = view.askNoiseVariable();
        LocalDate start = view.askStartDate();
        LocalDate end = view.askEndDate();

        // La fecha hasta no puede ser anterior a la fecha desde: se vuelve a preguntar.
        while (end.isBefore(start)) {
            view.showMessage("La fecha hasta no puede ser anterior a la fecha desde");
            end = view.askEndDate();
        }

        boolean added = projects.addProject(new Project(id, name, client, address, variable, start, end));
        view.showMessage(added ? "Proyecto creado" : "Ese ID ya existe");
    }

    /** Opción 2: pasa el proyecto de Recibido a En Medición. */
    private void startMeasurement() {
        String id = view.askProjectId();
        try {
            projects.startMeasurement(id);
            view.showProject(projects.findById(id));
        } catch (IllegalArgumentException e) {
            // ProjectList lanza esta excepción cuando el ID no existe.
            view.showMessage(e.getMessage());
        } catch (IllegalStateException e) {
            // Project lanza esta excepción cuando el estado no es RECEIVED.
            view.showMessage("Solo se puede iniciar la medición de un proyecto en estado Recibido");
        }
    }

    /** Opción 3: registra el dato de ruido; el modelo decide si queda en Alerta o Normal. */
    private void registerMeasurement() {
        String id = view.askProjectId();
        Project project = projects.findById(id);
        if (project == null) {
            view.showMessage("No existe un proyecto con el ID: " + id);
            return;
        }

        // Se revisa el estado antes de pedir el valor, para no hacer escribir un dato inútil.
        if (project.getStatus() == ProjectStatus.RECEIVED) {
            view.showMessage("Primero debe iniciar la medición (opción 2)");
            return;
        }
        if (project.getStatus() != ProjectStatus.IN_MEASUREMENT) {
            view.showMessage("A este proyecto ya se le registró un dato de ruido");
            return;
        }

        double value = view.askMeasuredValue();
        projects.registerMeasurement(id, value);
        view.showProject(project);
    }

    /** Opción 4: busca un proyecto por ID (la vista avisa si no existe). */
    private void searchProject() {
        view.showProject(projects.findById(view.askProjectId()));
    }

    /** Opción 7: muestra solo los proyectos que están en el estado elegido. */
    private void filterByStatus() {
        ProjectStatus status = view.askProjectStatus();
        List<Project> filtered = projects.getByStatus(status);
        view.showProjectList(filtered, "Proyectos filtrados por estado");
    }

    /** Opción 8: elimina un proyecto por ID. */
    private void deleteProject() {
        String id = view.askProjectId();
        boolean deleted = projects.deleteById(id);
        view.showMessage(deleted ? "Proyecto eliminado" : "No existe un proyecto con el ID: " + id);
    }

    /** Carga los proyectos del archivo JSON a la lista ligada al iniciar. */
    private void loadProjects() {
        try {
            for (Project project : repository.load()) {
                projects.addProject(project);
            }
        } catch (IOException e) {
            view.showMessage("No se pudieron cargar los proyectos: " + e.getMessage());
        }
    }

    /** Guarda la lista ligada en el archivo JSON (primero se convierte a una List plana). */
    private void saveProjects() {
        try {
            repository.save(projects.getAllForward());
        } catch (IOException e) {
            view.showMessage("No se pudieron guardar los proyectos: " + e.getMessage());
        }
    }
}