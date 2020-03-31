/* Created by andreea on 22/03/2020 */
package Infrastructure;

import Domain.DTO.DistanciaMinima;
import Domain.Interfaces.IPuntosService;
import Domain.Nube;
import Domain.Punto;
import Infrastructure.SortingAlgorithms.Sort;

import java.util.*;

public class PuntosService implements IPuntosService {

    private Sort sorter = null;
    private Thread worker;
    private Nube nubePuntos;
    private int algoritmoElegido;
    private boolean isPuntosOrdenados;
    private ArrayList<Punto[]> arrayPuntosOrdenados;
    private Punto[] puntosOrdenadosCoordenadaX;
    private Punto[] puntosOrdenadosCoordenadaY;

    public void setClaseSort(String p, int low, int high, boolean mirarCoordenadaX) {
        try {
            Class c = Class.forName(p);
            sorter = (Sort) c.getDeclaredConstructor().newInstance();
            switch(SortingTypes.valueOf(sorter.getNombreAlgoritmo())){
                case Javasort:
                    sorter = (Sort) c.getConstructor(boolean.class).newInstance(mirarCoordenadaX);
                    break;
                case Quicksort:
                    sorter = (Sort) c.getConstructor(int.class, int.class).newInstance(low, high);
                    break;
                case Mergesort:
                    //sorter = (Sort) c.getConstructor().newInstance();
                    break;
                case Bucketsort:
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Nube getNubePuntos() {
        return nubePuntos;
    }

    public void setNubePuntos(Nube nubePuntos) {
        this.nubePuntos = nubePuntos;
    }

    public void start() {
        worker = new Thread(this);
        worker.start();
    }

    @Override
    public void run() {
        long tiempoTardado;
        switch (algoritmoElegido){
            case 0:
                tiempoTardado = System.currentTimeMillis();
                System.out.println(">  (SORT): No se usa sorter (ALGORITMO) NAIVE con " + nubePuntos.getCantidad() + " puntos" + "\n" + naive(nubePuntos.getPuntos().clone(), nubePuntos.getCantidad()) + "\n ************");
                tiempoTardado = System.currentTimeMillis() - tiempoTardado;
                System.out.println(tiempoTardado);
                break;
            case 1:
                if (!isPuntosOrdenados){
                    arrayPuntosOrdenados = ordenarPuntosPorCoordenadas(nubePuntos);
                    puntosOrdenadosCoordenadaX = arrayPuntosOrdenados.get(0);
                    puntosOrdenadosCoordenadaY = arrayPuntosOrdenados.get(1);
                    isPuntosOrdenados = true;
                }
                tiempoTardado = System.currentTimeMillis();
                System.out.println(">  (SORT): " + sorter.getNombreAlgoritmo() + " (ALGORITMO) Onlogn2 con " + nubePuntos.getCantidad() + " puntos" + "\n" + divideConquerOnlogn2(puntosOrdenadosCoordenadaX.clone(), nubePuntos.getCantidad())+ "\n ************");
                tiempoTardado = System.currentTimeMillis() - tiempoTardado;
                System.out.println(tiempoTardado);
                break;
            case 2:
                if (!isPuntosOrdenados){
                    arrayPuntosOrdenados = ordenarPuntosPorCoordenadas(nubePuntos);
                    puntosOrdenadosCoordenadaX = arrayPuntosOrdenados.get(0);
                    puntosOrdenadosCoordenadaY = arrayPuntosOrdenados.get(1);
                    isPuntosOrdenados = true;
                }
                tiempoTardado = System.currentTimeMillis();
                System.out.println(">  (SORT): " + sorter.getNombreAlgoritmo() +" (ALGORITMO) Onlogn con " + nubePuntos.getCantidad() + " puntos" + "\n " + divideConquerOnlogn(puntosOrdenadosCoordenadaX.clone(), puntosOrdenadosCoordenadaY.clone(), nubePuntos.getCantidad())+ "\n ************");
                tiempoTardado = System.currentTimeMillis() - tiempoTardado;
                System.out.println(tiempoTardado);
                break;
        }
        /*
        Nube nubePuntosAux = new Nube(6);

        Punto[] puntos = new Punto[]{
                new Punto(1, 2, 3),
                new Punto(2, 12, 30),
                new Punto(3, 40, 50),
                new Punto(4, 5, 1),
                new Punto(5, 12, 10),
                new Punto(6, 3, 4)
        };

        nubePuntosAux.setPuntos(puntos);

        //long tiempoTardado = System.currentTimeMillis();
        System.out.println(">  (SORT): " + sorter.getNombreAlgoritmo() + " (ALGORITMO) NAIVE con " + nubePuntos.getCantidad() + " puntos" + "\n" + naive(nubePuntos.getPuntos().clone(), nubePuntos.getCantidad()) + "\n ************");
        tiempoTardado = System.currentTimeMillis() - tiempoTardado;
        System.out.println(tiempoTardado);

        ArrayList<Punto[]> arrayPuntosOrdenados = ordenarPuntosPorCoordenadas(nubePuntos);

        Punto[] puntosOrdenadosCoordenadaX = arrayPuntosOrdenados.get(0);
        Punto[] puntosOrdenadosCoordenadaY = arrayPuntosOrdenados.get(1);

        tiempoTardado = System.currentTimeMillis();

        System.out.println(">  (SORT): " + sorter.getNombreAlgoritmo() + " (ALGORITMO) Onlogn2 con " + nubePuntos.getCantidad() + " puntos" + "\n" + divideConquerOnlogn2(puntosOrdenadosCoordenadaX.clone(), nubePuntos.getCantidad())+ "\n ************");
        tiempoTardado = System.currentTimeMillis() - tiempoTardado;
        System.out.println(tiempoTardado);
        tiempoTardado = System.currentTimeMillis();

        System.out.println(">  (SORT): " + sorter.getNombreAlgoritmo() +" (ALGORITMO) Onlogn con " + nubePuntos.getCantidad() + " puntos" + "\n " + divideConquerOnlogn(puntosOrdenadosCoordenadaX.clone(), puntosOrdenadosCoordenadaY.clone(), nubePuntos.getCantidad())+ "\n ************");
        tiempoTardado = System.currentTimeMillis() - tiempoTardado;
        System.out.println(tiempoTardado);
        */
    }

    @Override
    public DistanciaMinima naive(Punto[] puntos, int n) {
        DistanciaMinima distanciaMinima = null;
        double min_distance = Double.MAX_VALUE;
        double computedDistance;
        for (int i = 0; i < n; i++){
            Punto puntoActual = puntos[i];
            for (int j = i + 1; j < n; j++){
                computedDistance = puntoActual.calcularDistanciaEuclidea(puntos[j]);
                if (computedDistance < min_distance){
                    min_distance = computedDistance;
                    distanciaMinima = new DistanciaMinima(puntoActual, puntos[j], computedDistance);
                }
            }
        }
        return distanciaMinima;
    }

    @Override
    public DistanciaMinima divideConquerOnlogn2(Punto[] puntos, int n){
        if (n <= 3){
            return naive(puntos, n);
        }

        int mid = n / 2;

        Punto puntoMedio = puntos[mid];

        Punto[] puntosXIzquierda = Arrays.copyOfRange(puntos, 0, (n + 1)/2);
        Punto[] puntosXDerecha = Arrays.copyOfRange(puntos, n/2, n);

        DistanciaMinima distanciaMinimaL = divideConquerOnlogn2(puntosXIzquierda, mid);
        DistanciaMinima distanciaMinimaR = divideConquerOnlogn2(puntosXDerecha, n - mid);

        DistanciaMinima distanciaMinima = min(distanciaMinimaL, distanciaMinimaR);

        Punto strip[] = new Punto[n];

        int j = 0;

        for (int i = 0; i < n; i++){
            if (Math.abs(puntos[i].getX() - puntoMedio.getX()) < distanciaMinima.getDistanciaPuntos()){
                strip[j++] = puntos[i];
            }
        }

        return min(distanciaMinima, stripClosest(strip, j, distanciaMinima.getDistanciaPuntos()));
    }

    @Override
    public DistanciaMinima divideConquerOnlogn(Punto[] puntosX, Punto[] puntosY, int n){
        if (n <= 3){
            return naive(puntosX, n);
        }

        int mitad = n / 2;

        Punto puntoMedio = puntosX[mitad];

        ArrayList<Punto> puntosIzq = new ArrayList<>();
        ArrayList<Punto> puntosDer = new ArrayList<>();

        for (int i = 0; i < puntosY.length; i++){
            if (puntosY[i].getX() <= puntoMedio.getX()){
                puntosIzq.add(puntosY[i]);
            } else {
                puntosDer.add(puntosY[i]);
            }
        }

        Punto [] puntosYIzquierda = toArray(puntosIzq);
        Punto [] puntosYDerecha = toArray(puntosDer);

        Punto[] puntosXIzquierda = Arrays.copyOfRange(puntosX, 0, (n + 1)/2);
        Punto[] puntosXDerecha = Arrays.copyOfRange(puntosX, n/2, n);

        DistanciaMinima distanciaMinimaL = divideConquerOnlogn(puntosXIzquierda, puntosYIzquierda, mitad);
        DistanciaMinima distanciaMinimaR = divideConquerOnlogn(puntosXDerecha, puntosYDerecha, n - mitad);

        DistanciaMinima distanciaMinima = min(distanciaMinimaL, distanciaMinimaR);

        ArrayList<Punto> stripList = new ArrayList<>();

        int puntosStrip = 0;

        for (Punto punto : puntosY) {
            if (Math.abs(punto.getX() - puntoMedio.getX()) < distanciaMinima.getDistanciaPuntos()) {
                stripList.add(punto);
                puntosStrip++;
            }
        }

        return min(distanciaMinima, stripClosest(toArray(stripList), puntosStrip, distanciaMinima.getDistanciaPuntos()));
    }

    private DistanciaMinima stripClosest(Punto[] strip, int cantidad, double distanciaPuntos){
        Punto punto1 = null;
        Punto punto2 = null;
        double min = distanciaPuntos;
        for (int i = 0; i < cantidad; ++i){
            for (int j = i + 1; j < cantidad && (strip[j].getY() - strip[i].getY()) < min; ++j){
                if (strip[i].calcularDistanciaEuclidea(strip[j]) < min){
                    punto1 = strip[i];
                    punto2 = strip[j];
                    min = strip[i].calcularDistanciaEuclidea(strip[j]);
                }
            }
        }
        if (min < distanciaPuntos){
            return new DistanciaMinima(punto1, punto2, min);
        }
        return new DistanciaMinima(null, null, Double.MAX_VALUE);
    }

    private ArrayList<Punto[]> ordenarPuntosPorCoordenadas(Nube puntos){
        ArrayList<Punto[]> arrayPuntosOrdenados = new ArrayList<>();
        Punto[] puntosOrdenadosCoordenadaX = puntos.getPuntos().clone();
        sorter.sort(puntosOrdenadosCoordenadaX);
        arrayPuntosOrdenados.add(puntosOrdenadosCoordenadaX);
        sorter.setMirarCoordenadaX(false);
        Punto[] puntosOrdenadosCoordenadaY = puntos.getPuntos().clone();
        sorter.sort(puntosOrdenadosCoordenadaY);
        arrayPuntosOrdenados.add(puntosOrdenadosCoordenadaY);
        return arrayPuntosOrdenados;
    }

    private Punto[] toArray(ArrayList<Punto> puntosLista){
        Punto[] puntosArray = new Punto[puntosLista.size()];
        for (int i = 0; i < puntosArray.length; i++){
            puntosArray[i] = puntosLista.get(i);
        }
        return puntosArray;
    }

    private DistanciaMinima min(DistanciaMinima d1, DistanciaMinima d2){
        if (d1.getDistanciaPuntos() < d2.getDistanciaPuntos()){
            return d1;
        }
        return d2;
    }

    public int getAlgoritmoElegido() {
        return algoritmoElegido;
    }

    public void setAlgoritmoElegido(int algoritmoElegido) {
        this.algoritmoElegido = algoritmoElegido;
    }
}
