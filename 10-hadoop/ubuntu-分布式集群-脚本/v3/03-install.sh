#!/bin/bash

# 该脚本做了什么？
# 1. 三台管理主机(NameNode, ResourceManager, SecondaryNameNode)节点，会生成 ssh 并对集群中的所有主机配置免密登录。(如果三台管理主机节点IP 相同，则只有一台管理主机节点)
# 2. 在目录 /opt/module 目录安装 hadoop 以及jdk  安装之前会先删除目录 /opt/module ，如果该目录存在数据，需要提前处理好。
# 3. 修改每台主机节点的主机名，所有的集群通信都是依赖主机名来的。
# 4. 使用主机名通信，需要配置 /etc/hosts 配置文件，会将每个主机的主机名跟内网IP 进行关联。
# 5. 添加全局环境变量，修改 /etc/profile 配置文件，将 jdk hadoop 相关的环境变量配置追加到该文件中。
# 6. 安装hadoop 的同时，修改对应的环境变量，以及通信配置


# TODO: suyh - IP 需要修改成对应的值，主机名也可修改，但要符合规范。
#  如果NameNode ResourceManager 以及SecondaryNameNode 为同一台主机，则可以将三台主机节点的IP 都配置为同一个即可。
# Hadoop NameNode 节点的IP
HADOOP_NN_IP="192.168.8.58"
HADOOP_NN_HOST="hadoop-name-node"
# Hadoop ResourceManager 节点的IP
HADOOP_RM_IP="192.168.8.58"
HADOOP_RM_HOST="hadoop-resource-manager"
# Hadoop SecondaryNameNode 节点的IP
HADOOP_2NN_IP="192.168.8.58"
HADOOP_2NN_HOST="hadoop-secondary-name-node"

# 以ip host 格式填充
# TODO: suyh - IP 需要修改成对应的值，主机名也要唯一。如果有多个则可以继续添加。
HADOOP_DN_SOURCE=()
HADOOP_DN_SOURCE+=("192.168.8.139 hadoop101")
HADOOP_DN_SOURCE+=("192.168.8.141 hadoop102")
HADOOP_DN_SOURCE+=("192.168.8.143 hadoop103")
HADOOP_DN_SOURCE+=("192.168.8.144 hadoop104")
HADOOP_DN_SOURCE+=("192.168.8.145 hadoop105")
HADOOP_DN_SOURCE+=("192.168.8.135 hadoop106")
HADOOP_DN_SOURCE+=("192.168.8.113 hadoop107")

# DATA NODE 主机的CPU 核心数量
# TODO: suyh - 修改成对应主机的CPU 核心数
DN_CORES=4
# 由真实核心数计算出来的虚拟核心数，一般是真实核心的两到三倍，flink 主要是使用内存，这里可以虚拟出稍多一点。
DN_VIRTUAL_CORES=`expr ${DN_CORES} \* 4`

# 所有主机统一的用户名和密码
HADOOP_USER="hdp"
HADOOP_PWD="hdp"

# 环境运行时区，包括hadoop yarn flink 都将以该时区处理时间的解析
# 中国时区：Asia/Shanghai 印度时区：Asia/Kolkata
# LOCAL_TZ=Asia/Shanghai
LOCAL_TZ=

# 每个节点主机的用户日志目录，保留日志的时间，单位：秒。默认值是：10800(3 小时)
# 在这里我们的 JobManager TaskManager 的相关日志都存放在该目录下。
# 所以至少也应该保留一天以上，一般保留30 天(2592000s)。
YARN_NODEMANAGER_LOG_RETAIN_SECONDS=2592000

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

# HOSTNAME=$(hostname)
# 使用nslookup 命令解析本机ipv4 地址
# CURR_HOST_IPV4=$(nslookup "$(hostname)" | grep 'Address:' | grep -v '#' | awk '/Address:/{ if ($2 !~ /:/) {print $2}}')
# 使用ip addr  命令解析本机ipv4 地址
CURR_HOST_IPV4=$(ip addr show | awk '/inet /{print $2}' | grep -v '127.0.0.1' | grep -v 'fe80::' | cut -d'/' -f1)
CURR_HOST_NAME=

