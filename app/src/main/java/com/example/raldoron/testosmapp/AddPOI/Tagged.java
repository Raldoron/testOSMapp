package com.example.raldoron.testosmapp.AddPOI;

/**
 * Created by Raldoron on 23.11.15.
 */

import java.util.Collection;
import java.util.Map;

/**
 * Objects implement Tagged if they provide a map of key/value pairs.
 *
 *
 */
public interface Tagged {
    /**
     * Sets the map of key/value pairs
     *
     * @param tags the map of key value pairs. If null, reset to the empty map.
     */
    void setTags(Map<String,String> tags);

    /**
     * Replies the map of key/value pairs. Never null, but may be the empty map.
     *
     * @return the map of key/value pairs
     */
    Map<String,String> getTags();

    /**
     * Sets a key/value pairs
     *
     * @param key the key
     * @param value the value. If null, removes the key/value pair.
     */
    void put(String key, String value);

    /**
     * Replies the value of the given key; null, if there is no value for this key
     *
     * @param key the key
     * @return the value
     */
    String get(String key);

    /**
     * Removes a given key/value pair
     *
     * @param key the key
     */
    void remove(String key);

    /**
     * Replies true, if there is at least one key/value pair; false, otherwise
     *
     * @return true, if there is at least one key/value pair; false, otherwise
     */
    boolean hasTags();

    /**
     * Replies the set of keys
     *
     * @return the set of keys
     */
    Collection<String> keySet();

    /**
     * Removes all tags
     */
    void removeAll();
}
