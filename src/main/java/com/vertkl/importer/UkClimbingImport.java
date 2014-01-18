package com.vertkl.importer;


import org.ixcode.logback.joda.LogbackJodaContext;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.File;

import static org.ixcode.logback.joda.LogbackJodaContext.configureLoggerContextWithJoda;

public class UkClimbingImport {

    private static final Logger log = LoggerFactory.getLogger(UkClimbingImport.class);

    private final File outputDirectory;
    private final UkClimbingConnector ukClimbingConnector = new UkClimbingConnector();
    private final UkClimbingJsonWriter ukClimbingJsonWriter = new UkClimbingJsonWriter();

    public UkClimbingImport(File outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public void importAllCrags() {
        log.info("Importing all crags to: " + outputDirectory.getAbsolutePath());
        outputDirectory.mkdirs();

        for (int cragId=20029; cragId<20030;++cragId) {
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