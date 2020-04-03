package Domain.Interfaces;

import java.util.concurrent.ExecutionException;

public interface IController {
    void updateGraph(double mean, double stdDeviation);
    void disableGaussianElements();
    void enableGaussianElements();
    void habilitarBotonesSorter();
    void deshabilitarBotonesSorter();
    void crearNubePunto() throws ExecutionException, InterruptedException;
    void start();
    void inicializarPuntos();
    void pintarPuntos();
}
