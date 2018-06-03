package com.job.github.convertor;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by slavik on 6/3/18.
 */

public class DateConverter {
    @TypeConverter
    public Long dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }

    @TypeConverter
    public Date dateFromTimestamp(long time) {
        return new Date(time);
    }
}
