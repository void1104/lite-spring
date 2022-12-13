# lite-spring
微型spring，用于理解spring底层原理


### 核心类的解释和作用
 - BeanDefinition：spring中bean的定义，存放类的一些基础信息 
 - BeanFactory：bean工厂类的最上级接口，定义了bean工厂的框架
 - BeanFactoryPostProcessor
   - 由Spring提供的容器扩展机制，允许在Bean注册后但未实例化前，对BeanDefinition进行修改.
   - 用户可实现该接口并自定义逻辑，spring会扫描所有实现了该接口的类，并在实例化前调用实现类的方法.
 - BeanPostProcessor
   - 也是Spring提供的容器扩展机制，但它是在对象实例化后修改Bean对象，也可以替换Bean对象.
   - 用户可实现该接口并自定义逻辑，spring会扫描所有实现了该接口的类，并在实例化前调用其beforeXXX和afterXXX方法.




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
- step04:
  - 做的事情：实现应用上下文允许用户在Bean对象从注册到实例化过程中执行自定义操作（修改bean定义信息）
  - 引入了`BeanFactoryPostProcessor`和`BeanPostProcessor`组件，用于实现bean的自定义扩展.
  - `DefaultListBeanFactory`类是面向spring内部设计的，所以要向上封装一层`ApplicationContext`的概念供用户使用.
- step05:
  - 做的事情：引入`InitializingBean`和`DisposableBean`俩接口，实现Bean的一些自定义初始化逻辑.
  - Spring的初始化方法其实就是实现了`InitializingBean`接口并调用其接口实现的方法，所以初始化顺序会比Java的初始化函数慢.
  - 在`AbstractAutowireCapableBeanFactory`的createBean方法中，执行初始化方法`invokeInitMethod`的调用
  - 用适配器模式引入`DisposableBean`在`DefaultSingletonBeanRegistry`中存放需要执行销毁的bean.
  - 销毁的bean在`AbstractAutowireCapableBeanFactory`中进行注册，在`AbstractApplicationContext`整体执行逻辑中，执行销毁逻辑