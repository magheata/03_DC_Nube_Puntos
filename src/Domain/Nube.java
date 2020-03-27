/* Created by andreea on 22/03/2020 */
package Domain;

import Domain.Interfaces.INube;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Nube implements INube {

    private Punto[] puntos;
    private int cantidad;
    private int media;
    private int desviacion = 1;
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
    public ArrayList<Punto[]> splitX(int boundary){
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
    public Future<Nube> crearNubePuntos (int cantidad, int media, int desviacion) {
        return executor.submit(() -> {
            for (int i = 0; i < cantidad; i++){
                setPunto(new Punto(i + 1, generateRandomCoordinate(media, desviacion), generateRandomCoordinate(media, desviacion)), i);
            }
            return this;
        });
    }

    private double generateRandomCoordinate(int media, int desviacion){
        return generator.nextGaussian() * media + desviacion;
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
}
