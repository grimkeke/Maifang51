package com.djmedia.maifang51.tools;

/**
 * Created by rd on 14-3-21.
 */
public enum FID {
    MAIN (0),
    APARTMENT_LIST (1),
    LOGIN (2),
    CLOUD_PHONE (3),
    MORE (4),
    MEMBER_CENTER (5),
    MEMBER_SPECIAL (6);

    private int id;

    private FID(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
