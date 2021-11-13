
文件流：
	0、注意点: 
		1、使用结束，关闭流. close();
		2、FileOutputStream 没有文件指针，无法进行位置偏移

	输出流
		0、注意点: 
			3、若不是追加方式，则文件流中原有数据将会被截断(删除)

		构造：
			若文件不存在则创建文件
			FileOutputStream(File file);
			FileOutputStream(File file, boolean append);
			FileOutputStream(String name);
			FileOutputStream(String name, boolean append);

	输入流
		0、注意点：
			3、若不存在，则失败
		构造：
			FileInputStream(File file);
			...
