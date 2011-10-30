package com.vertkl.importer;

import ixcode.platform.http.client.Http;
import ixcode.platform.http.representation.Representation;
import org.apache.log4j.Logger;
import org.htmlcleaner.ContentNode;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.junit.Test;

import java.util.ArrayList;
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
        String areaName = h1.getChildren().get(0).toString();

        List<String> routes = new ArrayList<String>();

        List<TagNode> links = root.getElementListByName("a", true);
        for (TagNode node : links) {
            String href = node.getAttributeByName("href");
            if (href.startsWith("c.php?")) {
                String name = node.getChildren().get(0).toString();

                String grade = null;
                if (node.getParent().getChildren().size() > 2) {
                    TagNode gradeNode = (TagNode) node.getParent().getParent().getChildren().get(2);
                    TagNode fontNode = (TagNode) gradeNode.getChildren().get(0);
                    ContentNode contentNode = (ContentNode)fontNode.getChildren().get(0);
                    grade = contentNode.toString().trim();
                }

                if (grade == null) {
                    TagNode gradeNode = (TagNode) node.getParent().getParent().getChildren().get(2);
                    ContentNode contentNode = (ContentNode)gradeNode.getChildren().get(0);
                    grade = contentNode.toString().trim();
                }
                String id = href.substring(8);
                routes.add(format("%s : %s : %s", name, grade, id));
            }
            //log.debug(node.getAttributeByName("href") + " :  " + node.getAttributeByName("rel") + " : " + node.getChildren().get(0));
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (String route : routes) {
            sb.append(route).append("\n");
        }

        log.debug("Crag Id   : " + 20);
        log.debug("Crag Name : " + areaName);
        log.debug("Routes    : " + sb);

    }


}