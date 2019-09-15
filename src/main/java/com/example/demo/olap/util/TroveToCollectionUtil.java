package com.example.demo.olap.util;

import gnu.trove.decorator.TLongSetDecorator;
import gnu.trove.set.TLongSet;

import java.util.HashSet;
import java.util.Set;

public class TroveToCollectionUtil {
    public static Set<Long> tLongSetToSet(TLongSet tSet) {
        TLongSetDecorator longs = new TLongSetDecorator(tSet);
        return new HashSet<>(longs);
    }
}
