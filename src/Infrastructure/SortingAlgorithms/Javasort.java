/* Created by andreea on 26/03/2020 */
package Infrastructure.SortingAlgorithms;

import Domain.Punto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Javasort extends Sort{

    private boolean compararX;

    public Javasort(){
        nombreAlgoritmo = "Javasort";
    }

    public Javasort(boolean compararX) {
        nombreAlgoritmo = "Javasort";
        this.compararX = compararX;
    }

    @Override
    public void sort(Punto[] puntos){
        sortPrivate(puntos);
    }

    public boolean isCompararX() {
        return compararX;
    }

    public void setCompararX(boolean compararX) {
        this.compararX = compararX;
    }

    private void sortPrivate(Punto[] puntos) {
        Collections.sort(new ArrayList<>(Arrays.asList(puntos)), new Comparator<Punto>() {
            @Override
            public int compare(Punto p1, Punto p2) {
                if (compararX){
                    return Double.compare(p1.getX(), p2.getX());
                }
                return Double.compare(p1.getY(), p2.getY());
            }
        });
    }
}
