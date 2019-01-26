package Producer_Customer;


import com.lmax.disruptor.RingBuffer;

/**
 * 生产者
 */
public class Producer {
    private final RingBuffer<Data> dataRingBuffer;

    public Producer (RingBuffer<Data> dataRingBuffer) {
        this.dataRingBuffer = dataRingBuffer;
    }

    public void pushData(String s){
        long next = dataRingBuffer.next();
        try{
            //获取容器
            Data data = dataRingBuffer.get(next);
            //设置数据
            data.setData(s);
        } finally {
            //插入数据
            dataRingBuffer.publish(next);
        }
    }
}
