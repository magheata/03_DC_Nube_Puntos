/**
 * @authors Miruna Andreea Gheata, Rafael Adrián Gil Cañestro
 */
package Application;

import Domain.DTO.DistanciaMinima;
import Domain.Interfaces.IController;
import Domain.Nube;
import Domain.Punto;
import Domain.Variables;
import Infrastructure.PuntosService;
import Infrastructure.SortingTypes;
import Presentation.Graph.GraphPanel;
import Presentation.PanelControl;
import Presentation.PanelCoordenadas;
import Presentation.PanelPuntos;
import Presentation.Window;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/***
 * Controlador del programa que se encarga de realizar la comuniación entre los distintos elementos del proyecto
 */
public class DCController implements IController {

    private GraphPanel graphPanel;
    private PanelControl panelControl;
    private PuntosService puntosService;
    private Window window;
    private PanelCoordenadas panelCoordenadas;
    private Nube nube;
    private PanelPuntos panelPuntos;

    private static  String[] pathsSort = Variables.pathsSort;

    private int algoritmoElegido;
    private int sorterElegido;
    private int totalPuntos = 1000;
    private double mediaPuntos;
    private double varianzaPuntos;
    private boolean parametrosSeteados;
    private DistanciaMinima distanciaMinima;
    private boolean isGaussianDistribution;
    private boolean puntosCambiados = true;
    private boolean nubePuntosCreada = false;
    private double maxX;
    private double maxY;

    public DCController(){
        puntosService = new PuntosService(this);
    }

    /**
     * Actualiza el gráfico de densidad (en la distribución Gaussiana)
     * @param mean
     * @param stdDeviation
     */
    @Override
    public void updateGraph(double mean, double stdDeviation) {
        graphPanel.updateGraphWithNewValues(mean, stdDeviation);
        puntosCambiados = true;
        nubePuntosCreada = false;
    }

    /**
     * Se cambia el gráfico para seguir una distribución aleatoria
     */
    @Override
    public void disableGaussianElements() {
        parametrosSeteados = false;
        panelCoordenadas.setPintarPuntos(false);
        puntosCambiados = true;
        nubePuntosCreada = false;
        isGaussianDistribution = false;
        panelControl.disableGaussianElements();
    }

    /**
     * Se cambia el gráfico para seguir una distribución gaussiana
     */
    @Override
    public void enableGaussianElements() {
        parametrosSeteados = false;
        panelCoordenadas.setPintarPuntos(false);
        puntosCambiados = true;
        nubePuntosCreada = false;
        isGaussianDistribution = true;
        panelControl.enableGaussianElements();
    }

    /**
     * Se habilitan los botones para seleccionar el algoritmo de ordenación
     */
    @Override
    public void habilitarBotonesSorter() {
        panelControl.enableBotonesSorter();
    }

    /**
     * Se deshabilitan los botones para seleccionar el algoritmo de ordenación
     */
    @Override
    public void deshabilitarBotonesSorter() {
        panelControl.disableBotonesSorter();
    }

    /**
     * Crea la nube de puntos según la distribución que se haya elegido
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Override
    public void crearNubePunto() throws ExecutionException, InterruptedException {
        nube = new Nube(totalPuntos);
        Future<Nube> future;
        if (isGaussianDistribution){
            future = nube.crearNubePuntos(totalPuntos, mediaPuntos, varianzaPuntos);
        } else {
            future = nube.crearNubePuntosRandom(0, totalPuntos);
        }
        while (!future.isDone()){}
        puntosService.setNubePuntos(future.get());
        puntosCambiados = false;
    }

    /**
     * Arrancamos el hilo encargado de realizar la búsqueda de los puntos más cercanos
     */
    @Override
    public void start() {
        puntosService.start();
    }

