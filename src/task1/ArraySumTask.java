package task1;

import java.util.concurrent.RecursiveTask;

public class ArraySumTask extends RecursiveTask<Long> {

    private final int[] array;
    private final int start;
    private final int end;

    private static final int THRESHOLD = 100_000;

    public ArraySumTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        int length = end - start;

        if (length <= THRESHOLD) {
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum;
        }

        int mid = start + length / 2;

        ArraySumTask left = new ArraySumTask(array, start, mid);
        ArraySumTask right = new ArraySumTask(array, mid, end);

        left.fork(); // запуск в другом потоке

        long rightResult = right.compute(); // выполняем здесь
        long leftResult = left.join(); // ждём левую подзадачу

        return leftResult + rightResult;
    }
}
