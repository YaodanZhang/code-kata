package com.thoughtworks.kata.refactor;

import com.google.common.base.Objects;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Objects.equal;
import static com.thoughtworks.kata.refactor.TrustIndicator.UNTRUSTED;

public class Summary {
    private String detail;
    private TrustIndicator trustIndicator = UNTRUSTED;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public TrustIndicator getTrustIndicator() {
        return trustIndicator;
    }

    public void setTrustIndicator(TrustIndicator trustIndicator) {
        this.trustIndicator = trustIndicator;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(detail, trustIndicator);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Summary other = (Summary) obj;
        return equal(this.detail, other.detail)
                && equal(this.trustIndicator, other.trustIndicator);
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("detail", detail)
                .add("trustIndicator", trustIndicator)
                .toString();
    }
}
