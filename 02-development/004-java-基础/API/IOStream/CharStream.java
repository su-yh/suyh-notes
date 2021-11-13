字符流(转换流)
	读写单位是字符，字符流只能操作文本数据，不能对其他数据进行操作

	抽象基类：Writer Reader
	Writer: 字符输出流的父类
		OutputStreamWriter 
			OutputStreamWriter(OutputStream out)
			OutputStreamWriter(OutputStream out, String charsetName)

	Reader: 字符输入流的父类
		InputStreamReader

字符缓冲流
	查看 BufferedStream.java 笔记文件
