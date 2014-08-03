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
        String requestSummaryDetail = requestSummary.getSummaryDetail();
        TrustIndicator requestTrustIndicator = requestSummary.getTrustIndicator();

        String dbSummaryDetail = dbSummary.getDetail();
        TrustIndicator dbTrustIndicator = dbSummary.getTrustIndicator();

        boolean shouldUpdate = shouldUpdate(requestSummaryDetail,
                dbSummaryDetail, requestTrustIndicator);

        if (shouldUpdate && !Objects.equals(dbSummaryDetail, requestSummaryDetail)) {
            dbSummary.setDetail(trimToNull(requestSummaryDetail));
            updatedFields.put("summary", dbSummary);
        }

        if (shouldUpdate && dbTrustIndicator != requestTrustIndicator) {
            dbSummary.setTrustIndicator(requestTrustIndicator);
            updatedFields.put("summary", dbSummary);
        }
    }

    private boolean shouldUpdate(String requestSummaryDetail, String dbSummaryDetail,
                                 TrustIndicator requestTrustIndicator) {
        boolean shouldUpdate = false;
        boolean isDbDetailBlank = isNullOrEmpty(dbSummaryDetail);
        boolean isRequestDetailBlank = isNullOrEmpty(requestSummaryDetail);

        if (isRequestDetailBlank) {
            return !isDbDetailBlank && TRUSTED == requestTrustIndicator;
        }

        if (!isRequestDetailBlank && isDbDetailBlank) {
            shouldUpdate = true;
        } else if (!isRequestDetailBlank) {
            if (TRUSTED == requestTrustIndicator) {
                shouldUpdate = true;
            } else if (UNTRUSTED == requestTrustIndicator
                    && !isSame(requestSummaryDetail, dbSummaryDetail)) {
                shouldUpdate = true;
            }
        }
        return shouldUpdate;
    }

    private boolean isSame(String requestSummaryDetail, String dbSummaryDetail) {
        return null != requestSummaryDetail && null != dbSummaryDetail
                && requestSummaryDetail.equalsIgnoreCase(dbSummaryDetail);
    }

    private static String trimToNull(String target) {
        if (target == null) {
            return null;
        }
        String trimmed = target.trim();
        return "".equals(trimmed) ? null : trimmed;
    }

}
