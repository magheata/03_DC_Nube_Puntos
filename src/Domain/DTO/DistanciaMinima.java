/**
 * @authors Miruna Andreea Gheata, Rafael Adrián Gil Cañestro
 */
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

    public void setPuntosSolucion(){
        punto1.setSolucion(true);
        punto2.setSolucion(true);
    }

    public double getDistanciaPuntos() {
        return distanciaPuntos;
    }

    @Override
    public String toString() {
        return "La distancia mínima entre puntos es de: " + distanciaPuntos + "\n" +
                "Punto 1: [" + punto1 + "]\n" +
                "Punto 2: [" + punto2 + "]\n" +

                "La ejecución del programa ha tardado " + tiempoTotal/1000 + "segundos.";
    }
}
