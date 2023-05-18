package com.ykn.fplugin.command;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.ykn.fplugin.config.command.CmdHelpConfig;
import com.ykn.fplugin.config.command.CmdRunConfig;
import com.ykn.fplugin.util.BroadcastInfo;

public class HelpCmd extends FCommand {

    public HelpCmd(CommandSender sender, Command command, String label, String[] args, int index) {
        super(sender, command, label, args, index);
    }

    private boolean isCommandAvailable(String cmd) {
        if (cmd.equalsIgnoreCase("version")) {
            return true;
        }

        if (CmdHelpConfig.isCmdActive(cmd)) {
            if (CmdHelpConfig.showNoPermission()) {
                return true;
            } else {
                return this.hasPermission("fplugin.f." + cmd);
            }
        }
        return false;
    }

    private List<String> getAvailableCmdList() {
        List<String> result = new LinkedList<String>();
        List<String> rawCmd = CmdHelpConfig.commandList();
        for (String cmd : rawCmd) {
            if (this.isCommandAvailable(cmd)) {
                result.add(cmd);
            }
        }

        return result;
    }

    private List<String> getCustomAvailableCmdList() {
        List<String> result = new LinkedList<String>();
        List<String> rawCmd = CmdRunConfig.customCommandList();
        if ((!this.hasPermission("fplugin.f.run")) && (!CmdHelpConfig.showNoPermission())) {
            return result;
        }
        for (String cmd : rawCmd) {
            if (this.hasPermission("fplugin.f.run." + cmd) || CmdHelpConfig.showNoPermission()) {
                result.add(cmd);
            }
        }
        return result;
    }

    private void showLegalPage(int page, int maxPage, int maxPerPage, List<String> helpList) {
        String[] holders = { Integer.toString(page), Integer.toString(maxPage) };
        BroadcastInfo.replyMessage(this.sender, true, CmdHelpConfig.firstLine(), holders);

        int i = 0;
        for (String str : helpList) {
            i++;
            if (i > page * maxPerPage - maxPerPage && i <= page * maxPerPage) {
                BroadcastInfo.replyMessage(this.sender, false, "/f " + str + " " + CmdHelpConfig.cmdHelpText(str), null);
            }
        }
    }

    private void showCustomLegalPage(int page, int maxPage, int maxPerPage, List<String> helpList) {
        int i = 0;
        for (String str : helpList) {
            i++;
            if (i > page * maxPerPage - maxPerPage && i <= page * maxPerPage) {
                BroadcastInfo.replyMessage(this.sender, false, "/f run " + str + " " + CmdRunConfig.help(str), null);
            }
        }
    }

    private boolean showHelpList() {
        if (!this.isActive()) {
            String[] holders = { "/f " + this.args[0] };
            BroadcastInfo.replyMessage(this.sender, true, CmdHelpConfig.cmdWrongParam(), holders);
            return false;
        }
        List<String> helpList = this.getAvailableCmdList();
        int maxPerPage = CmdHelpConfig.maxPerPage();
        int maxPage = (helpList.size() - 1) / maxPerPage + 1;

        if (this.args.length == 1) {
            this.showLegalPage(1, maxPage, maxPerPage, helpList);
            return true;
        }
        if (this.args[1].matches("^-?[0-9]{1,5}$")) {
            //输入了的页数
            int page = Integer.valueOf(this.args[1]);
            if (page > maxPage) {
                String[] holders = { Integer.toString(page), Integer.toString(maxPage) };
                BroadcastInfo.replyMessage(this.sender, true, CmdHelpConfig.pageTooLarge(), holders);
                return false;
            } 
            if (page <= 0) {
                String[] holders = { Integer.toString(page) };
                BroadcastInfo.replyMessage(this.sender, true, CmdHelpConfig.pageTooSmall(), holders);
                return false;
            } 
            this.showLegalPage(page, maxPage, maxPerPage, helpList);
            return true;
        } 
        //输入了指令名称
        for (String str : helpList) {
            if (str.equalsIgnoreCase(this.args[1])) {
                if (str.equalsIgnoreCase("run")) {
                    //显示自定义指令的帮助
                    return this.showCustomHelpList();
                }
                BroadcastInfo.replyMessage(this.sender, false, "/f " + str + " " + CmdHelpConfig.cmdHelpText(str), null);
                return true;
            }
        }
        String[] holders = { "/f " + this.args[1] };
        BroadcastInfo.replyMessage(this.sender, true, CmdHelpConfig.wrongPage(), holders);
        return false;
    }

