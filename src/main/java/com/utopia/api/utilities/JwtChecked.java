package com.utopia.api.utilities;

public class JwtChecked {
    public boolean isValid;
    public long userId;
    public Short userRole;

    public JwtChecked() {
        this.isValid = false;
        this.userId = -1;
        this.userRole = null;
    }
}
