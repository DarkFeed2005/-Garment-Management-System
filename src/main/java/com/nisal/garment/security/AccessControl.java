package com.nisal.garment.security;

import com.nisal.garment.model.SessionUser;

public final class AccessControl {
    private AccessControl() {}

    public static boolean isAdmin(SessionUser u) {
        return u != null && "ADMIN".equals(u.role());
    }

    public static boolean canManageUsers(SessionUser u) {
        return isAdmin(u);
    }

    public static boolean canManageProducts(SessionUser u) {
        return u != null && (isAdmin(u) || "PRODUCT_MANAGER".equals(u.role()));
    }

    public static boolean canManageOrders(SessionUser u) {
        return u != null && (isAdmin(u) || "PRODUCT_MANAGER".equals(u.role()));
    }

    public static boolean canManageProduction(SessionUser u) {
        return u != null && (isAdmin(u) || "PRODUCT_MANAGER".equals(u.role()) || "STAFF".equals(u.role()));
    }

    public static boolean canManageCustomers(SessionUser u) {
        return u != null && (isAdmin(u) || "PRODUCT_MANAGER".equals(u.role()));
    }

    public static boolean canManageDeliveries(SessionUser u) {
        return u != null && (isAdmin(u) || "PRODUCT_MANAGER".equals(u.role()) || "STAFF".equals(u.role()));
    }

    public static boolean canRegisterCustomer() { return true; }
}
