package Domain.Interfaces;

import Domain.Nube;
import Domain.Punto;

import java.util.ArrayList;
import java.util.concurrent.Future;

public interface INube extends Runnable{
    Future<Nube> crearNubePuntos(int cantidad, double media, double desviacion);
    Future<Nube> crearNubePuntosRandom (int lower, int upper);
    ArrayList<Punto[]> splitX(int cantidad, int boundary);
}
