package Producer_Customer;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 主线作业
 */
public class MainProcess {
    public static void main(String[] args) throws InterruptedException {
        //创建线程池
        ExecutorService service = Executors.newCachedThreadPool();
        //创建数据工厂
        DataFactory dataFactory = new DataFactory();
        //设置缓冲区大小，必须为2的指数，否则会有异常
        int buffersize = 1024;
        Disruptor<Data> dataDisruptor = new Disruptor<Data>(dataFactory,buffersize,service);
        //创建消费者线程
        dataDisruptor.handleEventsWithWorkerPool(
                new Customer(),
                new Customer(),
                new Customer(),
                new Customer(),
                new Customer(),
                new Customer(),
                new Customer(),
                new Customer()
        );
        //启动
        dataDisruptor.start();
        //获取其队列
        RingBuffer<Data> ringBuffer = dataDisruptor.getRingBuffer();
        for(int i = 0; i < 100; i++){
            //创建生产者
            Producer producer = new Producer(ringBuffer);
            //设置内容
            producer.pushData(UUID.randomUUID().toString());
            Thread.sleep(1000);
        }
    }
}
