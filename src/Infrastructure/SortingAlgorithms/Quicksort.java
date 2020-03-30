/* Created by andreea on 26/03/2020 */
package Infrastructure.SortingAlgorithms;

import Domain.Punto;

public class Quicksort extends Sort{

    private int low;
    private int high;

    public Quicksort(){
        nombreAlgoritmo = "Quicksort";
    }

    public Quicksort(int low, int high) {
        nombreAlgoritmo = "Quicksort";
        this.low = low;
        this.high = high;
    }

    @Override
    public void sort(Punto[] puntos){
        duracionTotal = System.nanoTime(); /* Guardamos el momento en el que empieza el proceso*/
        this.puntos = puntos;
        sort(low, high);
        duracionTotal = System.nanoTime() - duracionTotal; /* Guardamos el momento en el que empieza el proceso*/
    }

    private void sort(int low, int high) {
        if (puntos == null || puntos.length == 0)
            return;

        if (low >= high)
            return;

        // pick the pivot
        int middle = low + (high - low) / 2;
        Punto pivot = puntos[middle];

        // make left < pivot and right > pivot
        int i = low, j = high;
        while (i <= j) {
            if(mirarCoordenadaX){
                while (puntos[i].getX() < pivot.getX()) {
                    i++;
                }

                while (puntos[j].getX() > pivot.getX()) {
                    j--;
                }
            } else {
                while (puntos[i].getY() < pivot.getY()) {
                    i++;
                }

                while (puntos[j].getY() > pivot.getY()) {
                    j--;
                }
            }
            if (i <= j) {
                Punto temp = puntos[i];
                puntos[i] = puntos[j];
                puntos[j] = temp;
                i++;
                j--;
            }
        }

        // recursively sort two sub parts
        if (low < j) sort(low, j);

        if (high > i) sort(i, high);
    }
}
