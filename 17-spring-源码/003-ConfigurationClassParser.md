



> 在`ConfigurationClassParser` 中关键的就是4 个重载的`parse(..)` 方法



## 入口 parse(..) 方法

```java
class ConfigurationClassParser {
    
    // 该方法主要是提供给外部调用的，也就是主要的入口点
    // 一般情况下，这里的参数就只有一个，也就是被注解@SpringBootApplication 标识的那个类
    public void parse(Set<BeanDefinitionHolder> configCandidates) {
		for (BeanDefinitionHolder holder : configCandidates) {
			BeanDefinition bd = holder.getBeanDefinition();
			try {
				if (bd instanceof AnnotatedBeanDefinition) {
                    // 一般情况下是走这个分支
					parse(((AnnotatedBeanDefinition) bd).getMetadata(), holder.getBeanName());
				}
				else if (bd instanceof AbstractBeanDefinition && ((AbstractBeanDefinition) bd).hasBeanClass()) {
					parse(((AbstractBeanDefinition) bd).getBeanClass(), holder.getBeanName());
				}
				else {
					parse(bd.getBeanClassName(), holder.getBeanName());
				}
			}
			catch (BeanDefinitionStoreException ex) {
				throw ex;
			}
			catch (Throwable ex) {
				throw new BeanDefinitionStoreException(
						"Failed to parse configuration class [" + bd.getBeanClassName() + "]", ex);
			}
		}

		this.deferredImportSelectorHandler.process();
	}
}
```



## 三个重载的parse(..) 方法

> 如果算上上面的那个parse(..) 方法，parse(..) 一共有四个重载方法
>
> 但是这里只计 protected final 的三个重载方法

```java
class ConfigurationClassParser {
    // 这里的三个重载方法其实就是因为参数不同，但他们最终都是调用了 
    // processConfigurationClass(new ConfigurationClass(clazz, beanName), DEFAULT_EXCLUSION_FILTER);
    protected final void parse(@Nullable String className, String beanName) throws IOException {
		Assert.notNull(className, "No bean class name for configuration class bean definition");
		MetadataReader reader = this.metadataReaderFactory.getMetadataReader(className);
		processConfigurationClass(new ConfigurationClass(reader, beanName), DEFAULT_EXCLUSION_FILTER);
	}

	protected final void parse(Class<?> clazz, String beanName) throws IOException {
		processConfigurationClass(new ConfigurationClass(clazz, beanName), DEFAULT_EXCLUSION_FILTER);
	}

	protected final void parse(AnnotationMetadata metadata, String beanName) throws IOException {
		processConfigurationClass(new ConfigurationClass(metadata, beanName), DEFAULT_EXCLUSION_FILTER);
	}
    
}
```



## processConfigurationClass(..) 方法

> `processConfigurationClass` 方法很明显，就是针对配置类的解析处理
>
> 其实该方法并没有做什么逻辑处理，只是最终调用了：`doProcessConfigurationClass` 方法而以。

```java
class ConfigurationClassParser {
    protected void processConfigurationClass(ConfigurationClass configClass, Predicate<String> filter) throws IOException {
    	// 是否需要跳过
		if (this.conditionEvaluator.shouldSkip(configClass.getMetadata(), ConfigurationPhase.PARSE_CONFIGURATION)) {
			return;
		}

		// ... 忽略一些不太关心的代码

		// Recursively process the configuration class and its superclass hierarchy.
		SourceClass sourceClass = asSourceClass(configClass, filter);
		// 为什么这里有循环处理？
		// 其实这里物理处理的主要是有父类的情况，但是不处理接口，因为接口在里面已经处理了。
		do {
			// 重点在这里
			sourceClass = doProcessConfigurationClass(configClass, sourceClass, filter);
		}
		while (sourceClass != null);

		this.configurationClasses.put(configClass, configClass);
	}
}
```



## doProcessConfigurationClass(..) 方法

> 个人认为这个方法就相当重要了，重要的原因是它会有递归调用，而这个递归调用却不明显，不容易看。
>
> 新手第一次看这部分代码的时候往往会晕车的，特别是debug 的时候。你会迷茫，我到底跟到哪里去了。
>
> 这个地方我 是晕了好久才晕出来 的。

