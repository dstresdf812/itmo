package com.dstresdf.server.util;

import com.dstresdf.common.network.Request;

import java.util.Objects;

public class RequestKey {
    private final String clientAddressString;
    private final Integer requestId;
    public RequestKey(String clientAddressString, Integer requestId) {
        this.clientAddressString = clientAddressString;
        this.requestId = requestId;
    }
    public Integer getRequestId() {
        return requestId;
    }
    public String getClientAddressString() {
        return clientAddressString;
    }

    @Override
    public String toString() {
        return requestId.toString() + " " + clientAddressString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestKey)) return false;
        RequestKey that = (RequestKey) o;
        return Objects.equals(clientAddressString, that.clientAddressString) &&
                Objects.equals(requestId, that.requestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientAddressString, requestId);
    }
}
