package com.coder.springjwt.payload.sellerPayloads.sellerPayload;

import jakarta.validation.constraints.NotBlank;

public class SellerLoginPayload {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String userrole;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserrole() {
        return userrole;
    }

    public void setUserrole(String userrole) {
        this.userrole = userrole;
    }
}