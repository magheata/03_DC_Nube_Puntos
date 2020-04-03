/* Created by andreea on 22/03/2020 */
package Application;

import Domain.Interfaces.IController;
import Domain.Nube;
import Domain.Punto;
import Infrastructure.PuntosService;
import Infrastructure.SortingTypes;
import Presentation.Graph.GraphPanel;
import Presentation.PanelControl;
import Presentation.PanelPuntos;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class DCController implements IController {

    private GraphPanel graphPanel;
    private PanelControl panelControl;
    private PuntosService puntosService;
    private Nube nube;

    public void setPanelPuntos(PanelPuntos panelPuntos) {
        this.panelPuntos = panelPuntos;
    }

    private PanelPuntos panelPuntos;

    private int algoritmoElegido;
    private int sorterElegido;
    private int totalPuntos;
    private double mediaPuntos;
    private double varianzaPuntos;
    private boolean isGaussianDistribution;
    private boolean puntosCambiados = true;
    private boolean nubePuntosCreada = false;

    private static  String[] pathsSort = {"Infrastructure.SortingAlgorithms.Javasort", "Infrastructure.SortingAlgorithms.Quicksort",
            "Infrastructure.SortingAlgorithms.Mergesort", "Infrastructure.SortingAlgorithms.Bucketsort"};

    public DCController(){
        mediaPuntos = 0;
        varianzaPuntos = 1;
        totalPuntos = 100;
        isGaussianDistribution = false;
        puntosService = new PuntosService();
    }

    @Override
    public void updateGraph(double mean, double stdDeviation) {
        graphPanel.updateGraphWithNewValues(mean, stdDeviation);
        puntosCambiados = true;
        nubePuntosCreada = false;
    }

    @Override
    public void disableGaussianElements() {
        puntosCambiados = true;
        nubePuntosCreada = false;
        isGaussianDistribution = false;
        panelControl.disableGaussianElements();
    }

    @Override
    public void enableGaussianElements() {
        puntosCambiados = true;
        nubePuntosCreada = false;
        isGaussianDistribution = true;
        panelControl.enableGaussianElements();
    }

    @Override
    public void habilitarBotonesSorter() {
        panelControl.enableBotonesSorter();
    }

    @Override
    public void deshabilitarBotonesSorter() {
        panelControl.disableBotonesSorter();
    }

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

    @Override
    public void start() {
        puntosService.start();
    }

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

    @Override
    public void pintarPuntos() {
        if(panelPuntos.getTam()!=0) {
            panelPuntos.quitarPuntos();
        }
        double maxX = 0;
        if (Math.abs(nube.getMinX()) < nube.getMaxX()){
            maxX = nube.getMaxX();
        } else {
            maxX = Math.abs(nube.getMinX());
        }

        double maxY = 0;
        if (Math.abs(nube.getMinY()) < nube.getMaxY()){
            maxY = nube.getMaxY();
        } else {
            maxY = Math.abs(nube.getMinY());
        }
        panelPuntos.addPuntos(getPuntosNube(), maxX, maxY);
    }

    public void setGraphPanel(GraphPanel graphPanel) {
        this.graphPanel = graphPanel;
    }

    public void setPanelControl(PanelControl panelControl) {
        this.panelControl = panelControl;
    }

    public void setAlgoritmoElegido(int algoritmoElegido) {
        this.algoritmoElegido = algoritmoElegido;
    }

    public void setSorterElegido(int sorterElegido) {
        this.sorterElegido = sorterElegido;
    }

    public void setearParametrosElegidos(){
        setAlgoritmoElegido(panelControl.getAlgoritmoElegido());
        setSorterElegido(panelControl.getSorterElegido());
        puntosService.setAlgoritmoElegido(algoritmoElegido);
        if (algoritmoElegido != SortingTypes.Javasort.ordinal()){
            puntosService.setClaseSort(pathsSort[sorterElegido]);
        }
    }

    public void setMediaPuntos(double mediaPuntos) {
        this.mediaPuntos = mediaPuntos;
    }

    public void setVarianzaPuntos(double varianzaPuntos) {
        this.varianzaPuntos = varianzaPuntos;
    }

    public void setTotalPuntos(int totalPuntos) {
        this.totalPuntos = totalPuntos;
        nubePuntosCreada = false;
    }

    public Punto[] getPuntosNube(){
        return nube.getPuntos();
    }
}
