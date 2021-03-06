package consumer_local;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

/**
 * 将数据写入文件的子线程
 * 
 * @author zheng
 * 
 */
public class ConsumerTest implements Runnable {
	private KafkaStream m_stream;
	private int m_threadNumber;
	private String m_output;

	/**
	 * 
	 * @param a_stream
	 *            信息流
	 * @param a_threadNumber
	 *            线程号
	 * @param a_output
	 *            输出文件
	 */
	public ConsumerTest(KafkaStream a_stream, int a_threadNumber,
			String a_output) {
		m_threadNumber = a_threadNumber;
		m_stream = a_stream;
		m_output = a_output;
	}

	public void run() {
		ConsumerIterator<byte[], byte[]> it = m_stream.iterator();
		File f = new File(m_output + m_threadNumber);
		long start_time = System.currentTimeMillis();
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(f));

			while (it.hasNext()) { // 若consumer没有关闭，hasNext()会block
				String line = new String(it.next().message());
				writer.write(line);
				writer.newLine();
				writer.flush();
			}
			writer.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		long end_time = System.currentTimeMillis();
		System.out.println("线程"
				+ m_threadNumber
				+ "开始写入的时间： "
				+ new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(new java.util.Date(start_time)));
		System.out.println("线程"
				+ m_threadNumber
				+ "完成的时间： "
				+ new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(new java.util.Date(end_time)));
		long output_size = f.length();
		long timer = (end_time - start_time) / 1000;
		double in_size = output_size / (1024 * 1024);
		double speed = in_size / timer;
		System.out.println("发送文件的总大小：(MB)" + in_size);
		System.out.println("线程" + m_threadNumber + "处理速度(MB/s)： " + speed);
	}
}
