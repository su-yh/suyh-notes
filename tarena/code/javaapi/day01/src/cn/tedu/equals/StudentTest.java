package cn.tedu.equals;

public class StudentTest {
	public static void main(String[] args) {
		Student student = new Student(1001, "����", 23);
		Student student2  = new Student(1001, "����", 22);
		
		boolean flag = student.equals(student2);
		System.out.println(flag);
		
		
		
	}
}
