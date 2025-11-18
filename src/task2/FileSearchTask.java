package task2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class FileSearchTask extends RecursiveAction {

    private final File directory;
    private final String extension;
    private final List<String> results;

    public FileSearchTask(File directory, String extension, List<String> results) {
        this.directory = directory;
        this.extension = extension;
        this.results = results;
    }

    @Override
    protected void compute() {
        File[] files = directory.listFiles();
        if (files == null) return;

        List<FileSearchTask> subTasks = new ArrayList<>();

        for (File f : files) {
            if (f.isDirectory()) {
                FileSearchTask task = new FileSearchTask(f, extension, results);
                task.fork();
                subTasks.add(task);
            } else if (f.getName().endsWith(extension)) {
                synchronized (results) {
                    results.add(f.getAbsolutePath());
                }
            }
        }

        for (FileSearchTask t : subTasks)
            t.join();
    }
}
