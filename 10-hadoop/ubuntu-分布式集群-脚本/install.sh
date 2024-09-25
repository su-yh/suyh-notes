#!/bin/bash

# TODO: suyh - IP 需要修改成对应的值，主机名也可修改，但要符合要求。
# Hadoop NameNode 节点的IP
HADOOP_NN_IP="172.31.3.201"
HADOOP_NN_HOST="hadoopNameNode"
# Hadoop ResourceManager 节点的IP
HADOOP_RM_IP="172.31.3.202"
HADOOP_RM_HOST="hadoopResourceManager"
# Hadoop SecondaryNameNode 节点的IP
HADOOP_2NN_IP="172.31.3.203"
HADOOP_2NN_HOST="hadoop2NameNode"

# 以ip host 格式填充
# TODO: suyh - IP 需要修改成对应的值，主机名也要唯一。如果有多个则可以继续添加。
HADOOP_DN_SOURCE=()
HADOOP_DN_SOURCE+=("172.31.3.101 hadoop101")
HADOOP_DN_SOURCE+=("172.31.3.102 hadoop102")
HADOOP_DN_SOURCE+=("172.31.3.103 hadoop103")
HADOOP_DN_SOURCE+=("172.31.3.104 hadoop104")

# 所有主机统一的用户名和密码
HADOOP_USER="hdp"
HADOOP_PWD="hdp"

############################################################
# 到此为此，后面的就不需要动了!!!
# 到此为此，后面的就不需要动了!!!
# 到此为此，后面的就不需要动了!!!
############################################################




















# 解析出的所有ip 地址
HADOOP_DN_IPS=()
# 解析出的所有host 地址
HADOOP_DN_HOSTS=()

