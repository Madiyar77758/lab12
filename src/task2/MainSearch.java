package task2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class MainSearch {

    public static void main(String[] args) {

        String root = "./test_dir";
        File rootDir = new File(root);

        // Создаем тестовую структуру (если надо)
        createTestDirectories(rootDir);

        String ext = ".txt";
        System.out.println("=== Параллельный поиск файлов ===");
        System.out.println("Корневая директория: " + rootDir.getAbsolutePath());
        System.out.println("Искомое расширение: " + ext + "\n");

        List<String> results = new ArrayList<>();
        ForkJoinPool pool = ForkJoinPool.commonPool();
        FileSearchTask task = new FileSearchTask(rootDir, ext, results);

        long start = System.currentTimeMillis();
        pool.invoke(task);
        long end = System.currentTimeMillis();

        System.out.println("Найденные файлы:");
        for (int i = 0; i < results.size(); i++) {
            System.out.println((i + 1) + ". " + results.get(i));
        }

        System.out.println("\nВсего найдено: " + results.size());
        System.out.println("Время выполнения: " + (end - start) + " мс");
    }

    private static void createTestDirectories(File root) {
        root.mkdirs();
        try {
            new File(root, "file1.txt").createNewFile();
            new File(root, "file2.java").createNewFile();

            File sub1 = new File(root, "sub1");
            sub1.mkdir();
            new File(sub1, "file3.txt").createNewFile();

            File sub2 = new File(sub1, "deep");
            sub2.mkdir();
            new File(sub2, "file4.txt").createNewFile();

        } catch (Exception ignored) {}
    }
}
