�ڲ���
	InnerClass 
	��һ���ඨ�� ����һ������ڲ�����ʱ��������һ���ڲ��࣬��OutClass ��һ����Ա��
	
	�ڲ�����Է����ⲿ������г�Ա(����˽�г�Ա)
	
	-- ��ͨ�ڲ���:
		Thread thd1 = new Thread() {
			@Override
			public void run() {
				System.out.println("��������Thread ����1��");
			};
		};
		thd1.start();
	-- �����ڲ���: 
		��һ�������У���������б������Ĳ���������һ���ӿڻ�����࣬��ô��ʱ���ø÷������ݲ���ʱ���õ��������ڲ��ࡣ
		Thread thd2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("�ӿڴ���Thread ����2��");
			}
		});
		thd2.start();
		
	-- ʵ���ڲ��ࣺ
		class Outer {
			class WorkThread extends Thread {
				@Override
				public void run() {
					Sysout.out.println(Outer.this);
					Sysout.out.println(this);
					while (true) {}
				}
			}
		}
		
		ʹ��: 
		Outer o = new Outer();
		o.new WorkThread();
		
		
	-- ��̬�ڲ���: 
		class Outer {
			static class WorkThread extends Thread {
				@Override
				public void run() {
					Sysout.out.println(Outer.this);
					Sysout.out.println(this);
					while (true) {}
				}
			}
		}
		
		ʹ��: 
		new Outer.WorkThread();



