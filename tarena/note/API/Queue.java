Queue: ���� 
	java.util.Queue �ӿ�
		offer() �����������������
		poll() �Ӷ�������ȡ���ݣ�������Ӷ�����ɾ��
		peek() �Ӷ�������ȡ���ݣ������Ὣ��Ӷ�����ɾ��

	������
		for (String s : queue)
		{
			System.out.println(s);
		}

Deque: 
	public interface Deque<E>extends Queue<E> 
	Deque ��Queue ��һ���Ӷ��У�˫�˶���
	������
		offer(E e);
		offerFirst(E e);
		offerLast(E e)
		poll()
		pollFirst()
		pollLast()

	// java ��һ��˫�˶���ʵ����LinkedList<E>
	Deque<String> deque = new LinkedList<String>();
	
























