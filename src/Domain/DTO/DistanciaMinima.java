/* Created by andreea on 25/03/2020 */
package Domain.DTO;

import Domain.Punto;

public class DistanciaMinima {

    private Punto punto1;
    private Punto punto2;

    private double distanciaPuntos;

    public void setTiempoTotal(double tiempoTotal) {
        this.tiempoTotal = tiempoTotal;
    }

    private double tiempoTotal;

    public DistanciaMinima(Punto punto1, Punto punto2, double distanciaPuntos) {
        this.punto1 = punto1;
        this.punto2 = punto2;
        this.distanciaPuntos = distanciaPuntos;
    }

    public double getDistanciaPuntos() {
        return distanciaPuntos;
    }

    @Override
    public String toString() {
        return "DistanciaMinima{" +
                "idPunto1=" + punto1 +
                ", idPunto2=" + punto2 +
                ", distanciaPuntos=" + distanciaPuntos +
                '}';
    }
}
