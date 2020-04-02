/* Created by andreea on 26/03/2020 */
package Infrastructure.SortingAlgorithms;

import Domain.Punto;

public abstract class Sort{

    protected Punto[] puntos;
    protected String nombreAlgoritmo;
    protected long duracionTotal;
    protected boolean mirarCoordenadaX = true;

    protected Punto[] puntosOriginales;

    public long getDuracionTotal() {
        return duracionTotal;
    }

    public void setDuracionTotal(long duracionTotal) {
        this.duracionTotal = duracionTotal;
    }

    public String getNombreAlgoritmo() {
        return nombreAlgoritmo;
    }

    public void setNombreAlgoritmo(String nombreAlgoritmo) {
        this.nombreAlgoritmo = nombreAlgoritmo;
    }

    public boolean isMirarCoordenadaX() {
        return mirarCoordenadaX;
    }

    public void setMirarCoordenadaX(boolean mirarCoordenadaX) {
        this.mirarCoordenadaX = mirarCoordenadaX;
    }

    public Punto[] getPuntosOriginales() {
        return puntosOriginales;
    }

    public void setPuntosOriginales(Punto[] puntosOriginales) {
        this.puntosOriginales = puntosOriginales;
    }

    public void sort(Punto[] puntos){};

}
