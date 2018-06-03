package com.job.github.convertor;

import android.arch.persistence.room.TypeConverter;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by slavik on 6/3/18.
 */

public class UrlConverter {
    @TypeConverter
    public String fromURL(URL url) {
        return url.toString();
    }

    @TypeConverter
    public URL toURL(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            return null;
        }
    }

}
