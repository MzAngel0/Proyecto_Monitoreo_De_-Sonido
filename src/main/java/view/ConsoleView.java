package view;

import model.NoiseVariable;
import model.Project;
import model.ProjectStatus;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ConsoleView {
    private final Scanner scanner;

    public ConsoleView() {
        this.scanner = new Scanner(System.in);
    }

    public int showMainMenu() {
        System.out.println("\n=== SISTEMA DE MONITOREO DE SONIDO ===");
        System.out.println("1. Registrar nuevo proyecto (Estado: Recibido)");
        System.out.println("2. Iniciar medición de proyecto (Pasar a: En Medición)");
        System.out.println("3. Registrar dato de ruido y evaluar umbral (Alerta / Normal)");
        System.out.println("4. Buscar proyecto por ID");
        System.out.println("5. Listar proyectos (Inicio -> Fin)");
        System.out.println("6. Listar proyectos (Fin -> Inicio)");
        System.out.println("7. Filtrar proyectos por estado");
        System.out.println("8. Eliminar proyecto");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");

        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String askProjectId() {
        System.out.print("Ingrese el ID del proyecto: ");
        return scanner.nextLine().trim();
    }

    public String askProjectName() {
        System.out.print("Ingrese el nombre del proyecto: ");
        return scanner.nextLine().trim();
    }

    public String askClient() {
        System.out.print("Ingrese el cliente (Alcaldía, fábrica, aeropuerto, etc.): ");
        return scanner.nextLine().trim();
    }

    public String askAddress() {
        System.out.print("Ingrese la dirección donde se realizará la medición: ");
        return scanner.nextLine().trim();
    }

    public LocalDate askStartDate() {
        return askDate("Ingrese fecha desde (AAAA-MM-DD): ");
    }

    public LocalDate askEndDate() {
        return askDate("Ingrese fecha hasta (AAAA-MM-DD): ");
    }

    private LocalDate askDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Formato inválido. Use AAAA-MM-DD (ejemplo: 2026-11-20).");
            }
        }
    }

    public NoiseVariable askNoiseVariable() {
        while (true) {
            System.out.println("Seleccione la variable de ruido:");
            System.out.println("1. LAN01 (Umbral: " + NoiseVariable.LAN01.getThreshold() + " dB)");
            System.out.println("2. LAN02 (Umbral: " + NoiseVariable.LAN02.getThreshold() + " dB)");
            System.out.println("3. LAN03 (Umbral: " + NoiseVariable.LAN03.getThreshold() + " dB)");
            System.out.print("Opción: ");
            String option = scanner.nextLine().trim();
            switch (option) {
                case "1":
                    return NoiseVariable.LAN01;
                case "2":
                    return NoiseVariable.LAN02;
                case "3":
                    return NoiseVariable.LAN03;
                default:
                    System.out.println("Variable inválida. Intente de nuevo.");
            }
        }
    }

    public double askMeasuredValue() {
        while (true) {
            System.out.print("Ingrese el valor de ruido capturado (dB): ");
            try {
                double value = Double.parseDouble(scanner.nextLine().trim());
                if (value >= 0) {
                    return value;
                }
                System.out.println("El valor de ruido no puede ser negativo.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número decimal.");
            }
        }
    }

    public ProjectStatus askProjectStatus() {
        while (true) {
            System.out.println("Seleccione el estado a consultar:");
            System.out.println("1. Recibido");
            System.out.println("2. En Medición");
            System.out.println("3. Alerta");
            System.out.println("4. Normal");
            System.out.print("Opción: ");
            String option = scanner.nextLine().trim();
            switch (option) {
                case "1":
                    return ProjectStatus.RECEIVED;
                case "2":
                    return ProjectStatus.IN_MEASUREMENT;
                case "3":
                    return ProjectStatus.ALERT;
                case "4":
                    return ProjectStatus.NORMAL;
                default:
                    System.out.println("Estado inválido. Intente de nuevo.");
            }
        }
    }

    public void showProject(Project project) {
        if (project == null) {
            System.out.println("Proyecto no encontrado.");
            return;
        }
        System.out.println("--------------------------------------------------");
        System.out.println("ID: " + project.getId() + " | Nombre: " + project.getName());
        System.out.println("Cliente: " + project.getClient() + " | Dirección: " + project.getAddress());
        System.out.println("Variable: " + project.getVariable()
                + " (Umbral: " + project.getVariable().getThreshold() + " dB)");
        System.out.println("Fechas: " + project.getStartDate() + " hasta " + project.getEndDate());
        System.out.println("Ruido capturado: " + project.getMeasuredValue() + " dB"
                + " | Estado: " + formatStatus(project.getStatus()));
        System.out.println("--------------------------------------------------");
    }

    public void showProjectList(List<Project> projects, String title) {
        System.out.println("\n=== " + title + " ===");
        if (projects == null || projects.isEmpty()) {
            System.out.println("No hay proyectos registrados para mostrar.");
            return;
        }
        for (Project p : projects) {
            showProject(p);
        }
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    private String formatStatus(ProjectStatus status) {
        switch (status) {
            case RECEIVED:
                return "Recibido";
            case IN_MEASUREMENT:
                return "En Medición";
            case ALERT:
                return "Alerta";
            case NORMAL:
                return "Normal";
            default:
                return status.name();
        }
    }
}