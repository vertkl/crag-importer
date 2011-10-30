package com.vertkl.importer;

public class CragDescription {
    public final String description;
    public final String faces;
    public final String altitude;
    public final String rockType;

    CragDescription(String description, String faces, String altitude, String rockType) {
        this.description = description;
        this.faces = faces;
        this.altitude = altitude;
        this.rockType = rockType;
    }
}