HADOOP_HOST_CATEGORY="UNKNOWN"
if [ "${CURR_HOST_IPV4}" = "${HADOOP_NN_IP}" ]; then
    HADOOP_HOST_CATEGORY="HadoopNameNode"
    CURR_HOST_NAME=${HADOOP_NN_HOST}
    ssh-keygen -t ed25519 -C ${HADOOP_NN_HOST} -f ${HOME}/.ssh/id_ed25519 -N ""
elif [ "${CURR_HOST_IPV4}" = "${HADOOP_RM_IP}" ]; then
    HADOOP_HOST_CATEGORY="YarnResourceManager"
    CURR_HOST_NAME=${HADOOP_RM_HOST}
    ssh-keygen -t ed25519 -C ${HADOOP_RM_HOST} -f ${HOME}/.ssh/id_ed25519 -N ""
elif [ "${CURR_HOST_IPV4}" = "${HADOOP_2NN_IP}" ]; then
    HADOOP_HOST_CATEGORY="HadoopSecondaryNameNode"
    CURR_HOST_NAME=${HADOOP_2NN_HOST}
    ssh-keygen -t ed25519 -C ${HADOOP_2NN_HOST} -f ${HOME}/.ssh/id_ed25519 -N ""
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

echo "###########################################"
echo "HADOOP_NN_IP: ${HADOOP_NN_IP}"
echo "HADOOP_RM_IP: ${HADOOP_RM_IP}"
echo "HADOOP_2NN_IP: ${HADOOP_2NN_IP}"
echo "HADOOP_DN_IPS: ${HADOOP_DN_IPS[@]}"

if [ "${HADOOP_HOST_CATEGORY}" = "UNKNOWN" ]; then
  echo "CONFIG ERROR!!! current host ipv4: ${CURR_HOST_IPV4}"
  exit
fi

JDK_PATH=${HOME}/software/jdk-8u202-linux-x64.tar.gz
HADOOP_PATH=${HOME}/software/hadoop-3.2.4.tar.gz

if [ ! -f ${JDK_PATH} ]; then
    echo "WARN: file not exits: ${JDK_PATH}"
    exit 0
fi
if [ ! -f ${HADOOP_PATH} ]; then
    echo "WARN: file not exits: ${HADOOP_PATH}"
    exit 0
fi

# 处理当前节点主机的主机名
echo "${HADOOP_PWD}" | sudo -S hostnamectl set-hostname "${CURR_HOST_NAME}"

# 内网IP 通过主机名通信，配置/etc/hosts 文件
echo "${HADOOP_PWD}" | sudo -S sh -c "echo '' >> /etc/hosts"
echo "${HADOOP_PWD}" | sudo -S sh -c "echo '' >> /etc/hosts"
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

echo "${HADOOP_PWD}" | sudo -S sh -c 'echo "# JDK" >> /etc/profile'
echo "${HADOOP_PWD}" | sudo -S sh -c 'echo "export JAVA_HOME=/opt/module/jdk1.8.0_202" >> /etc/profile'
echo "${HADOOP_PWD}" | sudo -S sh -c 'echo "export PATH=\${PATH}:\${JAVA_HOME}/bin" >> /etc/profile'
echo "${HADOOP_PWD}" | sudo -S sh -c 'echo "# HADOOP" >> /etc/profile'
echo "${HADOOP_PWD}" | sudo -S sh -c 'echo "export HADOOP_HOME=/opt/module/hadoop-3.2.4" >> /etc/profile'
echo "${HADOOP_PWD}" | sudo -S sh -c 'echo "export PATH=\${PATH}:\${HADOOP_HOME}/bin:\${HADOOP_HOME}/sbin" >> /etc/profile'
# flink yarn 配置
echo "${HADOOP_PWD}" | sudo -S sh -c 'echo "# flink yarn" >> /etc/profile'
echo "${HADOOP_PWD}" | sudo -S sh -c 'echo "export HADOOP_CONF_DIR=\${HADOOP_HOME}/etc/hadoop" >> /etc/profile'
echo "${HADOOP_PWD}" | sudo -S sh -c 'echo "export HADOOP_CLASSPATH=\`hadoop classpath\`" >> /etc/profile'


