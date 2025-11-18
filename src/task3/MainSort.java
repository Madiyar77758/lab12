package task3;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class MainSort {

    public static void main(String[] args) {

        final int SIZE = 1_000_000;

        Random rnd = new Random();
        int[] arr = new int[SIZE];

        for (int i = 0; i < SIZE; i++)
            arr[i] = rnd.nextInt(100000);

        System.out.println("=== Параллельная сортировка слиянием ===");
        System.out.println("Размер массива: " + SIZE + " элементов");
        System.out.println("Порог: 10,000\n");

        int[] arrCopy = arr.clone();

        long start1 = System.currentTimeMillis();
        Arrays.sort(arrCopy);
        long end1 = System.currentTimeMillis();

        System.out.println("Стандартная сортировка: " + (end1 - start1) + " мс");

        ForkJoinPool pool = ForkJoinPool.commonPool();
        MergeSortTask task = new MergeSortTask(arr);

        long start2 = System.currentTimeMillis();
        int[] parallelSorted = pool.invoke(task);
        long end2 = System.currentTimeMillis();

        System.out.println("Параллельная сортировка: " + (end2 - start2) + " мс");

        System.out.println("\nПроверка корректности: " +
                Arrays.equals(parallelSorted, arrCopy));
    }
}
