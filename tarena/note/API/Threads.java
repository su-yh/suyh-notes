�����̣߳�
	�õ���ǰ�̵߳��߳�����
	Thread.currentThread().getName();

	�����߳��෽ʽһ: �̳� Thread ��
		����һ����̳��� Thread �࣬��ʱ��������һ���߳� �࣬���Ǵ����̵�Ŀ����ִ��һ����������������������Ҫ��д Thread::run() ������
		JAVA ���̵߳ĵ�������ռʽ��
		�����߳�Ӧ�õ��� start() ����������Ӧ��ֱ�ӵ��� run() ���������ֱ�ӵ���run() ��������ôֻ�ǵ���һ�����һ�����������̲߳�û����������������

	�����߳��෽ʽ��: �Զ����࣬ʵ�� Runnable �ӿ�
		��Ȼʵ����Runnable �ӿڵ� run() �����������ಢ����һ���߳��ࡣ����ֻ�ǽ��߳� �����񴴽������ˣ�����Ҫ����������ӵ�һ���߳����в��ܴ���һ���������̡߳�
		{
			// �����̶߳���ִ��RunnableDemo �е�����
			RunnableDemo demo = new RunnableDemo();
			
			Thread thd = new Thread(demo);
			thd.start();
		}

	ֱ�Ӳ��õ�һ�ַ�����ż�϶Ƚ���Ƚϸߡ�����ͨ��ʹ�õڶ��ַ��������̡߳�
	����һ�����Ǽ̳�ֻ���ǵ��̳У���ʵ�ֽӿ����ǿ���ʵ�ֶ���ӿڡ�

	ͨ�����ǿ���ͨ�������ڲ���ķ�ʽ�����̣߳�ʹ�ø÷�ʽ���Լ򻯱�д����ĸ��Ӷȣ���һ���߳� ����Ҫһ��ʵ��ʱ����ͨ��ʹ�����ַ�ʽ��������
		// ������ʱ����ʽ�����̶߳���
		Thread thd1 = new Thread() {
			@Override
			public void run() {
				System.out.println("��������Thread ����1��");
			};
		};
		thd1.start();
		
		Thread thd2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("�ӿڴ���Thread ����2��");
			}
		});
		thd2.start();


	�̲߳���API
		static Thread.currentThread() - Thread ��ȡ��ǰ�̶߳���
		static Thread.getName() - String ��ȡ��ǰ�߳�����
		static Thread.sleep(long ms) - void 

		static yield() - void ����
			���� yield() ��������ʱ�õ�ǰ�̲߳�û�н�������״̬����ֱ�ӽ������״̬����ʱ��ǰ�߳��п��ܻ��ٴ�����ʱ��Ƭ��

		���߳�������
			setName(String name) - void
			Thread(Runnable, String name);	���췽��

		��ȡ�߳������Ϣ������
			long getId(): �̱߳�ʶ��
			String getName();
			Boolean isAlive();
			int getPriority(); 	���ȼ�
			boolean isDaemon(); �ػ��߳�
			boolean isInterrupted(); �Ƿ����ж�


�ֲ��ڲ�����÷����ľֲ�������ע��㣺
	main() {
		// final int a = 3; // 1.8 �汾֮ǰҪʹ�þֲ�����������final ���ε�
		int a = 3;	// 1.8 �汾��û�д�����
		class A {
			public void test() {
				System.out.println(a);
			}
		}
	}

�߳����ȼ���
	�̵߳��л������̵߳��ȿ��Ƶģ������޷�ͨ�����������棬�������ǿ���ͨ������̵߳����ȼ������̶ȵĸ����̻߳�ȡʱ��Ƭ�ļ��ʡ�
	�̵߳����ȼ�������Ϊ10 ����ֵ�ֱ���1-10 ������1��ͣ�10 ��ߡ��߳��ṩ��3 ����������ʾ��ͣ���ߣ��Լ�Ĭ�����ȼ���
		Thread.MIN_PRIORITY,
		Thread.MAX_PRIORITY,
		Thread.NORM_PRIORITY,
	�������ȼ��ķ���: 
		setPriority(int prority);

