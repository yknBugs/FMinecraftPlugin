# FMinecraftPlugin

A Minecraft Bukkit Server Plugin, providing various functions and games.

## Plan

暂时取消gui动态图标，后续尝试实现利用占位符功能实现

holderText 为正则表达式时会抛出异常

## Project Function

- GUI Command Executor
- Dark-Forest Game
- Auto Broadcast
- Chat Watchdog
- Mob Death Message
- Home Set And Death Position
- Entity Limit
- Shoot Message
- Idle Time Out
- Surround By Monster Message

## Project Structure

GUI

CommandGUI extends GUI

ShopGUI extends GUI

HomeGUI extends GUI

Data

EntityData extends Data

PlayerData extends EntityDta

BlockData extends Data

ServerData extends Data

\<EventListener\>

Cmd

TabCmd

FCommand

\<Command Name\> extends Cmd

Msg

BossbarMsg extends Msg

ActionbarMsg extends Msg

ChatMsg extends Msg

Util

Scheduler

Config