```java
class ConfigurationClassParser {
    // 这里的参数，其实很明显的知道它是一个配置类，一般的普通bean 类，并不直接由它处理，不过这里会调用相关的方法去处理。
    @Nullable
	protected final SourceClass doProcessConfigurationClass(
			ConfigurationClass configClass, SourceClass sourceClass, Predicate<String> filter)
			throws IOException {

        // 这里主要处理的就是内部类(非静态内部类)，但一般是没有的。所以如果是一般情况基本都是跳过的。
		if (configClass.getMetadata().isAnnotated(Component.class.getName())) {
			// Recursively process any member (nested) classes first
			processMemberClasses(configClass, sourceClass, filter);
		}

        // 这里是专门处理标记为@PropertySource 的bean
		// Process any @PropertySource annotations
		for (AnnotationAttributes propertySource : AnnotationConfigUtils.attributesForRepeatable(
				sourceClass.getMetadata(), PropertySources.class,
				org.springframework.context.annotation.PropertySource.class)) {
			if (this.environment instanceof ConfigurableEnvironment) {
				processPropertySource(propertySource);
			}
			else {
				logger.info("Ignoring @PropertySource annotation on [" + sourceClass.getMetadata().getClassName() +
						"]. Reason: Environment must implement ConfigurableEnvironment");
			}
		}

        // 处理@ComponentScan 的bean，也包含@SpringBootApplication。所以一般情况我们的main 方法所在的类就由它处理。
		// Process any @ComponentScan annotations
		Set<AnnotationAttributes> componentScans = AnnotationConfigUtils.attributesForRepeatable(
				sourceClass.getMetadata(), ComponentScans.class, ComponentScan.class);
		if (!componentScans.isEmpty() &&
				!this.conditionEvaluator.shouldSkip(sourceClass.getMetadata(), ConfigurationPhase.REGISTER_BEAN)) {
			for (AnnotationAttributes componentScan : componentScans) {
                // 解析出要扫描的包下的bean，就是被相关注解(@Service、@Controller、@Component)标记的类。
                // 这里只处理对应包下的java 类，其他包下的，或者通过注解依赖了别的包的类，都不会包含在该set变量中。
				// The config class is annotated with @ComponentScan -> perform the scan immediately
				Set<BeanDefinitionHolder> scannedBeanDefinitions =
                    	// 这里就是：ComponentScanAnnotationParser#parse(..)
						this.componentScanParser.parse(componentScan, sourceClass.getMetadata().getClassName());
				// Check the set of scanned definitions for any further config classes and parse recursively if needed
				for (BeanDefinitionHolder holder : scannedBeanDefinitions) {
					BeanDefinition bdCand = holder.getBeanDefinition().getOriginatingBeanDefinition();
					if (bdCand == null) {
						bdCand = holder.getBeanDefinition();
					}
                    // 这里要注意了，递归调用到parse(..) 方法，也就是上面的三个重载方法中的第一个。
                    // 但是前提是该类是配置类，也就是这里的if 条件结果为true。绝大多数就是 @Configuration 标识的类。
					if (ConfigurationClassUtils.checkConfigurationClassCandidate(bdCand, this.metadataReaderFactory)) {
						parse(bdCand.getBeanClassName(), holder.getBeanName());
					}
				}
			}
		}

        // 如果当前bean 上有被@Import 注解标识，则将会做相应的处理。
        // 包括组合注解中包含了@Import 注解的，都在这里处理。
        // 可以先重点关注一下 getImports(sourceClass) 这个方法。不要简单的跳过哟，除非你清楚自己跳过了什么。
		// Process any @Import annotations
		processImports(configClass, sourceClass, getImports(sourceClass), filter, true);

        // 处理@ImportResource 注解标识 的bean，但这个东西不知道是啥，估计是历史原因遗留的吧，反正我是没用过。
        // 所以我都是直接跳过的。
		// Process any @ImportResource annotations
		AnnotationAttributes importResource =
				AnnotationConfigUtils.attributesFor(sourceClass.getMetadata(), ImportResource.class);
		if (importResource != null) {
			String[] resources = importResource.getStringArray("locations");
			Class<? extends BeanDefinitionReader> readerClass = importResource.getClass("reader");
			for (String resource : resources) {
				String resolvedResource = this.environment.resolveRequiredPlaceholders(resource);
				configClass.addImportedResource(resolvedResource, readerClass);
			}
		}

        // 处理@Bean 注解标识的方法
		// Process individual @Bean methods
		Set<MethodMetadata> beanMethods = retrieveBeanMethodMetadata(sourceClass);
		for (MethodMetadata methodMetadata : beanMethods) {
			configClass.addBeanMethod(new BeanMethod(methodMetadata, configClass));
		}

        // 父接口的方法是否有@Bean 注解标识的方法，如果有也做对应处理。
        // 这就是前面提到的，父类在do .. while(..) 循环中处理，而接口就是在这里处理了。
		// Process default methods on interfaces
		processInterfaces(configClass, sourceClass);

        // 如果存在父类，则返回给调用者，让它继续递归调用执行。
		// Process superclass, if any
		if (sourceClass.getMetadata().hasSuperClass()) {
			String superclass = sourceClass.getMetadata().getSuperClassName();
			if (superclass != null && !superclass.startsWith("java") &&
					!this.knownSuperclasses.containsKey(superclass)) {
				this.knownSuperclasses.put(superclass, configClass);
				// Superclass found, return its annotation metadata and recurse
				return sourceClass.getSuperClass();
			}
		}

        // 没有父类，那就直接结束了。
		// No superclass -> processing is complete
		return null;
	}
}
```



