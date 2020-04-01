/* Created by andreea on 22/03/2020 */
package Domain;

import Domain.Interfaces.INube;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Nube implements INube {

    private Punto[] puntos;
    private int cantidad;
    private int media;
    private int desviacion;
    private final Random generator = new Random();
    private Thread worker;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public Nube(int cantidad) {
        this.cantidad = cantidad;
        this.puntos = new Punto[cantidad];
    }

    public void start() {
        worker = new Thread(this);
        worker.start();
    }

    @Override
    public void run() {
        crearNubePuntos(cantidad, 5, 1);
        System.out.println("Puntos rellenados");
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

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        this.puntos = new Punto[cantidad];
    }

    @Override
    public ArrayList<Punto[]> splitX(int cantidad, int boundary){
        ArrayList<Punto[]> puntosSeparados = new ArrayList<>();
        Punto[] xL = new Punto[boundary];
        Punto[] xR = new Punto[cantidad - boundary];

        for (int i = 0; i < boundary; i++){
            xL[i] = getPunto(i);
        }

        for (int i = boundary; i < cantidad; i++){
            xR[i - boundary] = getPunto(i);
        }

        puntosSeparados.add(xL);
        puntosSeparados.add(xR);
        return puntosSeparados;
    }

    @Override
    public Future<Nube> crearNubePuntos (int cantidad, double media, double desviacion) {
        return executor.submit(() -> {
            for (int i = 0; i < cantidad; i++){
                setPunto(new Punto(i + 1, generateRandomCoordinate(media, desviacion), generateRandomCoordinate(media, desviacion)), i);
            }
            return this;
        });
    }

    @Override
    public Future<Nube> crearNubePuntosRandom (int lower, int upper) {
        //ThreadLocalRandom generator = ThreadLocalRandom.current();
        return executor.submit(() -> {
            for (int i = 0; i < cantidad; i++){
                setPunto(new Punto(i + 1, lower + (upper - lower) * generator.nextDouble(), lower + (upper - lower) * generator.nextDouble()), i);
            }
            return this;
        });
    }

    public double[] getCoordenateArray(boolean getCoordenadaX){
        double [] result = new double[cantidad];
        for (int i = 0; i < cantidad; i++){
            if (getCoordenadaX){
                result[i] = puntos[i].getX();
            } else {
                result[i] = puntos[i].getY();
            }
        }
        return result;
    }

    private double generateRandomCoordinate(double media, double desviacion){
        return generator.nextGaussian() * desviacion + media;
    }

    public static double findAverageUsingStream(double[] array) {
        return Arrays.stream(array).average().orElse(Double.NaN);
    }

    public double variance(double a[], int n) {
        double sum = 0;

        for (int i = 0; i < n; i++)
            sum += a[i];
        double mean = sum / (double)n;

        double sqDiff = 0;
        for (int i = 0; i < n; i++)
            sqDiff += (a[i] - mean) *
                    (a[i] - mean);

        return sqDiff / n;
    }

    public double standardDeviation(double arr[], int n) {
        return Math.sqrt(variance(arr, n));
    }

    public int getMedia() {
        return media;
    }

    public void setMedia(int media) {
        this.media = media;
    }

    public int getDesviacion() {
        return desviacion;
    }

    public void setDesviacion(int desviacion) {
        this.desviacion = desviacion;
    }

    @Override
    public String toString() {
        return "Nube{" +
                "puntos=" + Arrays.toString(puntos) +
                '}';
    }
}
