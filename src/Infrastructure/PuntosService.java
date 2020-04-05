/**
 * @authors Miruna Andreea Gheata, Rafael Adrián Gil Cañestro
 */
package Infrastructure;

import Application.DCController;
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
    private DCController controller;

    public PuntosService(DCController controller){
        this.controller = controller;
    }

    public void setClaseSort(String p) {
        try {
            Class c = Class.forName(p);
            sorter = (Sort) c.getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Nube getNubePuntos() {
        return nubePuntos;
    }

    public void setNubePuntos(Nube nubePuntos) {
        this.nubePuntos = nubePuntos;
        isPuntosOrdenados = false;
    }

    public void start() {
        worker = new Thread(this);
        worker.start();
    }

    @Override
    public void run() {
        long tiempoTardado;
        DistanciaMinima distanciaMinima = null;
        switch (algoritmoElegido){
            case 0:
                int n = nubePuntos.getCantidad();
                int v= n*(n-1)/2;
                controller.setMax(v);
                tiempoTardado = System.currentTimeMillis();
                distanciaMinima = naive(nubePuntos.getPuntos().clone(), nubePuntos.getCantidad());
                distanciaMinima.setTiempoTotal(System.currentTimeMillis() - tiempoTardado);
                break;
            case 1:
                sorter.setPuntosOriginales(nubePuntos.getPuntos().clone());

                if (!isPuntosOrdenados){
                    arrayPuntosOrdenados = ordenarPuntosPorCoordenadas(nubePuntos);
                    puntosOrdenadosCoordenadaX = arrayPuntosOrdenados.get(0);
                    puntosOrdenadosCoordenadaY = arrayPuntosOrdenados.get(1);
                    isPuntosOrdenados = true;
                }
                tiempoTardado = System.currentTimeMillis();
                controller.barraGo();
                distanciaMinima = divideConquerOnlogn2(puntosOrdenadosCoordenadaX.clone(), nubePuntos.getCantidad());
                controller.barraStop();
                distanciaMinima.setTiempoTotal(System.currentTimeMillis() - tiempoTardado);
                break;
            case 2:
                sorter.setPuntosOriginales(nubePuntos.getPuntos().clone());
                if (!isPuntosOrdenados){
                    arrayPuntosOrdenados = ordenarPuntosPorCoordenadas(nubePuntos);
                    puntosOrdenadosCoordenadaX = arrayPuntosOrdenados.get(0);
                    puntosOrdenadosCoordenadaY = arrayPuntosOrdenados.get(1);
                    isPuntosOrdenados = true;
                }
                tiempoTardado = System.currentTimeMillis();
                controller.barraGo();
                distanciaMinima = divideConquerOnlogn(puntosOrdenadosCoordenadaX.clone(), puntosOrdenadosCoordenadaY.clone(), nubePuntos.getCantidad());
                controller.barraStop();
                distanciaMinima.setTiempoTotal(System.currentTimeMillis() - tiempoTardado);
                break;
        }
        controller.setPuntoSolucion(distanciaMinima);
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
                //Aumento Barra
                if(algoritmoElegido==0){
                 controller.barraAct();
                }
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

        ArrayList<Punto> stripList = new ArrayList<>();

        int puntosStrip = 0;

        for (int i = 0; i < n; i++){
            if (Math.abs(puntos[i].getX() - puntoMedio.getX()) < distanciaMinima.getDistanciaPuntos()){
                stripList.add(puntos[i]);
                puntosStrip++;
            }
        }

        return min(distanciaMinima, stripClosest(toArray(stripList), puntosStrip, distanciaMinima.getDistanciaPuntos(), false));
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

        return min(distanciaMinima, stripClosest(toArray(stripList), puntosStrip, distanciaMinima.getDistanciaPuntos(), true));
    }

    private DistanciaMinima stripClosest(Punto[] strip, int cantidad, double distanciaPuntos, boolean isNLogN){
        Punto punto1 = null;
        Punto punto2 = null;
        double min = distanciaPuntos;
        if (!isNLogN){
            sorter.setMirarCoordenadaX(false);
            Punto[] puntosOrdenadosCoordenadaY = strip.clone();
            sorter.sort(puntosOrdenadosCoordenadaY);
            strip = puntosOrdenadosCoordenadaY.clone();
        }
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

    public void setAlgoritmoElegido(int algoritmoElegido) {
        this.algoritmoElegido = algoritmoElegido;
    }
}
