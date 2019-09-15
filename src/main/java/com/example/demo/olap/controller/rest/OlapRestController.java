package com.example.demo.olap.controller.rest;

import com.example.demo.olap.service.OlapService;
import com.example.demo.olap.util.TroveToCollectionUtil;
import gnu.trove.list.TLongList;
import gnu.trove.set.TLongSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/olap")
public class OlapRestController {

    private static final Logger logger = LoggerFactory.getLogger(OlapRestController.class);

    @Autowired
    @Qualifier("defaultImpl")
    OlapService olapService;

    @GetMapping
    public String ping() {
        return "It works!";
    }

    @GetMapping("/unique-pages-count")
    public String uniquePagesCount() throws IOException {
        return String.valueOf(olapService.getUniquePagesCount());
    }

    @GetMapping("/unique-pages-ids")
    public String uniquePagesIds() throws IOException {
        Set<Long> longs = TroveToCollectionUtil.tLongSetToSet(olapService.getUniquePagesIds());
        return longs.toString();
    }

    @GetMapping("/similarity")
    public String similarity(
            @RequestParam(value = "page1", required = true) String page1,
            @RequestParam(value = "page2", required = true) String page2,
            @RequestParam(value = "from", required = true) String from,
            @RequestParam(value = "to", required = true) String to) throws IOException {

        if (Long.parseLong(from) > Long.parseLong(to)) {
            return "Value \"To\" could be more than value \"From\"";
        }
        TLongSet uniquePages = olapService.getUniquePagesIds();
        if (!uniquePages.contains(Long.parseLong(page1))) {
            return "Page with id " + page1 + " does not exist";
        }
        if (!uniquePages.contains(Long.parseLong(page2))) {
            return "Page with id " + page2 + " does not exist";
        }

        Map<String, TLongList> map = olapService.f(Long.parseLong(page1), Long.parseLong(page2), Long.parseLong(from), Long.parseLong(to));
        TLongList uidList1 = map.get("page1");
        TLongList uidList2 = map.get("page2");
        TLongList intersection  = uidList1;
        intersection.retainAll(uidList2);

        double jaccard = jaccardIndex(uidList1.size(), uidList2.size(), intersection.size());
        return String.format("%.2f", jaccard);
    }

    private double jaccardIndex(double setA, double setB, double setIntersection ) {
        return (setIntersection  / (setA + setB - setIntersection ));
    }
}
