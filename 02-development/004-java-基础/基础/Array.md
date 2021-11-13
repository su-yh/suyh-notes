
- ### 数组的声明：
	> - 数组的声明：
	>>		int[] arr;
	>>		int arr[];
	>>		// 数组的声明只有上面的两种，初始化数组的时候不允许在初始化的时候指定
	>>		int[4] arr;	// 这样的声明是错误的。
	>>		int arr[4];	// 这样的声明也是错误的。

- ### 数组的初始化：
	> - 数组的初始化
	>>		int[] arr = { 10, 20, 30 };	// 静态初始化
	>>		int[] arr = new int[2];	// 动态初始化	给定长度但不赋修士，元素会默认初始化
	>>		int[] arr = new int[]{ 10, 20 };	// 静态初始化
	>>		int[] arr = null;	// 将这个数组初始化为null，然后就可以使用 arr == null 进行判断




	
- ### 数组的复制
	> - Arrays.copyOf() 数组复制
	> - 数组的扩容，此扩容并不是真正的扩容
	>>		ary = Arrays.copyOf(ary, 5);

	> - System.arraycopy()
	>>		arraycopy(Object src, int srcPos, Object dest, int destPos, int length);
	>>		src - 源数组
	>>		srcPos - 源数组中的起始位置
	>>		dest - 目标数组
	>>		destPos - 目标数组中的起始位置
	>>		length - 复制的元素个数

- ### 多维数组
	> - 多维数组中的二组中的元素个数可以不同
	>> 		int[][] arr1 = {{1, 1, 1}, {2, 2}, {3, 3, 3, 3}};
	> - JAVA 中允许第二维的长度不一致，与C++ 的有很大的不同。像上一行那样的，每一个二维数组的个数都是不一样的。
	> - 通过循环遍历，可以得到第一个二维数组的长度是3， 第二个是2， 第三个是4














