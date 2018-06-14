package com.sammie.httpbox.util;

import android.text.TextUtils;

public class Utils {

    public static String getFullUrl(String baseUrl, String url) {
        if (TextUtils.isEmpty(baseUrl) || url.startsWith("http")) {
            return url;
        } else {
            return baseUrl + "/" + url;
        }
    }
}