# 整个hadoop data node 的节点数量
HADOOP_DN_SIZE=${#HADOOP_DN_SOURCE[@]}
echo "hadoop data node size: ${HADOOP_DN_SIZE}"

for dn in "${HADOOP_DN_SOURCE[@]}"; do
    # dn="172.31.3.104 hadoop104"
    read -r ip host <<< "$dn"

    HADOOP_DN_IPS+=("$ip")
    HADOOP_DN_HOSTS+=("$host")
#
#    echo "dn: $dn"
#    echo "ip: $ip"
#    echo "host: $host"
done

# 至此，得到了所有节点的主机IP 以及主机名


# 通过查询当前主机的IPV4 地址来匹配当前节点主机是什么角色，以及给配置的主机名和IP 地址

HOSTNAME=$(hostname)
CURR_HOST_IPV4=$(nslookup "${HOSTNAME}" | grep 'Address:' | grep -v '#' | awk '/Address:/{ if ($2 !~ /:/) {print $2}}')
CURR_HOST_NAME=

HADOOP_HOST_CATEGORY="UNKNOWN"
if [ "${CURR_HOST_IPV4}" = "${HADOOP_NN_IP}" ]; then
    HADOOP_HOST_CATEGORY="HadoopNameNode"
    CURR_HOST_NAME=${HADOOP_NN_HOST}
    ssh-keygen -t ed25519 -C ${HADOOP_NN_HOST} -f ~/.ssh/id_ed25519 -N ""
elif [ "${CURR_HOST_IPV4}" = "${HADOOP_RM_IP}" ]; then
    HADOOP_HOST_CATEGORY="YarnResourceManager"
    CURR_HOST_NAME=${HADOOP_RM_HOST}
    ssh-keygen -t ed25519 -C ${HADOOP_RM_HOST} -f ~/.ssh/id_ed25519 -N ""
elif [ "${CURR_HOST_IPV4}" = "${HADOOP_2NN_IP}" ]; then
    HADOOP_HOST_CATEGORY="HadoopSecondaryNameNode"
    CURR_HOST_NAME=${HADOOP_2NN_HOST}
    ssh-keygen -t ed25519 -C ${HADOOP_2NN_HOST} -f ~/.ssh/id_ed25519 -N ""
else
    for ((i=0; i<${HADOOP_DN_SIZE}; i++)); do
        ip=${HADOOP_DN_IPS[i]}
        host=${HADOOP_DN_HOSTS[i]}
        if [ "${CURR_HOST_IPV4}" = "$ip" ]; then
            HADOOP_HOST_CATEGORY="DataNode"
            CURR_HOST_NAME=${host}
            break
        fi
    done
fi

echo "current host category is: ${HADOOP_HOST_CATEGORY}"
echo "current host ipv4: ${CURR_HOST_IPV4}"
echo "current host name: ${CURR_HOST_NAME}"

if [ "${HADOOP_HOST_CATEGORY}" = "UNKNOWN" ]; then
  echo "###########################################"
  echo "HADOOP_NN_IP: ${HADOOP_NN_IP}"
  echo "HADOOP_RM_IP: ${HADOOP_RM_IP}"
  echo "HADOOP_2NN_IP: ${HADOOP_2NN_IP}"
  echo "HADOOP_DN_IPS: ${HADOOP_DN_IPS[@]}"
  echo "config error. current host ipv4: ${CURR_HOST_IPV4}"
  exit
fi

JDK_PATH=~/software/jdk-8u202-linux-x64.tar.gz
HADOOP_PATH=~/software/hadoop-3.2.4.tar.gz
FLINK_PATH=~/software/flink-1.18.0-bin-scala_2.12.tgz

if [ ! -f ${JDK_PATH} ]; then
    echo "WARN: file not exits: ${JDK_PATH}"
    exit 0
fi
if [ ! -f ${HADOOP_PATH} ]; then
    echo "WARN: file not exits: ${HADOOP_PATH}"
    exit 0
fi
if [ ! -f ${FLINK_PATH} ]; then
    echo "WARN: file not exits: ${FLINK_PATH}"
    exit 0
fi

# 处理当前节点主机的主机名
echo "${HADOOP_PWD}" | sudo -S hostnamectl set-hostname "${CURR_HOST_NAME}"

# 内网IP 通过主机名通信，配置/etc/hosts 文件
echo "${HADOOP_PWD}" | sudo -S sh -c "echo '${HADOOP_NN_IP} ${HADOOP_NN_HOST}' >> /etc/hosts"
echo "${HADOOP_PWD}" | sudo -S sh -c "echo '${HADOOP_RM_IP} ${HADOOP_RM_HOST}' >> /etc/hosts"
echo "${HADOOP_PWD}" | sudo -S sh -c "echo '${HADOOP_2NN_IP} ${HADOOP_2NN_HOST}' >> /etc/hosts"
for ((i=0; i<${HADOOP_DN_SIZE}; i++)); do
    ip=${HADOOP_DN_IPS[i]}
    host=${HADOOP_DN_HOSTS[i]}
    echo "${HADOOP_PWD}" | sudo -S sh -c "echo '${ip} ${host}' >> /etc/hosts"
done







## 输出整个数组
#echo "HADOOP_DN_HOSTS: ${HADOOP_DN_HOSTS[@]}"
#echo "HADOOP_DN_IPS: ${HADOOP_DN_IPS[@]}"
#
## 以for in 的形式遍历数组元素
#for ip in "${HADOOP_DN_IPS[@]}"; do
#    echo "ip: ${ip}"
#done
#for host in "${HADOOP_DN_HOSTS[@]}"; do
#    echo "host: ${host}"
#done
#
## 以下标的形式遍历数组
#for ((i=0; i<${HADOOP_DN_SIZE}; i++)); do
#    echo "Element $i: ip: ${HADOOP_DN_IPS[i]}, host: ${HADOOP_DN_HOSTS[i]}"
#done


# 准备所有软件安装的目录
echo "${HADOOP_PWD}" | sudo -S mkdir -p /opt/module
echo "${HADOOP_PWD}" | sudo -S chown ${HADOOP_USER}:${HADOOP_USER} /opt/module

# 先清理该目录
rm -rf /opt/module/*

# 安装jdk
echo "tar jdk"
tar -zxvf ${JDK_PATH} -C /opt/module
echo "tar hadoop"
tar -zxvf ${HADOOP_PATH} -C /opt/module
echo "tar flink"
tar -zxvf ${FLINK_PATH} -C /opt/module

echo "${HADOOP_PWD}" | sudo -S sh -c 'echo "# JDK" >> /etc/profile'
echo "${HADOOP_PWD}" | sudo -S sh -c 'echo "export JAVA_HOME=/opt/module/jdk1.8.0_202" >> /etc/profile'
echo "${HADOOP_PWD}" | sudo -S sh -c 'echo "export PATH=\${PATH}:\${JAVA_HOME}/bin" >> /etc/profile'
echo "${HADOOP_PWD}" | sudo -S sh -c 'echo "# HADOOP" >> /etc/profile'
echo "${HADOOP_PWD}" | sudo -S sh -c 'echo "export HADOOP_HOME=/opt/module/hadoop-3.2.4" >> /etc/profile'
echo "${HADOOP_PWD}" | sudo -S sh -c 'echo "export PATH=\${PATH}:\${HADOOP_HOME}/bin:\${HADOOP_HOME}/sbin" >> /etc/profile'
# flink yarn 配置
echo "${HADOOP_PWD}" | sudo -S sh -c 'echo "export HADOOP_CONF_DIR=\${HADOOP_HOME}/etc/hadoop" >> /etc/profile'
echo "${HADOOP_PWD}" | sudo -S sh -c 'echo "export HADOOP_CLASSPATH=\`hadoop classpath\`" >> /etc/profile'

source /etc/profile


# hadoop 配置 begin #####################################################################################################
# core-site 配置
HADOOP_CORE_SITE_PATH="${HADOOP_HOME}/etc/hadoop/core-site.xml"

rm -f ${HADOOP_CORE_SITE_PATH}

echo '<?xml version="1.0" encoding="UTF-8"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<!--
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License. See accompanying LICENSE file.
-->

<!-- Put site-specific property overrides in this file. -->

' >> "${HADOOP_CORE_SITE_PATH}"

echo "
<configuration>
    <!-- 指定NameNode的地址，hadoop 内网之间进行通信的端口 -->
    <property>
        <name>fs.defaultFS</name>
        <value>hdfs://${HADOOP_NN_HOST}:8020</value>
    </property>

    <!-- 指定hadoop数据的存储目录 -->
    <property>
        <name>hadoop.tmp.dir</name>
        <!-- 注意一下这个地方换成对应的目录位置 -->
        <value>${HADOOP_HOME}/data</value>
    </property>

    <!-- 配置HDFS网页登录使用的静态用户 -->
    <property>
        <name>hadoop.http.staticuser.user</name>
        <value>${HADOOP_USER}</value>
    </property>
</configuration>

" >> "${HADOOP_CORE_SITE_PATH}"


# hdfs-site 配置
HADOOP_HDFS_SITE_PATH="${HADOOP_HOME}/etc/hadoop/hdfs-site.xml"

rm -f ${HADOOP_HDFS_SITE_PATH}

echo '<?xml version="1.0" encoding="UTF-8"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<!--
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License. See accompanying LICENSE file.
-->

<!-- Put site-specific property overrides in this file. -->

' >> ${HADOOP_HDFS_SITE_PATH}

echo "
<configuration>
    <!-- nn(NameNode) web端访问地址，提供给用户使用的页面地址-->
    <property>
        <name>dfs.namenode.http-address</name>
        <value>${HADOOP_NN_HOST}:9870</value>
    </property>
	<!-- 2nn(SecondaryNameNode) web端访问地址-->
    <property>
        <name>dfs.namenode.secondary.http-address</name>
        <value>${HADOOP_2NN_HOST}:9868</value>
    </property>
</configuration>
" >> ${HADOOP_HDFS_SITE_PATH}

# yarn-site 配置
HADOOP_YARN_SITE_PATH="${HADOOP_HOME}/etc/hadoop/yarn-site.xml"

rm -f ${HADOOP_YARN_SITE_PATH}

echo '<?xml version="1.0"?>
<!--
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License. See accompanying LICENSE file.
-->
' >> ${HADOOP_YARN_SITE_PATH}

echo "
<configuration>
    <!-- 指定MR走shuffle -->
    <property>
        <name>yarn.nodemanager.aux-services</name>
        <value>mapreduce_shuffle</value>
    </property>

    <!-- 指定yarn ResourceManager的地址-->
    <property>
        <name>yarn.resourcemanager.hostname</name>
        <value>${HADOOP_RM_HOST}</value>
    </property>
</configuration>

" >> ${HADOOP_YARN_SITE_PATH}

# mapred-site 配置
HADOOP_MAPRED_SITE_PATH="${HADOOP_HOME}/etc/hadoop/mapred-site.xml"

rm -f ${HADOOP_MAPRED_SITE_PATH}

echo '<?xml version="1.0"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<!--
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License. See accompanying LICENSE file.
-->

<!-- Put site-specific property overrides in this file. -->

' >> ${HADOOP_MAPRED_SITE_PATH}

echo "
<configuration>
    <!-- 指定MapReduce程序运行在Yarn上 -->
    <property>
        <name>mapreduce.framework.name</name>
        <value>yarn</value>
    </property>
</configuration>
" >> ${HADOOP_MAPRED_SITE_PATH}

# hadoop-env.sh 配置
HADOOP_ENV_SH_PATH="${HADOOP_HOME}/etc/hadoop/hadoop-env.sh"
echo "export JAVA_HOME=${JAVA_HOME}" >> ${HADOOP_ENV_SH_PATH}

# 配置 hadoop workers
HADOOP_WORKERS_PATH="${HADOOP_HOME}/etc/hadoop/workers"
rm -f ${HADOOP_WORKERS_PATH}

for host in "${HADOOP_DN_HOSTS[@]}"; do
    echo "${host}" >> ${HADOOP_WORKERS_PATH}
done

# hadoop 配置 end   #####################################################################################################

# flink 配置 begin #####################################################################################################

# flink 配置 end   #####################################################################################################




# ssh 相关处理
#sshpass -p "${password}" ssh-copy-id ${host}
SSH_ENABLED=false
if [ "${HADOOP_HOST_CATEGORY}" = "HadoopNameNode" ]; then
    SSH_ENABLED=true
    set -x
    hdfs namenode -format
    set +x
elif [ "${HADOOP_HOST_CATEGORY}" = "YarnResourceManager" ]; then
    SSH_ENABLED=true
elif [ "${HADOOP_HOST_CATEGORY}" = "HadoopSecondaryNameNode" ]; then
    SSH_ENABLED=true
else
    SSH_ENABLED=false
fi

if [ ${SSH_ENABLED} = "true" ]; then
    echo "以下命令需要依次手动执行，并需要按提示输入前面配置的密码："
    echo "    ssh-copy-id ${HADOOP_NN_HOST}"
    echo "    ssh-copy-id ${HADOOP_RM_HOST}"
    echo "    ssh-copy-id ${HADOOP_2NN_HOST}"
    for host in "${HADOOP_DN_HOSTS[@]}"; do
        echo "    ssh-copy-id ${host}"
    done
fi
