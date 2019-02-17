package com.shsxt.xmjf.server.base;

public interface BaseMapper<T> {

    public  int insert(T entity);

    public  T queryById(Integer id);

    public  int update(T entity);

    public  int delete(Integer id);
}
