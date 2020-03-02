package com.android.basics.core;

public interface Inverter<From, To> {
    To invert(From fromObj);
}
