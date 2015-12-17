package com.example.raldoron.testosmapp.AddPOI;

import com.example.raldoron.testosmapp.Constants;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by Raldoron on 02.12.15.
 */
public class OSMWriter extends XmlWriter {
    private boolean osmConform;
    private Changeset changeset;

    public OSMWriter(PrintWriter out) {
        super(out);
    }

    public void setChangeset(Changeset cs) {
        this.changeset = cs;
    }

    public void header() {
        out.println("<?xml version='1.0' encoding='UTF-8'?>");
        out.print("<osm version='");
        out.print(Constants.OSM_API_VERSION);
        out.print("' generator='");
        out.print(Constants.OSM_CREATOR_INFO);
        out.println("'>");
    }
    public void footer() {
        out.println("</osm>");
    }

    public void osmChangeHeader() {
        out.println("<?xml version='1.0' encoding='UTF-8'?>");
        out.print("<osmChange version='");
        out.print(Constants.OSM_API_VERSION);
        out.print("' generator='");
        out.print(Constants.OSM_CREATOR_INFO);
        out.println("'>");
    }

    public void osmChangeActionOpen(String action) {
        out.println("  <"+action+">");
    }

    public void osmChangeActionClose(String action) {
        out.println("  </"+action+">");
    }

    public void osmChangeFooter() {
        out.println("</osmChange>");
    }


    public void visit(OSMNode node) {
        out.println("<node");
        addAttributes(node);
        out.print(">");
        addTags(node);
        out.println("</node>");
    }

    public void visit(Changeset cs) {
        out.println("  <changeset>");
        out.print("    <tag k='created_by' v='");
        out.print(Constants.OSM_CREATOR_INFO);
        out.println("' />");
        out.println("  </changeset>");
    }


    private static final Comparator<Map.Entry<String, String>> byKeyComparator = new Comparator<Map.Entry<String,String>>() {
        public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
            return o1.getKey().compareTo(o2.getKey());
        }
    };

    private void addTags(OSMNode node) {
        String name = node.getName();
        String addr_h = node.get_addr_housenumber();
        String addr_s = node.get_addr_street();
        String website = node.get_website();
        String phone = node.get_phone();
        String open_hours = node.get_opening_hours();
        String description = node.get_description();
        //String cmTypeName = node.getType();
        //List<TagItem> cmTypeTags = OsmDriver.getInstance().getTagging(cmTypeName);

        if (name != null)
            out.println("    <tag k='name' v='"+XmlWriter.encode(name)+ "' />");
        if (addr_h != null)
            out.println("    <tag k='addr:housenumber' v='"+XmlWriter.encode(addr_h)+ "' />");
        if (addr_s != null)
            out.println("    <tag k='addr:street' v='"+XmlWriter.encode(addr_s)+ "' />");
        if (website != null)
            out.println("    <tag k='website' v='"+XmlWriter.encode(website)+ "' />");
        if (phone != null)
            out.println("    <tag k='phone' v='"+XmlWriter.encode(phone)+ "' />");
        if (open_hours != null)
            out.println("    <tag k='opening_hours' v='"+XmlWriter.encode(open_hours)+ "' />");
        if (description != null)
            out.println("    <tag k='description' v='"+XmlWriter.encode(description)+ "' />");
        /*
        for (TagItem tag : cmTypeTags) {
            out.println("    <tag k='"+XmlWriter.encode(tag.getKey())+"' v='"+XmlWriter.encode(tag.getValue())+ "' />");
        }
        */

        if (node.hasTags()) {
            List<Map.Entry<String, String>> entries = new ArrayList<Map.Entry<String,String>>(node.getTags().entrySet());
            Collections.sort(entries, byKeyComparator);
            for (Map.Entry<String, String> e : entries) {
                if (!("created_by".equals(e.getKey()))) {
                    out.println("    <tag k='"+ XmlWriter.encode(e.getKey()) +
                            "' v='"+XmlWriter.encode(e.getValue())+ "' />");
                }
            }
        }

    }

    /**
     * Add the common part as the form of the tag as well as the XML attributes
     * id, action, user, and visible.
     */
    private void addAttributes(OSMNode node) {
        if (node.getId() != 0) {
            out.print(" id='"+ node.getId()+"'");
        } else
            throw new IllegalStateException(String.format("Unexpected id 0 for osm primitive found"));

        if (node.getVersion() != 0) {
            out.print(" version='"+node.getVersion()+"'");
        }

        if (this.changeset != null && this.changeset.getId() != 0) {
            out.print(" changeset='"+this.changeset.getId()+"'" );
        }

        out.print(" lat='"+node.getCoordinates().getLatitudeE6()/1.E6+"' lon='"+node.getCoordinates().getLongitudeE6()/1.E6+"'");

        Map<String, String> otherAttributes = node.getAttributes();
        for(Map.Entry<String, String> keyValuePair : otherAttributes.entrySet()) {
            out.print(" "+keyValuePair.getKey()+"='"+keyValuePair.getValue()+"'");
        }
    }

    public void close() {
        out.close();
    }

    public void flush() {
        out.flush();
    }
}
