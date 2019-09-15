package com.example.demo.olap.service;

import gnu.trove.list.TLongList;
import gnu.trove.set.TLongSet;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public interface OlapService {
    long getUniquePagesCount() throws IOException;
    TLongSet getUniquePagesIds() throws IOException;
    Map<String, TLongList> f(long page1, long page2, long from, long to) throws IOException;
}
