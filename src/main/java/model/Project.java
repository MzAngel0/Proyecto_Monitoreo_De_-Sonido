package model;

import java.time.LocalDate;

public class Project {
    private final String id;
    private String name;
    private String client;
    private String address;
    private NoiseVariable variable;
    private LocalDate startDate;
    private LocalDate endDate;
    private double measuredValue;
    private ProjectStatus status;

    public Project(String id, String name, String client, String address,
                   NoiseVariable variable, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.name = name;
        this.client = client;
        this.address = address;
        this.variable = variable;
        this.startDate = startDate;
        this.endDate = endDate;
        this.measuredValue = 0;
        this.status = ProjectStatus.RECEIVED;
    }

    public void startMeasurement(){
        if(status != ProjectStatus.RECEIVED){
            throw new IllegalStateException("The Project is not in RECEIVED state!");
        }
        status = ProjectStatus.IN_MEASUREMENT;
    }

    public void registerMeasurement(double value){
        if (status != ProjectStatus.IN_MEASUREMENT) {
            throw new IllegalStateException("Project is not in IN_MEASUREMENT state");
        }
        measuredValue = value;
        status = (value > variable.getThreshold())
                ? ProjectStatus.ALERT
                : ProjectStatus.NORMAL;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getClient() {
        return client;
    }

    public String getAddress() {
        return address;
    }

    public NoiseVariable getVariable() {
        return variable;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getMeasuredValue() {
        return measuredValue;
    }

    public ProjectStatus getStatus() {
        return status;
    }
}
