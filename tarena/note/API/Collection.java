Collection ��һ���ӿڣ������˼�����صĲ������������������ӽӿڣ�
	List: ���ظ���
	Set: �����ظ���

	ע��㣺
		�����д洢�Ķ�����������Ԫ�أ����Ҽ���ֻ����ÿ��Ԫ�ض�������ã������ǽ�Ԫ�ض�������뼯�ϡ�

	���ϵĲ�����
		add(Object obj): - boolean
			Collection ������һ��add ���������򼯺��������Ԫ�ء��ý������ӽ����ϣ�����ӳɹ��򷵻�true, ���򷵻�false��
		contains(Object obj) - boolean
			�������жϸ����ķ񱻰����ڼ����С�
			�жϷ�ʽ��
				������Ҫע����ǣ��������жϷ񱻰����ڼ������Ǹ���ÿ��Ԫ�ص�equals() �������бȽϺ�Ľ����ͨ���б�Ҫ��дequals() ��֤contains() �����ĺ�����
		size():  ��ȡ������Ԫ�صĸ���
			�����л�ȡ����: 
				int[] ary = new int[3];
				int length = ary.length;
			String �л�ȡ�ַ��ĸ���: 
				String str = "abn";
				int len = str.length();
		clear():
		isEmpty():
		addAll(Collection):
		containsAll(Collection):

	���ͻ��ƣ�
		ע��㣺
			����λ�õ��������ͱ������������ͣ��������ǻ�����������
			collection<int> col = ArrayList<int>();	// ���󣬲���������������� int
			
		����
			Collection col = new ArrayList();
			col.add("java");
			
			Collection<Integer> col1 = new ArrayList<Integer>();
			col1.add(1);
			
			col1.addAll(col);	// �ɹ��ˣ�������Ĵ�����һ���ַ����ˡ�
			
		ԭ��
			���ͻ�������������û�еġ�
			
	������ Iterator
		���������ڱ�������Ԫ�ء���ȡ����������ʹ��Collection ����ķ���: iterator()
		������Iterator ��һ���ӿڼ�������д Collection ��iterator() ����ʱ�����ڲ����ṩ �˵�������ʵ�֡�
			������Iterator ����ָ����ǵ�һ��Ԫ�ص�ǰ��һ��λ�ã���û������ָ��һ��Ԫ�ء�
		������ʽ��
			hasNext();
			next();
			remove();
				��ʹ�õ�������������ʱ������ͨ�����ϵ�Collection<E>::remove()����ɾ������Ԫ�أ�������׳����������쳣�����ǿ���ͨ�������������ṩ��Iterator<E>::remove() ������ɾ��ͨ��next() ��������Ԫ�ء�
					Collection<String> col = new ArrayList<String>();
					col.add("java");
					col.add("web");
					col.add(".net");
					java.util.Iterator<String> itr = col.iterator();
					while (itr.hasNext())
					{
						String str = itr.next();
						// ��str �����Ƿ����ַ�a �ж� 
						int nIndex = str.indexOf('a');
						if (nIndex >= 0)
						{
							itr.remove();
						}
					}

	foreach ѭ������ǿfor ѭ��
		java5.0 ֮���Ƴ���һ���µ����ԣ���ǿfor ѭ����Ҳ��Ϊ��ѭ������ѭ����ͨ���ڴ�ͳѭ���Ĺ�������ֻ���ڱ������ϻ����顣
		�ڱ�������У��������Ὣ��ѭ��ת��Ϊ������ģʽ��������ѭ���������ǵ�������
		
		д��: 
			for (�������� ���� : Collection)
			{
				�����
			}

java.util
Collections -- ע���Collection ����һ��'s' 
	Collections �Ǽ��ϵĹ����࣬���ṩ �˺ܶ�������ǲ������ϵķ��������о������ڼ��������sort ����
		void sort(List<T> list);	���ڸ������ϵ���Ȼ����Ĭ������������

	Comparable:
		Collections ��sort �����ǶԼ���Ԫ�ؽ�����Ȼ������ô����Ԫ�ض���֮��һ��Ҫ�д�С֮�֡������С֮������� �綨���أ�ʵ���ϣ���ʹ��Collections ��sort ����ļ���Ԫ�ض�������Comparable �ӿڵ�ʵ���࣬�ýӿڱ�ʾ �������ǿɱȽϵģ���Ϊʵ�� �� �ӿڱ��� ��д ���󷽷���
			- int compareTo(T t)
				�÷�������ʹ��ǰ���������������бȽϡ�
					����ǰ������ڸ���������ô����ֵӦΪ > 0 ��������
					��С�ڸ���������ô����ֵӦΪ < 0 ��������
					����ȣ��򷵻�0��
		Comparable �ӿڣ���Ƚ���
			��� һ����ʵ�� �˴˽ӿڣ���ô һ��Ҫ��дcompareTo(Object obj), �ڴ˷����У����ȽϹ���д��֮��ſ��Ե���(Collections.sort())
		Comparator:	// ��Ƚ����������һ�������н�������ʱ�������ü�����Ԫ�����е�������򣬶������Զ�����򣬴�ʱ��Ҫдһ����ʵ��compare() �ӿ�
			һ��Java ��ʵ�� ��Comparable �ӿڣ���Ƚ��߼� ���Ѿ�ȷ�������ϣ�������� �Ĳ�������ʱ ָ���ȽϹ��򣬿��Բ���Comparator �ӿڻص��ķ�ʽ ��
			Comparator �ӿ�Ҫ��ʵ������� ��д�䶨�� �ķ���: int compare(T o1, T o2)
				// ��Ƚ��� Comparator
				// ʵ��ѧ���ఴ�����������
				private class ByAge implements Comparator<Student>
				{
					@Override
					public int compare(Student o1, Student o2) {
						return o1.getAge() - o2.getAge();
					}
				}
				// Ȼ����ö�Ӧ������sort() ����
				List<Student> lsStudent = new ArrayList<Student>();
				Collections.sort(lsStudent, new ByAge());

		Comparable �� Comparator ���ǽӿ�
		Comparable: �ӿ�
			ʹ�ã�
				1��һ������Ҫʵ�ִ˽ӿڣ�
				2����дcompareTo(T other) ������
				3���Լ��Ͻ������򣬵���Collections.sort(List) ������
		Comparator: �ӿ�
			ʹ�ã�
				1��һ����ʵ�ִ˽ӿڣ�
				2����дcompare(T o1, To2) ����; 
				2���Լ��Ͻ������򣬵���Collections.sort(List, Comparator) ������










































