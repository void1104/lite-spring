package cn.pjx.springlite.beans.factory;

import cn.pjx.springlite.beans.BeanException;

/**
 * Defines a factory which can return an Object instance.
 */
public interface ObjectFactory<T> {

    T getObject() throws BeanException;
}
