
# java 中的异常处理


## 异常处理的步骤
- 示例代码如下: 

        int method() {
            int a = 10;
            try {
                业务代码
                return a;    // 如果核心业务出问题，则此切点将不会执行
            } catch(Exception e) {
                System.out.println("catch");
                throw new RuntimeException(e);
            } finally {
                ++a;
                System.out.println("finally");
                // return a;
            }
        }

    1. 如果`业务代码`中没有异常被抛出，那么上面代码的执行顺序将是: 
        > `return a;` 先将a 的值保存到一个临时变量`temp` 中; <br>
        > 跳到`finally` 块中执行；<br>
        > 回到`return a;` 将保存到的`temp` 中的值返回出去；<br>
        >> 最终的结果是：10；<br>
    2. 如果`业务代码`中有异常抛出了，那么上面的代码的执行顺序将是: 
        > `return a;` 将不会被执行；<br>
        > 首先跳到`catch` 块中，执行相关代码；<br>
        > 当执行到`throw` 语句时，检测到有`finally` 块，跳到`finally` 块；<br>
        > 执行`finally` 块中的语句；<br>
        > 最后回到`catch` 块中抛出新的异常；<br>
    3. 如果的`finally` 块中有return 语句，那么 `try` 块中的return 将不会被执行。
    4. 如果业务代码中的异常，并且`finally` 块中有return 语句的话。那么这个`catch` 块中的`throw` 语句将不会被抛出了。
        > 这样做的话，`catch` 块中的`throw` 语句就被巧妙的跳过了。


