文件操作 - File
	java.io.File 用于表示文件(目录)，也就是说程序员可以通过File 类在程序中操作硬盘上的文件和目录。
	
	创建File 对象
	构造方法: 
		1. File(String pathName);
			路径名应尽量使用相对路径，并且目录的层级分隔符不要直接写"/"或"\" ，应使用File.separator 这个常量表示，以避免不同系统带来的差异。
		2. File(File parent, String child);
		3. File(String parent, String child);

	注意点：
		创建File 对象是指向某个目录或者文件，但目录或者文件并没有真正被创建出来。

	方法: 
		createNewFile();	创建文件，如果文件已存在则创建失败，返回false。
			创建文件时相对径，则ecplise 中的相对路径是以相对当前项目目录下的。
		isFile() - boolean;	判断当前对象是否表示一个文件
		isDirectory() - boolean; 判断当前对象是否表示一个目录
		length();	文件的长度(实际字节数)
		exists();	判断File 对象表示的文件或者目录是否存在 
		delete();	将File 对象表示的文件或者目录删除
			如果要删除一个目录，那么此目录下必须没有内容才可以删除，如果有子目录或者子文件，则无法删除。
			不可以直接删除多级目录，必须从最底层开始逐级删除。

		mkdir();	创建目录
		mkdirs();	创建多级目录
		
		list() - String[];	 返回一个字符串数组，这些字符串指定此抽象路径名表示的目录中的文件和目录。
		listFiles() - File[]; 	返回一个抽象路径名数组，这些路径名表示此抽象路径名表示的目录中的文件。
		listFiles(FileFilter filter); 文件过滤，通过指定过滤器接口对象
			private static void demo05() {
				File file = new File("D:" + File.separator);
				
				File[] files = file.listFiles(new FileFilter() {
					
					@Override
					public boolean accept(File file) {
						// 以.txt 结尾的文件
						boolean flag1 = file.isFile();
						boolean flag2 = file.getName().endsWith(".txt");
						return flag1 && flag2;
					}
				});
				
				if (files != null)
				{
					for (File f : files)
					{
						System.out.println(f);
					}
				}
			}
		listFiles(FilenameFilter filter): 文件名过滤
			private static void demo06() {
				File file = new File("D:" + File.separator);
				File[] files = file.listFiles(new FilenameFilter() {
					
					@Override
					public boolean accept(File dir, String name) {
						// dir: 父目录，name: 遍历到的文件名
						File dest = new File(dir, name);
						// 以.txt 结尾的文件
						return dest.isFile() && name.endsWith(".txt");
					}
				});
				
				if (files != null)
				{
					for (File f : files)
					{
						System.out.println(f);
					}
				}
			}


		getName(); 获取文件名
		getPath(); 获取针对当前程序的相对路径
		getAbsolutePath(); 获取绝对路径
		


	路径过滤器 - FileFilter
		查找某盘下所有以 ".jpg" 结尾的文件

	临时文件: 
		static File createTempFile(String prefix, String suffix);
		static File createTempFile(String prefix, String suffix, File directory);
			创建一个临时文件，指定文件的前缀以及后缀
			File.createTempFile("test", ".tmp");
 

编码：
	互联网或者文件都是按照byte(8位数)进行数据传输的！
	字符数据在互联网(文件)传输时候必须拆分为byte进行传输
		- 将字符数据拆分为byte 数据的过程称为"编码"
		- 将byte 数据重新合并为字符数据过程称为"解码"
	常见编码
		- UTF16-BE: 将字符char 从中间拆分两段，两个byte, 只能支持65535 个字符，英文浪费空间
		- GBK 中国标准，1~2字节变长编码，支持字符2万+
		- UTF-8 采用变长编码(1~4字节)，支持100万+字符，按照字符数值的大小进行编码，英文采用1字节编码。
			UTF-8 编码的中文为3 个字节
		> 国际化关键应该采用UTF-8编码

RandomAccessFile 文件操作
	Java 提供 了一个可以对文件随机访问的操作，访问包括读和写操作。该 类名为RandomAccessFile。该类的读写是基于指针的操作。
	注意点：
		使用结束后，需要关闭流。 close();
		写文件的时候总是从文件开始位置，如果文件原已经有了，则会替换，但是原有的内容不会被删除。
	
	创建对象
		RandomAccess 在对文件进行随机访问操作时有两个 模式，分别为只读模式(只读文件数据)，和读写模式(对文件数据进行读写)。
		构造方法:
			RandomAccessFile(File file, String mode);
			RandomAccessFile(String filename, String mode);
				mode 参数指定用以打开文件的访问模式。允许的值及其含意为： 

				值				 含意
				 
				"r" 		以只读方式打开。调用结果对象的任何 write 方法都将导致抛出 IOException。  
				"rw" 		打开以便读取和写入。如果该文件尚不存在，则尝试创建该文件。  
				"rws" 		打开以便读取和写入，对于 "rw"，还要求对文件的内容或元数据的每个更新都同步写入到底层存储设备。  
				"rwd"   	打开以便读取和写入，对于 "rw"，还要求对文件内容的每个更新都同步写入到底层存储设备。  

				"rws" 和 "rwd" 模式的工作方式极其类似 FileChannel 类的 force(boolean) 方法，分别传递 true 和 false 参数，除非它们始终应用于每个 I/O 操作，并因此通常更为高效。如果该文件位于本地存储设备上，那么当返回此类的一个方法的调用时，可以保证由该调用对此文件所做的所有更改均被写入该设备。这对确保在系统崩溃时不会丢失重要信息特别有用。如果该文件不在本地设备上，则无法提供这样的保证。 
				"rwd" 模式可用于减少执行的 I/O 操作数量。使用 "rwd" 仅要求更新要写入存储的文件的内容；使用 "rws" 要求更新要写入的文件内容及其元数据，这通常要求至少一个以上的低级别 I/O 操作。 

				如果存在安全管理器，则使用 file 参数的路径名作为其参数调用它的 checkRead 方法，以查看是否允许对该文件进行读取访问。如果该模式允许写入，那么还使用该路径参数调用该安全管理器的 checkWrite 方法，以查看是否允许对该文件进行写入访问。 


		读写操作：
			写数据:
				write(int b);
				write(byte[] bys);
				write(byte[] bys, int off, int len);

					RandomAccessFile raf = new RandomAccessFile("a.txt", "rw");
					raf.write(97);
					raf.write("hello world!".getBytes());
					raf.close();

			读数据: 
				read() - int
				read(byte[] b) - int

					private static void demo01() throws IOException {
						RandomAccessFile raf = new RandomAccessFile("a.txt", "rw");
						byte[] btRead = new byte[100];
						int nLen = raf.read(btRead);
						System.out.println(nLen);
						System.out.println(new String(btRead));

						raf.close();
					}

		随机访问: 
			getFilePointer() - long 返回此文件中的当前偏移量。
			seek(long pos) - void 设置文件当前偏移量。





















































































































