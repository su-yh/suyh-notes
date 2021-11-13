Queue: 队列 
	java.util.Queue 接口
		offer() 往队列里面添加数据
		poll() 从队列里面取数据，并将其从队列中删除
		peek() 从队列里面取数据，但不会将其从队列中删除

	遍历：
		for (String s : queue)
		{
			System.out.println(s);
		}

Deque: 
	public interface Deque<E>extends Queue<E> 
	Deque 是Queue 的一个子队列，双端队列
	方法：
		offer(E e);
		offerFirst(E e);
		offerLast(E e)
		poll()
		pollFirst()
		pollLast()

	// java 的一个双端队列实现类LinkedList<E>
	Deque<String> deque = new LinkedList<String>();

























