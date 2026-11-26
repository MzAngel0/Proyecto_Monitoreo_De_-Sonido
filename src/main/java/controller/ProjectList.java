package controller;

import model.Node;
import model.Project;
import model.ProjectStatus;

import java.util.ArrayList;
import java.util.List;

public class ProjectList extends DoublyLinkedList<Project> {

    /**
     * Agrega un proyecto si su ID no está repetido.
     * El constructor de Project ya lo inicia en RECEIVED y measuredValue = 0.
     */
    public boolean addProject(Project project) {
        if (project == null || findById(project.getId()) != null) {
            return false;
        }
        addLast(project);
        return true;
    }

    public Project findById(String id) {
        Node<Project> current = head;
        while (current != null) {
            if (current.getInfo().getId().equalsIgnoreCase(id)) {
                return current.getInfo();
            }
            current = current.getNext();
        }
        return null;
    }

    public void startMeasurement(String id) {
        Project project = findById(id);
        if (project == null) {
            throw new IllegalArgumentException("No existe un proyecto con el ID: " + id);
        }
        project.startMeasurement();
    }

    public void registerMeasurement(String id, double value) {
        Project project = findById(id);
        if (project == null) {
            throw new IllegalArgumentException("No existe un proyecto con el ID: " + id);
        }
        project.registerMeasurement(value);
    }

    public boolean deleteById(String id) {
        Node<Project> current = head;
        while (current != null) {
            if (current.getInfo().getId().equalsIgnoreCase(id)) {
                removeNode(current);
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    public List<Project> getByStatus(ProjectStatus status) {
        List<Project> filtered = new ArrayList<>();
        Node<Project> current = head;
        while (current != null) {
            if (current.getInfo().getStatus() == status) {
                filtered.add(current.getInfo());
            }
            current = current.getNext();
        }
        return filtered;
    }
}