package me.nuccio.chatgame.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.nuccio.chatgame.Chatgame;
import me.nuccio.chatgame.managers.GameManager;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ChatListener implements Listener {

    private final Chatgame plugin;

    public ChatListener(Chatgame plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onChat(AsyncChatEvent e) {
        Player player = e.getPlayer();
        String message = PlainTextComponentSerializer.plainText().serialize(e.message()).trim();

        if (plugin.isChatMuted() && !player.hasPermission("chatgame.bypassmute")) {
            e.setCancelled(true);
            String msg = plugin.getConfig().getString("messages.chat-currently-muted");
            player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(msg));
            return;
        }

        GameManager gm = plugin.getGameManager();
        if (gm.getCurrentGame() != GameManager.GameType.NONE) {
            if (message.equalsIgnoreCase(gm.getCurrentAnswer())) {
                e.setCancelled(true);

                long elapsedSeconds = (System.currentTimeMillis() - gm.getStartTime()) / 1000;
                String configKey = "messages.win-" + gm.getCurrentGame().name().toLowerCase();
                String winMessage = plugin.getConfig().getString(configKey)
                        .replace("%player%", player.getName())
                        .replace("%time%", String.valueOf(elapsedSeconds))
                        .replace("%answer%", gm.getCurrentAnswer());

                gm.stopGame();

                Bukkit.getScheduler().runTask(plugin, () ->
                        Bukkit.broadcast(LegacyComponentSerializer.legacyAmpersand().deserialize(winMessage))
                );
            }
        }
    }
}