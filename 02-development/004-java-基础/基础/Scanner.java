import java.util.Scanner;

{
    // Scanner 一旦打开必须 要关闭
    Scanner input = new Scanner(System.in);
    // ...
    input.close();
}

{
    // Scanner 的打开与关闭之间不能有死循环，否则编译能不过，编译器会认为你永远无法关闭这个流。
    Scanner input = new Scanner(System.in);
    while (true) {
        // no break;
    }
    
    input.close();  // 如果 在循环中没有出现break, 则编译器会报错。
}
