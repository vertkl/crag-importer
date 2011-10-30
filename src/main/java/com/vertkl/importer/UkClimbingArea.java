package com.vertkl.importer;

import java.net.URI;

public class UkClimbingArea {
    private String name;
    private URI uri;

    public UkClimbingArea(URI uri, String name) {
        this.uri = uri;
        this.name = name;
    }
}