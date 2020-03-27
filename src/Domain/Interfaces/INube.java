package Domain.Interfaces;

import Domain.Nube;
import Domain.Punto;

import java.util.ArrayList;
import java.util.concurrent.Future;

public interface INube extends Runnable{
    Future<Nube> crearNubePuntos(int cantidad, int media, int desviacion);
    ArrayList<Punto[]> splitX(int boundary);
}
