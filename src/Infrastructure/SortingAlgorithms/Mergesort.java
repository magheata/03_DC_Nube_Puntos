/**
 * @authors Miruna Andreea Gheata, Rafael Adrián Gil Cañestro
 */
package Infrastructure.SortingAlgorithms;

import Domain.Punto;


/**
 * Clase que implementa el Mergesort.
 */
public class Mergesort extends Sort{

    public Mergesort(){
        nombreAlgoritmo = "Mergesort";
    }

    @Override
    public void sort(Punto[] puntos){
        sort(puntos, 0, puntos.length - 1);
    }

    void sort(Punto arr[], int l, int r){
        if (l < r)
        {
            // Find the middle point
            int m = (l+r)/2;

            // Sort first and second halves
            sort(arr, l, m);
            sort(arr , m+1, r);

            // Merge the sorted halves
            merge(arr, l, m, r);
        }
    }

    void merge(Punto arr[], int l, int m, int r)
    {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;

        /* Create temp arrays */
        Punto L[] = new Punto[n1];
        Punto R[] = new Punto[n2];

        /*Copy data to temp arrays*/
        for (int i=0; i<n1; ++i)
            L[i] = arr[l + i];
        for (int j=0; j<n2; ++j)
            R[j] = arr[m + 1+ j];


        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarry array
        int k = l;
        while (i < n1 && j < n2)
        {
            if (mirarCoordenadaX){
                if (L[i].getX() <= R[j].getX())
                {
                    arr[k] = L[i];
                    i++;
                }
                else
                {
                    arr[k] = R[j];
                    j++;
                }
            } else {
                if (L[i].getY() <= R[j].getY())
                {
                    arr[k] = L[i];
                    i++;
                }
                else
                {
                    arr[k] = R[j];
                    j++;
                }
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1)
        {
            arr[k] = L[i];
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2)
        {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
}
