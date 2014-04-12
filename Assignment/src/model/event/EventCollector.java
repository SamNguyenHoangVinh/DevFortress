/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.event;

import java.util.LinkedHashMap;
import java.util.Set;

/**
 *
 * @author Hung
 */
public class EventCollector {
    
    private static EventCollector collector;
    
    private LinkedHashMap<String, Integer> events;

    private EventCollector() {
        events = new LinkedHashMap<String, Integer>();
    }
    
    public void add(String event) {
        if(events.containsKey(event)) {
            int num = events.get(event);
            events.put(event, num+1);
        } else {
            events.put(event, 1);
        }
    }
    
    public Set<String> getSet() {
        return events.keySet();
    }
    
    public void clear() {
        events.clear();
    }
    
    public static EventCollector getInstance() {
        if(collector == null) {
            collector = new EventCollector();
        }
        return collector;
    }

    public LinkedHashMap<String, Integer> getEvents() {
        return events;
    }
    
}
