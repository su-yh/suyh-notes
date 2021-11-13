import java.text.DecimalFormat;

// 参数为格式化，保留两位小数位
// DecimalFormat df = new DecimalFormat("##.00");	// 这样写的话如果结果为0.10 那么实际输出则为.10
DecimalFormat df = new DecimalFormat("#0.00");		// 这样写的话如果结果为0.10 那么实际输出则为0.10
double total = 0.3384;
String totalStr = df.format(total);	// 将total 保留两位小数位，并进行四舍五入，并返回一个String


DecimalFormat df = new DecimalFormat("00.00");
double d1 = 12.3456;	// 12.35
double d2 = 2.34567;	// 02.35

DecimalFormat df = new DecimalFormat("##.00");
double d1 = 512.3456;	// 512.35
double d2 = 2.34567;	// 2.35
double d3 = 0.1133;		// .11

	##############################  重要   ##############################
	double d4 = 0.115;	// 有可能会出现最后的5 没有进位的情况，这个主要是因为底层的二进制将0.115 认成0.114999999999，从而导致了最终的结果为0.11


java 中保留小数的做法：
	DecimalFormat(数字转换类)
	DecimalFormat df = new DecimalFormat("格式");
	String str = df.format(double);

	如果定义格式: 
		#  0: 都代表一位数字      ##.00
		#: 对应的数字如果是1~9 则原样显示，如果是0 则不显示
		0: 有数字显示数字无数字显示0


	// 001 002 003
    @Test
    public void numberFormatDemo() {
        DecimalFormat format = new DecimalFormat("000"); 
        for (long i = 0; i < 10; i++) {
            System.out.println(format.format(i));
        }
    }
