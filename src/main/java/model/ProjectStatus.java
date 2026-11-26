package model;

/**
 * Estados posibles de un proyecto de medición de ruido.
 * La clase Project guarda uno de estos valores en su atributo "status",
 * y solo sus métodos (startMeasurement y registerMeasurement) lo cambian.
 */

public enum ProjectStatus {

    RECEIVED, /** Estado inicial o sea el proyecto fue recibido y aún no tiene medición. */

    IN_MEASUREMENT, /** La medición ya comenzó y se espera el valor medido. */

    ALERT, /** El valor medido superó el umbral de su variable de ruido. */

    NORMAL; /** El valor medido no superó el umbral de su variable de ruido. */
}

