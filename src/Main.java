/* Created by andreea on 26/03/2020 */

import Domain.Nube;
import Domain.Punto;
import Infrastructure.PuntosService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Main {
    private static final int TOTAL_PUNTOS = 10000;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Nube nubePuntos = new Nube(TOTAL_PUNTOS);
        PuntosService puntosService = new PuntosService();
        String sortPath = "Infrastructure.SortingAlgorithms.Javasort";
        Future<Nube> future = nubePuntos.crearNubePuntos(TOTAL_PUNTOS, 5, 1);

        while (!future.isDone()){}

        puntosService.setNubePuntos(future.get());
        puntosService.setClaseSort(sortPath, 0, TOTAL_PUNTOS - 1, true);

        puntosService.start();
    }
}
