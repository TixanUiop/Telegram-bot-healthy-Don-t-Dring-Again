package DAO

interface DAO<R,T> {
    //CRUD
    Optional<R> create(R user);
    Optional<R> delete(T id);
    Optional<R> read(T id);
    Optional<R> update(T id);
}