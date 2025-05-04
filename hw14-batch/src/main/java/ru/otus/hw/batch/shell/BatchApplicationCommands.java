package ru.otus.hw.batch.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.batch.job.MigrationCache;

@ShellComponent
@RequiredArgsConstructor
public class BatchApplicationCommands {

    private final JobLauncher jobLauncher;

    private final Job migrateJob;

    private final MigrationCache migrationCache;

    @ShellMethod(value = "start migration", key = {"start"})
    public String migrate() throws Exception {
        migrationCache.init();
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(migrateJob, jobParameters);
        return "Миграция запущена успешно!";
    }
}
