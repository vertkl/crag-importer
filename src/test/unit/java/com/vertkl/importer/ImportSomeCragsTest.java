package com.vertkl.importer;

import org.apache.log4j.Logger;
import org.junit.Test;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

public class ImportSomeCragsTest {

    private static final Logger log = Logger.getLogger(ImportSomeCragsTest.class);

    @Test
    public void grab_a_crag() {

        UkClimbingImport.UkClimbingCrag ukClimbingCrag = new UkClimbingImport().loadCrag(20);

        assertThat(ukClimbingCrag.id, is(20));
        assertThat(ukClimbingCrag.name, is("Baslow Edge"));
        assertThat(ukClimbingCrag.routes.size(), is(greaterThan(10)));
    }


}