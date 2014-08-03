package com.thoughtworks.kata.refactor;

public class RequestSummary {
    private String summaryDetail;
    private TrustIndicator trustIndicator;

    public RequestSummary(String summaryDetail, TrustIndicator trustIndicator) {
        this.summaryDetail = summaryDetail;
        this.trustIndicator = trustIndicator;
    }

    public String getSummaryDetail() {
        return summaryDetail;
    }

    public TrustIndicator getTrustIndicator() {
        return trustIndicator;
    }
}
