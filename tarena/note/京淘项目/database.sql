-- 1������jtds ���ݿ⣬ʹ��utf8 ����
	create database jtds charset utf8;
	use jtds;
-- 2������ tb_item ��
	create table tb_item(
		id 			bigint(20),		-- id
		cid 		bigint(20),		-- ����id
		brand 		varchar(50),	-- Ʒ��
		model 		varchar(50),	-- �ͺ�
		title 		varchar(100), 	-- ��Ʒ��
		sell_point 	varchar(500),	-- �����ı�
		price 		long,			-- �۸� * 100
		num 		int(10),		-- �����
		barcode 	varchar(30),	-- ����
		image 		varchar(500),	-- ͼƬ·��
		status 		tinyint(4),		-- ״̬��1 ������2 �¼ܣ�3 ɾ��
		created 	timestamp,		-- ����ʱ��
		updated 	timestamp		-- ����ʱ��
	) engine=innodb charset=utf8;

3����tb_iter ���в�����Ʒ����
	insert into tb_item(id, brand, title, price, created) values(
		7, 'ƻ��', 'iPhone x', 999999, now());

4���޸� id ��7 ����Ʒ������12%, �޸Ŀ����Ϊ
	update tb_item set price = round(price * 0.88), num = 20 where id = 7;
	round(num) -- ȡ����������
5��ɾ����Ʒ7














