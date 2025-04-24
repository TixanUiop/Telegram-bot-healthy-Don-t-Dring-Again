package Mapper

interface Mapper<F, T> {
    T ToMap(F entity)
}