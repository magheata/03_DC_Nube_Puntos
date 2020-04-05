package Domain.Interfaces;

import Domain.Nube;
import java.util.concurrent.Future;

public interface INube {
    Future<Nube> crearNubePuntos(int cantidad, double media, double desviacion);
    Future<Nube> crearNubePuntosRandom (int lower, int upper);
}
