/* Created by andreea on 26/03/2020 */

import Domain.Nube;
import Infrastructure.PuntosService;
import Presentation.Graph.Graph;
import Presentation.Graph.PlotSettings;

import java.awt.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Main {
    private static int TOTAL_PUNTOS;

    private static  String[] pathsSort = {"Infrastructure.SortingAlgorithms.Javasort", "Infrastructure.SortingAlgorithms.Quicksort",
            "Infrastructure.SortingAlgorithms.Mergesort", "Infrastructure.SortingAlgorithms.Bucketsort"};

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        PuntosService puntosService = new PuntosService();
        String sortPath = pathsSort[1];

        TOTAL_PUNTOS = (int) Math.pow(10, 2);
        Nube nubePuntos = new Nube(TOTAL_PUNTOS);
        Future<Nube> future = nubePuntos.crearNubePuntos(TOTAL_PUNTOS, 2, 1);
        //Future<Nube> future = nubePuntos.crearNubePuntosRandom(0, TOTAL_PUNTOS);
        while (!future.isDone()){}
        puntosService.setNubePuntos(future.get());
        //puntosService.setClaseSort(sortPath, 0, 6 - 1, true);
        puntosService.setClaseSort(sortPath, 0, TOTAL_PUNTOS - 1, true);
        puntosService.start();
        //new GraphApplication(getExampleGraph2());
        Window panelControl = new Presentation.Window();
        panelControl.setVisible(true);
        panelControl.pack();
    }

    public static Graph getExampleGraph2() {
        PlotSettings p = new PlotSettings(-10, 10, 0, 1);
        p.setPlotColor(Color.RED);
        p.setGridSpacingX(0.5);
        p.setGridSpacingY(0.5);
        p.setTitle("Two functions being rendered together");
        Graph graph = new Graph(p, 0, 2);
        //graph.functions.add(new Circle1());
        //graph.functions.add(new SineWave());
        //graph.functions.add(new Gaussian(2, 5));
        return graph;
    }
}
