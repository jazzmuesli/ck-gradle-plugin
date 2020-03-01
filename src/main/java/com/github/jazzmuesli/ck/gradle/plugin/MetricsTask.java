package com.github.jazzmuesli.ck.gradle.plugin;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.TaskAction;


import com.github.mauricioaniche.ck.CK;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

public class MetricsTask extends DefaultTask {

	private Project project;
	public void setProject(Project project) {
		this.project = project;
	}

    @TaskAction
	void processDirectories() {
		try {
			
			String projectDir = project.getProjectDir().getAbsolutePath();
			Path path = Paths.get(projectDir, "sourceDirs.csv");
			System.out.println("projectDir: " + projectDir + ", csvPath: " + path);
			CSVPrinter printer = new CSVPrinter(Files.newBufferedWriter(path), CSVFormat.DEFAULT
					.withHeader("sourceSetName", "dirName","processed").withSystemRecordSeparator().withDelimiter(';'));

			SourceSetContainer sourceSets = project.getConvention().getPlugin(JavaPluginConvention.class)
					.getSourceSets();
			
			for (Map.Entry<String, SourceSet> entry : sourceSets.getAsMap().entrySet()) {
				SourceSet sourceSet = entry.getValue();
				Set<File> srcDirs = sourceSet.getAllJava().getSrcDirs();
				System.out.println("Processing sourceSet " + entry.getKey() + " with srcDirs=" + srcDirs);
				for (File srcDir : srcDirs) {
					boolean processed = processSourceDirectory(srcDir.getAbsolutePath());
					printer.printRecord(entry.getKey(), srcDir.getAbsolutePath(), processed);
					printer.flush();
				}
			}
			printer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    private boolean processSourceDirectory(String dirName) {
        try {
            if (new File(dirName).exists()) {
                System.out.printf("processing: " + dirName);
                MetricsWriter writer = createMetricsWriter(dirName);
                new CK().calculate(dirName, writer);
                writer.finish();
                return true;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    protected MetricsWriter createMetricsWriter(String dirName) {
        MetricsCSVWriter writer = new MetricsCSVWriter(dirName);
        return writer;
    }
}
