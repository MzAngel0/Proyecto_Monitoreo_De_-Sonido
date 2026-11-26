package model;

public enum NoiseVariable {
    LAN01(70.0), LAN02(65.0), LAN03(60.0);

    private final double threshold; //Umbral

    NoiseVariable(double threshold) {
        this.threshold = threshold;
    }

    public double getThreshold() {
        return threshold;
    }
}
