package cn.tedu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Demo01 {
	public static void main(String[] args) {
		String email = "piaoqian@tarena.com.cn";
		String regex = "^\\w+@(\\w+)(?:\\.\\w+)+$";
		
		//boolean flag = email.matches(regex);
		//System.out.println(flag);
		
		//Pattern pattern = Pattern.compile(regex);
		//Matcher matcher = pattern.matcher(email);
		//boolean flag = matcher.matches();
		//System.out.println(flag);
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		boolean flag = matcher.matches();
		if(flag){
			for(int i = 0;i<=matcher.groupCount();i++){
				String g = matcher.group(i);
				System.out.println(g);
			}
		}else{
			System.out.println("匹配失败！");
		}
    }
    
    // 最普通的正则匹配
    public static demo01() {
        String email = "piaoqian@tarena.com.cn";
		String regex = "^\\w+@(\\w+)(?:\\.\\w+)+$";
		
		boolean flag = email.matches(regex);
        
        System.out.println(flag);
    }

    // 匹配分组
    /**
     * 正则中的每一个左小括号都会被匹配为一个组，所有的组合成一个集合
     * 下标为0 的元素是匹配到的整个字符串
     * 下标从1 开始则为每一个分组中的字符串
     * 如果某个分组中不是想要的数据，那么可以在() 中添加(?:)开头，表示这里的元素不是作为一个分组
     * 
     * 详细信息查看API 文档 Pattern
     */
    public static demo02() {
        String email = "piaoqian@tarena.com.cn";
        // (?:\\.\\w+)  以?:开头的将不会被添加到分组中处理
		String regex = "^\\w+@(\\w+)(?:\\.\\w+)+$";
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		boolean flag = matcher.matches();
		if(flag){
            // * 循环遍历的时候跟普通集合不一样，这里会比分组多一个，因为下标为0 的元素为匹配的整个字符串
			for(int i = 0; i <= matcher.groupCount(); i++) {
				String g = matcher.group(i);
				System.out.println(g);
			}
		}else{
			System.out.println("匹配失败！");
		}
    }
}
