package com.vertkl.importer;

import java.net.URI;

public class UkClimbingRoute {
    private final URI uri;
    public final String name;
    public final String grade;
    public final int id;


    public UkClimbingRoute(int id, URI uri, String name, String grade) {
        this.id = id;
        this.uri = uri;
        this.name = name;
        this.grade = grade;
    }
}