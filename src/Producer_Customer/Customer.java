package Producer_Customer;

import com.lmax.disruptor.WorkHandler;

/**
 * 消费者
 */
public class Customer implements WorkHandler<Data> {

    @Override
    public void onEvent(Data data) throws Exception {
        System.out.println(Thread.currentThread().getName() + "--" + data.getData());
    }
}
