package com.github.malcolmszx.web.rest.convert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转换器
 * 
 * @author Leo
 * @date 2018/3/16
 */
final class DateConverter implements Converter<Date> {

    /**
     * 类型转换
     * 
     * @param source
     * @return
     */
    @Override
    public Date convert(Object source) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(source.toString());
        } catch (ParseException e) {
            return null;
        }
    }

}
