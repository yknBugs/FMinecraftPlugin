package com.ykn.fplugin.message;

/**
 * 需要权限才能显示的且拥有数值不断变化的占位符的消息
 */
public class PlaceholderMessage {

    public String message;
    public String permission;

    public PlaceholderMessage() {
        this.permission = null;
        this.message = "";
    }

    public PlaceholderMessage(String permission, String message) {
        this.permission = permission;
        this.message = message;
    }

    /**
     * 将含占位符的字符串替换成指定的数值
     * @return 格式化后的字符串结果
     */
    public String formatPlaceholders() {
        return this.message;
    }

}
