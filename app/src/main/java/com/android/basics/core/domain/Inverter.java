package com.android.basics.core.domain;

public interface Inverter<From, To> {
    To invert(From fromObj);
}
