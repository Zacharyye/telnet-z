package Producer_Customer;

import com.lmax.disruptor.EventFactory;

/**
 * 数据工厂
 */
public class DataFactory implements EventFactory<Data> {

    @Override
    public Data newInstance() {
        return new Data();
    }
}
