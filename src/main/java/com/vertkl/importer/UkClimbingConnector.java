package com.vertkl.importer;

import ixcode.platform.http.client.Http;
import ixcode.platform.http.representation.Representation;
import org.htmlcleaner.ContentNode;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static ixcode.platform.http.protocol.UriFactory.uri;
import static java.lang.String.format;

public class UkClimbingConnector {

    private static final String CRAG_URI_TEMPLATE = "http://www.ukclimbing.com/logbook/crag.php?id=%d";

    public UkClimbingCrag loadCrag(int cragId) {

        URI uri = uri(format(CRAG_URI_TEMPLATE, cragId));
        Representation representation = new Http().GET().from(uri);

        if (representation.<String>getEntity().contains("that entry does not exist in our crags database")) {
            return null;
        }


        TagNode root = new HtmlCleaner().clean(representation.<String>getEntity());
        TagNode h1 = root.findElementByName("h1", true);
        String cragName = h1.getChildren().get(0).toString().trim();

        List<UkClimbingRoute> routes = new ArrayList<UkClimbingRoute>();

        List<TagNode> links = root.getElementListByName("a", true);
        for (TagNode node : links) {
            String href = node.getAttributeByName("href");
            if (href.startsWith("c.php?")) {
                routes.add(loadRoute(node, href));
            }
        }

        return new UkClimbingCrag(cragId, uri, cragName, routes);
    }

    private UkClimbingRoute loadRoute(TagNode node, String href) {
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
        int id = Integer.parseInt(href.substring(8));
        return new UkClimbingRoute(id, name, grade);
    }

}