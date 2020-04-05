/**
 * @authors Miruna Andreea Gheata, Rafael Adrián Gil Cañestro
 */
package Infrastructure.SortingAlgorithms;

import Domain.Punto;

public abstract class Sort{

    protected Punto[] puntos;
    protected String nombreAlgoritmo;
    protected long duracionTotal;
    protected boolean mirarCoordenadaX = true;

    protected Punto[] puntosOriginales;

    public void setMirarCoordenadaX(boolean mirarCoordenadaX) {
        this.mirarCoordenadaX = mirarCoordenadaX;
    }

    public void setPuntosOriginales(Punto[] puntosOriginales) {
        this.puntosOriginales = puntosOriginales;
    }

    public void sort(Punto[] puntos){};

}
