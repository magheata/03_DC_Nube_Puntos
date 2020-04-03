/* Created by andreea on 26/03/2020 */
package Infrastructure.SortingAlgorithms;

import Domain.Punto;

import java.util.*;

public class Javasort extends Sort{

    public Javasort() {
        nombreAlgoritmo = "Javasort";
    }

    @Override
    public void sort(Punto[] puntos){
        this.puntos = puntos;
        duracionTotal = System.nanoTime(); /* Guardamos el momento en el que empieza el proceso*/
        sortPrivate();
        duracionTotal = System.nanoTime() - duracionTotal; /* Guardamos el momento en el que empieza el proceso*/
    }

    private void sortPrivate() {
        List<Punto> prueba = Arrays.asList(puntos);
        prueba.sort((p1, p2) -> {
            if (mirarCoordenadaX) {
                return Double.compare(p1.getX(), p2.getX());
            }
            return Double.compare(p1.getY(), p2.getY());
        });
    }
}
