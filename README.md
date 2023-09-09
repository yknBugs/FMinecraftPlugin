# FMinecraftPlugin

Minecraft 服务器插件，旨在给玩家提供额外的数据信息

## Plan

重构代码

## Project Function

- 射中实体消息提醒
- 实体死亡提醒
- 玩家死亡坐标提醒
- 玩家挂机提醒
- 生物群系改变提示
- 玩家被较多怪物围攻提示

## known issue

- 给实体重命名成包含配置文件中可用占位符字符的名称，部分情况下（例如实体死亡消息）会导致实体的名称作为占位符被异常解析

## TODO

需要定期清理 ServerData.entityKiller 集合提高运行效率
