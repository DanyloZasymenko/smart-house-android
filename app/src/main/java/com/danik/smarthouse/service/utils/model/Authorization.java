package com.danik.smarthouse.service.utils.model;

public class Authorization {

    private String access_token;
    private String token_type;
    private String refresh_token;
    private String expires_in;
    private String scope;
    private String jti;

    public String getAccess_token() {
        return access_token;
    }

    public Authorization setAccess_token(String access_token) {
        this.access_token = access_token;
        return this;
    }

    public String getToken_type() {
        return token_type;
    }

    public Authorization setToken_type(String token_type) {
        this.token_type = token_type;
        return this;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public Authorization setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
        return this;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public Authorization setExpires_in(String expires_in) {
        this.expires_in = expires_in;
        return this;
    }

    public String getScope() {
        return scope;
    }

    public Authorization setScope(String scope) {
        this.scope = scope;
        return this;
    }

    public String getJti() {
        return jti;
    }

    public Authorization setJti(String jti) {
        this.jti = jti;
        return this;
    }

    @Override
    public String toString() {
        return "Authorization{" +
                "access_token='" + access_token + '\'' +
                ", token_type='" + token_type + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                ", expires_in='" + expires_in + '\'' +
                ", scope='" + scope + '\'' +
                ", jti='" + jti + '\'' +
                '}';
    }
}
