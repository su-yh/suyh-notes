���ϲ��� - ���Ա�
List
	List �ӿ���Collection ���ӽӿڣ����ڶ������Ա����� �ṹ�����Խ�List ��� Ϊ��� ��������飬ֻ������Ԫ�ظ������Զ�̬�����ӻ��߼��١�
	List �ӿڵ����� ����ʵ�� ��ΪArrayList ��LinkedList, �ֱ��ö�̬���������ķ�ʽ ʵ����List �ӿڡ�
	������ΪArrayList ��LinkedList �ķ������߼� ����ȫ һ����ֻ������������һ���Ĳ��ArrayList ���ʺ���������ʶ�LinkedList �����ʺϲ����ɾ����������Ҫ�����ر� ���� �������¿��Ժ��� ������

	ArrayList ��LinkedList ���ߵ����ݽṹ
	ArrayList ��̬�������ʽ��
	LinkedList �������ʽ��
	
	List<String> list = new ArrayList<String>();
	List<String> list = new LinkedList<String>();

	get �� set:
		get(int index): �±��0 ��ʼ��
		set(int index, E element): ������Ԫ�ز��뵽ָ��λ�á�
	�����ɾ����
		add remove
		void add(int index, E element): ��Ȼλ���Լ������Ԫ�ض�˳�����
		E remove(int index): ɾ��ָ��λ�õ�Ԫ�أ�����ɾ����Ԫ�ط���
		����ڱ�����������Ҫɾ��Ԫ�أ�������ʹ�ô�remove() ��������Ӧ��ʹ��Iterator ��remove() ������

	List ת��Ϊ���飺
		List ��toArray �������ڽ�����ת��Ϊ���顣��ʵ���ϸ÷�������Collection �ж���ģ��������еļ��϶��߱�������ܡ�
		��������������
			Object[] toArray()
			<T>T[] toArray(T[] a)
			���еڶ��������ǱȽϳ��õģ����ǿ��Դ���һ��ָ�����͵����飬�������Ԫ������Ӧ�뼯�ϵ�Ԫ������һ�¡�����ֵ����ת��������� ������ᱣ�漯�������е�Ԫ�ء�

	����ת��ΪList:
		Arrays �����ṩ��һ����̬����asList, ʹ�ø� �������ǿ��Խ�һ������ת��Ϊ��Ӧ��List ���ϡ�
		���� Ҫע����ǣ����صļ������ǲ��ܶ�����ɾԪ�أ�������׳��쳣�����ҶԼ��ϵ�Ԫ�ؽ����޸Ļ�Ӱ�������Ӧ��Ԫ�ء�

	List �������ת����
		List -> ���� : toArray()  toArray(T[])
		���� -> List : Arrays.toList(T...a) @ static ����

	������
		List<String> list = new ArrayList<String>();
		Iterator<String> it = list.iterator();
		while (it.HasNext())
		{
			String str = it.next();
			
			{
				�����
			}
		}















































