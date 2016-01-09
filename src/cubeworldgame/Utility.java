package cubeworldgame;

import java.util.ArrayList;

public class Utility {

    // insertion sort for array
    public static void insertionSort(Comparable [] a) {

        for(int p = 1; p < a.length; p++) {

            Comparable tmp = a[p];
            int j = p;

            for(; j > 0 && tmp.compareTo(a[j - 1]) < 0; j--) {

                a[j] = a[j - 1];
            }

            a[j] = tmp;
        }
    }

    // insertion sort for ArrayList
    public static void insertionSort(ArrayList a) {

        for(int p = 1; p < a.size(); p++)
        {
            Comparable tmp = (Comparable)a.get(p);
            int j = p;

            for(; j > 0 && tmp.compareTo(a.get(j - 1)) < 0; j--) {

                a.set(j, a.get(j - 1));
            }

            a.set(j, tmp);
        }
    }
}
