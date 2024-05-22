







> 它就是对应注解`@ComponentScan` 的处理类，这个类关键的方法就是`parse(..)` ，但是这里面的逻辑基本都没怎么看。
>
> 我知道它就是扫描对应包下面的java 类，然后把标识 了`@Componet`、`@Controller`、`@Service`等这些注解的类找出来，并返回出去。
>
> 还有一个比较关键的就是`@Configuration` 注解的类



```java
class ComponentScanAnnotationParser {
    
    public Set<BeanDefinitionHolder> parse(AnnotationAttributes componentScan, final String declaringClass) {
		// ... 这个类的逻辑我基本都没看
	}
    
}
```

