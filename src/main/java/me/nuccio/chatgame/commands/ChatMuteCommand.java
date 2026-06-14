package me.nuccio.chatgame.commands;

import me.nuccio.chatgame.Chatgame;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ChatMuteCommand implements CommandExecutor {

    private final Chatgame plugin;

    public ChatMuteCommand(Chatgame plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Se non ha i permessi, usciamo subito restituendo true per evitare i messaggi di "usage"
        if (!sender.hasPermission("chatgame.mute")) {
            // Nota: se non mandi un messaggio qui, ci penserà Bukkit a dire "I'm sorry, but you do not have permission..."
            // in base a come è configurato il plugin.yml.
            return true;
        }

        boolean newState = !plugin.isChatMuted();
        plugin.setChatMuted(newState);

        String path = newState ? "messages.chat-muted-broadcast" : "messages.chat-unmuted-broadcast";
        String broadcastMsg = plugin.getConfig().getString(path).replace("%player%", sender.getName());

        Bukkit.broadcast(LegacyComponentSerializer.legacyAmpersand().deserialize(broadcastMsg));
        return true;
    }
}