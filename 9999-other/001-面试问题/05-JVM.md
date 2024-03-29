JVM 内存模型
	博客： https://www.jianshu.com/p/76959115d486

## JVM GC 触发条件
- > #### Minor GC ，
    > - 从年轻代空间（包括 Eden 和 Survivor 区域）回收内存被称为 Minor GC；
- > #### Major GC
    > - 对老年代GC称为Major GC；
- > #### Full GC 
    > - 而Full GC是对整个堆来说的；
    >> - 在最近几个版本的JDK里默认包括了对永生带即方法区的回收（JDK8中无永生带了），出现Full GC的时候经常伴随至少一次的Minor GC,但非绝对的。Major GC的速度一般会比Minor GC慢10倍以上。下边看看有那种情况触发JVM进行Full GC及应对策略。
    >> - Minor GC触发条件：
    >> - 当Eden区满时，触发Minor GC。
- >> #### Full GC触发条件：
- >>> ##### System.gc()方法的调用
    > - 此方法的调用是建议JVM进行Full GC,虽然只是建议而非一定,但很多情况下它会触发 Full GC,从而增加Full GC的频率,也即增加了间歇性停顿的次数。强烈影响系建议能不使用此方法就别使用，让虚拟机自己去管理它的内存，可通过通过-XX:+ DisableExplicitGC来禁止RMI（Java远程方法调用）调用System.gc。
- >>> ##### 老年代空间不足
    > - 旧生代空间只有在新生代对象转入及创建为大对象、大数组时才会出现不足的现象，当执行Full GC后空间仍然不足，则抛出如下错误： java.lang.OutOfMemoryError: Java heap space 为避免以上两种状况引起的FullGC，调优时应尽量做到让对象在Minor GC阶段被回收、让对象在新生代多存活一段时间及不要创建过大的对象及数组。
- >>> ##### 方法区空间不足
    > - JVM规范中运行时数据区域中的方法区，在HotSpot虚拟机中又被习惯称为永生代或者永生区，Permanet Generation中存放的为一些class的信息、常量、静态变量等数据，当系统中要加载的类、反射的类和调用的方法较多时，Permanet Generation可能会被占满，在未配置为采用CMS GC的情况下也会执行Full GC。如果经过Full GC仍然回收不了，那么JVM会抛出如下错误信息：java.lang.OutOfMemoryError: PermGen space为避免Perm Gen占满造成Full GC现象，可采用的方法为增大Perm Gen空间或转为使用CMS GC。
- >>> ##### 通过Minor GC后进入老年代的平均大小大于老年代的可用内存
    > - 如果发现统计数据说之前Minor GC的平均晋升大小比目前old gen剩余的空间大，则不会触发Minor GC而是转为触发full GC
- >>> ##### 由Eden区、From Space区向To Space区复制时
    > - 对象大小大于To Space可用内存，则把该对象转存到老年代，且老年代的可用内存小于该对象大小



