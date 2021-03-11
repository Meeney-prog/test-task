package com.rincentral.test.repositories;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbstractInMemoryRepository<T> implements RepositoryInterface<T> {
    protected final Set<T> repository = new HashSet<>();

    @Override
    public void save(T t) {
        repository.add(t);
    }

    @Override
    public void saveAll(Collection<? extends T> t) {
        repository.addAll(t);
    }

    @Override
    public Set<T> getAll() {
        return repository;
    }

    @Override
    public Set<T> search(Predicate<T> predicate) {
        return repository
                .stream()
                .filter(predicate)
                .collect(Collectors.toCollection(HashSet::new));
    }
}
