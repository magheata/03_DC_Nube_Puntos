package Domain.Interfaces;

import Domain.DTO.DistanciaMinima;
import Domain.Nube;
import Domain.Punto;

public interface IPuntosService extends Runnable{
    DistanciaMinima naive(Punto[] puntos, int n);
    DistanciaMinima divideConquerOnlogn2(Punto[] puntos, int n);
    DistanciaMinima divideConquerOnlogn(Punto[] puntosX, Punto[] puntosY,  int n);
}
