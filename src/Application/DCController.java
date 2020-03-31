/* Created by andreea on 22/03/2020 */
package Application;

import Domain.Interfaces.IController;
import Domain.Nube;
import Infrastructure.PuntosService;
import Presentation.Graph.GraphPanel;
import Presentation.PanelControl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class DCController implements IController {

    private GraphPanel graphPanel;
    private PanelControl panelControl;
    private PuntosService puntosService;
    private Nube nube;

    private int algoritmoElegido;
    private int sorterElegido;
    private int totalPuntos;
    private double mediaPuntos;
    private double varianzaPuntos;
    private boolean isGaussianDistribution;
    private boolean puntosCambiados = true;

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
    }

    @Override
    public void disableGaussianElements() {
        puntosCambiados = true;
        panelControl.disableGaussianElements();
    }

    @Override
    public void enableGaussianElements() {
        puntosCambiados = true;
        panelControl.enableGaussianElements();
    }

    @Override
    public void habilitarBotonesSorter() {
        panelControl.enableBotonesSorter();
        isGaussianDistribution = true;
    }

    @Override
    public void deshabilitarBotonesSorter() {
        panelControl.disableBotonesSorter();
        isGaussianDistribution = false;
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
    }

    @Override
    public void start() {
        if (puntosCambiados){
            try {
                crearNubePunto();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        puntosService.start();
        puntosCambiados = false;
    }

    public void setGraphPanel(GraphPanel graphPanel) {
        this.graphPanel = graphPanel;
    }

    public void setPanelControl(PanelControl panelControl) {
        this.panelControl = panelControl;
    }

    public int getAlgoritmoElegido() {
        return algoritmoElegido;
    }

    public void setAlgoritmoElegido(int algoritmoElegido) {
        this.algoritmoElegido = algoritmoElegido;
    }

    public int getSorterElegido() {
        return sorterElegido;
    }

    public void setSorterElegido(int sorterElegido) {
        this.sorterElegido = sorterElegido;
    }

    public void setearParametrosElegidos(){
        setAlgoritmoElegido(panelControl.getAlgoritmoElegido());
        setSorterElegido(panelControl.getSorterElegido());
        puntosService.setAlgoritmoElegido(algoritmoElegido);
        if (algoritmoElegido != 0){
            puntosService.setClaseSort(pathsSort[sorterElegido], 0, totalPuntos - 1, true);
        }
    }

    public double getMediaPuntos() {
        return mediaPuntos;
    }

    public void setMediaPuntos(double mediaPuntos) {
        this.mediaPuntos = mediaPuntos;
    }

    public double getVarianzaPuntos() {
        return varianzaPuntos;
    }

    public void setVarianzaPuntos(double varianzaPuntos) {
        this.varianzaPuntos = varianzaPuntos;
    }

    public int getTotalPuntos() {
        return totalPuntos;
    }

    public void setTotalPuntos(int totalPuntos) {
        this.totalPuntos = totalPuntos;
    }

    public boolean isGaussianDistribution() {
        return isGaussianDistribution;
    }

    public void setGaussianDistribution(boolean gaussianDistribution) {
        isGaussianDistribution = gaussianDistribution;
    }
}
