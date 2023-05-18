package com.ykn.fplugin.util;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.EnumWrappers.TitleAction;
import com.ykn.fplugin.config.GlobalConfig;

import me.clip.placeholderapi.PlaceholderAPI;

public class BroadcastInfo {

    /**
     * 检查字符串中是否包含可能被解析为PlaceholderAPI字符等不安全内容
     * 避免玩家利用PlaceholderAPI获取关键数据
     * @param text 要检查的文本内容
     * @return true，如果不包含可被解析的内容
     */
    public static boolean isTextSafe(String text) {
        if (text.matches("^.*%.*$") || text.matches("^.*\\[.*\\].*$")) {
            return false;
        }
        return true;
    }

    /**
     * 给指定玩家发送Actionbar消息
     * @param player 玩家
     * @param text 消息文本内容，支持PlaceholderAPI占位符
     */
    public static void sendActionbarText(Player player, String text) {
        try {
            PacketContainer packet = new PacketContainer(PacketType.Play.Server.SET_ACTION_BAR_TEXT);
            packet.getModifier().writeDefaults();
            packet.getTitleActions().write(0, TitleAction.ACTIONBAR);
            packet.getChatComponents().write(0, WrappedChatComponent.fromText(PlaceholderAPI.setPlaceholders(player, text)));
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet, false);
        } catch (Exception e) {
            GlobalConfig.getLogger().warning("Send actionbar package failed.");
            e.printStackTrace();
        }
    }
    
    /**
     * 向所有玩家广播Actionbar消息
     * @param text 消息内容
     */
    public static void broadcastActionbarText(String text) {
        try {
            PacketContainer packet = new PacketContainer(PacketType.Play.Server.SET_ACTION_BAR_TEXT);
            packet.getModifier().writeDefaults();
            packet.getTitleActions().write(0, TitleAction.ACTIONBAR);
            packet.getChatComponents().write(0, WrappedChatComponent.fromText(text));
            ProtocolLibrary.getProtocolManager().broadcastServerPacket(packet);
        } catch (Exception e) {
            GlobalConfig.getLogger().warning("Send actionbar package failed.");
            e.printStackTrace();
        }
    }

    /**
     * 向所有玩家广播Actionbar消息，支持PlaceholderAPI占位符
     * @param player 用于占位符解析用的玩家
     * @param text 消息内容
     */
    public static void broadcastActionbarText(Player player, String text) {
        try {
            PacketContainer packet = new PacketContainer(PacketType.Play.Server.SET_ACTION_BAR_TEXT);
            packet.getModifier().writeDefaults();
            packet.getTitleActions().write(0, TitleAction.ACTIONBAR);
            packet.getChatComponents().write(0, WrappedChatComponent.fromText(PlaceholderAPI.setPlaceholders(player, text)));
            ProtocolLibrary.getProtocolManager().broadcastServerPacket(packet);
        } catch (Exception e) {
            GlobalConfig.getLogger().warning("Send actionbar package failed.");
            e.printStackTrace();
        }
    }

    /**
     * 向有权限的玩家发送actionbar消息
     * @param permission 需要的权限
     * @param text 文本内容，其中PlaceholderAPI将分别对每个玩家进行单独的解析
     */
    public static void broadcastActionbarText(String permission, String text) {
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        for (Player player : players) {
            if (player.hasPermission(permission)) {
                sendActionbarText(player, text);
            }
        } 
    }

    /**
     * 以指定玩家作为PlaceholderAPI占位符，向所有有权限的玩家发送actionbar消息
     * @param player 作为PlaceholderAPI占位符的玩家
     * @param permission 需要的权限
     * @param text 文本内容
     */
    public static void broadcastActionbarText(Player player, String permission, String text) {
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        for (Player p : players) {
            if (p.hasPermission(permission)) {
                sendActionbarText(p, PlaceholderAPI.setPlaceholders(player, text));
            }
        } 
    }

    /**
     * 回复带前缀的，指令发送者的消息。如果指令发送者是玩家则会被PlaceholderAPI解析
     * @param sender 指令发送者
     * @param text 消息内容
     */
    public static void replyMessage(CommandSender sender, String text) {
        replyMessage(sender, false, text, null);
    }

    /**
     * 回复带前缀的，指令发送者的消息。如果指令发送者是玩家则会被PlaceholderAPI解析
     * @param sender 指令发送者
     * @param safeMode 安全模式。开启后，PlaceholderAPI将仅解析玩家输入内容以外的部分，否则会解析全部内容
     * @param text 消息内容。其中用[1]，[2]，[3]等占位符替换玩家输入的内容
     * @param holderText 用于替换消息内容中的占位符的，玩家输入的内容
     */
    public static void replyMessage(CommandSender sender, boolean safeMode, String text, String[] holderText) {
        //TODO:holderText为正则表达式会抛出异常
        if (holderText == null) {
            if (sender instanceof Player) {
                if (!((Player) sender).isOnline()) {
                    return;
                }
                sender.sendMessage(GlobalConfig.getPrefix() + GlobalConfig.toColorString(PlaceholderAPI.setPlaceholders((Player) sender, text)));
            } else {
                sender.sendMessage(GlobalConfig.getPrefix() + GlobalConfig.toColorString(text));
            }
        } else {
            if (sender instanceof Player) {
                if (!((Player) sender).isOnline()) {
                    return;
                }
                
                if (safeMode) {
                    Player player = (Player) sender;
                    int i = 1;
                    String msg = PlaceholderAPI.setPlaceholders(player, text);
                    for (String holder : holderText) {
                        msg = msg.replaceAll("\\[" + i + "\\]", holder);
                        i++;
                    }
                    sender.sendMessage(GlobalConfig.getPrefix() + GlobalConfig.toColorString(msg));
                } else {
                    Player player = (Player) sender;
                    int i = 1;
                    String msg = text;
                    for (String holder : holderText) {
                        msg = msg.replaceAll("\\[" + i + "\\]", holder);
                        i++;
                    }
                    sender.sendMessage(GlobalConfig.getPrefix() + GlobalConfig.toColorString(PlaceholderAPI.setPlaceholders(player, msg)));
                }
            } else {
                int i = 1;
                String msg = text;
                for (String holder : holderText) {
                    msg = msg.replaceAll("\\[" + i + "\\]", holder);
                    i++;
                }
                sender.sendMessage(GlobalConfig.getPrefix() + GlobalConfig.toColorString(msg));
            }
        }
    }

    /**
     * 向指定玩家发送带前缀的聊天消息，支持PlaceholderAPI占位符
     * @param player 玩家
     * @param text 消息内容
     */
    public static void sendMessage(Player player, String text) {
        player.sendMessage(GlobalConfig.getPrefix() + GlobalConfig.toColorString(PlaceholderAPI.setPlaceholders(player, text)));
    }

    /**
     * 向指定玩家发送带前缀的聊天消息，用另外一个玩家作为PlaceholderAPI占位符
     * @param player 接收消息的玩家
     * @param holder 用作占位符的玩家
     * @param text 消息内容
     */
    public static void sendMessage(Player player, Player holder, String text) {
        player.sendMessage(GlobalConfig.getPrefix() + GlobalConfig.toColorString(PlaceholderAPI.setPlaceholders(holder, text)));
    }

    /**
     * 向指定玩家发送不带前缀的聊天消息，支持PlaceholderAPI占位符
     * @param player 玩家
     * @param text 消息内容
     */
    public static void sendNoPrefixMessage(Player player, String text) {
        player.sendMessage(GlobalConfig.toColorString(PlaceholderAPI.setPlaceholders(player, text)));
    }

    /**
     * 向所有玩家广播不带前缀的聊天消息
     * @param text 消息内容
     */
    public static void broadcastNoPrefixMessage(String text) {
        Bukkit.broadcastMessage(GlobalConfig.toColorString(text));
    }
    
    /**
     * 向所有玩家广播带前缀的聊天消息
     * @param text 消息内容
     */
    public static void broadcastMessage(String text) {
        Bukkit.broadcastMessage(GlobalConfig.getPrefix() + GlobalConfig.toColorString(text));
    }

    /**
     * 向所有玩家广播带前缀的聊天消息，支持PlaceholderAPI占位符
     * @param player 玩家
     * @param text 消息内容
     */
    public static void broadcastMessage(Player player, String text) {
        Bukkit.broadcastMessage(GlobalConfig.getPrefix() + GlobalConfig.toColorString(PlaceholderAPI.setPlaceholders(player, text)));
    }

    /**
     * 向有权限的玩家发送带前缀的聊天消息
     * @param permission 需要的权限
     * @param text 文本内容，其中PlaceholderAPI将分别对每个玩家进行单独的解析
     */
    public static void broadcastMessage(String permission, String text) {
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        for (Player player : players) {
            if (player.hasPermission(permission)) {
                sendMessage(player, text);
            }
        }
    }

    /**
     * 以指定玩家作为PlaceholderAPI占位符，向所有有权限的玩家发送带前缀的聊天消息
     * @param player 作为PlaceholderAPI占位符的玩家
     * @param permission 需要的权限
     * @param text 文本内容
     */
    public static void broadcastMessage(Player player, String permission, String text) {
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        for (Player p : players) {
            if (p.hasPermission(permission)) {
                sendMessage(p, PlaceholderAPI.setPlaceholders(player, text));
            }
        } 
    }

    /**
     * 用前缀和PlaceholderAPI格式化文本
     * @param player 用于PlaceholderAPI占位符解析的玩家
     * @param text 要格式化的文本
     * @return 格式化后的文本
     */
    public static String formatText(Player player, String text) {
        return GlobalConfig.getPrefix() + GlobalConfig.toColorString(PlaceholderAPI.setPlaceholders(player, text));
    }

    /**
     * 用前缀格式化文本
     * @param text 要格式化的文本
     * @return 格式化后的文本
     */
    public static String formatText(String text) {
        return GlobalConfig.getPrefix() + GlobalConfig.toColorString(text);
    }

    /**
     * 让控制台执行指令，支持Placeholder占位符
     * @param player 用作占位符的玩家
     * @param text 指令内容，不含开头斜杠
     */
    public static void executePlaceholderCommand(Player player, String text) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders(player, text));
    }

    /**
     * 让控制台代替指令发送者执行指令，如果指令发送者是玩家则使用Placeholder占位符
     * @param sender 指令发送者相关
     * @param text 指令内容，不含开头斜杠
     */
    public static void executeCommand(CommandSender sender, String text) {
        if (sender instanceof Player) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders((Player) sender, text));
        } else {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), text);
        }
    }

    /**
     * 让玩家自己执行指令，利用Placeholder占位符
     * @param player 执行指令的玩家
     * @param text 指令内容，不含开头斜杆
     */
    public static void performCommand(Player player, String text) {
        player.performCommand(PlaceholderAPI.setPlaceholders(player, text));
    }

    /**
     * 解析文本中的Placeholder占位符
     * @param player 玩家
     * @param text 文本
     * @return 解析后的文本
     */
    public static String setPapi(Player player, String text) {
        return PlaceholderAPI.setPlaceholders(player, text);
    }
}
