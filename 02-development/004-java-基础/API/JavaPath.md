# Java 中的路径

## 包路径
- 通过指定包来找到它所在的路径
	- `getClassLoader()` 
	- `getClass()` 可以在任何类下面，因为参数里面给了包全路径
	
			URL url = getClass().getClassLoader().getResource("com.tedu");
			String strPath = url.getPath();


- 给定一个包，找出这个包以及子包下面的所有类名

		// 给定一个包，找出这个包下面的所有类名
		public static void main(String[] args) {
			
			String strDir = "project.controller".replaceAll("\\.", "/");
			List<String> lsClassName = findClassName(strDir);
			
			System.out.println(lsClassName);
		}
	
		private static List<String> findClassName(String strDir) {
			List<String> lsClassName = new ArrayList<String>();
			
			TestMain tm = new TestMain();
			URL url = tm.getClass().getClassLoader().getResource(strDir);
			File f = new File(url.getPath());
			
			File[] fs = f.listFiles();
			for (File ff : fs) {
				if (ff.isDirectory()) {
					List<String> ls = findClassName(strDir + "/" + ff.getName());
					if (ls != null && !ls.isEmpty()) {
						// do something
						lsClassName.addAll(ls);
					}
				} else {
					String strFileName = ff.getName();
					if (strFileName.endsWith(".class")) {
						int nEndIndex = strFileName.lastIndexOf('.');
						String strClassName = strFileName.substring(0, nEndIndex);
						lsClassName.add(strClassName);
					}
				}
			}
			
			return lsClassName;
		}