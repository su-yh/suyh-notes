�����������
	int[] arr;
	int arr[];
	// ���������ֻ����������֣���ʼ�������ʱ�������ڳ�ʼ����ʱ��ָ��
	int[4] arr;	// �����������Ǵ���ġ�
	int arr[4];	// ����������Ҳ�Ǵ���ġ�

����ĳ�ʼ����
	int[] arr = { 10, 20, 30 };	// ��̬��ʼ��
	int[] arr = new int[2];	// ��̬��ʼ��	�������ȵ�������ʿ��Ԫ�ػ�Ĭ�ϳ�ʼ��
	int[] arr = new int[]{ 10, 20 };	// ��̬��ʼ��
	int[] arr = null;	// ����������ʼ��Ϊnull��Ȼ��Ϳ���ʹ�� arr == null �����ж�

����ĳ��ȣ�
	arr.length ������һ������length ֱ��ʹ�þͿ��Եõ����ȣ�����ֵ��int ����

foreach ѭ��
	int[] ary = new int[10];
	for (int m : ary) {
	}

��������
	import java.util.Arrays;
	String[] ary = {"d", "a", "c", "b"};
	Arrays.sort(ary);
	Arrays.toString(ary);
	
����ĸ���
	Arrays.copyOf() ���鸴��
	// ��������ݣ������ݲ���������������
	ary = Arrays.copyOf(ary, 5);

	System.arraycopy()
		arraycopy(Object src, int srcPos, Object dest, int destPos, int length);
		src - Դ����
		srcPos - Դ�����е���ʼλ��
		dest - Ŀ������
		destPos - Ŀ�������е���ʼλ��
		length - ���Ƶ�Ԫ�ظ���

��ά����
	����ά�����еĶ����е�Ԫ�ظ������Բ�ͬ















