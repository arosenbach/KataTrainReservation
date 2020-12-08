package coltrain;

import models.Train;

import java.util.HashMap;
import java.util.Map;

public class TrainCaching {

    Map<String, Map<Train, String>> cache = new HashMap<String, Map<Train, String>>();

    public void clear() {
        cache = new HashMap<String, Map<Train, String>>();
    }

    public void save(final String train, final Train trainInst, final String bookingRef) {
        final HashMap<Train, String> value = new HashMap<Train, String>();
        value.put(trainInst, bookingRef);
        cache.put(train, value);
    }

    public Map<Train, String> load(String trainId){
        return cache.get(trainId);
    }
}
