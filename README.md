## 目录

- [kafka-consumer-example文件目录结构](http://gitlab.local/liuzheng/kafka/tree/master/kafka-consumer-example#kafka-consumer-example%E6%96%87%E4%BB%B6%E7%9B%AE%E5%BD%95%E7%BB%93%E6%9E%84)
- [如何编译](http://gitlab.local/liuzheng/kafka/tree/master/kafka-consumer-example#%E5%A6%82%E4%BD%95%E7%BC%96%E8%AF%91)
- [如何运行](http://gitlab.local/liuzheng/kafka/tree/master/kafka-consumer-example#%E5%A6%82%E4%BD%95%E8%BF%90%E8%A1%8C)
- [实验室kafka集群环境](http://gitlab.local/liuzheng/kafka/tree/master/kafka-consumer-example#%E5%AE%9E%E9%AA%8C%E5%AE%A4kafka%E9%9B%86%E7%BE%A4%E7%8E%AF%E5%A2%83)

### kafka-consumer-example文件目录结构

src/main/java下包含两个工程。

- 一个是consumer_local,消费你的kafka topic中的消息，并写入指定的本地文件中。
- 一个是consumer_hdfs,消费你的kafka topic中的消息，并写入指定的HDFS文件中。

### 如何编译

> 如果已经安装了maven可使用git clone复制repo到本地之后，在Eclipse里面直接从File -\> import -\> Maven -\> Existing Maven Projects导入项目，这样即可忽略下面1～3步。

1. 在Eclipse中创建新的Maven（[Maven的介绍](http://m.oschina.net/blog/145869)、[Maven在eclipse中的安装](https://www.ibm.com/developerworks/cn/java/j-lo-maven/)）项目，将src/main/java目录下所有文件放入项目的src中。
2. 编写新建好的maven项目里的pom.xml文件，可参照上面的[pom.xml](http://gitlab.local/liuzheng/kafka/blob/master/kafka-consumer-example/pom.xml)文件。
3. eclipse里右击项目Run As -\> Maven build -\> Goals：clean package,将会在target目录下生成相应的jar包

**注意：** 点击Eclipse中的Window在下拉菜单中左击Preference，在弹出的菜单左栏找到Java，并左击下拉菜单中的Installed JREs，选中你的JDK（路径是JDK而不是JRE，如果一开始的路径是JRE请改成JDK，例如应该是H:\Program Files\Java\jdk1.7.0_51\jre而不是H:\Program Files\Java\jre7）并选择Edit，在弹出的菜单栏里指定Default VM arguments：为-Dmaven.multiModuleProjectDirectory=$M2_HOME

### 如何运行

- 运行target下的kafka-consuemr-example-0.0.1-SNAPSHOT.jar
  1. jar包放到集群上自己对应的目录下
  2. 使用如下命令运行：
     - 消费你的kafka topic中的消息，并写入指定的本地文件中

         ```
         java -cp jarname packagename.Classname zookeeper consumergroupId topic threadnum outputpath
         ```

     - 示例：
    
         ```
         java -cp kafka-consumer-example-0.0.1-SNAPSHOT.jar consumer_hdfs.ConsumerGroupExample 192.168.80.11:2181 consumerLZ topicLZ 1 /home/liuzheng/output
         ```
     
     
     - 消费你的kafka topic中的消息，并写入指定的HDFS文件中
     
         ```
         hadoop jar jarname packagename.Classname zookeeper consumergroupId topic threadnum outputpath
         ```

     - 示例：

         ```
         hadoop jar kafka-consumer-example-0.0.1-SNAPSHOT.jar consumer_hdfs.ConsumerGroupExample 192.168.80.11:2181 consumerLZ topicLZ 1 /user/liuzheng/output
         ```
     
### 实验室kafka集群环境

现有的Kafka集群如下：
1. 测试集群：Broker为：80.71-80.75:9092，连接的zookeeper为:datanode11-15:2181，主要用做平时测试练习，可以创建普通用户的topic，[kafka consumer offset monitor](http://192.168.80.71:8080/#/)
2. 生产集群：Broker为：80.76-80.80:9092，连接的zookeeper为:datanode21-25:2181，主要用做实验室话单日常上传，不可以创建普通用户的topic,[kafka consumer offset monitor](http://192.168.80.76:8080/#/)

要使用Kafka测试集群，可与我联系，申请在集群中属于自己的topic