package com.vertkl.importer;

import org.apache.log4j.Logger;
import org.hamcrest.Matchers;
import org.junit.Test;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class UkClimbingConnectorTest {

    private static final Logger log = Logger.getLogger(UkClimbingConnectorTest.class);

    @Test
    public void grab_a_crag() {

        UkClimbingCrag ukClimbingCrag = new UkClimbingConnector().loadCrag(20);

        assertThat(ukClimbingCrag.id, is(20));
        assertThat(ukClimbingCrag.name, is("Baslow Edge"));
        assertThat(ukClimbingCrag.routes.size(), is(greaterThan(10)));
    }

    @Test
    public void returns_nothing_if_no_crag_is_found() {
        UkClimbingCrag ukClimbingCrag = new UkClimbingConnector().loadCrag(-1);

        assertThat(ukClimbingCrag, is(nullValue()));
    }


}