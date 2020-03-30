/* Created by andreea on 22/03/2020 */

package Domain;

public class Punto {

    private double x;
    private double y;
    private int id;

    public Punto(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public Punto(){}

    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double calcularDistanciaEuclidea(Punto punto){
        return Math.sqrt((Math.pow((this.x - punto.x), 2) + Math.pow((this.y - punto.y), 2)));
    }

    @Override
    public String toString() {
        return "Punto{" +
                "x=" + x +
                ", y=" + y +
                ", id=" + id +
                '}';
    }
}
