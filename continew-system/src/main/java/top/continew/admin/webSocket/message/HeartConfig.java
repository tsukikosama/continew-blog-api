package top.continew.admin.webSocket.message;

import lombok.Data;

@Data
public class HeartConfig {
    private volatile Integer missHeartCount;
    private volatile Long lastHeartTime;

    public HeartConfig(Integer missHeartCount, Long lastHeartTime) {
        this.missHeartCount = missHeartCount;
        this.lastHeartTime = lastHeartTime;
    }

    public void reset() {
        missHeartCount = 0;
        lastHeartTime = System.currentTimeMillis();
    }
}
