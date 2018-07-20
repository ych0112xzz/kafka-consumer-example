## Ŀ¼

- [kafka-consumer-example�ļ�Ŀ¼�ṹ](http://gitlab.local/liuzheng/kafka/tree/master/kafka-consumer-example#kafka-consumer-example%E6%96%87%E4%BB%B6%E7%9B%AE%E5%BD%95%E7%BB%93%E6%9E%84)
- [��α���](http://gitlab.local/liuzheng/kafka/tree/master/kafka-consumer-example#%E5%A6%82%E4%BD%95%E7%BC%96%E8%AF%91)
- [�������](http://gitlab.local/liuzheng/kafka/tree/master/kafka-consumer-example#%E5%A6%82%E4%BD%95%E8%BF%90%E8%A1%8C)
- [ʵ����kafka��Ⱥ����](http://gitlab.local/liuzheng/kafka/tree/master/kafka-consumer-example#%E5%AE%9E%E9%AA%8C%E5%AE%A4kafka%E9%9B%86%E7%BE%A4%E7%8E%AF%E5%A2%83)

### kafka-consumer-example�ļ�Ŀ¼�ṹ

src/main/java�°����������̡�

- һ����consumer_local,�������kafka topic�е���Ϣ����д��ָ���ı����ļ��С�
- һ����consumer_hdfs,�������kafka topic�е���Ϣ����д��ָ����HDFS�ļ��С�

### ��α���

> ����Ѿ���װ��maven��ʹ��git clone����repo������֮����Eclipse����ֱ�Ӵ�File -\> import -\> Maven -\> Existing Maven Projects������Ŀ���������ɺ�������1��3����

1. ��Eclipse�д����µ�Maven��[Maven�Ľ���](http://m.oschina.net/blog/145869)��[Maven��eclipse�еİ�װ](https://www.ibm.com/developerworks/cn/java/j-lo-maven/)����Ŀ����src/main/javaĿ¼�������ļ�������Ŀ��src�С�
2. ��д�½��õ�maven��Ŀ���pom.xml�ļ����ɲ��������[pom.xml](http://gitlab.local/liuzheng/kafka/blob/master/kafka-consumer-example/pom.xml)�ļ���
3. eclipse���һ���ĿRun As -\> Maven build -\> Goals��clean package,������targetĿ¼��������Ӧ��jar��

**ע�⣺** ���Eclipse�е�Window�������˵������Preference���ڵ����Ĳ˵������ҵ�Java������������˵��е�Installed JREs��ѡ�����JDK��·����JDK������JRE�����һ��ʼ��·����JRE��ĳ�JDK������Ӧ����H:\Program Files\Java\jdk1.7.0_51\jre������H:\Program Files\Java\jre7����ѡ��Edit���ڵ����Ĳ˵�����ָ��Default VM arguments��Ϊ-Dmaven.multiModuleProjectDirectory=$M2_HOME

### �������

- ����target�µ�kafka-consuemr-example-0.0.1-SNAPSHOT.jar
  1. jar���ŵ���Ⱥ���Լ���Ӧ��Ŀ¼��
  2. ʹ�������������У�
     - �������kafka topic�е���Ϣ����д��ָ���ı����ļ���

         ```
         java -cp jarname packagename.Classname zookeeper consumergroupId topic threadnum outputpath
         ```

     - ʾ����
    
         ```
         java -cp kafka-consumer-example-0.0.1-SNAPSHOT.jar consumer_hdfs.ConsumerGroupExample 192.168.80.11:2181 consumerLZ topicLZ 1 /home/liuzheng/output
         ```
     
     
     - �������kafka topic�е���Ϣ����д��ָ����HDFS�ļ���
     
         ```
         hadoop jar jarname packagename.Classname zookeeper consumergroupId topic threadnum outputpath
         ```

     - ʾ����

         ```
         hadoop jar kafka-consumer-example-0.0.1-SNAPSHOT.jar consumer_hdfs.ConsumerGroupExample 192.168.80.11:2181 consumerLZ topicLZ 1 /user/liuzheng/output
         ```
     
### ʵ����kafka��Ⱥ����

���е�Kafka��Ⱥ���£�
1. ���Լ�Ⱥ��BrokerΪ��80.71-80.75:9092�����ӵ�zookeeperΪ:datanode11-15:2181����Ҫ����ƽʱ������ϰ�����Դ�����ͨ�û���topic��[kafka consumer offset monitor](http://192.168.80.71:8080/#/)
2. ������Ⱥ��BrokerΪ��80.76-80.80:9092�����ӵ�zookeeperΪ:datanode21-25:2181����Ҫ����ʵ���һ����ճ��ϴ��������Դ�����ͨ�û���topic,[kafka consumer offset monitor](http://192.168.80.76:8080/#/)

Ҫʹ��Kafka���Լ�Ⱥ����������ϵ�������ڼ�Ⱥ�������Լ���topic