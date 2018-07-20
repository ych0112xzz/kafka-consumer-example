package consumer_hdfs;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 将消息写入到HDFS指定的文件中
 * 
 * @author zheng
 * 
 */
public class ConsumerGroupExample {
	private final ConsumerConnector consumer;
	private final String topic;
	private ExecutorService executor;

	/**
	 * 
	 * @param a_zookeeper
	 *            集群中连接的zookeeper，形如datanode11:2181
	 * @param a_groupId
	 *            consumer处理过程中的consumer group
	 * @param a_topic
	 *            消息对应的topic
	 */
	public ConsumerGroupExample(String a_zookeeper, String a_groupId,
			String a_topic) {
		consumer = kafka.consumer.Consumer
				.createJavaConsumerConnector(createConsumerConfig(a_zookeeper,
						a_groupId)); // 创建Connector
		this.topic = a_topic;
	}

	public void shutdown() {
		if (consumer != null)
			consumer.shutdown();
		if (executor != null)
			executor.shutdown();
		try {
			// 5秒后检测线程池中的线程是否结束
			if (!executor.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
				System.out
						.println("Timed out waiting for consumer threads to shut down, exiting uncleanly");
			}
		} catch (InterruptedException e) {
			System.out
					.println("Interrupted during shutdown, exiting uncleanly");
		}
	}

	/**
	 * 
	 * @param a_numThreads
	 *            consumer的线程数，原则上a_numThreads不大于topic对应的partition数，这里每个partition对应一个线程
	 * @param output
	 *            输出文件
	 */
	public void run(int a_numThreads, String output) {
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic, new Integer(a_numThreads)); // 描述读取哪个topic，需要几个线程读
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer
				.createMessageStreams(topicCountMap); // 创建Streams
		List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic); // 每个线程对应于一个KafkaStream

		executor = Executors.newFixedThreadPool(a_numThreads);

		int threadNumber = 1;
		for (final KafkaStream stream : streams) {
			executor.submit(new ConsumerTest(stream, threadNumber, output));
			threadNumber++;
		}
	}

	private static ConsumerConfig createConsumerConfig(String a_zookeeper,
			String a_groupId) {
		Properties props = new Properties();
		// 指定集群中连接的zookeeper，形如datanode11:2181
		props.put("zookeeper.connect", a_zookeeper);
		// 指定consumer处理过程中的consumer group
		props.put("group.id", a_groupId);
		// 连接zookeeper的session超时时间
		props.put("zookeeper.session.timeout.ms", "400");
		// zookeeper follower落后于zookeeper leader的最长时间
		props.put("zookeeper.sync.time.ms", "200");
		// 往zookeeper上写offset的频率
		props.put("auto.commit.interval.ms", "10000");
		// 如果要读旧数据，则必须要加；默认largest表示读取新的数据
		props.put("auto.offset.reset", "smallest");
		
		return new ConsumerConfig(props);
	}

	public static void main(String[] args) {
		if (args.length != 5) {
			System.err
					.println("please <zookeeper> <groupId> <topic> <threadNum> <pathname> ");
		}
		String zooKeeper = args[0];
		String groupId = args[1];
		String topic = args[2];
		int threads = Integer.parseInt(args[3]);
		String output = args[4];

		ConsumerGroupExample example = new ConsumerGroupExample(zooKeeper,
				groupId, topic);
		example.run(threads, output);

		//让主线程休眠10秒（数据量大时可设置更长的时间）确保后面shutdown时consumer threads写数据完毕
		try {
			Thread.sleep(10000);
		} catch (InterruptedException ie) {

		}
		
		//关闭consumer和executor，也可不关闭，让consumer一直监测topic
		example.shutdown();
	}
}
