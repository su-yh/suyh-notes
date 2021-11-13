Calendar
	是一个抽象类，其具体子类针对不同国家的日历系统，其中应用最广泛的是GregorianCalendar(格里高里历，即通用的阳历), 对应世界上绝大多数国家/地区使用的标准日历系统。
	
	属于包：java.util
	
得到Calendar 对象
	1): Calendar calendar = new GregorianCalendar();
	2):
		调用静态方法 getInstance()
			getInstance 会根据系统当前时区来创建子类对象(有可能会创建当时代历法对象)，但是一般创建的都是 GregorianCalendar 对象

Calendar 类中定义了一些日历字段(字段摘要)
	使用Calendar 提供的 get 方法及一些常量可以获取日期及时间分量
	static int YEAR  指示年份的字段数字
	static int MONTH 月份 : 从 0 开始
	static int DATE 一个月中的第几天
	static int DAY_OF_WEEK 一周是的某天， 从 1 开始

方法: 
	get(int field);	// 这里的字段就是上面的 YEAR MONTH DATE DAY_OF_WEEK 这一类静态字段
	getTime();	// 返回一个Date 对象
	getTimeInMills();	// 和Date 类中的getTime 是一样的
	getActualMaximum(int field);

	set(int field, int value);
	setTime(Date date);

	add(int field, int amount);	// 对给定日历字段值进行加减操作，amount 变量的值可正可负


	