�ػ��̣߳�
	�ػ��߳�����ͨ�߳��ڱ�����û��ʲô��������ֻ��Ҫͨ��Thread �ṩ�ķ������趨���ɣ�
		void setDaemon(boolean)
	�ػ��߳�����ͨ�߳�Ψһ���������ڽ�����ʱ���ϡ���һ������������ǰ̨�̶߳�����ʱ�����̽��������۸ý����е��ػ��߳��Ƿ������ж�Ҫǿ�ƽ����ǽ�����
	GC ����һ���ػ��̡߳�







�߳�ͬ��
	ʵ�ַ�ʽ:
		�ؼ���: synchronized: ͬ����
	������:
		JAVA �ṩ��һ�����õ���������֧��ԭ���ԣ�
		ͬ�������(synchronized �ؼ���)��ͬ�����������������֣�һ����Ϊ���Ķ�������ã�һ����Ϊ������������Ĵ���顣
			synchronized ���η���:
			// ����������η�������ô��������������Ķ���(������������ this)������
			// Ҳ����˵���������Ķ���ʵ��(����ͬһ��ʵ��)��������������ᱻ��ͬ��
			ʾ����
			public synchronized void test() {
				
			}
			synchronized (ͬ��������--����������) {
				// �����
				// ...
			}
			// һ�㶼��this ��Ϊ������
			ʾ����
			synchronized (this) { }

		���������д��붼��Ҫͬ�����Ը�����ֱ�Ӽ�����
		ÿ��JAVA ���󶼿��Ը첲��һ��ʵ��ͬ���������߳̽���ͬ�������֮ǰ���Զ� ����������� ���˳�ͬ�������ʱ�Զ� �ͷ���������������ͨ������;���˳�����ͨ�����쳣�˳���һ������ȡ��������Ψһ;�����ǽ����������������ͬ���������߷���
		synchronized ���δ����
			��Ч��Сͬ����Χ�����ڱ�֤������ȫ��ǰ������߲���ִ��Ч�ʡ�
			ͬ���飬ͬ������Ը���ȷ�Ŀ�����Ҫͬ���Ĵ���Ƭ��

		ʹ�� synchronized ��Ҫ��һ�����������Ա�֤�߳�ͬ������ô���������Ӧ��ע��: 
			* �����Ҫͬ�����߳��ڷ��ʸ�ͬ����ʱ��������Ӧ����һ�����������á�����ﲻ��ͬ��Ч����
			* ͨ�����ǻ�ʹ��this ����Ϊ������

		��̬����ʹ�� synchronized ���κ󣬸÷���һ������ͬ��Ч��
			synchronized ���ξ�̬������������Ķ����ǵ�ǰ��.class - Class ����
			ʾ����
			public static synchronized void doSomething() { 
				// do something
			}
		
		synchronized �Ļ�����
			synchronized ���ζ�δ��룬������Щͬ�����ͬ��������������ͬһ��ʱ����ô��Щ�������ǻ���ġ�������Щ����̲߳���ͬʱִ�У�����ȴ���
			class A {
				public synchronized void methodA() { }
				public synchronized void methodb() { }
			}
			�� A Ϊ�������߳�1 ���� methodA ����ʱ�߳�2 ���� methodB ��ô�������߳�Ҳ����һ�����ľ�����


�̰߳�ȫAPI
	StringBuilder �Ƿ��̰߳�ȫ�ģ� StringBuffer ���̰߳�ȫ��
	���ڼ��϶��ԣ����õ�ʵ����; ArrayList, LinkedList, HashSet ���Ƕ������̰߳�ȫ�ġ�
	Collections ���Խ����еļ���ת��Ϊ�̰߳�ȫ��
		- Collections.synchronizedList(List)
		- Collections.synchronizedSet(Set)
		- Collections.synchronizedMap(Map)

�̳߳�
	ExecutorService(���ؽӿ�) Executors.newFixedThreadPool(int nThreads); - ExecutorService(�ӿ�)
	
	�������ύ���̳߳�
		execute(Runnable) - Executor
	
	�ر�: 
		shutdown()
		shutDownNow()
























