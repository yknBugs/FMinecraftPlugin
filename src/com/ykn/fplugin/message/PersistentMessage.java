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
    public int time;

    /**
     * 消息显示的优先级 <p>
     * 高优先级的消息会覆盖掉低优先级的消息强制显示 <p>
     * 正在显示高优先级的消息时，无法显示低优先级的消息 <p>
     * 优先级相同的消息，优先显示后产生从消息
     */
    public int priority;

    /**
     * 要显示的消息的内容
     */
    public PlaceholderMessage placeholderMessage;

    public PersistentMessage() {
        this.delay = 1;
        this.time = 1;
        this.placeholderMessage = new PlaceholderMessage();
    }

    public PersistentMessage(int delay, int tick, int priority, PlaceholderMessage placeholderMessage) {
        this.delay = delay;
        this.time = tick;
        this.priority = priority;
        this.placeholderMessage = placeholderMessage;
    }

    /**
     * 帧事件，减少相关的计时器
     */
    public void tick() {
        if (this.delay <= 0) {
            this.time--;
        }
        this.delay--;
    }
    
}
