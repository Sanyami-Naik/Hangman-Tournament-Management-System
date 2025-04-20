package com.coditas.HangMan.payload;

public class JwtAuthResponse {
    private String accessToken;
//    private String tokenType = "Bearer";
    private String tokenRole;

    public JwtAuthResponse(String accessToken, String tokenRole, long tokenUserID) {
        this.accessToken = accessToken;
        this.tokenRole = tokenRole;
        this.tokenUserID = tokenUserID;
    }

    public long getTokenUserID() {
        return tokenUserID;
    }

    public void setTokenUserID(long tokenUserID) {
        this.tokenUserID = tokenUserID;
    }

    private long tokenUserID;

    public String getTokenRole() {
        return tokenRole;
    }


    public void setTokenRole(String tokenRole) {
        this.tokenRole = tokenRole;
    }

    public JwtAuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

//    public void setTokenType(String tokenType) {
//        this.tokenType = tokenType;
//    }

    public String getAccessToken() {
        return accessToken;
    }

//    public String getTokenType() {
//        return tokenType;
//    }


}
