/* Created by andreea on 26/03/2020 */
package Infrastructure.SortingAlgorithms;

import Domain.Punto;

import java.util.*;

public class Bucketsort extends Sort{

    private List<List<Punto>> buckets;
    private Comparator<Integer> comparator;

    public Bucketsort() {
        nombreAlgoritmo = "Bucketsort";
        comparator = (p1, p2) -> {
            if (mirarCoordenadaX) {
                return Double.compare(puntosOriginales[p1 - 1].getX(), puntosOriginales[p2 - 1].getX());
            }
            return Double.compare(puntosOriginales[p1 - 1].getY(), puntosOriginales[p2 - 1].getY());
        };
    }

    @Override
    public void sort(Punto[] puntos) {
        this.puntos = puntos;
        List<Integer> idOrdenados = sort(Arrays.asList(puntos));

        int index = 0;

        for (Integer id : idOrdenados) {
            puntos[index] = puntosOriginales[id - 1];
            index++;
        }
    }

    private List<Integer> concatenateSortedBuckets(List<List<Integer>> buckets){
        List<Integer> sortedArray = new LinkedList<>();
        for(List<Integer> bucket : buckets){
            sortedArray.addAll(bucket);
        }
        sortedArray.sort(comparator);
        return sortedArray;
    }

    private List<List<Integer>> splitIntoUnsortedBuckets(List<Integer> initialList){

        Collections.sort(initialList);
        final double max = initialList.get(initialList.size() - 1);
        final int numberOfBuckets = (int) Math.sqrt(initialList.size());

        List<List<Integer>> buckets = new ArrayList<>();
        for(int i = 0; i < numberOfBuckets; i++) buckets.add(new ArrayList<>());

        //distribute the data
        for (Integer i : initialList) {
            int j = hash(i, max, numberOfBuckets);
            buckets.get(hash(i, max, numberOfBuckets)).add(i);
        }

        return buckets;
    }

    private static int hash(double i, double max, int numberOfBuckets) {
        return (int) ( i / max * (numberOfBuckets - 1));
    }

    private List<Integer> sort(List<Punto> arrayToSort) {
        Integer[] arrayPuntosId = new Integer[arrayToSort.size()];
        int index = 0;
        for (Punto punto: arrayToSort) {
            arrayPuntosId[index] = punto.getId();
            index++;
        }

        List<List<Integer>> buckets = splitIntoUnsortedBuckets(Arrays.asList(arrayPuntosId));

        for(List<Integer> bucket  : buckets){
            bucket.sort(comparator);
        }
        return concatenateSortedBuckets(buckets);
    }
}
