package com.thoughtworks.kata.refactor

import spock.lang.Specification
import spock.lang.Unroll

import static com.google.common.collect.Maps.newHashMap
import static com.thoughtworks.kata.refactor.SummaryUpdateHelperTest.SummaryType.BLANK
import static com.thoughtworks.kata.refactor.SummaryUpdateHelperTest.SummaryType.EMPTY
import static com.thoughtworks.kata.refactor.SummaryUpdateHelperTest.SummaryType.ORIGINAL
import static com.thoughtworks.kata.refactor.SummaryUpdateHelperTest.SummaryType.UPDATED
import static com.thoughtworks.kata.refactor.TrustIndicator.TRUSTED
import static com.thoughtworks.kata.refactor.TrustIndicator.UNTRUSTED

class SummaryUpdateHelperTest extends Specification {

    @Unroll
    def "request[#requestSummary, #requestTrust] update db[#dbSummary, #dbTrust] = [#finalSummary, #finalTrust]"() {
        given:
        def helper = new SummaryUpdateHelper(newHashMap())
        def request = createRequest(requestSummary, requestTrust)
        def customer = createCustomer(dbSummary, dbTrust)

        when:
        helper.updateSummary(request, customer)

        then:
        customer.summary == createSummary(finalSummary, finalTrust)

        where:
        requestSummary | requestTrust | dbSummary | dbTrust   | finalSummary | finalTrust
        null           | null         | null      | null      | null         | null
        null           | null         | ORIGINAL  | TRUSTED   | ORIGINAL     | TRUSTED

        BLANK          | TRUSTED      | null      | null      | BLANK        | TRUSTED
        BLANK          | UNTRUSTED    | null      | null      | null         | null
        BLANK          | TRUSTED      | BLANK     | TRUSTED   | BLANK        | TRUSTED
        BLANK          | TRUSTED      | BLANK     | UNTRUSTED | BLANK        | TRUSTED
        BLANK          | UNTRUSTED    | BLANK     | TRUSTED   | BLANK        | TRUSTED
        BLANK          | UNTRUSTED    | BLANK     | UNTRUSTED | BLANK        | UNTRUSTED
        BLANK          | TRUSTED      | EMPTY     | TRUSTED   | EMPTY        | TRUSTED
        BLANK          | TRUSTED      | EMPTY     | UNTRUSTED | EMPTY        | TRUSTED
        BLANK          | UNTRUSTED    | EMPTY     | TRUSTED   | EMPTY        | TRUSTED
        BLANK          | UNTRUSTED    | EMPTY     | UNTRUSTED | EMPTY        | UNTRUSTED
        BLANK          | TRUSTED      | ORIGINAL  | TRUSTED   | BLANK        | TRUSTED
        BLANK          | TRUSTED      | ORIGINAL  | UNTRUSTED | BLANK        | TRUSTED
        BLANK          | UNTRUSTED    | ORIGINAL  | TRUSTED   | ORIGINAL     | TRUSTED
        BLANK          | UNTRUSTED    | ORIGINAL  | UNTRUSTED | ORIGINAL     | UNTRUSTED

        EMPTY          | TRUSTED      | null      | null      | BLANK        | TRUSTED
        EMPTY          | UNTRUSTED    | null      | null      | null         | null
        EMPTY          | TRUSTED      | BLANK     | TRUSTED   | BLANK        | TRUSTED
        EMPTY          | TRUSTED      | BLANK     | UNTRUSTED | BLANK        | TRUSTED
        EMPTY          | UNTRUSTED    | BLANK     | TRUSTED   | BLANK        | TRUSTED
        EMPTY          | UNTRUSTED    | BLANK     | UNTRUSTED | BLANK        | UNTRUSTED
        EMPTY          | TRUSTED      | EMPTY     | TRUSTED   | EMPTY        | TRUSTED
        EMPTY          | TRUSTED      | EMPTY     | UNTRUSTED | EMPTY        | TRUSTED
        EMPTY          | UNTRUSTED    | EMPTY     | TRUSTED   | EMPTY        | TRUSTED
        EMPTY          | UNTRUSTED    | EMPTY     | UNTRUSTED | EMPTY        | UNTRUSTED
        EMPTY          | TRUSTED      | ORIGINAL  | TRUSTED   | BLANK        | TRUSTED
        EMPTY          | TRUSTED      | ORIGINAL  | UNTRUSTED | BLANK        | TRUSTED
        EMPTY          | UNTRUSTED    | ORIGINAL  | TRUSTED   | ORIGINAL     | TRUSTED
        EMPTY          | UNTRUSTED    | ORIGINAL  | UNTRUSTED | ORIGINAL     | UNTRUSTED

        ORIGINAL       | TRUSTED      | null      | null      | ORIGINAL     | TRUSTED
        ORIGINAL       | UNTRUSTED    | null      | null      | ORIGINAL     | UNTRUSTED
        ORIGINAL       | TRUSTED      | BLANK     | TRUSTED   | ORIGINAL     | TRUSTED
        ORIGINAL       | TRUSTED      | BLANK     | UNTRUSTED | ORIGINAL     | TRUSTED
        ORIGINAL       | UNTRUSTED    | BLANK     | TRUSTED   | BLANK        | TRUSTED
        ORIGINAL       | UNTRUSTED    | BLANK     | UNTRUSTED | ORIGINAL     | UNTRUSTED
        ORIGINAL       | TRUSTED      | EMPTY     | TRUSTED   | ORIGINAL     | TRUSTED
        ORIGINAL       | TRUSTED      | EMPTY     | UNTRUSTED | ORIGINAL     | TRUSTED
        ORIGINAL       | UNTRUSTED    | EMPTY     | TRUSTED   | EMPTY        | TRUSTED
        ORIGINAL       | UNTRUSTED    | EMPTY     | UNTRUSTED | ORIGINAL     | UNTRUSTED
        ORIGINAL       | TRUSTED      | ORIGINAL  | TRUSTED   | ORIGINAL     | TRUSTED
        ORIGINAL       | TRUSTED      | ORIGINAL  | UNTRUSTED | ORIGINAL     | TRUSTED
        ORIGINAL       | UNTRUSTED    | ORIGINAL  | TRUSTED   | ORIGINAL     | TRUSTED
        ORIGINAL       | UNTRUSTED    | ORIGINAL  | UNTRUSTED | ORIGINAL     | UNTRUSTED

        UPDATED        | TRUSTED      | null      | null      | UPDATED      | TRUSTED
        UPDATED        | UNTRUSTED    | null      | null      | UPDATED      | UNTRUSTED
        UPDATED        | TRUSTED      | BLANK     | TRUSTED   | UPDATED      | TRUSTED
        UPDATED        | TRUSTED      | BLANK     | UNTRUSTED | UPDATED      | TRUSTED
        UPDATED        | UNTRUSTED    | BLANK     | TRUSTED   | BLANK        | TRUSTED
        UPDATED        | UNTRUSTED    | BLANK     | UNTRUSTED | UPDATED      | UNTRUSTED
        UPDATED        | TRUSTED      | EMPTY     | TRUSTED   | UPDATED      | TRUSTED
        UPDATED        | TRUSTED      | EMPTY     | UNTRUSTED | UPDATED      | TRUSTED
        UPDATED        | UNTRUSTED    | EMPTY     | TRUSTED   | EMPTY        | TRUSTED
        UPDATED        | UNTRUSTED    | EMPTY     | UNTRUSTED | UPDATED      | UNTRUSTED
        UPDATED        | TRUSTED      | ORIGINAL  | TRUSTED   | UPDATED      | TRUSTED
        UPDATED        | TRUSTED      | ORIGINAL  | UNTRUSTED | UPDATED      | TRUSTED
        UPDATED        | UNTRUSTED    | ORIGINAL  | TRUSTED   | ORIGINAL     | TRUSTED
        UPDATED        | UNTRUSTED    | ORIGINAL  | UNTRUSTED | UPDATED      | UNTRUSTED
    }

    def createCustomer(SummaryType summaryType, TrustIndicator trustIndicator) {
        new Customer(summary: createSummary(summaryType, trustIndicator))
    }

    private Summary createSummary(SummaryType summaryType, TrustIndicator trustIndicator) {
        summaryType ? summaryWithDetail(summaryType, trustIndicator) : null
    }

    def summaryWithDetail(SummaryType summaryType, TrustIndicator trustIndicator) {
        new Summary(
                detail: summaryType.summaryDetail,
                trustIndicator: trustIndicator
        )
    }

    def createRequest(SummaryType requestSummary, TrustIndicator trustIndicator) {
        new Request(summary: requestSummary ?
                requestSummaryWithDetail(requestSummary, trustIndicator) : null)
    }

    def requestSummaryWithDetail(SummaryType summaryType, TrustIndicator trustIndicator) {
        new RequestSummary(summaryType.summaryDetail, trustIndicator)
    }

    static enum SummaryType {
        BLANK(null), EMPTY(''), ORIGINAL('init.summary'), UPDATED('changed.summary')

        final String summaryDetail

        private SummaryType(String summaryDetail) {
            this.summaryDetail = summaryDetail
        }
    }
}
