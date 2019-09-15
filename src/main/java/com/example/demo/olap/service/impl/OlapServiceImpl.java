package com.example.demo.olap.service.impl;

import com.example.demo.olap.service.OlapService;
import gnu.trove.list.TLongList;
import gnu.trove.list.array.TLongArrayList;
import gnu.trove.set.TLongSet;
import gnu.trove.set.hash.TLongHashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service
@Qualifier("defaultImpl")
public class OlapServiceImpl implements OlapService {
    private static final String REGEX = ",";

    @Value("${data.file.name}")
    Resource resource;

    @Autowired
    CacheManager cacheManager;

    @Override
    public long getUniquePagesCount() throws IOException {
        if (cacheManager.getCache("uniquePageIds") != null) {
            return ((TLongSet) cacheManager.getCache("uniquePageIds").get(new SimpleKey()).get()).size();
        } else {
            return getUniquePagesIds().size();
        }
    }

    @Override
    @Cacheable(value = "uniquePageIds")
    public TLongSet getUniquePagesIds() throws IOException {
        if (cacheManager.getCache("uniquePageIds").get(new SimpleKey()) != null) {
            return ((TLongSet) cacheManager.getCache("uniquePageIds").get(new SimpleKey()).get());
        } else {
            TLongSet tSet = new TLongHashSet(10000);
            InputStream is = new FileInputStream(resource.getFile());
            String line;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                while ((line = br.readLine()) != null) {
                    tSet.add(Long.parseLong(line.split(REGEX)[1]));
                }
            }
            return tSet;
        }
    }

    @Override
    public Map<String, TLongList> f(long page1, long page2, long from, long to) throws IOException {
        Map<String, TLongList> map = new HashMap<>();
        TLongList list1 = new TLongArrayList(1000000);
        TLongList list2 = new TLongArrayList(1000000);
        InputStream is = new FileInputStream(resource.getFile());
        String line;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            while ((line = br.readLine()) != null) {
                long uid = Long.parseLong(line.split(REGEX)[0]);
                long page = Long.parseLong(line.split(REGEX)[1]);
                long timestamp = Long.parseLong(line.split(REGEX)[2]);
                if (timestamp > from  && timestamp < to) {
                    if (page == page1) list1.add(uid);
                    if (page == page2) list2.add(uid);
                }
            }
        }

        map.put("page1", list1);
        map.put("page2", list2);
        return map;
    }
}
