# lite-spring
微型spring，用于理解spring底层原理


### 核心类的解释和作用
 - BeanDefinition：spring中bean的定义，存放类的一些基础信息 




### 渐进实现过程
- step01
  - 做的事情：bean容器及bean的定义、注册、获取
  - 引入`BeanDefinition`的概念，及其注册中心，其是bean容器实现懒加载的基础.
  - 围绕`BeanFactory`设计bean容器架构，由其子类承接bean的实例化和获取.
- step02:
  - 做的事情：实现有参构造bean的实例和对象属性的注入
  - 在`AbstractAutowireCapableFactory`类中接入这两能力
    - 有参构造实例化：策略模式引入实例化的不同策略，在实例化bean时将入参于beanClass拿到的构造函数进行匹配，选择匹配成功的构造函数进行实例化.
    - 对象属性的注入：引入了`BeanReference`和`PropertyValue`的概念，在实例化时通过反射的方式为bean注入对象属性.
- step03:
  - 做的事情：实现配置文件去初始化载入`beanDefinition`
  - 定义了`Resource`和`ResourceLoader`概念，可以从指定路径的配置文件、资源中读取类定义的信息
  - 在`BeanDefinitionRegistry`中引入`BeanDefinitionReader`及其子类，为其提供了从配置文件读取bean定义的能力.
  - `BeanDefinitionReader`负责解析从Resource获取的数据，将其转换为`beanDefinition`并注册.