package fr.netapsys.core;

import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;

import java.io.File;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

/**
 * Génère dynamiquement le template Asciidoctor (assez basique)
 * Appelé quand tous les tests sont passés.
 */
public class AsciidoctorListener extends RunListener {

    public void testRunFinished(Result result) throws Exception {

        Path FROM = Paths.get("src/main/asciidoc/index.adoc");
        Path TO = Paths.get("target/generated-snippets/index.adoc");
        //overwrite existing file, if exists
        CopyOption[] options = new CopyOption[]{
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.COPY_ATTRIBUTES
        };
        Files.copy(FROM, TO, options);

        File[] files = new File("target/generated-snippets/").listFiles();
        StringBuilder contentTest = new StringBuilder();
        showFiles(files, contentTest, 0, "");


        Files.write(TO, contentTest.toString().getBytes(), StandardOpenOption.APPEND);
    }

    static void showFiles(File[] files, StringBuilder a, int level, String path) {
        for (File file : files) {
            if (file.isDirectory()) {
                String newpath;
                if (level == 0) {
                    a.append("\n\n= Resource " + file.getName().replace("-rest-test", ""));
                    newpath = file.getName();
                    showFiles(file.listFiles(), a, level + 1, newpath);
                }
                if (level == 1) {
                    newpath = path + "/" + file.getName();
                    a.append("\n\n== > " + file.getName());

                    a.append("\n\nlink:" + newpath + "/section.html" + "[]");
                }

            }
        }
    }
}
