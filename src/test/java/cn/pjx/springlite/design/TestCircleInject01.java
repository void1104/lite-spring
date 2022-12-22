package cn.pjx.springlite.design;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author pengjiaxin3
 * @description 测试最简单的一级缓存解决循环依赖
 * @date 12/22/22 5:31 PM
 */
public class TestCircleInject01 {

    static class A {
        private B b;

        public String say() {
            return "This is A";
        }

        public B getB() {
            return b;
        }
    }

    static class B {
        private A a;

        public String say() {
            return "This is B";
        }

        public A getA() {
            return a;
        }
    }

    private final static Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

    private static <T> T getBean(Class<T> beanClass) throws Exception {
        String beanName = beanClass.getSimpleName().toLowerCase();
        if (singletonObjects.containsKey(beanName)) {
            return (T) singletonObjects.get(beanName);
        }
        // 实例化对象缓存
        Object object = beanClass.newInstance();
        // 这里往单例map里放的是还没实例化完成的bean(只实例化,但没注入对象属性).
        singletonObjects.put(beanName, object);
        // 属性填充补全对象
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Class<?> fieldClass = field.getType();
            String fieldBeanName = fieldClass.getSimpleName().toLowerCase();
            // 当出现循环依赖时,从singleObject里拿到的是半成品的bean,不过没关系,因为存放的是引用,当前递归退出后,bean会完成剩下的实例化操作.
            field.set(object, singletonObjects.containsKey(fieldBeanName) ? singletonObjects.get(fieldBeanName) : getBean(fieldClass));
            field.setAccessible(false);
        }
        return (T) object;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getBean(B.class).getA());
        System.out.println(getBean(A.class).getB());
    }
}
