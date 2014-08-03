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
                dbSummaryDetail, requestTrustIndicator, dbTrustIndicator);

        if (shouldUpdateDetail) {
            dbSummary.setDetail(trimToNull(requestSummaryDetail));
            updatedFields.put("summary", dbSummary);
        }

        UpdateAction updateIndicatorAction = getUpdateIndicatorAction(requestSummaryDetail,
                dbSummaryDetail, requestTrustIndicator, dbTrustIndicator);

        dbSummary.setTrustIndicator(updateIndicatorAction.updatedTrustIndicator);

        if (updateIndicatorAction.updatedTrustIndicator != dbTrustIndicator) {
            updatedFields.put("summary", dbSummary);
        }
    }

    private boolean shouldUpdateDetail(String requestSummaryDetail, String dbSummaryDetail,
                                       TrustIndicator requestTrustIndicator,
                                       TrustIndicator dbTrustIndicator) {
        boolean isDbDetailBlank = isNullOrEmpty(dbSummaryDetail);
        boolean isRequestDetailBlank = isNullOrEmpty(requestSummaryDetail);
        boolean isRequestAndDbDetailSame = isSame(requestSummaryDetail, dbSummaryDetail);

        boolean shouldUpdateDetail = false;
        if (isRequestDetailBlank && !isDbDetailBlank && TRUSTED == requestTrustIndicator) {
            if (TRUSTED == dbTrustIndicator) {
                shouldUpdateDetail = true;
            } else if (UNTRUSTED == dbTrustIndicator) {
                shouldUpdateDetail = true;
            }
        } else if (!isRequestDetailBlank && isDbDetailBlank) {
            shouldUpdateDetail = true;
        } else if (!isRequestDetailBlank) {
            if (TRUSTED == dbTrustIndicator && TRUSTED == requestTrustIndicator
                    && !isRequestAndDbDetailSame) {
                shouldUpdateDetail = true;
            } else if (UNTRUSTED == dbTrustIndicator && TRUSTED == requestTrustIndicator) {
                if (!isRequestAndDbDetailSame) {
                    shouldUpdateDetail = true;
                }
            } else if (UNTRUSTED == dbTrustIndicator && UNTRUSTED == requestTrustIndicator
                    && !isRequestAndDbDetailSame) {
                shouldUpdateDetail = true;
            } else if (TRUSTED == dbTrustIndicator && UNTRUSTED == requestTrustIndicator
                    && !isRequestAndDbDetailSame) {
                shouldUpdateDetail = true;
            }
        }
        return shouldUpdateDetail;
    }

    private UpdateAction getUpdateIndicatorAction(String requestSummaryDetail, String dbSummaryDetail,
                                                  TrustIndicator requestTrustIndicator,
                                                  TrustIndicator dbTrustIndicator) {
        UpdateAction updateAction;
        boolean isRequestDetailBlank;
        boolean isDbDetailBlank;
        boolean isRequestAndDbDetailSame;

        updateAction = new UpdateAction();
        updateAction.updatedTrustIndicator = dbTrustIndicator;

        isDbDetailBlank = isNullOrEmpty(dbSummaryDetail);

        isRequestDetailBlank = isNullOrEmpty(requestSummaryDetail);

        isRequestAndDbDetailSame = isSame(requestSummaryDetail, dbSummaryDetail);

        if (isRequestDetailBlank && !isDbDetailBlank && TRUSTED == requestTrustIndicator) {
            if (UNTRUSTED == dbTrustIndicator) {
                updateAction.updatedTrustIndicator = TRUSTED;
            }
        } else if (!isRequestDetailBlank && isDbDetailBlank) {
            updateAction.updatedTrustIndicator = requestTrustIndicator;
        } else if (!isRequestDetailBlank) {
            if (UNTRUSTED == dbTrustIndicator && TRUSTED == requestTrustIndicator) {
                updateAction.updatedTrustIndicator = TRUSTED;
            } else if (TRUSTED == dbTrustIndicator && UNTRUSTED == requestTrustIndicator
                    && !isRequestAndDbDetailSame) {
                updateAction.updatedTrustIndicator = UNTRUSTED;
            }
        }
        return updateAction;
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

    private class UpdateAction {
        public boolean updateDetail;
        public TrustIndicator updatedTrustIndicator;
    }
}
