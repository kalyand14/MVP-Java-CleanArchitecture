package com.android.basics.core.utils.res;

import android.graphics.Color;
import android.util.DisplayMetrics;

import androidx.annotation.IdRes;
import androidx.annotation.PluralsRes;

import com.android.basics.core.utils.ResourceManager;

import java.util.HashMap;
import java.util.Map;

public class TestResourceManager implements ResourceManager {

    private static final String ONE = "one";
    private static final String OTHER = "other";
    private static final int QUANTITY_ONE = 1;

    private Map<Integer, String> stringMap = new HashMap<>();
    private Map<Integer, Map<String, String>> pluralMap = new HashMap<>();
    private Map<Integer, String[]> stringArrayMap = new HashMap<>();
    private Map<Integer, Integer> integerMap = new HashMap<>();
    private Map<String, String> colorsMap = new HashMap<>();

    public TestResourceManager() {
        parseString();
        parsePlurals();
        parseStringArrays();
        parseIntegers();
        parseColors();
    }

    private void parseColors() {
        ColorResourceSaxParse parser = new ColorResourceSaxParse();
        parser.parse(map -> colorsMap.putAll(map));
    }

    private void parseIntegers() {
        IntegerResourceSaxParser parser = new IntegerResourceSaxParser();
        parser.parse(map -> integerMap.putAll(map));
    }

    private void parseStringArrays() {
        StringArrayResourceSaxParser parser = new StringArrayResourceSaxParser();
        parser.parse(map -> stringArrayMap.putAll(map));
    }

    private void parsePlurals() {
        PluralsResourceSaxParse parser = new PluralsResourceSaxParse();
        parser.parse(map -> pluralMap.putAll(map));
    }

    private void parseString() {
        StringResourceSaxParser parser = new StringResourceSaxParser();
        parser.parse(map -> stringMap.putAll(map));
    }

    @Override
    public String getString(@IdRes int resourceId) {
        return stringMap.get(resourceId);
    }

    @Override
    public String getString(int resourceId, Object... formatArgs) {
        String temp = stringMap.get(resourceId);
        return String.format(temp, formatArgs);
    }

    @Override
    public String[] getStringArray(int resourceId) {
        return stringArrayMap.get(resourceId);
    }

    @Override
    public int getColor(int resourceId) {
        return Color.parseColor(colorsMap.get(resourceId));
    }

    private String getQuantityAsString(int quantity) {
        return quantity == QUANTITY_ONE ? ONE : OTHER;
    }

    @Override
    public String getQuantityString(@PluralsRes int resourceId, int quantity) {
        Map<String, String> map = pluralMap.get(resourceId);
        return map.get(getQuantityAsString(quantity));
    }

    @Override
    public String getQuantityString(@PluralsRes int resourceId, int quantity, Object... formatArgs) {
        Map<String, String> map = pluralMap.get(resourceId);
        String quantityString = map.get(getQuantityAsString(quantity));
        return String.format(quantityString, formatArgs);
    }

    @Override
    public int getInteger(int resourceId) {
        return integerMap.get(resourceId);
    }

    @Override
    public DisplayMetrics getDisplayMetrics() {
        return null;
    }
}