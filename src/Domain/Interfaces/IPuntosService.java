package Domain.Interfaces;

import Domain.DTO.DistanciaMinima;
import Domain.Nube;

public interface IPuntosService extends Runnable{
    DistanciaMinima naive(Nube puntos);
    DistanciaMinima divideConquerOnlogn2(Nube puntos, int n);
    DistanciaMinima divideConquerOnlogn(Nube puntos, int n);
    DistanciaMinima divideConquerMixed(Nube puntos, int n);
}