source /etc/profile


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
    <!-- 节点主机名 -->
    <property>
        <name>dfs.datanode.hostname</name>
        <value>${CURR_HOST_NAME}</value>
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
    
    <!-- 一般需要按真实CPU 核心的两三倍设置 -->
    <property>
        <name>yarn.nodemanager.resource.cpu-vcores</name>
        <value>${DN_VIRTUAL_CORES}</value> <!--设置虚拟 CPU 内核数-->
    </property>

    <!-- 设置yarn 的调度器为容量调度器 -->
    <property>
        <name>yarn.resourcemanager.scheduler.class</name>
        <value>org.apache.hadoop.yarn.server.resourcemanager.scheduler.fair.FairScheduler</value>
        <!-- <value>org.apache.hadoop.yarn.server.resourcemanager.scheduler.capacity.CapacityScheduler</value> -->
        <description>配置Yarn使用的调度器插件类名；Fair Scheduler对应的是：org.apache.hadoop.yarn.server.resourcemanager.scheduler.fair.FairScheduler</description>
    </property>

    <!-- 容器的最小内存限制 -->
    <property>
        <description>The minimum allocation for every container request at the RM
        in MBs. Memory requests lower than this will be set to the value of this
        property. Additionally, a node manager that is configured to have less memory
        than this value will be shut down by the resource manager.</description>
        <name>yarn.scheduler.minimum-allocation-mb</name>
        <value>1024</value>
    </property>

    <!-- 保留用户日志的时间，日志聚合禁用时有效。 -->
    <property>
        <description>Time in seconds to retain user logs. Only applicable if
        log aggregation is disabled
        </description>
        <name>yarn.nodemanager.log.retain-seconds</name>
        <value>${YARN_NODEMANAGER_LOG_RETAIN_SECONDS}</value>
    </property>

    <property>
        <description>The maximum allocation for every container request at the RM
        in terms of virtual CPU cores. Requests higher than this will throw an
        InvalidResourceRequestException.</description>
        <name>yarn.scheduler.maximum-allocation-vcores</name>
        <value>4</value>
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

# export JAVA_HOME=
# hadoop-env.sh 配置
HADOOP_ENV_SH_PATH="${HADOOP_HOME}/etc/hadoop/hadoop-env.sh"
# JDK 配置
echo "export JAVA_HOME=${JAVA_HOME}" >> ${HADOOP_ENV_SH_PATH}
# 时区配置
echo "export TZ=${LOCAL_TZ}" >> ${HADOOP_ENV_SH_PATH}





# 配置workers
HADOOP_WORKERS_PATH="${HADOOP_HOME}/etc/hadoop/workers"
rm -f ${HADOOP_WORKERS_PATH}

for host in "${HADOOP_DN_HOSTS[@]}"; do
    echo "${host}" >> ${HADOOP_WORKERS_PATH}
done

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
    echo "以下命令需要依次手动执行，并需要按提示输入前面配置的密码（密码值为：\"${HADOOP_PWD}\"）："
    echo "    ssh-copy-id -o StrictHostKeyChecking=no ${HADOOP_NN_HOST}"
    echo "    ssh-copy-id -o StrictHostKeyChecking=no ${HADOOP_RM_HOST}"
    echo "    ssh-copy-id -o StrictHostKeyChecking=no ${HADOOP_2NN_HOST}"
    for host in "${HADOOP_DN_HOSTS[@]}"; do
        echo "    ssh-copy-id -o StrictHostKeyChecking=no ${host}"
    done
fi