## processImports(..) 方法



```java
class ConfigurationClassParser {
    
	private void processImports(ConfigurationClass configClass, SourceClass currentSourceClass,
			Collection<SourceClass> importCandidates, Predicate<String> exclusionFilter,
			boolean checkForCircularImports) {

		if (importCandidates.isEmpty()) {
			return;
		}

		if (checkForCircularImports && isChainedImportOnStack(configClass)) {
			this.problemReporter.error(new CircularImportProblem(configClass, this.importStack));
		}
		else {
			this.importStack.push(configClass);
			try {
				for (SourceClass candidate : importCandidates) {
					if (candidate.isAssignable(ImportSelector.class)) {
						// Candidate class is an ImportSelector -> delegate to it to determine imports
						Class<?> candidateClass = candidate.loadClass();
						ImportSelector selector = ParserStrategyUtils.instantiateClass(candidateClass, ImportSelector.class,
								this.environment, this.resourceLoader, this.registry);
						Predicate<String> selectorFilter = selector.getExclusionFilter();
						if (selectorFilter != null) {
							exclusionFilter = exclusionFilter.or(selectorFilter);
						}
						if (selector instanceof DeferredImportSelector) {
							this.deferredImportSelectorHandler.handle(configClass, (DeferredImportSelector) selector);
						}
						else {
							String[] importClassNames = selector.selectImports(currentSourceClass.getMetadata());
							Collection<SourceClass> importSourceClasses = asSourceClasses(importClassNames, exclusionFilter);
							processImports(configClass, currentSourceClass, importSourceClasses, exclusionFilter, false);
						}
					}
					else if (candidate.isAssignable(ImportBeanDefinitionRegistrar.class)) {
						// Candidate class is an ImportBeanDefinitionRegistrar ->
						// delegate to it to register additional bean definitions
						Class<?> candidateClass = candidate.loadClass();
						ImportBeanDefinitionRegistrar registrar =
								ParserStrategyUtils.instantiateClass(candidateClass, ImportBeanDefinitionRegistrar.class,
										this.environment, this.resourceLoader, this.registry);
						configClass.addImportBeanDefinitionRegistrar(registrar, currentSourceClass.getMetadata());
					}
					else {
						// Candidate class not an ImportSelector or ImportBeanDefinitionRegistrar ->
						// process it as an @Configuration class
						this.importStack.registerImport(
								currentSourceClass.getMetadata(), candidate.getMetadata().getClassName());
						processConfigurationClass(candidate.asConfigClass(configClass), exclusionFilter);
					}
				}
			}
			catch (BeanDefinitionStoreException ex) {
				throw ex;
			}
			catch (Throwable ex) {
				throw new BeanDefinitionStoreException(
						"Failed to process import candidates for configuration class [" +
						configClass.getMetadata().getClassName() + "]", ex);
			}
			finally {
				this.importStack.pop();
			}
		}
	}
}
```

