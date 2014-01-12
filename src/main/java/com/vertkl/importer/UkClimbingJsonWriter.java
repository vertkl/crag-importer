package com.vertkl.importer;

import ixcode.platform.serialise.JsonSerialiser;
import ixcode.platform.serialise.TransformToJson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import static java.lang.String.format;

public class UkClimbingJsonWriter {


    private final JsonSerialiser jsonSerialiser = new JsonSerialiser(new TransformToJson());

    public UkClimbingJsonWriter() {
    }


    public void writeTo(UkClimbingCrag crag, File outputDirectory) {
        File outputFile = generateFileFor(outputDirectory, crag);

        Writer out = null;
        try {
            outputFile.createNewFile();
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile)));

            out.write(jsonSerialiser.toJson(crag));

            out.flush();

        } catch (IOException e) {
            throw new RuntimeException("Could not write to output file [" + outputFile.getAbsolutePath() + "]");
        } finally {
            tryToClose(out);
        }
    }

    private static void tryToClose(Writer out) {
        if (out == null) {
            return;
        }
        try {
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static File generateFileFor(File outputDirectory, UkClimbingCrag crag) {
        return new File(outputDirectory, format("crag-%06d.json", crag.id));
    }


}