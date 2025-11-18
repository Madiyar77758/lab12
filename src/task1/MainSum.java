package task1;

import java.util.concurrent.ForkJoinPool;

public class MainSum {

    public static void main(String[] args) {

        final int SIZE = 10_000_000;
        int[] arr = new int[SIZE];

        for (int i = 0; i < SIZE; i++)
            arr[i] = i + 1;

        System.out.println("=== Параллельное суммирование массива ===");
        System.out.println("Размер массива: " + SIZE + " элементов");
        System.out.println("Порог разделения: 100,000 элементов\n");

        long start1 = System.currentTimeMillis();
        long seqSum = 0;
        for (int v : arr) seqSum += v;
        long end1 = System.currentTimeMillis();

        System.out.println("Последовательное суммирование:");
        System.out.println("Результат: " + seqSum);
        System.out.println("Время выполнения: " + (end1 - start1) + " мс\n");

        ForkJoinPool pool = ForkJoinPool.commonPool();
        ArraySumTask task = new ArraySumTask(arr, 0, arr.length);

        long start2 = System.currentTimeMillis();
        long parallelSum = pool.invoke(task);
        long end2 = System.currentTimeMillis();

        System.out.println("Параллельное суммирование (Fork/Join):");
        System.out.println("Результат: " + parallelSum);
        System.out.println("Время выполнения: " + (end2 - start2) + " мс");

        System.out.println("\nУскорение: " +
                String.format("%.2f", (double)(end1 - start1) / (end2 - start2)) + "x");
    }
}
