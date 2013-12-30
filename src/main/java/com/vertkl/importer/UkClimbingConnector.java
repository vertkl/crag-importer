package com.vertkl.importer;

import ixcode.platform.http.client.Http;
import ixcode.platform.http.representation.Representation;
import org.htmlcleaner.ContentNode;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static ixcode.platform.http.protocol.UriFactory.uri;
import static java.lang.String.format;

public class UkClimbingConnector {

    private static final String CRAG_URI_TEMPLATE = "http://www.ukclimbing.com/logbook/crag.php?id=%d";
    public static final String BASE_PATH = "http://www.ukclimbing.com/logbook/";

    public UkClimbingCrag loadCrag(int cragId) {

        URI uri = uri(format(CRAG_URI_TEMPLATE, cragId));
        Representation representation = new Http().GET().from(uri);

        if (representation.<String>getEntity().contains("that entry does not exist in our crags database")) {
            return null;
        }


        TagNode root = new HtmlCleaner().clean(representation.<String>getEntity());
        String cragName = loadCragName(root);

        UkClimbingArea area = null;
        List<UkClimbingRoute> routes = new ArrayList<UkClimbingRoute>();
        List<UkClimbingGuideBook> guideBooks = new ArrayList<UkClimbingGuideBook>();

        List<TagNode> links = root.getElementListByName("a", true);
        for (TagNode node : links) {
            String href = node.getAttributeByName("href");
            if (href.startsWith("c.php?")) {
                routes.add(loadRoute(node, href));
            } else if (href.startsWith("book.php?")) {
                guideBooks.add(loadGuideBook(node, href));
            }
            String title = node.getAttributeByName("title");
            if ("List crags in this area".equals(title)) {
                area = loadArea(node);
            }
        }

        CragDescription cragDescription = loadCragDescription(root);

        return new UkClimbingCrag(cragId, uri, cragName,
                                  area, routes, guideBooks,
                                  cragDescription);
    }

    private static CragDescription loadCragDescription(TagNode root) {
        String features = null;
        String faces = null;
        String altitude = null;
        String rockType = null;
        try {
            Object[] results = root.evaluateXPath("//tr/td/p/b");
            for (Object node : results) {
                TagNode tagNode = (TagNode) node;
                String propertyName = getContentFromNode(tagNode).toString();
                if ("Crag features".equals(propertyName)) {
                    features = cleanUpValue(tagNode.getParent().getAllChildren().get(3));
                } else if ("Faces".equals(propertyName)) {
                    faces = cleanUpValue(tagNode.getParent().getAllChildren().get(8));
                } else if ("Altitude".equals(propertyName)) {
                    altitude = cleanUpValue(tagNode.getParent().getAllChildren().get(6));
                } else if ("Rocktype".equals(propertyName)) {
                    Object o = tagNode.getParent().getAllChildren().get(4);
                    rockType = cleanUpValue(o);
                }
            }
            return new CragDescription(features, faces, altitude, rockType);
        } catch (XPatherException e) {
            throw new RuntimeException(e);
        }

    }

    private static String cleanUpValue(Object o) {
        String value = o.toString().trim();
        int trimIndex = value.indexOf("&");
        if (trimIndex > -1) {
            return (value).substring(0, trimIndex).trim();
        }
        return value;
    }


    private static String loadCragName(TagNode root) {
        TagNode h1 = root.findElementByName("h1", true);
        return cleanUpValue(h1.getAllChildren().get(0));
    }

    private static UkClimbingGuideBook loadGuideBook(TagNode node, String href) {
        String title = getContentFromNode(node).toString();
        URI uri = uri(BASE_PATH + href);
        return new UkClimbingGuideBook(title, uri);
    }

    private static UkClimbingArea loadArea(TagNode node) {
        String uri = "" + node.getAttributeByName("href");
        return new UkClimbingArea(uri(BASE_PATH + uri), getContentFromNode(node).toString());
    }

    private static UkClimbingRoute loadRoute(TagNode node, String href) {
        String name = getContentFromNode(node).toString();

        String grade = null;
        if (node.getParent().getChildren().size() > 2) {
            TagNode gradeNode = (TagNode) node.getParent().getParent().getChildren().get(2);
            TagNode fontNode = (TagNode) gradeNode.getChildren().get(0);
            TagNode content = fontNode.getChildren().get(0);
            System.out.println(content);
            ContentNode contentNode = (ContentNode) getContentFromNode(fontNode);
            grade = cleanUpValue(contentNode);
        }

        if (grade == null) {
            TagNode gradeNode = (TagNode) node.getParent().getParent().getChildren().get(2);
            ContentNode contentNode = getContentFromNode(gradeNode);
            grade = cleanUpValue(contentNode);
        }
        int id = Integer.parseInt(href.substring(8));
        return new UkClimbingRoute(id, uri(BASE_PATH + href), name, grade);
    }

    private static ContentNode getContentFromNode(TagNode node) {
        Object child = node.getAllChildren().get(0);
        if (child instanceof TagNode) {
            return getContentFromNode((TagNode) child);
        }
        return (ContentNode) child;
    }



}