#!/bin/bash

# 所有主机统一的用户名和密码
# TODO: suyh - 用户名和密码修改成对应的值
HADOOP_USER="hdp"
HADOOP_PWD="hadoop"

# Hadoop NameNode 节点的IP
# TODO: suyh - IP 需要修改成对应的值，主机名建议不动。
HADOOP_NN_IP="172.31.3.201"
HADOOP_NN_HOST="hadoopnn"
HADOOP_RM_IP="172.31.3.202"
HADOOP_RM_HOST="hadooprm"
HADOOP_2NN_IP="172.31.3.203"
HADOOP_2NN_HOST="hadoop2nn"

# 以ip host 格式填充
# TODO: suyh - IP 需要修改成对应的值，主机名也要唯一。如果有多个则可以继续添加。
HADOOP_DN_SOURCE=()
HADOOP_DN_SOURCE+=("172.31.3.101 hadoop101")
HADOOP_DN_SOURCE+=("172.31.3.102 hadoop102")
HADOOP_DN_SOURCE+=("172.31.3.103 hadoop103")
HADOOP_DN_SOURCE+=("172.31.3.104 hadoop104")

############################################################
# 到此为此，后面的就不需要动了!!!
# 到此为此，后面的就不需要动了!!!
# 到此为此，后面的就不需要动了!!!
############################################################





















# 解析出来的所有ip 地址
HADOOP_DN_IPS=()
# 解析出来的所有host 地址
HADOOP_DN_HOSTS=()

# 整个hadoop data node 的节点数量
HADOOP_DN_SIZE=${#HADOOP_DN_SOURCE[@]}
echo "hadoop data node size: ${HADOOP_DN_SIZE}"

for dn in "${HADOOP_DN_SOURCE[@]}"; do
    # dn="172.31.3.104 hadoop104"
    read -r ip host <<< "$dn"

    HADOOP_DN_IPS+=("$ip")
    HADOOP_DN_HOSTS+=("$host")

    echo "dn: $dn"
    echo "ip: $ip"
    echo "host: $host"
done

# 至此，得到了所有节点的主机IP 以及主机名
# 获取当前系统的主机名
HOSTNAME=$(hostname)

# 使用 nslookup 查询主机名对应的 IP 地址，并仅获取 IPv4 地址
CURR_HOST_IPV4=$(nslookup "$HOSTNAME" | grep 'Address:' | grep -v '#' | awk '/Address:/{ if ($2 !~ /:/) {print $2}}')
echo "current host ipv4: ${CURR_HOST_IPV4}"
HADOOP_HOST_CATEGORY="UNKNOWN"
if [ "$CURR_HOST_IPV4" = "$HADOOP_NN_IP" ]; then
    HADOOP_HOST_CATEGORY="HadoopNameNode"
elif [ "$CURR_HOST_IPV4" = "$HADOOP_RM_IP" ]; then
    HADOOP_HOST_CATEGORY="YarnResourceManager"
elif [ "$CURR_HOST_IPV4" = "$HADOOP_2NN_IP" ]; then
    HADOOP_HOST_CATEGORY="HadoopSecondaryNameNode"
else
    for ip in "${HADOOP_DN_IPS[@]}"; do
        if [ "$CURR_HOST_IPV4" = "$ip" ]; then
            HADOOP_HOST_CATEGORY="DataNode"
            break
        fi
    done
fi

echo "current host category is: ${HADOOP_HOST_CATEGORY}"










# 输出整个数组
echo "HADOOP_DN_HOSTS: ${HADOOP_DN_HOSTS[@]}"
echo "HADOOP_DN_IPS: ${HADOOP_DN_IPS[@]}"

# 以for in 的形式遍历数组元素
for ip in "${HADOOP_DN_IPS[@]}"; do
    echo "ip: ${ip}"
done
for host in "${HADOOP_DN_HOSTS[@]}"; do
    echo "host: ${host}"
done

# 以下标的形式遍历数组
for ((i=0; i<$HADOOP_DN_SIZE; i++)); do
    echo "Element $i: ip: ${HADOOP_DN_IPS[i]}, host: ${HADOOP_DN_HOSTS[i]}"
done
