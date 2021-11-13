#Java 中的反射机制

## java 反射机制
- 介绍
	> 运行期检查类的信息：类的名字、属性、方法<br>
	> 反射的好处：和未来的程序降低耦合
	
		java.lang.reflect.Field;
		
		Class cls = obj.getClass();
		// 获取类的所有成员变量
		Field[] fs = cls.getDeclaredFields();


- 反射的动态执行
	- > 动态加载类到内存方法区
		>> - 加载类到方法区  需要指定完整包路径
		>> - `Class cls = Class.forName("cn.tedu.reflact.Foo");`
	
	- > 动态创建对象
		>>		newInstance() - Class
		>>		Object obj = cls.newInstance();
		
	- > 动态调用方法
		>>		Object obj = cls.newInstance();
		>>		Method[] methods = cls.getDeclaredMethods();
		>>		
		>>		// 遍历所有方法，判断方法是否以test 开头，如果是则执行此方法
		>>		for (Method m : methods) {
		>>			if (m.getName().startsWith("test")) {
		>>				// 执行此方法
		>>				Object val = m.invoke(obj);	// 参数一：该方法所在的对象, 参数二：参数列表 返回方法的返回值
		>>			}
		>>		}
	
	> 动态调用不可访问方法
	>> private 修饰的<br>
	>> 跨包访问
	>> 
	>> 在调用方法之前先调用方法 `setAccessible` 将不可访问的所有属性变为全可访问<br>
	>> `method.setAccessible(true);`
	
	
- 示例: 
	> 输入: java.util.Date  
	> 输入: getTime

		public static void main(String[] args) throws Exception {
			Scanner sc = new Scanner(System.in);
			System.out.print("Please input class: ");
			String pkgcls = sc.nextLine();
			System.out.print("Please input method: ");
			String methodName = sc.nextLine();
			
			// 1. 构建类对象
			Class<?> c = Class.forName(pkgcls);
			// 2. 获取类中方法对象
			Method m = c.getDeclaredMethod(methodName);
			// 3. 执行类的对象的方法
			Object obj = c.newInstance();
			
			Object result = m.invoke(obj);
			System.out.println(result);
			
			sc.close();
		}
	
## 普通反射的使用
- 通过类名创建一个对象: **<span style="color:red">无参构造</span>**
	
		Class<?> c = Class.forName("java.util.Date");
		java.util.Date d = c.newInstance();
- 通过对象创建一个对象: **<span style="color:red">带参构造</span>**

		Class<?> c = Class.forName("reflect.Point");
		Constructor<?> con1 = c.getDeclaredConstructor(int.class, int.class);
		reflect.Point p2 = (Point)con1.newInstance(10, 20);

## 反射API
- `getDeclaredConstructors()` 获取所有的构造方法
	- 遍历所有的构造方法

			Class<?> c = Class.forName("reflect.Point");
			Constructor<?>[] cs = c.getDeclaredConstructors();
			for (Constructor<?> cc : cs) {
				// ...
			}
