package com.janero.movies.domain.mapper;

public interface Mapper<T, U> {

    public U mapToDTO(T source);

}
