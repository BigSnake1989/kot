package com.kot.utils;

import java.lang.reflect.Field;

/**
 * Created by wl on 16/8/5.
 */
public class TestClass {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> clazz = Class.forName(TestModel.class.getName());
        Object instance = clazz.newInstance();
        set(instance, "id", "hehe");
        set(instance, "msg", "John");
        TestModel model = (TestModel) instance;
        System.out.println(model.getId());
    }

    public static boolean set(Object object, String fieldName, Object fieldValue) {
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(object, fieldValue);
                return true;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return false;
    }
}

