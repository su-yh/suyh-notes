BlockingQueue 
	往里面存数据与取数据都具有阻塞功能

	BlockingQueue<String> queue = new LinkedBlockingQueue<String>(3);
	queue.put("1");
	String str = queue.take();


















































