Map 是一个接口
	接口定义的集合又称为查找表，用于存储所谓 "key-value" 映射对。key 可以看成是value 索引，作为key 的对象在集合中不可以重复。 
	根据内部数据结构的不同，Map 接口有多种实现类，其中常用的内部为Hash 表实现的HashMap 和内部为排序红黑树实现的TreeMap。

	Map<key, value> key 和value 都必须是引用类型，不可以是基本数据类型

	put(K key, V value): 向集合中添加元素

	创建：
		Map<String, Integer> map = new HashMap<String, Integer>();

		map.put("语言", 90);	// 如果已经存在了，则会使用最新的值替换掉原来的值，并返回被替换掉的值；如果原来不存在，则返回一个null 值
		map.containsKey(K key);		// 调用equals() 方法
		
	Map 集合的遍历
		keySet(): 得到一个Key 值 的set 集合;
			Map<String, Integer> map = new HashMap<String, Integer>();
			Set<String> keyset = map.keySet();
			for (String str : keyset)
				System.out.println(str);
			
		entrySet(): 得到map 集合中的所有key-value 的set 集合
			Map<String, Integer> map = new HashMap<String, Integer>();
			Set<java.util.Map.Entry<String, Integer>> set = map.entrySet();
			for (java.util.Map.Entry<String, Integer> e : set)
			{
				String key = e.getKey();
				Integer value = e.getValue();
			}

Object 类说明文档：
	public int hashCode()返回该对象的哈希码值。支持此方法是为了提高哈希表（例如 java.util.Hashtable 提供的哈希表）的性能。 
	hashCode 的常规协定是： 

		在 Java 应用程序执行期间，在对同一对象多次调用 hashCode 方法时，必须一致地返回相同的整数，前提是将对象进行 equals 比较时所用的信息没有被修改。从某一应用程序的一次执行到同一应用程序的另一次执行，该整数无需保持一致。 
		如果根据 equals(Object) 方法，两个对象是相等的，那么对这两个对象中的每个对象调用 hashCode 方法都必须生成相同的整数结果。 
		如果根据 equals(java.lang.Object) 方法，两个对象不相等，那么对这两个对象中的任一对象上调用 hashCode 方法不 要求一定生成不同的整数结果。但是，程序员应该意识到，为不相等的对象生成不同整数结果可以提高哈希表的性能。 
		实际上，由 Object 类定义的 hashCode 方法确实会针对不同的对象返回不同的整数。（这一般是通过将该对象的内部地址转换成一个整数来实现的，但是 JavaTM 编程语言不需要这种实现技巧。） 



HashMap - 无序Map
	Object::HashCode()

	Hash算法：
		* key 的 HashCode 值经过散列算法，得到散列下标，不同的 hashcode 值经过散列算法得到的散列下标可能相同，如果散列下标相同，那么出现链表
		* 出现链表会降低查询性能，HashMap查询性能是最好的。
		* 为了降低链表出现的概率，需重写equals 和hashcode 方法，重写这两个方法可以降低链表出现的概率，但不能避免。
		* 总结：当key 元素HashCode 相同，但是equals 比较不同时就会在HashMap 中产生链表，影响查询性能。


TreeMap - 排序Map
	使用了红黑树进行了排序。

LinkedHasMap
	使用Map接口的哈希表和链表实现，具有可预知的迭代顺序。此实现与HashMap的不同之处在于：LinkedHasMap 维护着一个双向循环链表。此链表定义了迭代顺序，该迭代顺序 通常就是存放 顺序。

	// 1. 能按put 顺序存储对象
	// 2. 能按LRU 算法记录最近最少访问的对象
	// LinkedHashMap<String, Object> map 
	//		= new LinkedHashMap<String, Object>(10, 0.75f, true);

	案例：
		HaspMap中元素取出顺序 和Put的顺序不一定一样。
		LinkedHashMap 中元素取出顺序和put 的顺序一致。

线程安全的map
	1. Map<String, String> map = new Hashtable<>();
		// 对整个map 加锁，效率低下，性能很差。
	2. Map<String, String> map = new HashMap<>();
	   	map =  Collections.synchronizedMap(map);
	   	// 效率可以
	3. Map<String, String> map = new ConcurrentHashMap<>();
		// 分段加锁，效率较好 高并发环境用
	从性能上讲: Hashtable < Collections.synchronizedMap(map) < ConcurrentHashMap




























































