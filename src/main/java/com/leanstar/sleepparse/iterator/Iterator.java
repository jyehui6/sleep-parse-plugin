package com.leanstar.sleepparse.iterator;

/**
 * 自定义迭代器的抽象接口
 */
public interface Iterator<E> {

    boolean hasNext();

    E next();

}
