package com.vertkl.importer;

import java.net.URI;
import java.util.List;

public class UkClimbingCrag {
    public final String is = "crag";
    public final String name;
    public final URI uri;
    public final int id;

    public final List<UkClimbingRoute> routes;

    public UkClimbingCrag(int id, URI uri, String name, List<UkClimbingRoute> routes) {
        this.id = id;
        this.uri = uri;
        this.name = name;
        this.routes = routes;
    }
}