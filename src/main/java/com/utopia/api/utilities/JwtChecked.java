package com.utopia.api.utilities;

public class JwtVerified {
    public boolean isValid;
    public long userId;
    public String userRole;

    public JwtVerified() {
        this.isValid = false;
        this.userId = -1;
        this.userRole = null;
    }
}
