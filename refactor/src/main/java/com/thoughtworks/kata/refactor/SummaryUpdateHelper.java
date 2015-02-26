package com.thoughtworks.kata.refactor;

import java.util.Map;
import java.util.Objects;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.thoughtworks.kata.refactor.TrustIndicator.TRUSTED;
import static com.thoughtworks.kata.refactor.TrustIndicator.UNTRUSTED;

public class SummaryUpdateHelper {
    private Map<String, Object> updatedFields;

    public SummaryUpdateHelper(Map<String, Object> updatedFields) {
        this.updatedFields = updatedFields;
    }

    public void updateSummary(Request request, Customer customer) {
        RequestSummary requestSummary = request.getSummary();
        Summary summaryFromDb = customer.getSummary();

        if (summaryFromDb == null && requestSummary != null) {
            summaryFromDb = new Summary();
        }

        if (null != requestSummary) {
            updateSummaryAttributes(requestSummary, summaryFromDb);
        }
        if (updatedFields.containsKey("summary")) {
            customer.setSummary(summaryFromDb);
        }
    }

    private void updateSummaryAttributes(RequestSummary requestSummary, Summary dbSummary) {
        String requestSummaryDetail = trimToNull(requestSummary.getSummaryDetail());
        TrustIndicator requestTrustIndicator = requestSummary.getTrustIndicator();

        String dbSummaryDetail = trimToNull(dbSummary.getDetail());
        TrustIndicator dbTrustIndicator = dbSummary.getTrustIndicator();

        boolean shouldUpdate = shouldUpdate(requestSummaryDetail,
                requestTrustIndicator, dbTrustIndicator);

        if (shouldUpdate && !Objects.equals(dbSummaryDetail, requestSummaryDetail)) {
            dbSummary.setDetail(trimToNull(requestSummaryDetail));
            updatedFields.put("summary", dbSummary);
        }

        if (shouldUpdate && dbTrustIndicator != requestTrustIndicator) {
            dbSummary.setTrustIndicator(requestTrustIndicator);
            updatedFields.put("summary", dbSummary);
        }
    }

    private boolean shouldUpdate(String requestSummaryDetail, TrustIndicator requestTrustIndicator,
                                 TrustIndicator dbTrustIndicator) {
        if (isNullOrEmpty(requestSummaryDetail)) {
            return TRUSTED == requestTrustIndicator;
        }
        return !(TRUSTED == dbTrustIndicator && UNTRUSTED == requestTrustIndicator);
    }

    private static String trimToNull(String target) {
        if (target == null) {
            return null;
        }
        String trimmed = target.trim();
        return "".equals(trimmed) ? null : trimmed;
    }

}
