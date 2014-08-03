package com.thoughtworks.kata.refactor;

import java.util.Map;

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

        boolean shouldUpdateDetail = shouldUpdateDetail(requestSummaryDetail,
                dbSummaryDetail, requestTrustIndicator);

        if (shouldUpdateDetail) {
            dbSummary.setDetail(trimToNull(requestSummaryDetail));
            updatedFields.put("summary", dbSummary);
        }

        TrustIndicator updatedIndicator = getUpdateIndicatorAction(requestSummaryDetail,
                dbSummaryDetail, requestTrustIndicator, dbTrustIndicator);

        dbSummary.setTrustIndicator(updatedIndicator);

        if (updatedIndicator != dbTrustIndicator) {
            updatedFields.put("summary", dbSummary);
        }
    }

    private boolean shouldUpdateDetail(String requestSummaryDetail, String dbSummaryDetail,
                                       TrustIndicator requestTrustIndicator) {
        boolean isDbDetailBlank = isNullOrEmpty(dbSummaryDetail);

        if (isNullOrEmpty(requestSummaryDetail)) {
            return !isDbDetailBlank && TRUSTED == requestTrustIndicator;
        }

        return isDbDetailBlank || !isSame(requestSummaryDetail, dbSummaryDetail);
    }

    private TrustIndicator getUpdateIndicatorAction(String requestSummaryDetail, String dbSummaryDetail,
                                                    TrustIndicator requestTrustIndicator,
                                                    TrustIndicator dbTrustIndicator) {
        TrustIndicator updatedIndicator = dbTrustIndicator;

        boolean isDbDetailBlank = isNullOrEmpty(dbSummaryDetail);
        boolean isRequestDetailBlank = isNullOrEmpty(requestSummaryDetail);
        boolean isRequestAndDbDetailSame = isSame(requestSummaryDetail, dbSummaryDetail);

        if (isRequestDetailBlank && !isDbDetailBlank && TRUSTED == requestTrustIndicator) {
            if (UNTRUSTED == dbTrustIndicator) {
                updatedIndicator = TRUSTED;
            }
        } else if (!isRequestDetailBlank && isDbDetailBlank) {
            updatedIndicator = requestTrustIndicator;
        } else if (!isRequestDetailBlank) {
            if (TRUSTED == requestTrustIndicator) {
                updatedIndicator = TRUSTED;
            } else if (UNTRUSTED == requestTrustIndicator && !isRequestAndDbDetailSame) {
                updatedIndicator = UNTRUSTED;
            }
        }
        return updatedIndicator;
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
