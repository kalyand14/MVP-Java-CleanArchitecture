package com.android.basics.core;

public interface Mapper<From, To> {

    To convert(From fromObj);
}
