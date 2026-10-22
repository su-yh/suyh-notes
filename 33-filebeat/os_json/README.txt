
结果输出到json 文件中

# 验证配置文件是否有错误
/Users/suyunhong/suyh-develop/medcase/filebeat-7.17.29-darwin-x86_64/filebeat test config -c os_json/filebeat-medcase.yml
# 验证es 连接是否正常
/Users/suyunhong/suyh-develop/medcase/filebeat-7.17.29-darwin-x86_64/filebeat test output -c os_json/filebeat-medcase.yml


rm -rf medcase/data-file/*
/Users/suyunhong/suyh-develop/medcase/filebeat-7.17.29-darwin-x86_64/filebeat -e -c os_json/filebeat-medcase.yml

