package Domain.Interfaces;

import Application.DTO.DistanciaMinima;

import java.util.concurrent.ExecutionException;

public interface IController {
    void updateGraph(double mean, double stdDeviation, boolean coordenadaX);
    void disableGaussianElements();
    void enableGaussianElements();
    void habilitarBotonesSorter();
    void deshabilitarBotonesSorter();
    void crearNubePunto() throws ExecutionException, InterruptedException;
    void start();
    void inicializarPuntos();
    void pintarPuntos();
    void setPuntoSolucion(DistanciaMinima distanciaMinima);
    void setearParametrosElegidos();
    void disableButtons();
    void enableButtons();
}
