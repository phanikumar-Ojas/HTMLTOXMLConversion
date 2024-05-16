package com.org.xml.util;

import com.org.dto.XMLParsedData;

import java.lang.reflect.Field;

public class ReflectionUtil {

    public static Field[] getFields(Object object) {
        return object.getClass().getDeclaredFields();
    }

    public static Object getFieldValue(Field field,String key) {
        try {
            field.setAccessible(true);
            return field.get(field.getName());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
