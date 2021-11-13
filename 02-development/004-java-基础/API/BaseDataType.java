基本数据类型的包装类
JAVA 为每种基本数据类型都提供了一个包装类


基本类型	包装类					父类
int 		java.lang.Integer		java.lang.Number
long 		java.lang.Long          java.lang.Number
double 		java.lang.Double        java.lang.Number
short 		java.lang.Short         java.lang.Number
float 		java.lang.Float         java.lang.Number
byte 		java.lang.Byte          java.lang.Number
char 		java.lang.Character     java.lang.Object
boolean 	java.lang.Boolean       java.lang.Object

包装类是不可变，在构造了包装类对象后，不允许更改包装在其中的值；
包装类是 final 的，不能定义他们的子类。


Number 类
	抽象类，是六个数值类的共同的父类
	
Integer: 
	parseInt(String) - int 有非数字字符则会抛异常
	toString() - int -> string 
	static valueOf(int) - Integer 
	intValue() - int 将一个Integer -> int 
Double:
	
自动装箱和拆箱
	自动装箱: int -> Integer
	自动拆箱: Integer -> int
	















































