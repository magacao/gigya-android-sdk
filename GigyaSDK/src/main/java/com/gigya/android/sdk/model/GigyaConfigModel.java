package com.gigya.android.sdk.model;

/**
 * Gigya SDK main configuration model.
 */
public class GigyaConfigModel extends GigyaModel {

    private Ids ids;

    public Ids getIds() {
        return this.ids;
    }

    public static class Ids {

        private String gmid;
        private String ucid;

        public String getGmid() {
            return this.gmid;
        }

        public String getUcid() {
            return this.ucid;
        }
    }
}
