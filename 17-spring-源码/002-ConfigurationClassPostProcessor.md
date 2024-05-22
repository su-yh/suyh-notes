





```java
public class ConfigurationClassPostProcessor implements BeanDefinitionRegistryPostProcessor, 
        PriorityOrdered, ResourceLoaderAware, ApplicationStartupAware, BeanClassLoaderAware, EnvironmentAware {
            
	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
		// ... 其他代码省略
        
        // 关键入口
		processConfigBeanDefinitions(registry);
	}

    public void processConfigBeanDefinitions(BeanDefinitionRegistry registry) {
		List<BeanDefinitionHolder> configCandidates = new ArrayList<>();
        // 此时还比较早，candidateNames 中包含有spring 内部的bean 以及最关键的一个就是main 方法对应的那个类。
		String[] candidateNames = registry.getBeanDefinitionNames();

        // 这里的for 循环的关键作用就是将spring 内部的bean 给过滤掉，同时保留下其他的配置类(@Configuration) ，
        // 具体哪些算配置类，参考：ConfigurationClassUtils#checkConfigurationClassCandidate(..)
        // 一般情况下，这里就只会留下一个被@SpringBootApplication 标识的那个类。
		for (String beanName : candidateNames) {
			BeanDefinition beanDef = registry.getBeanDefinition(beanName);
			if (beanDef.getAttribute(ConfigurationClassUtils.CONFIGURATION_CLASS_ATTRIBUTE) != null) {
				if (logger.isDebugEnabled()) {
					logger.debug("Bean definition has already been processed as a configuration class: " + beanDef);
				}
			}
			else if (ConfigurationClassUtils.checkConfigurationClassCandidate(beanDef, this.metadataReaderFactory)) {
				configCandidates.add(new BeanDefinitionHolder(beanDef, beanName));
			}
		}

		// ... 其他代码省略

        // 为解析配置类，创建对应的解析器
		// Parse each @Configuration class
		ConfigurationClassParser parser = new ConfigurationClassParser(
				this.metadataReaderFactory, this.problemReporter, this.environment,
				this.resourceLoader, this.componentScanBeanNameGenerator, registry);

		Set<BeanDefinitionHolder> candidates = new LinkedHashSet<>(configCandidates);
		Set<ConfigurationClass> alreadyParsed = new HashSet<>(configCandidates.size());
		do {
			StartupStep processConfig = this.applicationStartup.start("spring.context.config-classes.parse");
            // 关键就是这里了，这里一调用结束，所有的bean 都被扫描并解析了，也就是所有的BeanDefinition 都准备好了。
			parser.parse(candidates);
			parser.validate();

			// ... 其他代码省略
		}
		while (!candidates.isEmpty());

		// ... 其他代码省略
	}
}
```

