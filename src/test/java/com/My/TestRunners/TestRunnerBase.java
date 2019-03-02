package com.My.TestRunners;

import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class TestRunnerBase {
    protected static void setProperties (String[] args, Class<?> classes) {
        String filename = "src/test/resources/config.properties";
        Properties prop = new Properties();
        OutputStream output = null;

        try {
            output = new FileOutputStream(filename);
            prop.setProperty("endpoint", "/rs/users");
            if (args.length>0) {
                prop.setProperty("port", args[0]);
            } else {
                prop.setProperty("port", "28092");
            }
            prop.store(output, null);
        } catch(IOException io){
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        JUnitCore junitCore = new JUnitCore();
        junitCore.addListener(new TextListener(System.out));
        junitCore.run(classes);
    }
}
