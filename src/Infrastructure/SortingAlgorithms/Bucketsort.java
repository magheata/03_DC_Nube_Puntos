/* Created by andreea on 26/03/2020 */
package Infrastructure.SortingAlgorithms;

import Domain.Punto;

import java.util.*;

public class Bucketsort extends Sort{

    private List<List<Punto>> buckets;
    private final Comparator<Punto> comparator;

    public Bucketsort() {
        nombreAlgoritmo = "Bucketsort";
        comparator = (p1, p2) -> {
            if (mirarCoordenadaX) {
                return Double.compare(p1.getX(), p2.getX());
            }
            return Double.compare(p1.getY(), p2.getY());
        };
    }

    public Bucketsort(Punto[] puntos, Comparator<Punto> comparator) {
        this.comparator = comparator;
        nombreAlgoritmo = "Bucketsort";
    }

    @Override
    public void sort(Punto[] puntos) {
        puntos = (Punto[]) sort(Arrays.asList(puntos)).toArray();
    }

    private List<Punto> concatenateSortedBuckets(List<List<Punto>> buckets){
        List<Punto> sortedArray = new LinkedList<>();
        for(List<Punto> bucket : buckets){
            sortedArray.addAll(bucket);
        }
        return sortedArray;
    }

    private List<List<Punto>> splitIntoUnsortedBuckets(List<Punto> initialList){

        final double max = findMax(initialList);
        final int numberOfBuckets = (int) Math.sqrt(initialList.size());

        List<List<Punto>> buckets = new ArrayList<>();
        for(int i = 0; i < numberOfBuckets; i++) buckets.add(new ArrayList<>());

        //distribute the data
        for (Punto i : initialList) {
            if (mirarCoordenadaX){
                buckets.get(Math.abs(hash(i.getX(), max, numberOfBuckets))).add(i);
            } else {
                buckets.get(Math.abs(hash(i.getY(), max, numberOfBuckets))).add(i);
            }
        }
        return buckets;
    }

    private double findMax(List<Punto> input){
        double m = Double.MIN_VALUE;
        for (Punto i : input){
            if (mirarCoordenadaX){
                m = Math.max(i.getX(), m);
            } else {
                m = Math.max(i.getY(), m);
            }
        }
        return m;
    }

    private static int hash(double i, double max, int numberOfBuckets) {
        return (int) ( i / max * (numberOfBuckets - 1));
    }

    private List<Punto> sort(List<Punto> arrayToSort) {
        List<List<Punto>> buckets = splitIntoUnsortedBuckets(arrayToSort);

        for(List<Punto> bucket  : buckets){
            bucket.sort(comparator);
        }
        return concatenateSortedBuckets(buckets);
    }
}
