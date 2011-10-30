package com.vertkl.importer;

import ixcode.platform.http.client.Http;
import ixcode.platform.http.representation.Representation;
import org.apache.log4j.Logger;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.junit.Test;

import java.util.List;

import static ixcode.platform.http.protocol.UriFactory.uri;
import static java.lang.String.format;

public class ImportSomeCragsTest {

    private static final Logger log = Logger.getLogger(ImportSomeCragsTest.class);

    private Http http = new Http();

    @Test
    public void grab_a_crag() {
        final String uriTemplate = "http://www.ukclimbing.com/logbook/crag.php?id=%d";

        Representation representation = http.GET().from(uri(format(uriTemplate, 20)));


        TagNode root = new HtmlCleaner().clean(representation.<String>getEntity());
        TagNode h1 = root.findElementByName("h1", true);
        String cragName = h1.getChildren().get(0).toString();

        List<TagNode> links = root.getElementListByName("a", true);
        for (TagNode node : links) {
            log.debug(node.getAttributeByName("href"));
        }

        log.debug("Crag Id   : " + 20);
        log.debug("Crag Name : " + cragName);


    }


}