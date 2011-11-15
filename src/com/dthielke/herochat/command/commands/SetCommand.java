/**
 * Copyright (C) 2011 David Thielke <dave.thielke@gmail.com>
 **/

package com.dthielke.herochat.command.commands;

import org.bukkit.command.CommandSender;

import com.dthielke.herochat.Channel;
import com.dthielke.herochat.ChannelManager;
import com.dthielke.herochat.HeroChat;
import com.dthielke.herochat.command.BasicCommand;
import com.dthielke.herochat.util.Messaging;

public class SetCommand extends BasicCommand {

    public SetCommand() {
        super("Set Channel Setting");
        setDescription("Modifies a setting of an existing channel");
        setUsage("/ch set §8<channel> <setting> <value>");
        setArgumentRange(3, 3);
        setIdentifiers("ch set");
    }

    @Override
    public boolean execute(CommandSender sender, String identifier, String[] args) {
        String name = args[0];
        String setting = args[1];
        String value = args[2];

        ChannelManager channelMngr = HeroChat.getChannelManager();
        Channel channel = channelMngr.getChannel(name);
        if (channel == null) {
            Messaging.send(sender, "Channel not found.");
            return true;
        }

        if (setting.equalsIgnoreCase("name")) {
            if (channelMngr.hasChannel(value)) {
                Messaging.send(sender, "Identifier taken.");
            } else {
                channel.setName(value);
                Messaging.send(sender, "Name changed.");
            }
        } else if (setting.equalsIgnoreCase("nick")) {
            if (channelMngr.hasChannel(value)) {
                Messaging.send(sender, "Identifier taken.");
            } else {
                channel.setNick(value);
                Messaging.send(sender, "Nick changed.");
            }
        } else if (setting.equalsIgnoreCase("format")) {
            channel.setFormat(value);
            Messaging.send(sender, "Format changed.");
        } else if (setting.equalsIgnoreCase("distance")) {
            try {
                int distance = Integer.parseInt(value);
                channel.setDistance(distance);
                Messaging.send(sender, "Distance changed.");
            } catch (NumberFormatException e) {
                Messaging.send(sender, "Invalid distance.");
            }
        }

        return true;
    }

}