package com.vertkl.importer;

import org.apache.log4j.Logger;

import java.io.File;

public class UkClimbingImport {

    private static final Logger log = Logger.getLogger(UkClimbingImport.class);

    private final File outputDirectory;
    private final UkClimbingConnector ukClimbingConnector = new UkClimbingConnector();
    private final UkClimbingJsonWriter ukClimbingJsonWriter = new UkClimbingJsonWriter();

    public UkClimbingImport(File outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public void importAllCrags() {
        log.info("Importing all crags to: " + outputDirectory.getAbsolutePath());
        outputDirectory.mkdirs();

        for (int cragId=0; cragId<10;++cragId) {
            UkClimbingCrag ukClimbingCrag = ukClimbingConnector.loadCrag(cragId);
            if (ukClimbingCrag != null) {
                ukClimbingJsonWriter.writeTo(ukClimbingCrag, outputDirectory);
            } else {
                log.info("No crag for id " + cragId);
            }
        }
    }

    public static void main(String[] args) {
        new UkClimbingImport(new File(args[0])).importAllCrags();
    }
}