Map ��һ���ӿ�
	�ӿڶ���ļ����ֳ�Ϊ���ұ����ڴ洢��ν "key-value" ӳ��ԡ�key ���Կ�����value ��������Ϊkey �Ķ����ڼ����в������ظ��� 
	�����ڲ����ݽṹ�Ĳ�ͬ��Map �ӿ��ж���ʵ���࣬���г��õ��ڲ�ΪHash ��ʵ�ֵ�HashMap ���ڲ�Ϊ��������ʵ�ֵ�TreeMap��

	Map<key, value> key ��value ���������������ͣ��������ǻ�����������

	put(K key, V value): �򼯺������Ԫ��

	������
		Map<String, Integer> map = new HashMap<String, Integer>();

		map.put("����", 90);	// ����Ѿ������ˣ����ʹ�����µ�ֵ�滻��ԭ����ֵ�������ر��滻����ֵ�����ԭ�������ڣ��򷵻�һ��null ֵ
		map.containsKey(K key);		// ����equals() ����
		
	Map ���ϵı���
		keySet(): �õ�һ��Key ֵ ��set ����;
			Map<String, Integer> map = new HashMap<String, Integer>();
			Set<String> keyset = map.keySet();
			for (String str : keyset)
				System.out.println(str);
			
		entrySet(): �õ�map �����е�����key-value ��set ����
			Map<String, Integer> map = new HashMap<String, Integer>();
			Set<java.util.Map.Entry<String, Integer>> set = map.entrySet();
			for (java.util.Map.Entry<String, Integer> e : set)
			{
				String key = e.getKey();
				Integer value = e.getValue();
			}

Object ��˵���ĵ���
	public int hashCode()���ظö���Ĺ�ϣ��ֵ��֧�ִ˷�����Ϊ����߹�ϣ������ java.util.Hashtable �ṩ�Ĺ�ϣ�������ܡ� 
	hashCode �ĳ���Э���ǣ� 

		�� Java Ӧ�ó���ִ���ڼ䣬�ڶ�ͬһ�����ε��� hashCode ����ʱ������һ�µط�����ͬ��������ǰ���ǽ�������� equals �Ƚ�ʱ���õ���Ϣû�б��޸ġ���ĳһӦ�ó����һ��ִ�е�ͬһӦ�ó������һ��ִ�У����������豣��һ�¡� 
		������� equals(Object) ������������������ȵģ���ô�������������е�ÿ��������� hashCode ����������������ͬ����������� 
		������� equals(java.lang.Object) ����������������ȣ���ô�������������е���һ�����ϵ��� hashCode ������ Ҫ��һ�����ɲ�ͬ��������������ǣ�����ԱӦ����ʶ����Ϊ����ȵĶ������ɲ�ͬ�������������߹�ϣ������ܡ� 
		ʵ���ϣ��� Object �ඨ��� hashCode ����ȷʵ����Բ�ͬ�Ķ��󷵻ز�ͬ������������һ����ͨ�����ö�����ڲ���ַת����һ��������ʵ�ֵģ����� JavaTM ������Բ���Ҫ����ʵ�ּ��ɡ��� 



HashMap - ����Map
	Object::HashCode()

	Hash�㷨��
		* key �� HashCode ֵ����ɢ���㷨���õ�ɢ���±꣬��ͬ�� hashcode ֵ����ɢ���㷨�õ���ɢ���±������ͬ�����ɢ���±���ͬ����ô��������
		* ��������ή�Ͳ�ѯ���ܣ�HashMap��ѯ��������õġ�
		* Ϊ�˽���������ֵĸ��ʣ�����дequals ��hashcode ��������д�������������Խ���������ֵĸ��ʣ������ܱ��⡣
		* �ܽ᣺��key Ԫ��HashCode ��ͬ������equals �Ƚϲ�ͬʱ�ͻ���HashMap �в�������Ӱ���ѯ���ܡ�
	
	

TreeMap - ����Map
	ʹ���˺��������������

LinkedHasMap
	ʹ��Map�ӿڵĹ�ϣ�������ʵ�֣����п�Ԥ֪�ĵ���˳�򡣴�ʵ����HashMap�Ĳ�֮ͬ�����ڣ�LinkedHasMap ά����һ��˫��ѭ���������������˵���˳�򣬸õ���˳�� ͨ�����Ǵ�� ˳��

	������
		HaspMap��Ԫ��ȡ��˳�� ��Put��˳��һ��һ����
		LinkedHashMap ��Ԫ��ȡ��˳���put ��˳��һ�¡�

























































