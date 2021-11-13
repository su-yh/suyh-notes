集合操作 - 线性表
List
	List 接口是Collection 的子接口，用于定义线性表数据 结构。可以将List 理解 为存放 对象的数组，只不过其元素个数可以动态的增加或者减少。
	List 接口的两个 常见实现 类为ArrayList 和LinkedList, 分别用动态数组和链表的方式 实现了List 接口。
	可以认为ArrayList 和LinkedList 的方法在逻辑 上完全 一样，只是在性能上有一定的差别。ArrayList 更适合于随机访问而LinkedList 更有适合插入和删除。在性能要求不是特别 苛刻 的情形下可以忽略 这个差别。

	ArrayList 和LinkedList 两者的数据结构
	ArrayList 动态数组的形式。
	LinkedList 链表的形式。
	
	List<String> list = new ArrayList<String>();
	List<String> list = new LinkedList<String>();

	get 和 set:
		get(int index): 下标从0 开始。
		set(int index, E element): 将给定元素插入到指定位置。
	插入和删除：
		add remove
		void add(int index, E element): 当然位置以及后面的元素都顺序后移
		E remove(int index): 删除指定位置的元素，并将删除的元素返回
		如果在遍历过程在需要删除元素，则不允许使用此remove() 方法，而应该使用Iterator 的remove() 方法。

	List 转换为数组：
		List 的toArray 方法用于将集合转换为数组。但实际上该方法是在Collection 中定义的，所以所有的集合都具备这个功能。
		其有两个方法：
			Object[] toArray()
			<T>T[] toArray(T[] a)
			其中第二个方法是比较常用的，我们可以传入一个指定类型的数组，该数组的元素类型应与集合的元素类型一致。返回值则是转换后的数组 该数组会保存集合中所有的元素。

	数组转换为List:
		Arrays 类中提供了一个静态方法asList, 使用该 方法我们可以将一个数组转换为对应的List 集合。
		并且 要注意的是，返回的集合我们不能对其增删元素，否则会抛出异常。并且对集合的元素进行修改会影响数组对应的元素。

	List 和数组的转换：
		List -> 数组 : toArray()  toArray(T[])
		数组 -> List : Arrays.toList(T...a) @ static 方法

	遍历：
		List<String> list = new ArrayList<String>();
		Iterator<String> it = list.iterator();
		while (it.HasNext())
		{
			String str = it.next();
			
			{
				代码块
			}
		}















































