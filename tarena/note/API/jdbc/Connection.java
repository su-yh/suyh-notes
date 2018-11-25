

���ݿ����� java.sql.Connection ���� com.mysql.jdbc.Connection
Connection ���Ӷ���
Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jt_db", "suyh", "suyunfei");



������
Statement state = conn.createStatement();
Statement �����ܹ������ݿⷢ��SQL �Ĵ���
preparseStatement ���ڷ����ܹ�����Ԥ����SQL �Ĵ���������

executeQuery() - ִ�в�ѯ���SQL ���
executeUpdate() - ִ�����ӡ�ɾ�������µ�SQL ���

preparseStatement ������
����飺
		preparseStatement ps = null;
		try {
			String sql = "select * from user where username=? and password=?";
			ps = conn.prepareStatement(sql);
			// ���ò�����ֵ
			ps.setString(1, "zhangsan");	// SQL ����в����±�λ�ã���1 ��ʼ
			ps.setString(2, "123");
			
			// ִ��
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

�������
ResultSet
rs.next() - ��ȡ��һ����¼
rs.preview() - ��ȡ��һ����¼

������
	batch
	1. ʹ��statement ����������
		Statement state = null;
		state.addBatch(sql);
	
	2. ʹ�� PreparseStatement ����������
		// MYSQL Ĭ�Ͽ�������Ĭ��SQL ���ִ��һ�����ύһ��
	    Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		conn = JdbcUtils.getConnection();
        String sql = "insert into batch values(null, ?)";
        ps = conn.prepareStatement(sql);
        // ���ò���(ʹ��������)
        for (int i = 0; i < 100; ++i) {
            ps.setString(1, "zhangsz" + (i + 1));
            ps.addBatch();  // ��SQL ��ӵ���������
        }
        
        ps.executeBatch();
		
		�ص㣺
			1). ��ֹ SQL ע�빥��
			2). Ч�ʸ���
			3). ��ִ�е�SQL ���Ǽ���ͬʱ��SQL ���ֻ��дһ�μ��ɣ�ͬʱ�Ǽ�Ҳ�����Ա������������д�Ǽ�SQL

	3. mysql Ч��
		// Ϊ�����Ч������Ӧ�ý��Զ��ύ���رգ��ֶ������ύ
		conn.setAutoCommit(false);
		// ... 
		conn.commit();

sql> show variables like 'char%';
-- mysql ��֧�� '-' ����û�� 'UTF-8' ֻ�� 'UTF8'
-- �����ݿ���ַ���������ΪGBK
sql> alter database jt_db charet=GBK;
-- ���߷�������gbk ����������ݲ����д���
-- ����MYSQL �ͻ��ˣ����ӵĴ������
sql> set names GBK;








