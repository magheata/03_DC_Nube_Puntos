/**
 * @authors Miruna Andreea Gheata, Rafael Adrián Gil Cañestro
 */
package Domain;

import java.awt.*;

/**
 * Clase que contiene todas las variables empleadas en el proyecto
 */
public class Variables {
    public static final String TEXTO_ERROR = "Error";
    public static final String TEXTO_SOLUCION = "Solución";

    public static final String BTN_START_EMPEZAR = "Empezar ejecución";
    public static final String BTN_START_DETENER = "Detener ejecución";
    public static final String BTN_RANDOM_TEXT = "Distribución Aleatoria";
    public static final String BTN_GAUSSIAN_TEXT = "Distribución Gaussiana";

    public static final String[] nombresBotonesSorter = {"Collections.sort()", "Quicksort", "Mergesort", "Bucketsort"};
    public static final String[] nombresBotonesAlgoritmo = {"O(n^2)", "O((n·logn)^2)", "O(n·logn)"};
    public static  String[] pathsSort = {"Infrastructure.SortingAlgorithms.Javasort", "Infrastructure.SortingAlgorithms.Quicksort",
            "Infrastructure.SortingAlgorithms.Mergesort", "Infrastructure.SortingAlgorithms.Bucketsort"};

    public static final String LABEL_GRAPH = "DEFINICIÓN DE LOS DATOS";
    public static final String LABEL_PUNTOS_TOTALES = "Total puntos: ";
    public static final String LABEL_SORTER = "ALGORITMO DE ORDENACIÓN";
    public static final String LABEL_ALGORITMO = "COMPLEJIDAD DEL ALGORITMO";
    public static final String LABEL_INCREMENTAR = "+";
    public static final String LABEL_DECREMENTAR = "-";
    public static final String LABEL_MEAN = "μ: ";
    public static final String LABEL_VARIANCE = "σ: ";


    public static final Color colorPuntos = new Color(0.5f,0f,1f,.25f );
    public static final Color colorPuntosSolucion = new Color(0f,1f,0f);

    public static final int MEAN_MAX = 5;
    public static final double VARIANCE_MAX = 2.75;
    public static final double VARIANCE_MIN = 0;
    public static final int PUNTOS_MAX = 100000;
    public static final int PUNTOS_MIN = 10;
    public static final int WIDTH_PANELPUNTOS = 560;
    public static final int HEIGHT_PANELPUNTOS = 610;
    public static final int WIDTH_PANELCOORDENADAS = 560;
    public static final int HEIGHT_PANELCOORDENADAS = 560;
    public static final int WIDTH_GRAFICO_DENSIDAD = 450;
    public static final int HEIGHT_GRAFICO_DENSIDAD = 150;
    public static final int COORDENADAS_BORDER_GAP = 30;
    public static final int COORDENADAS_SIZE_GRAPH = 500;

    public static final int xMinPlotSettings = -5;
    public static final int xMaxPlotSettings = 5;
    public static final int yMinPlotSettings = 0;
    public static final int yMaxPlotSettings = 1;
}
