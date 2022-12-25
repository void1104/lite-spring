package cn.pjx.springlite.design;

import cn.pjx.springlite.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * @author pengjiaxin3
 * @description
 * @date 12/25/22 12:57 PM
 */
public class HusbandMother implements FactoryBean<IMother> {

    @Override
    public IMother getObject() throws Exception {
        return (IMother) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{IMother.class}, (proxy, method, args) -> "婚后媳妇妈妈的职责被婆婆代理了！" + method.getName());
    }

    @Override
    public Class<?> getObjectType() {
        return IMother.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
