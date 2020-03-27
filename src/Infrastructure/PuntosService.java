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

    public Nube getNubePuntos() {
        return nubePuntos;
    }

    public void setNubePuntos(Nube nubePuntos) {
        this.nubePuntos = nubePuntos;
    }

    private Nube nubePuntos;

    public void start() throws InterruptedException {
        worker = new Thread(this);
        worker.start();
    }

    @Override
    public void run() {
        //System.out.println(naive(nubePuntos));
        System.out.println(divideConquerOnlogn2(nubePuntos, nubePuntos.getCantidad()));
        //System.out.println(divideConquerOnlogn(nubePuntos, nubePuntos.getCantidad()));
        //System.out.println(divideConquerMixed(nubePuntos, nubePuntos.getCantidad()));

    }

    @Override
    public DistanciaMinima naive(Nube puntos) {
        double min_distance = Double.MAX_VALUE;
        double computedDistance;

        Punto punto1 = new Punto();
        Punto punto2 = new Punto();

        for (int i = 0; i < puntos.getCantidad(); i++){
            Punto puntoActual = puntos.getPunto(i);
            for (int j = 0; j < puntos.getCantidad(); j++){
                if (i != j){
                    computedDistance = puntoActual.calcularDistanciaEuclidea(puntos.getPunto(j));
                    if (computedDistance < min_distance){
                        punto1 = puntos.getPunto(i);
                        punto2 = puntos.getPunto(j);
                        min_distance = computedDistance;
                    }
                }
            }
        }
        return new DistanciaMinima(punto1, punto2, min_distance);
    }


    @Override
    public DistanciaMinima divideConquerOnlogn2(Nube puntos, int n) {
        Punto[] puntosOrdenadosCoordenadaX = nubePuntos.getPuntos();
        sorter.sort(puntosOrdenadosCoordenadaX);
        //Fixed once we know Px
        double medianaX = n/2;

        ArrayList<Punto[]> puntosSeparadosPorX = nubePuntos.splitX((int) medianaX);

        Punto[] puntosL = puntosSeparadosPorX.get(0);
        Punto[] puntosR = puntosSeparadosPorX.get(1);

       /*DistanciaMinima distanciaMinimaL = divideConquer(puntosL, n);
        DistanciaMinima distanciaMinimaR = divideConquer(puntosR, n);*/
        return min(divideConquerOnlogn2(puntosL, n), divideConquerOnlogn2(puntosR, n));
    }

    private DistanciaMinima divideConquerOnlogn2(Punto[] puntos, int n){
        if (n <= 3){
            Nube nubeAux = new Nube(puntos.length);
            nubeAux.setPuntos(puntos);
            return naive(nubeAux);
        }
        ArrayList<Punto[]> puntosSeparadosPorX = nubePuntos.splitX(n/2);
        DistanciaMinima distanciaMinimaL = divideConquerOnlogn2(puntosSeparadosPorX.get(0), n/2);
        DistanciaMinima distanciaMinimaR = divideConquerOnlogn2(puntosSeparadosPorX.get(1), n - n/2);
        return min(distanciaMinimaL, distanciaMinimaR);
    }

    @Override
    public DistanciaMinima divideConquerOnlogn(Nube puntos, int n) {
        ArrayList<Punto[]> arrayPuntosOrdenados = ordenarPuntosPorCoordenadas(puntos);
        Punto[] puntosOrdenadosCoordenadaX = arrayPuntosOrdenados.get(0);
        Punto[] puntosOrdenadosCoordenadaY = arrayPuntosOrdenados.get(1);
        return divideConquerOnlogn(puntosOrdenadosCoordenadaX, puntosOrdenadosCoordenadaY, n);
    }

    private DistanciaMinima divideConquerOnlogn(Punto[] puntosX, Punto[] puntosY, int n){
        if (n <= 3 || puntosY.length == 0){
            Nube nubeAux = new Nube(puntosX.length);
            nubeAux.setPuntos(puntosX);
            return naive(nubeAux);
        }

        int mitad = n/2;
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

        ArrayList<Punto[]> puntosSeparadosPorX = nubePuntos.splitX(mitad);

        Punto[] puntosXIzquierda = puntosSeparadosPorX.get(0);
        Punto[] puntosXDerecha = puntosSeparadosPorX.get(1);

        DistanciaMinima distanciaMinimaIzquierda = divideConquerOnlogn(puntosXIzquierda, puntosYIzquierda, mitad);
        DistanciaMinima distanciaMinimaDerecha = divideConquerOnlogn(puntosXDerecha, puntosYDerecha, n - mitad);

        return min(distanciaMinimaDerecha, distanciaMinimaIzquierda);
    }

    private Punto[] toArray(ArrayList<Punto> puntosLista){
        Punto[] puntosArray = new Punto[puntosLista.size()];
        for (int i = 0; i < puntosArray.length; i++){
            puntosArray[i] = puntosLista.get(i);
        }
        return puntosArray;
    }

    @Override
    public DistanciaMinima divideConquerMixed(Nube nubePuntos, int n) {
        ArrayList<Punto[]> arrayPuntosOrdenados = ordenarPuntosPorCoordenadas(nubePuntos);
        Punto[] puntosOrdenadosCoordenadaX = arrayPuntosOrdenados.get(0);
        Punto[] puntosOrdenadosCoordenadaY = arrayPuntosOrdenados.get(1);
        return divideConquerMixed(puntosOrdenadosCoordenadaX, puntosOrdenadosCoordenadaY, n);
    }

    private DistanciaMinima divideConquerMixed(Punto[] puntosX, Punto[] puntosY, int n) {
        if (n <= 3 || puntosY.length == 0){
            Nube nubeAux = new Nube(puntosX.length);
            nubeAux.setPuntos(puntosX);
            return naive(nubeAux);
        }

        int mitad = n/2;
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

        ArrayList<Punto[]> puntosSeparadosPorX = nubePuntos.splitX(mitad);

        Punto[] puntosXIzquierda = puntosSeparadosPorX.get(0);
        Punto[] puntosXDerecha = puntosSeparadosPorX.get(1);

        DistanciaMinima distanciaMinimaIzquierda = divideConquerOnlogn(puntosXIzquierda, puntosYIzquierda, mitad);
        DistanciaMinima distanciaMinimaDerecha = divideConquerOnlogn(puntosXDerecha, puntosYDerecha, n - mitad);
        DistanciaMinima d = min(distanciaMinimaDerecha, distanciaMinimaIzquierda);

        Punto [] strip = new Punto[n];
        int j = 0;
        for (int i = 0; i < puntosY.length; i++){
            if (Math.abs(puntosY[i].getX() - puntoMedio.getX()) < d.getDistanciaPuntos()){
                strip[j++] = puntosY[i];
            }
        }
        return min(d, stripClosest(strip, j, d.getDistanciaPuntos()));
    }

    private DistanciaMinima stripClosest(Punto[] strip, int cantidad, double distanciaPuntos){
        Punto punto1 = new Punto();
        Punto punto2 = new Punto();
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
        return new DistanciaMinima(punto1, punto2, min);
    }

    private ArrayList<Punto[]> ordenarPuntosPorCoordenadas(Nube puntos){
        ArrayList<Punto[]> arrayPuntosOrdenados = new ArrayList<>();
        Punto[] puntosOrdenadosCoordenadaX = nubePuntos.getPuntos();
        sorter.sort(puntosOrdenadosCoordenadaX);
        arrayPuntosOrdenados.add(puntosOrdenadosCoordenadaX);
        sorter.setMirarCoordenadaX(false);
        Punto[] puntosOrdenadosCoordenadaY = nubePuntos.getPuntos();
        sorter.sort(puntosOrdenadosCoordenadaY);
        arrayPuntosOrdenados.add(puntosOrdenadosCoordenadaY);
        return arrayPuntosOrdenados;
    }

    //region D&C Closest GeeksForGeeks
    public static double closest(Punto[] p) {
        Arrays.sort(p, (p1, p2) -> (int) (p1.getX() - p2.getX()));
        return closestUtil(p, 0, p.length-1);
    }

    private static double closestUtil(Punto[] p, int i, int j) {
        if (j-i+1 <= 3) return bruteForce(p, i, j);
        int n = j - i + 1;
        int mid = i + (j - i)/2;
        Punto midPoint = p[mid];
        double dl = closestUtil(p, i, mid);
        double dr = closestUtil(p, mid+1, j);
        double d = Math.min(dl, dr);
        List<Punto> list = new ArrayList<>();
        for (int k = 0; k < n; k++) {
            if (Math.abs(p[k].getX() - midPoint.getX()) < d) list.add(p[k]);
        }
        return Math.min(d, stripClosest(list.toArray(new Punto[0]), d));
    }

    private static double bruteForce(Punto[] p, int start, int end) {
        double min = Double.MAX_VALUE;
        if (p != null && end-start > 0) {
            for (int i = start; i < end-1; i++) {
                for (int j = i+1; j < end; j++) {
                    min = Math.min(min, dist(p[i], p[j]));
                }
            }
        }
        return min;
    }

    private static double dist(Punto p1, Punto p2) {
        return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
    }

    private static double stripClosest(Punto[] strip, double d) {
        double min = d;
        Arrays.sort(strip, (p1, p2) -> (int) (p1.getY() - p2.getY()));
        for (int i = 0; i < strip.length; i++) {
            for (int j = i+1; j < strip.length && (strip[j].getY() - strip[i].getY()) < min; j++) {
                double distance = dist(strip[i], strip[j]);
                if (distance < min) min = distance;
            }
        }
        return min;
    }
    //endregion

    public void setClaseSort(String p, int low, int high, boolean mirarCoordenadaX) {
        try {
            Class c = Class.forName(p);
            sorter = (Sort) c.getDeclaredConstructor().newInstance();
            switch(SortingTypes.valueOf(sorter.getNombreAlgoritmo())){
                case Javasort:
                    sorter = (Sort) c.getConstructor(boolean.class).newInstance(mirarCoordenadaX);
                    break;
                case Quicksort:
                    sorter = (Sort) c.getConstructor(int.class, int.class, Punto[].class).newInstance(low, high);
                    break;
                case Mergesort:
                    break;
                case Bucketsort:
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private DistanciaMinima min(DistanciaMinima d1, DistanciaMinima d2){
        if (d1.getDistanciaPuntos() < d2.getDistanciaPuntos()){
            return d1;
        }
        return d2;
    }
}
