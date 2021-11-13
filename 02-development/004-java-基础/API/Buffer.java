Buffer: 
	写数据的时候：
		1. 调用clear() 将当前读写位置置为0，可读写限制置为最大容量；
		2. 然后就可以写了；
	
	读数据的时候：
		1. 首先是Buffer 中已经存在了被写好的数据，这个时候 position(位置) 为最后写的偏移量；
		2. 调用flip() 将限制置为position 的值(这个位置是可读的最大位置)，然后将position(位置) 置0(数据开始读的位置)。
		3. 然后开始读数据。

	方法：
		clear() ：position = 0, limit = capacity, mark = -1
		mark() : mark = position - 显然用之前需要控制好position 的值
		reset() : position = mark - 显然在用之前需要控制好 mark 的值
		remaining() : 返回当前位置与限制之间的元素个数，实际就是可读的个数
		