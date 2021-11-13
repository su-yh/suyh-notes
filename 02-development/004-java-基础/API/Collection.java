Collection 是一个接口，定义了集合相关的操作方法，其有两个子接口：
	List: 可重复集
	Set: 不可重复集

	注意点：
		集合中存储的都是引用类型元素，并且集合只保存每个元素对象的引用，而并非将元素对象本身存入集合。

	集合的操作：
		add(Object obj): - boolean
			Collection 定义了一个add 方法用于向集合中添加新元素。该将给定加进集合，若添加成功则返回true, 否则返回false。
		contains(Object obj) - boolean
			该用于判断给定的否被包含在集合中。
			判断方式：
				这里需要注意的是，集合在判断否被包含在集合中是根据每个元素的equals() 方法进行比较后的结果。通常有必要重写equals() 保证contains() 方法的合理结果
		size():  获取集合中元素的个数
			数组中获取长度: 
				int[] ary = new int[3];
				int length = ary.length;
			String 中获取字符的个数: 
				String str = "abn";
				int len = str.length();
		clear():
		isEmpty():
		addAll(Collection):
		containsAll(Collection):

	泛型机制：
		注意点：
			泛型位置的数据类型必须是引用类型，不允许是基本数据类型
			collection<int> col = ArrayList<int>();	// 错误，不允许基本数据类型 int
			
		现象：
			Collection col = new ArrayList();
			col.add("java");
			
			Collection<Integer> col1 = new ArrayList<Integer>();
			col1.add(1);
			
			col1.addAll(col);	// 成功了，并且真的存在了一个字符串了。
			
		原因：
			泛型机制在运行期是没有的。
			
	迭代器 Iterator
		迭代器用于遍历集合元素。获取迭代器可以使用Collection 定义的方法: iterator()
		迭代器Iterator 是一个接口集合在重写 Collection 的iterator() 方法时利用内部类提供 了迭代器的实现。
			迭代器Iterator 首先指向的是第一个元素的前面一个位置，并没有真正指向一个元素。
		遍历方式：
			hasNext();
			next();
			remove();
				在使用迭代器遍历集合时，不能通过集合的Collection<E>::remove()方法删除集合元素，否则会抛出并发更改异常。我们可以通过迭代器自身提供的Iterator<E>::remove() 方法来删除通过next() 迭代出的元素。
					Collection<String> col = new ArrayList<String>();
					col.add("java");
					col.add("web");
					col.add(".net");
					java.util.Iterator<String> itr = col.iterator();
					while (itr.hasNext())
					{
						String str = itr.next();
						// 对str 进行是否有字符a 判断 
						int nIndex = str.indexOf('a');
						if (nIndex >= 0)
						{
							itr.remove();
						}
					}

	foreach 循环：增强for 循环
		java5.0 之后推出了一个新的特性，增强for 循环，也称为新循环。该循环不通用于传统循环的工作，其只用于遍历集合或数组。
		在编译过程中，编译器会将新循环转换为迭代器模式。所以新循环本质上是迭代器。
		
		写法: 
			for (数据类型 变量 : Collection)
			{
				代码块
			}

java.util
Collections -- 注意比Collection 多了一个's' 
	Collections 是集合的工具类，它提供了很多便于我们操作集合的方法，其中就有用于集合排序的sort 方法
		void sort(List<T> list);	用于给定集合的自然排序。默认是升序排列

	Comparable:
		Collections 的sort 方法是对集合元素进行自然排序，那么两个元素对象之间一定要有大小之分。这个大小之分是如何 界定的呢？实际上，在使用Collections 的sort 排序的集合元素都必须是Comparable 接口的实现类，该接口表示 其子类是可比较的，因为实现 该 接口必须 重写 抽象方法：
			- int compareTo(T t)
				该方法用于使当前对象与给定对象进行比较。
					若当前对象大于给定对象，那么返回值应为 > 0 的整数。
					若小于给定对象，那么返回值应为 < 0 的整数。
					若相等，则返回0。
		Comparable 接口：类比较器
			如果 一个类实现 了此接口，那么 一定要重写compareTo(Object obj), 在此方法中，将比较规则写出之后才可以调用(Collections.sort())
		Comparator:	// 外比较器，如果对一个集合中进行排序时，不想用集合中元素已有的排序规则，而是用自定义规则，此时需要写一个类实现compare() 接口
			一但Java 类实现 了Comparable 接口，其比较逻辑 就已经确定；如果希望在排序 的操作中临时 指定比较规则，可以采用Comparator 接口回调的方式 。
			Comparator 接口要求实现类必须 重写其定义 的方法: int compare(T o1, T o2)
				// 外比较器 Comparator
				// 实现学生类按年龄进行排序
				private class ByAge implements Comparator<Student>
				{
					@Override
					public int compare(Student o1, Student o2) {
						return o1.getAge() - o2.getAge();
					}
				}
				// 然后调用对应参数的sort() 方法
				List<Student> lsStudent = new ArrayList<Student>();
				Collections.sort(lsStudent, new ByAge());

		Comparable 和 Comparator 都是接口
		Comparable: 接口
			使用：
				1、一个类需要实现此接口；
				2、重写compareTo(T other) 方法；
				3、对集合进行排序，调用Collections.sort(List) 方法；
		Comparator: 接口
			使用：
				1、一个类实现此接口；
				2、重写compare(T o1, To2) 方法; 
				2、对集合进行排序，调用Collections.sort(List, Comparator) 方法；










































