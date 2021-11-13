日期操作 Date
java.util 工具包内

构造方法：
	Date(): 创建一个Date() 对象，此对象表示当前时间
	Date(long ms): 创建一个Date() 对象，表示ms 指向的时间对象

方法：
	getTime(): 得到当前时间与历元时间的差值，单元毫秒

获取当前时间方式：
	1). Date date = new Date();	// 空构造函数
	2). System.currentTimeMills();	// -- long 与方法1 所得到的值是一样的



