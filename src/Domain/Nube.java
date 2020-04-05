/**
 * @authors Miruna Andreea Gheata, Rafael Adrián Gil Cañestro
 */
package Domain;

import Domain.Interfaces.INube;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Nube implements INube {

    private Punto[] puntos;
    private int cantidad;
    private final Random generator = new Random();
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public double getMinX() {
        return minX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMaxY() {
        return maxY;
    }

    private double minX = Double.MAX_VALUE;
    private double minY = Double.MAX_VALUE;
    private double maxX = Double.MIN_VALUE;
    private double maxY = Double.MIN_VALUE;

    public Nube(int cantidad) {
        this.cantidad = cantidad;
        this.puntos = new Punto[cantidad];
    }

    public Punto[] getPuntos() {
        return puntos;
    }

    public void setPuntos(Punto[] puntos) {
        this.puntos = puntos;
    }

    public Punto getPunto(int i) {
        return puntos[i];
    }

    public void setPunto(Punto punto, int i) {
        this.puntos[i] = punto;
    }

    public int getCantidad() {
        return cantidad;
    }

    @Override
    public Future<Nube> crearNubePuntos (int cantidad, double media, double desviacion) {
        return executor.submit(() -> {
            double coordenadaX;
            double coordenadaY;
            for (int i = 0; i < cantidad; i++){
                coordenadaX = generateRandomCoordinate(media, desviacion);
                coordenadaY = generateRandomCoordinate(media, desviacion);
                if (coordenadaX < minX){
                    minX = coordenadaX;
                }
                if (coordenadaX > maxX){
                    maxX = coordenadaX;
                }
                if (coordenadaY < minY){
                    minY = coordenadaY;
                }
                if (coordenadaY > maxY){
                    maxY = coordenadaY;
                }
                setPunto(new Punto(i + 1, coordenadaX, coordenadaY), i);
            }
            return this;
        });
    }

    @Override
    public Future<Nube> crearNubePuntosRandom (int lower, int upper) {
        return executor.submit(() -> {
            double coordenadaX;
            double coordenadaY;
            for (int i = 0; i < cantidad; i++){
                coordenadaX = lower + (upper - lower) * generator.nextDouble();
                coordenadaY = lower + (upper - lower) * generator.nextDouble();
                setPunto(new Punto(i + 1, coordenadaX, coordenadaY), i);
                if (coordenadaX < minX){
                    minX = coordenadaX;
                }
                if (coordenadaX > maxX){
                    maxX = coordenadaX;
                }
                if (coordenadaY < minY){
                    minY = coordenadaY;
                }
                if (coordenadaY > maxY){
                    maxY = coordenadaY;
                }
            }
            return this;
        });
    }

    private double generateRandomCoordinate(double media, double desviacion){
        return generator.nextGaussian() * desviacion + media;
    }

    @Override
    public String toString() {
        return "Nube{" +
                "puntos=" + Arrays.toString(puntos) +
                '}';
    }
}
