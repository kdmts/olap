package com.example.demo.olap.controller;

import com.example.demo.olap.payload.SimilarityPayload;
import com.example.demo.olap.service.OlapService;
import com.example.demo.olap.util.TroveToCollectionUtil;
import gnu.trove.list.TLongList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/olap")
public class OlapController {

    @Autowired
    @Qualifier("defaultImpl")
    OlapService olapService;

    @GetMapping("/pages")
    public String pages(Map<String, Object> model) {
        model.put("title", "Pages");
        try {
            // Конвертация TLongSet в HashSet<Long> сделана для вывода на фронт, TLongSet выходит за <div>
            Set<Long> uniquePagesIds = TroveToCollectionUtil.tLongSetToSet(olapService.getUniquePagesIds());
            model.put("ids", uniquePagesIds);
            long uniquePagesCount = uniquePagesIds.size();
            model.put("uniquePagesCount", uniquePagesCount);
            model.put("jaccard", "0.00");
        } catch (IOException ex) {
            model.put("exception", ex.getMessage());
        }

        return "pages";
    }

    @PostMapping("/similarity")
    public String similarity(Map<String, Object> model, @ModelAttribute("payload") SimilarityPayload payload) throws IOException {
        Map<String, TLongList> map = olapService.f(payload.getPage1(), payload.getPage2(), payload.getFrom(), payload.getTo());
        TLongList uidList1 = map.get("page1");
        TLongList uidList2 = map.get("page2");
        TLongList intersection  = uidList1;
        intersection.retainAll(uidList2);
        double jaccard = jaccardIndex(uidList1.size(), uidList2.size(), intersection.size());
        model.put("jaccard", jaccard);

        model.put("title", "Pages");
        // Конвертация TLongSet в HashSet<Long> сделана для вывода на фронт, TLongSet выходит за <div>
        Set<Long> uniquePagesIds = TroveToCollectionUtil.tLongSetToSet(olapService.getUniquePagesIds());
        model.put("ids", uniquePagesIds);
        long uniquePagesCount = uniquePagesIds.size();
        model.put("uniquePagesCount", uniquePagesCount);

        return "pages";
    }

    private double jaccardIndex(double setA, double setB, double setIntersection ) {
        return (setIntersection  / (setA + setB - setIntersection ));
    }


}