    private boolean showCustomHelpList() {
        List<String> helpList = this.getCustomAvailableCmdList();
        int maxPerPage = CmdHelpConfig.maxPerPage();
        int maxPage = (helpList.size() - 1) / maxPerPage + 1;

        if (this.args.length == 2 || helpList.size() == 0) {
            if (helpList.size() == 0) {
                BroadcastInfo.replyMessage(this.sender, false, "/f run " + CmdHelpConfig.cmdHelpText("run"), null);
                return true;
            }
            String[] holders = { "1", Integer.toString(maxPage) };
            BroadcastInfo.replyMessage(this.sender, true, CmdHelpConfig.firstLine(), holders);
            this.showCustomLegalPage(1, maxPage, maxPerPage, helpList);
            return true;
        } else if (this.args[2].matches("^-?[0-9]{1,5}$")) {
            //输入了的页数
            int page = Integer.valueOf(this.args[2]);
            if (page > maxPage) {
                String[] holders = { Integer.toString(page), Integer.toString(maxPage) };
                BroadcastInfo.replyMessage(this.sender, true, CmdHelpConfig.pageTooLarge(), holders);
                return false;
            }
            if (page <= 0) {
                String[] holders = { Integer.toString(page) };
                BroadcastInfo.replyMessage(this.sender, true, CmdHelpConfig.pageTooSmall(), holders);
                return false;
            } 
            this.showCustomLegalPage(page, maxPage, maxPerPage, helpList);
            return true;      
        }
        //输入了指令名称
        BroadcastInfo.replyMessage(this.sender, false, "/f run " + CmdHelpConfig.cmdHelpText("run"), null);
        for (String str : helpList) {
            if (str.equalsIgnoreCase(this.args[2])) {
                BroadcastInfo.replyMessage(this.sender, false, "/f run " + str + " " + CmdRunConfig.help(str), null);
                return true;
            }
        }
        String[] holders = { "/f run " + this.args[2] };
        BroadcastInfo.replyMessage(this.sender, true, CmdHelpConfig.wrongPage(), holders);
        return false;
    }

    private List<String> getNextCustomParamList() {
        List<String> result = this.getCustomAvailableCmdList();
        int maxPerPage = CmdHelpConfig.maxPerPage();
        int maxPage = (result.size() - 1) / maxPerPage + 1;
        if (result.size() == 0) {
            return result;
        }
        for (int i = 1; i <= maxPage; i++) {
            result.add(Integer.toString(i));
        }
        return result;
    }

    @Override
    public List<String> getNextParamList() {
        List<String> result = this.getAvailableCmdList();
        int maxPerPage = CmdHelpConfig.maxPerPage();
        int maxPage = (result.size() - 1) / maxPerPage + 1;
        for (int i = 1; i <= maxPage; i++) {
            result.add(Integer.toString(i));
        }
        return result;
    }

    @Override
    public List<String> onTabComplete(String permissionNeed) {
        if (this.args.length == 0) {
            return null;
        } else if (!this.hasPermission(permissionNeed) || !this.isActive()) {
            return new ArrayList<String>();
        } else if (this.args.length == 2) {
            return this.getNextParamList();
        } else if (this.args.length == 3 && this.args[1].equalsIgnoreCase("run")) {
            return this.getNextCustomParamList();
        } else {
            return new ArrayList<String>();
        }
    }

    @Override
    public boolean isActive() {
        return CmdHelpConfig.isCmdActive("help");
    }

    @Override
    public boolean onConsoleExecute() {
        return this.showHelpList();
    }

    @Override
    public boolean onPlayerLegalExecute() {
        return this.showHelpList();
    }
    
}
