
## 字节缓冲流
- > 缓冲输出流: BufferedOutputStream 
	> - 构造
	>>			FileOutputStream fos = new FileOutputStream("bos.txt");
	>>			BufferedOutputStream bos = new BufferedOutputStream(fos);
	>>			bos.write('a');
	>>			bos.flush();
	>>			bos.close();
	>>			fos.close();

- > 缓冲输入流：BufferedInputStream
	> - 构造
	>>		FileInputStream fis = new FileInputStream("fis.txt");
	>>		BufferedInputStream bis = new BufferedInputStream(fis);
	>>		...
	>>		bis.close();
	>>		fis.close();


## 字符缓冲流
- > 缓冲输出流 BufferedWriter PrintWriter
	> - PrintWriter 也是缓冲字符输出流，它内部总是连接 BufferedWriter, 除此之外 PW 还提供了自动行刷新功能。所以更常用。
	> - PrintWriter 提供了直接对文件进行操作的构造方法
	> - PrintWriter(File file) 使用指定文件创建不具有自动行刷新的新 PrintWriter。
	> - PrintWriter(File file, String csn /* => charSetName*/) 创建具有指定文件和字符集且不带自动刷行新的新 PrintWriter。
	> - PrintWriter(String fileName) 创建具有指定文件名称且不带自动行刷新的新 PrintWriter。
	> - PrintWriter(String fileName, String csn /* => charSetName*/) 创建具有指定文件名称和字符集且不带自动行刷新的新 PrintWriter。

	- > 特点：行自动刷新
		> - 当PrintWriter 的构造方法第一个参数为流(字节流，字符流均可)时，那么支持一个重载的构造方法可以传入一个boolean 值，该值若为true, 则当前 PrintWriter 具有自动行刷新功能，即：每当调用 println 方法写出一个行字符串后会自动调用flush 其数据写出。需要注意，调用print 方法是不会flush 的

	- > 思考：如果 PrintWriter 对象时，可供使用的参数类型是流对象，可不可以指定字符集?
		> - 在PrintWriter 提供的构造方法中没有提供流对象时可以指定字符集的。但是参数流对象是可以指定字符编码的。
		>>		PrintWriter pw = new PrintWriter(
		>>			new OutputStreamWriter(
		>>				new FileOutputStream("fileName"), "UTF-8"));

- > 缓冲输入流 BufferedReader
	> - readLine() 这个方法是读取一行文本，但是文本的最后一个标志着换行的字符将不会被保存下来，它将会被丢弃。






















