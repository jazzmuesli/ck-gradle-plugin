package com.github.jazzmuesli.ck.gradle.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class MetricsPlugin implements Plugin<Project> {
    public void apply(Project project) {
        project.getTasks().create("ck", MetricsTask.class, (task) -> { 
            task.setProject(project);
        });
    }
}
