package com.android.basics.core.domain;

public interface Mapper<From, To> {

    To convert(From fromObj);
}
