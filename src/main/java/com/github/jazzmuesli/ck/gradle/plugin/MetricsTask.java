package com.github.jazzmuesli.ck.gradle.plugin;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.TaskAction;


import com.github.mauricioaniche.ck.CK;

import java.io.File;
import java.util.Map;
import java.util.Set;

public class MetricsTask extends DefaultTask {

	private Project project;
	public void setProject(Project project) {
		this.project = project;
	}

    @TaskAction
    void processDirectories() {
        SourceSetContainer sourceSets = project.getConvention().getPlugin(JavaPluginConvention.class).getSourceSets();
        for (Map.Entry<String, SourceSet> entry : sourceSets.getAsMap().entrySet()) {
            SourceSet sourceSet = entry.getValue();
            Set<File> srcDirs = sourceSet.getAllJava().getSrcDirs();
            System.out.println("Processing sourceSet " +  entry.getKey() + " with srcDirs=" + srcDirs);
            srcDirs.forEach(x -> processSourceDirectory(x.getAbsolutePath()));

        }
    }


    private void processSourceDirectory(String dirName) {
        try {
            if (new File(dirName).exists()) {
                System.out.printf("processing: " + dirName);
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
