package factory;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;

public class AnnotationAppContext {
	public AnnotationAppContext() {
		String pkg = "project";
		scanPkg(pkg);
	}

	// ɨ��ָ���İ����ҵ����е����ļ�
	private void scanPkg(String pkg) {
		String pkgDir = pkg.replaceAll("\\.", "/");
		URL url = getClass().getClassLoader().getResource(pkgDir);
		File file = new File(url.getFile());
		File[] fs = file.listFiles(new FileFilter() {

			@Override
			public boolean accept(File file) {
				String fileName = file.getName();
				if (file.isDirectory()) {
					scanPkg(pkg + "." + fileName);
					return false;
				}
				
				if (fileName.endsWith(".class")) {
					return true;
					
					
				}

				return false;
			}
		});

		for (File f : fs) {
			String fileName = f.getName();
			fileName = fileName.substring(0, fileName.lastIndexOf('.'));
			String clsName = pkg + "." + fileName;

			try {
				Class<?> c = Class.forName(clsName);
				// �ж���������Ƿ���CGB1709 ע��
				if (c.isAnnotationPresent(CGB1709.class)) {
					Object obj = c.newInstance();
				}
//				Object obj = Class.forName(clsName).newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			
			
		}
	}

	public static void main(String[] args) {
		AnnotationAppContext ctx = new AnnotationAppContext();
	}
}
