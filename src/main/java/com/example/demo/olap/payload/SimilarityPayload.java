package com.example.demo.olap.payload;

public class SimilarityPayload {
    private long page1;
    private long page2;
    private long from;
    private long to;

    public SimilarityPayload() {
    }

    public SimilarityPayload(long page1, long page2, long from, long to) {
        this.page1 = page1;
        this.page2 = page2;
        this.from = from;
        this.to = to;
    }

    public long getPage1() {
        return page1;
    }

    public void setPage1(long page1) {
        this.page1 = page1;
    }

    public long getPage2() {
        return page2;
    }

    public void setPage2(long page2) {
        this.page2 = page2;
    }

    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public long getTo() {
        return to;
    }

    public void setTo(long to) {
        this.to = to;
    }
}
