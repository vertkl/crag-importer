package com.vertkl.importer;

import java.net.URI;
import java.util.List;

public class UkClimbingCrag {
    public final String is = "crag";
    public final String name;
    public final URI uri;
    public final int id;
    public final String description;
    public final String altitude;
    public final String aspect;
    public final String rockType;

    public final UkClimbingArea area;

    public final List<UkClimbingRoute> routes;
    private List<UkClimbingGuideBook> guideBooks;

    public UkClimbingCrag(int id,
                          URI uri,
                          String name,
                          UkClimbingArea area,
                          List<UkClimbingRoute> routes,
                          List<UkClimbingGuideBook> guideBooks, CragDescription details) {
        this.id = id;
        this.uri = uri;
        this.name = name;
        this.area = area;
        this.description = details.description;
        this.altitude = details.altitude;
        this.aspect = details.faces;
        this.rockType = details.rockType;
        this.routes = routes;
        this.guideBooks = guideBooks;

    }
}