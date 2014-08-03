package com.thoughtworks.kata.refactor;

public class Request {
    private RequestSummary summary;

    public void setSummary(RequestSummary summary) {
        this.summary = summary;
    }

    public RequestSummary getSummary() {
        return summary;
    }
}