    /**
     * Se inicializan los puntos en caso de que no estén creados o que los puntos hayan sido modificados (porque
     * el número total de puntos se ha modificado, la media/desviación es diferente o la distribución es distinta)
     */
    @Override
    public void inicializarPuntos() {
        if (!nubePuntosCreada || puntosCambiados){
            try {
                crearNubePunto();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            nubePuntosCreada = true;
        }
    }

    /**
     * Se pintan los puntos en el grafo
     */
    @Override
    public void pintarPuntos() {
        // Obtenemos el máximo de las coordenadas para poder pintar los puntos escalados
        if (Math.abs(nube.getMinX()) < nube.getMaxX()){
            maxX = nube.getMaxX();
        } else {
            maxX = Math.abs(nube.getMinX());
        }

        if (Math.abs(nube.getMinY()) < nube.getMaxY()){
            maxY = nube.getMaxY();
        } else {
            maxY = Math.abs(nube.getMinY());
        }
        panelCoordenadas.setPintarPuntos(true);
        // Repintamos panel de puntos para que aparezcan
        panelPuntos.repaint();
    }

    /**
     * Método que una vez se haya encontrado la distancia mínima entre los puntos muestra la solución
     * @param distanciaMinima
     */
    @Override
    public void setPuntoSolucion(DistanciaMinima distanciaMinima) {
        this.distanciaMinima = distanciaMinima;
        // Se marcan los puntos como solución para que se pinten
        distanciaMinima.setPuntosSolucion();
        // Repintamos el panel con los puntos
        panelPuntos.repaint();
        // Se notifica al usuario de la solución encontrada
        window.UserMsg(distanciaMinima.toString(), false);
        panelPuntos.setTextStartButton("Empezar ejecución");
        parametrosSeteados = false;
    }

    /**
     * Método que recoge los parámetros (algoritmo de ordenación elegido, complejidad elegida) y los setea en los
     * lugares pertinentes
     */
    @Override
    public void setearParametrosElegidos(){
        //Si no se ha seleccionado ningún algoritmo se muestra un error
        if (panelControl.getAlgoritmoElegido() == -1){
            window.UserMsg("Debe seleccionar la complejidad del algoritmo", true);
        /* Si se ha seleccionado un algoritmo distinto al Naive y no se ha seleccionado un algoritmo de ordenación
        para ordenar los puntos, se muestra un error*/
        } else if (panelControl.getAlgoritmoElegido() > 0 && panelControl.getSorterElegido() == -1){
            window.UserMsg("Debe seleccionar el algoritmo de ordenación", true);
        // En caso contrario se setean los parámetros
        } else {
            setAlgoritmoElegido(panelControl.getAlgoritmoElegido());
            setSorterElegido(panelControl.getSorterElegido());
            puntosService.setAlgoritmoElegido(algoritmoElegido);
            if (algoritmoElegido != SortingTypes.Javasort.ordinal()){
                puntosService.setClaseSort(pathsSort[sorterElegido]);
            }
            parametrosSeteados = true;
        }
    }

    //region SETTERS Y GETTERS
    public void setMediaPuntos(double mediaPuntos) { this.mediaPuntos = mediaPuntos; }

    public void setVarianzaPuntos(double varianzaPuntos) { this.varianzaPuntos = varianzaPuntos; }

    public void setTotalPuntos(int totalPuntos) {
        this.totalPuntos = totalPuntos;
        nubePuntosCreada = false;
    }

    public Punto[] getPuntosNube(){ return nube.getPuntos(); }

    public void setPanelCoordenadas(PanelCoordenadas panelCoordenadas) { this.panelCoordenadas = panelCoordenadas; }

    public void barraGo(){panelControl.barraGo();}

    public void barraStop(){panelControl.barraStop();}

    public void setGraphPanel(GraphPanel graphPanel) { this.graphPanel = graphPanel; }

    public void setPanelControl(PanelControl panelControl) { this.panelControl = panelControl; }
    public void barraAct(){panelControl.barraAct();}
    public void setMax(int v){ panelControl.setmax(v);}

    public void setAlgoritmoElegido(int algoritmoElegido) { this.algoritmoElegido = algoritmoElegido; }

    public void setSorterElegido(int sorterElegido) { this.sorterElegido = sorterElegido; }

    public double getMaxX() { return maxX; }

    public double getMaxY() { return maxY; }

    public void setPanelPuntos(PanelPuntos panelPuntos) { this.panelPuntos = panelPuntos; }

    public void setWindow(Window window) { this.window = window; }

    public boolean isParametrosSeteados() { return parametrosSeteados; }

    public boolean isGaussianDistribution() { return isGaussianDistribution; }
    //endregion
}
