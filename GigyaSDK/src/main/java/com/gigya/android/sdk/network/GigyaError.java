package com.gigya.android.sdk.network;

import android.support.annotation.NonNull;

import com.gigya.android.sdk.model.BaseModel;

import org.json.JSONObject;

import java.util.Map;

public class GigyaError extends BaseModel {

    public static class Codes {
        public static final int ERROR_ACCOUNT_PENDING_REGISTRATION = 206001;
        public static final int ERROR_ACCOUNT_PENDING_VERIFICATION = 206002;
        public static final int ERROR_PENDING_PASSWORD_CHANGE = 403100;
        public static final int ERROR_LOGIN_IDENTIFIER_EXISTS = 403043;

        public static final int SUCCESS_ERROR_ACCOUNT_LINKED = 200009;
    }

    /* Raw Json data. */
    private String data;

    private String localizedMessage;

    public GigyaError(int errorCode, String localizedMessage, String callId) {
        this.errorCode = errorCode;
        this.localizedMessage = localizedMessage;
        this.callId = callId;
    }

    public GigyaError(String rawJson, int errorCode, String localizedMessage, String callId) {
        this.data = rawJson;
        this.errorCode = errorCode;
        this.localizedMessage = localizedMessage;
        this.callId = callId;
    }

    public static GigyaError generalError() {
        return new GigyaError(400, "", "");
    }

    public static GigyaError errorFrom(String message) {
        return new GigyaError(400, message, "");
    }

    public static GigyaError cancelledOperation() {
        return new GigyaError(200001, "Operation canceled", "");
    }

    public static GigyaError fromResponse(GigyaResponse response) {
        final int errorCode = response.getErrorCode();
        final String localizedMessage = response.getErrorDetails();
        final String callId = response.getCallId();
        return new GigyaError(response.asJson(), errorCode, localizedMessage, callId);
    }

    public static GigyaError errorFrom(Map<String, Object> errorEvent) {
        final JSONObject jsonObj = new JSONObject(errorEvent);
        final Object errorCode = errorEvent.get("errorCode");
        int code = 400;
        if (errorCode != null) {
            if (errorCode instanceof String) {
                code = Integer.parseInt((String) errorCode);
            } else if (errorCode instanceof Integer) {
                code = (int) errorCode;
            }
        }
        final String errorMessage = (String) errorEvent.get("errorMessage");

        return new GigyaError(
                jsonObj.toString(),
                code,
                errorMessage,
                null
        );
    }

    @NonNull
    @Override
    public String toString() {
        return "<< Gigya error: code: " +
                this.errorCode +
                ", message: " +
                this.localizedMessage +
                ", callId: " +
                this.callId;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getLocalizedMessage() {
        return localizedMessage;
    }

    public String getCallId() {
        return callId;
    }

    public String getData() {
        return data;
    }
}
