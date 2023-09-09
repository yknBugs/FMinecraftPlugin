# FMinecraftPlugin

Minecraft Spigot 服务器插件，旨在给玩家提供额外的数据信息

## 插件功能

- 允许自定义几乎所有显示的消息的内容
- 射中生物消息提醒
- 生物死亡提醒
- 玩家死亡坐标提醒
- 玩家挂机提醒
- 生物群系改变提示
- 玩家被较多怪物围攻提示
- 玩家与高生命值的生物战斗时提示

## 插件指令

- /f 显示插件的版本
- /f reload 重载插件

## 编译

1.克隆本仓库到本地

```bash
git clone https://github.com/yknBugs/FMinecraftPlugin
```

2.下载编译所需要的[依赖库](https://hub.spigotmc.org/nexus/content/repositories/snapshots/org/spigotmc/spigot-api/1.19.4-R0.1-SNAPSHOT/spigot-api-1.19.4-R0.1-20230531.075921-84-shaded.jar)文件

3.使用任意IDE (例如VSCode) 打开整个项目文件夹，然后将[依赖库](https://hub.spigotmc.org/nexus/content/repositories/snapshots/org/spigotmc/spigot-api/1.19.4-R0.1-SNAPSHOT/spigot-api-1.19.4-R0.1-20230531.075921-84-shaded.jar)文件添加到项目依赖

4.编译，注意不要将依赖库文件包含在最终输出的.jar文件中

## 已知问题

- 给实体重命名成包含配置文件中可用占位符字符的名称，部分情况下（例如实体死亡消息）会导致实体的名称作为占位符被异常解析
