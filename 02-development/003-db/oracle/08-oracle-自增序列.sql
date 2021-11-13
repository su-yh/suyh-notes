

-- oracle 中的自增序列

-- ####################################################

-- 创建自增序列
    CREATE SEQUENCE seq_intf_account_document_source_line_id_seq;

    create sequence SEQ_INTF_ACCOUNT_DOCUMENT_SOURCE_LINE_ID_SEQ
        minvalue 1
        maxvalue 90000000
        start with 1
        increment by 1
        nocache
        cycle;

-- 删除自增序列
    DROP SEQUENCE seq_intf_account_document_source_line_id_seq;
-- 删除序列之前先判断是否存在
	DECLARE
	  AN_FLAG NUMBER := 0;
	BEGIN
	  SELECT COUNT(1) INTO AN_FLAG FROM ALL_SEQUENCES
	   WHERE SEQUENCE_NAME = 'SEQ_INTF_ACCOUNT_DOCUMENT_SOURCE_LINE_ID_SEQ';
	  IF AN_FLAG = 1 THEN
		EXECUTE IMMEDIATE 'DROP SEQUENCE SEQ_INTF_ACCOUNT_DOCUMENT_SOURCE_LINE_ID_SEQ';
	  END IF;
	END;
	/

-- 使用自增序列
	INSERT INTO intf_account_document(source_line_id) VALUES(seq_intf_account_document_source_line_id_seq.nextval);





    




