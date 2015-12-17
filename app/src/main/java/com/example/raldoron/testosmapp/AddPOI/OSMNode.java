package com.example.raldoron.testosmapp.AddPOI;

import android.location.Location;

import org.osmdroid.util.GeoPoint;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Raldoron on 23.11.15.
 */
public class OSMNode implements Tagged {

    private Map<String, String> _tags = new HashMap<String, String>();
    private Map<String, String> _attributes = new HashMap<String, String>();
    private long _id;
    private int _version;
    private GeoPoint _coordinates;
    private GeoPoint _previousCoordinates;
    private long _changesetId;
    // Properties, initialized from tags
    private String _name;
    private String _addr_housenumber;
    private String _addr_street;
    private String _website;
    private String _phone;
    private String _opening_hours;
    private String _description;

    //========================================
    /** Constructors */
    public OSMNode(final double aLatitude, final double aLongitude, Map<String, String> tags) {
        _coordinates = new GeoPoint(aLatitude, aLongitude);
        _tags = tags;
    }

    public OSMNode(final double aLatitude, final double aLongitude) {
        _coordinates = new GeoPoint(aLatitude, aLongitude);
    }

    public OSMNode(final int aLatitudeE6, final int aLongitudeE6, Map<String, String> tags) {
        this(aLatitudeE6/1.E6, aLongitudeE6/1.E6, tags);
    }

    public OSMNode(final Location aLocation, final Map<String, String> tags) {
        this(aLocation.getLatitude(), aLocation.getLongitude(), tags);
    }

    public OSMNode(final double aLatitude, final double aLongitude, final long id, final long changesetId, final int version) {
        this(aLatitude, aLongitude);
        _id = id;
        _changesetId = changesetId;
        _version = version;
    }

    public OSMNode(OSMNode poi) {
        if (poi._addr_housenumber != null)
            this._addr_housenumber = new String(poi._addr_housenumber);
        if (poi._addr_street != null)
            this._addr_street = new String(poi._addr_street);
        if (poi._attributes != null)
            this._attributes = new HashMap<String, String>(poi._attributes);
        this._changesetId = new Long(poi._changesetId);
        this._coordinates = new GeoPoint(poi._coordinates.getLatitudeE6(), poi._coordinates.getLongitudeE6());
        if (poi._description != null)
            this._description = new String(poi._description);
        this._id = new Long(poi._id);
        if (poi._name != null)
            this._name = new String(poi._name);
        if (poi._opening_hours != null)
            this._opening_hours = new String(poi._opening_hours);
        if (poi._phone != null)
            this._phone = new String(poi._phone);
        if (poi._tags != null)
            this._tags = new HashMap<String, String>(poi._tags);
        this._version = new Integer(poi._version);
        if (poi._website != null)
            this._website = new String(poi._website);
        /*
        this._draggable = new Boolean(poi._draggable);
        if (poi._type != null)
            this._type = new String(poi._type);
        this.hotSpot = new Point(poi.hotSpot);
        */
    }

    //========================================
    public String get_addr_housenumber() {
        return _addr_housenumber;
    }

    public void set_addr_housenumber(String addrHousenumber) {
        _addr_housenumber = checkString(addrHousenumber);
    }

    public String get_addr_street() {
        return _addr_street;
    }

    public void set_addr_street(String addrStreet) {
        _addr_street = checkString(addrStreet);
    }

    public String get_website() {
        return _website;
    }

    public void set_website(String website) {
        _website = checkString(website);
    }

    public String get_phone() {
        return _phone;
    }

    public void set_phone(String phone) {
        _phone = checkString(phone);
    }

    public String get_opening_hours() {
        return _opening_hours;
    }

    public void set_opening_hours(String openingHours) {
        _opening_hours = checkString(openingHours);
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String description) {
        _description = checkString(description);
    }

    public String getFullAddressString() {
        if ((_addr_housenumber == null) && (_addr_street == null))
            return null;

        String prefix = _addr_housenumber != null ? _addr_housenumber + " " : " ";
        String suffix = _addr_street != null ? _addr_street : "";

        return prefix + suffix;
    }
    //========================================
    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = checkString(name);
    }
    /*
    public String getType() {
        return _type;
    }
    public void setType(String type) {
        _type = type;
    }
    */
    public void setVersion(int version) {
        _version = version;
    }

    public int getVersion() {
        return _version;
    }

    public void addAttribute(final String key, final String value) {
        _attributes.put(key, value);
    }

    public Map<String, String> getAttributes() {
        return _attributes;
    }

    //========================================
    //Tagged interface implementation
    public void setTags(Map<String, String> tags) {
        _tags = tags;
    }

    public Map<String, String> getTags() {
        return _tags;
    }

    public void put(String key, String value) {
        _tags.put(key, value);
    }

    public String get(String key) {
        return _tags.get(key);
    }

    public void remove(String key) {
        _tags.remove(key);
    }

    public boolean hasTags() {
        return !_tags.isEmpty();
    }

    public Collection<String> keySet() {
        return _tags.keySet();
    }

    public void removeAll() {
        _tags.clear();
    }

    //========================================
    public long getId() {
        return this._id;
    }

    public void setId(long id) {
        this._id = id;
    }

    public void setChangesetId(long changesetId) {
        this._changesetId = changesetId;
    }

    public void setOsmIdAndVersion(long id, int version) {
        this._id = id;
        this._version = version;
    }
    //========================================

    public GeoPoint getCoordinates() {
        return this._coordinates;
    }

    public void setCoordinates(final GeoPoint gp) {
        this._coordinates = gp;
    }

    public GeoPoint getPrevCoordinates() {
        return this._previousCoordinates;
    }

    public void setPrevCoordinates(final GeoPoint gp) {
        this._previousCoordinates = gp;
    }
    //========================================
    //helper
    private String checkString(String parameter) {
        if (parameter.length() == 0)
            parameter = null;
        return parameter;
    }

    public void revertCoordinates() {
        this.setCoordinates(new GeoPoint(_previousCoordinates.getLatitudeE6(), _previousCoordinates.getLongitudeE6()));
    }
}
