package com.github.jazzmuesli.ck.gradle.plugin;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskAction;


import com.github.mauricioaniche.ck.CK;

import java.io.File;
import java.util.Set;

public class MetricsTask extends DefaultTask {

	private Project project;
	public void setProject(Project project) {
		this.project = project;
	}

    @TaskAction
    void sayGreeting() {
        SourceSet main = project.getConvention().getPlugin(JavaPluginConvention.class).getSourceSets().getByName("main");
        Set<File> srcDirs = main.getAllJava().getSrcDirs();
        System.out.printf("srcDirs: " + srcDirs);
        srcDirs.forEach(x -> processSourceDirectory(x.getAbsolutePath()));
    }


    private void processSourceDirectory(String dirName) {
        try {
            if (new File(dirName).exists()) {
                MetricsWriter writer = createMetricsWriter(dirName);
                new CK().calculate(dirName, writer);
                writer.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected MetricsWriter createMetricsWriter(String dirName) {
        MetricsCSVWriter writer = new MetricsCSVWriter(dirName);
        return writer;
    }
}
