package com.rincentral.test.utils.mapers;

public interface Mapper<T1, T2, T3> {
    T1 map(T2 t2, T3 t3);
}
