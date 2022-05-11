package Domain.ADTs;

import Domain.Values.IValue;

import java.util.HashMap;
import java.util.Map;

public class HeapADT extends DictionaryADT<Integer, IValue> {
    private int lastAddress = 0;

    public synchronized int getNewAddress() throws Exception {
        lastAddress++;
        boolean trigger = false;
        while (this.map.containsKey(lastAddress)) {
            lastAddress++;
            if (lastAddress < 0)
                if (!trigger) {
                    lastAddress = 1;
                    trigger = true;
                } else {
                    throw new Exception();
                }
        }
        return lastAddress;
    }

    public synchronized Map<Integer, IValue> getHeapContent() {
        return this.map;
    }

    public Map<Integer, String> getHeapDisplayFormatContent() {
        Map<Integer, String> display_format_map = new HashMap<>();
        for (Map.Entry<Integer, IValue> entry : this.map.entrySet()) {
            display_format_map.put(entry.getKey(), entry.getValue().toString());
        }
        return display_format_map;
    }

    public synchronized void setContent(Map<Integer, IValue> newMap) {
        this.map = newMap;
    }
}
