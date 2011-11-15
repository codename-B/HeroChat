/**
 * Copyright (C) 2011 David Thielke <dave.thielke@gmail.com>
 **/

package com.dthielke.herochat.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.dthielke.herochat.Channel;
import com.dthielke.herochat.ChannelManager;
import com.dthielke.herochat.Chatter;
import com.dthielke.herochat.Chatter.Result;
import com.dthielke.herochat.HeroChat;
import com.dthielke.herochat.command.BasicCommand;
import com.dthielke.herochat.util.Messaging;

public class FocusCommand extends BasicCommand {

    public FocusCommand() {
        super("Focus");
        setDescription("Changes your active channel");
        setUsage("/ch §8<channel>");
        setArgumentRange(1, 1);
        setIdentifiers("ch");
    }

    @Override
    public boolean execute(CommandSender sender, String identifier, String[] args) {
        if (!(sender instanceof Player))
            return true;
        Player player = (Player) sender;

        ChannelManager channelMngr = HeroChat.getChannelManager();
        Channel channel = channelMngr.getChannel(args[0]);
        if (channel == null) {
            Messaging.send(sender, "Channel not found.");
            return true;
        }

        Chatter chatter = HeroChat.getChatterManager().getChatter(player);
        if (!chatter.hasChannel(channel)) {
            Result result = chatter.canJoin(channel);
            switch (result) {
                case NO_PERMISSION:
                    Messaging.send(sender, "Insufficient permission.");
                    return true;
                case BANNED:
                    Messaging.send(sender, "You are banned from this channel.");
                    return true;
            }
            chatter.addChannel(channel);
        }
        
        chatter.setActiveChannel(channel);
        Messaging.send(player, "Active channel changed.");

        return true;
    }

}