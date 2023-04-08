package com.gb;

import io.netty.buffer.Unpooled;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.protocol.packet.PluginMessage;

public class PassProxyListener implements Listener {

    private final Plugin plugin;

    public PassProxyListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPluginMessage(PluginMessageEvent event) {
        if (event.getTag().equals("PASSPROXY")) {
            Server server = ((ProxiedPlayer) event.getReceiver()).getServer();
            byte[] data = event.getData();
            ServerInfo serverInfo = plugin.getProxy().getServerInfo(server.getInfo().getName());
            serverInfo.sendData("PASSPROXY", data);
        }
    }
}
