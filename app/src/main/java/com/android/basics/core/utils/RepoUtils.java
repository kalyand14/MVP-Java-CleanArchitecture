package com.android.basics.core.utils;

import java.util.ArrayList;
import java.util.List;

public class RepoUtils {

    private RepoUtils() {
    }

    public interface FilterList<T> {
        boolean addToList(T object);
    }

    public static <T> List<T> filterList(List<T> objectList, FilterList<T> filter) {
        List<T> result = new ArrayList<>();
        if (filter != null) {
            for (T object : objectList) {
                if (filter.addToList(object)) {
                    result.add(object);
                }
            }
        }
        return result;
    }

    public interface FilterItem<T> {
        boolean get(T object);
    }

    public static <T> T filterItem(List<T> objectList, FilterItem<T> filter) {
        if (filter != null) {
            for (T object : objectList) {
                if (filter.get(object)) {
                    return object;
                }
            }
        }
        return null;
    }

    public static <T> boolean isNotNullNotEmpty(List<T> objectList) {
        return objectList != null && !objectList.isEmpty();
    }

}
