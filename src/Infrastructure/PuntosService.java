/**
 * @authors Miruna Andreea Gheata, Rafael Adrián Gil Cañestro
 */
package Infrastructure;

import Application.DCController;
import Application.DTO.DistanciaMinima;
import Domain.Interfaces.IPuntosService;
import Domain.Nube;
import Domain.Punto;
import Infrastructure.SortingAlgorithms.Sort;

import java.util.*;

/**
 * Clase encargada de realizar la búsqueda de la distancia mínima entre los puntos de la nube. El proceso de cálculo se
 * realiza mediante un Thread para agilizar los cálculos y así no bloquear a la GUI.
 */
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

    /**
     * Método que sirve para instanciar a la clase que toca del tipo Sorter según el botón que haya seleccionado
     * el usuario
     * @param p
     */
    public void setClaseSort(String p) {
        try {
            Class c = Class.forName(p);
            sorter = (Sort) c.getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setNubePuntos(Nube nubePuntos) {
        this.nubePuntos = nubePuntos;
        isPuntosOrdenados = false;
    }

    /**
     * Proceso que inicia el hilo
     */
    public void start() {
        worker = new Thread(this);
        worker.start();
    }

    @Override
    public void run() {
        long tiempoTardado;
        DistanciaMinima distanciaMinima = null;
        // Seleccionamos el algoritmo que el usuario quiere
        switch (algoritmoElegido){
            case 0:
                int n = nubePuntos.getCantidad();
                controller.setMax(n * (n-1) / 2);
                tiempoTardado = System.currentTimeMillis();
                distanciaMinima = naive(nubePuntos.getPuntos().clone(), nubePuntos.getCantidad());
                distanciaMinima.setTiempoTotal(System.currentTimeMillis() - tiempoTardado);
                break;
            case 1:
                // Se guardan los puntos originales porque en el algoritmo de Bucketsort son necesarios
                sorter.setPuntosOriginales(nubePuntos.getPuntos().clone());
                // En el caso de los algoritmos recursivos, se deben ordenar los puntos por las coordenadas. Se mira si ya se
                // han ordenado para no volver a ordenarlos

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
        // Se avisa al controlador de que se tiene que notificar al usuario de la solución
        controller.setPuntoSolucion(distanciaMinima);
    }

    /**
     * Método iterativo que calcula la distancia entre los puntos. Es de coste n^2 ya que mira todos los puntos con todos
     * los demás.
     * @param puntos
     * @param n
     * @return
     */
    @Override
    public DistanciaMinima naive(Punto[] puntos, int n) {
        if(n == 100000 && algoritmoElegido == 0){
            controller.barraGo();
        } else if (algoritmoElegido != 0){
            controller.barraGo();
        }

        DistanciaMinima distanciaMinima = null;
        double min_distance = Double.MAX_VALUE;
        double computedDistance;
        for (int i = 0; i < n; i++){
            Punto puntoActual = puntos[i];
            for (int j = i + 1; j < n; j++){
                computedDistance = puntoActual.calcularDistanciaEuclidea(puntos[j]);
                //Aumento Barra
                if(algoritmoElegido == 0 && nubePuntos.getCantidad() != 100000){
                 controller.barraAct();
                }
                if (computedDistance < min_distance){
                    min_distance = computedDistance;
                    distanciaMinima = new DistanciaMinima(puntoActual, puntos[j], computedDistance);
                }
            }
        }
        if(n == 100000 && algoritmoElegido == 0){
            controller.barraStop();
        } else if (algoritmoElegido != 0){
            controller.barraStop();
        }
        return distanciaMinima;
    }

    /**
     * Método recursivo que busca la distancia mínima aplicando el concepto de Divide & Conquer. Divide de manera recursiva
     * el total de puntos y busca para cada mitad la solución local, al salir de la recursividad se encontrará la solcuión
     * global.
     * @param puntos
     * @param n
     * @return
     */
    @Override
    public DistanciaMinima divideConquerOnlogn2(Punto[] puntos, int n){
        controller.barraGo();
        // Si la n es pequeña se llama al método iterativo
        if (n <= 3){
            return naive(puntos, n);
        }

        int mid = n / 2;

        Punto puntoMedio = puntos[mid];

        // Separamos los puntos por las coordenadas X
        Punto[] puntosXIzquierda = Arrays.copyOfRange(puntos, 0, (n + 1)/2);
        Punto[] puntosXDerecha = Arrays.copyOfRange(puntos, n/2, n);

        // Se calcula la solución de la izquierda
        DistanciaMinima distanciaMinimaL = divideConquerOnlogn2(puntosXIzquierda, mid);

        // Se calcula la solución de la derecha
        DistanciaMinima distanciaMinimaR = divideConquerOnlogn2(puntosXDerecha, n - mid);

        // La mínima de las anteriores es una posible solución local
        DistanciaMinima distanciaMinima = min(distanciaMinimaL, distanciaMinimaR);

        // Array en el que se guardan los puntos de la mitad del array total, puede ser que aquí se encuentre la
        // solución global
        ArrayList<Punto> stripList = new ArrayList<>();

        int puntosStrip = 0;

        // Calculamos los puntos que estén entre la mitad de los puntos y que además tengan una distancia mínima a la ya encontrada
        for (int i = 0; i < n; i++){
            if (Math.abs(puntos[i].getX() - puntoMedio.getX()) < distanciaMinima.getDistanciaPuntos()){
                stripList.add(puntos[i]);
                puntosStrip++;
            }
        }
        controller.barraStop();
        // Retornamos la distancia minima de la encontrada de las dos mitades o del medio
        return min(distanciaMinima, stripClosest(toArray(stripList), puntosStrip, distanciaMinima.getDistanciaPuntos(), false));
    }

    /**
     * Método recursivo que busca la distancia mínima aplicando el concepto de Divide & Conquer. Divide de manera recursiva los puntos
     * y calcula las soluciones locales para después encontrar la solución global. A diferencia del anterior este tiene los puntos
     * ordenador tanto por eje X como por eje Y.
     * @param puntosX
     * @param puntosY
     * @param n
     * @return
     */
    @Override
    public DistanciaMinima divideConquerOnlogn(Punto[] puntosX, Punto[] puntosY, int n){
        controller.barraGo();
        // Si la n es pequeña se llama al método iterativo
        if (n <= 3){
            return naive(puntosX, n);
        }

        int mitad = n / 2;

        Punto puntoMedio = puntosX[mitad];

        ArrayList<Punto> puntosIzq = new ArrayList<>();
        ArrayList<Punto> puntosDer = new ArrayList<>();

        // Se separan los puntos por coordenada Y en dos mitades
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
        controller.barraStop();
        return min(distanciaMinima, stripClosest(toArray(stripList), puntosStrip, distanciaMinima.getDistanciaPuntos(), true));
    }

    /**
     * Método que se encarga de encontar una distancia menor a la ya encontrada en las dos mitades. Esta distancia se puede
     * encontrar entre los puntos que están a una distancia menor a la encontrada en el array
     * @param strip
     * @param cantidad
     * @param distanciaPuntos
     * @param isNLogN
     * @return
     */
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

    /**
     * Método que ordena la nube de puntos por coordenadas
     * @param puntos
     * @return
     */
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

    /**
     * Método que transforma una lista de Puntos en un array de Puntos
     * @param puntosLista
     * @return
     */
    private Punto[] toArray(ArrayList<Punto> puntosLista){
        Punto[] puntosArray = new Punto[puntosLista.size()];
        for (int i = 0; i < puntosArray.length; i++){
            puntosArray[i] = puntosLista.get(i);
        }
        return puntosArray;
    }

    /**
     * Método que devuelve la distancia minima
     * @param d1
     * @param d2
     * @return
     */
    private DistanciaMinima min(DistanciaMinima d1, DistanciaMinima d2){
        if (d1.getDistanciaPuntos() < d2.getDistanciaPuntos()){
            return d1;
        }
        return d2;
    }

    /**
     * Método que setea el tipo de algoritmo elegido
     * @param algoritmoElegido
     */
    public void setAlgoritmoElegido(int algoritmoElegido) {
        this.algoritmoElegido = algoritmoElegido;
    }
}
