package com.rincentral.test.repositories;

import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;

public interface RepositoryInterface<T> {
    void save(T t);

    void saveAll(Collection<? extends T> t);

    Set<T> getAll();

    Set<T> search(Predicate<T> predicate);
}
