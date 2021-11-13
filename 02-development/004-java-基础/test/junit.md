# java 中的单元测试

## 重点: 
- **<span style="color:red">JUnit 不能使用静态方法，否则会报错.</span>**

## JUnit
> JUnit 是一个单元测试框架 <br>
> 基于方法的单元测试<br>
> 依赖`JUnit` 库在maven 中的配置

    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.7</version>
        <scope>test</scope>
    </dependency>

## JUnit 的使用
> 1. 在一个方法上面添加一个`@Test` 注解。
> 2. 选择要运行的测试方法， `run as --> JUnit Test`
>> 也可以在项目的目录结果中选择方法然后右键`run as` 也可以

    import org.junit.Test;
    @Test
	public void testUpdateUser() {
		System.out.println("TestCglib02.testUpdateUser()");
	}

> 3. `@Before` 与`@After`注解，在每一个`@Test` 方法之前与之后执行。

    import org.junit.Before;
    import org.junit.After;
	@Before
	public void init() {
		System.out.println("TestCglib02.init()");
	}
    
    @After
	public void destroy() {
		System.out.println("TestCglib02.destroy()");
	}

