package me.nuccio.chatgame;

import me.nuccio.chatgame.commands.ChatGameCommand;
import me.nuccio.chatgame.commands.ChatMuteCommand;
import me.nuccio.chatgame.listeners.ChatListener;
import me.nuccio.chatgame.managers.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Chatgame extends JavaPlugin {

    private GameManager gameManager;
    private boolean chatMuted = false;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.gameManager = new GameManager(this);

        ChatGameCommand chatGameCommand = new ChatGameCommand(this);
        getCommand("chatgame").setExecutor(chatGameCommand);
        getCommand("chatgame").setTabCompleter(chatGameCommand);
        getCommand("chatmute").setExecutor(new ChatMuteCommand(this));

        Bukkit.getPluginManager().registerEvents(new ChatListener(this), this);
    }

    @Override
    public void onDisable() {
    }

    public void reloadPluginConfig() {
        reloadConfig();
        this.gameManager.loadWords();
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public boolean isChatMuted() {
        return chatMuted;
    }

    public void setChatMuted(boolean chatMuted) {
        this.chatMuted = chatMuted;
    }
}