package com.ykn.fplugin.message;

/**
 * 持续显示在 ActionBar 上的消息的某一行
 */
public class PersistentMessage {

    /**
     * 延迟显示消息
     */
    public int delay;

    /**
     * 消息显示的剩余时长
     */
    public int tick;

    /**
     * 要显示的消息的内容
     */
    public PlaceholderMessage placeholderMessage; 

    public PersistentMessage() {
        this.delay = 1;
        this.tick = 1;
        this.placeholderMessage = new PlaceholderMessage();
    }

    public PersistentMessage(int delay, int tick, PlaceholderMessage placeholderMessage) {
        this.delay = delay;
        this.tick = tick;
        this.placeholderMessage = placeholderMessage;
    }

    /**
     * 帧事件，减少相关的计时器
     */
    public void tick() {
        if (this.delay <= 0) {
            this.tick--;
        }
        this.delay--;
    }
    
}
