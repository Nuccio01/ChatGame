package me.nuccio.chatgame.commands;

import me.nuccio.chatgame.Chatgame;
import me.nuccio.chatgame.managers.GameManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ChatGameCommand implements CommandExecutor, TabCompleter {

    private final Chatgame plugin;
    private final List<String> subCommands = Arrays.asList("math", "guess", "word", "random", "answer", "stop", "reload");

    public ChatGameCommand(Chatgame plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getConfig().getString("messages.only-players"));
            return true;
        }

        Player player = (Player) sender;
        GameManager gm = plugin.getGameManager();

        if (args.length == 0) {
            Component menu = Component.text("► Select a game: ", NamedTextColor.AQUA)
                    .append(Component.text("[ MATH ] ", NamedTextColor.YELLOW).clickEvent(ClickEvent.runCommand("/chatgame math")))
                    .append(Component.text("[ GUESS ] ", NamedTextColor.YELLOW).clickEvent(ClickEvent.runCommand("/chatgame guess")))
                    .append(Component.text("[ WORD ] ", NamedTextColor.YELLOW).clickEvent(ClickEvent.runCommand("/chatgame word")))
                    .append(Component.text("[ RANDOM ] ", NamedTextColor.YELLOW).clickEvent(ClickEvent.runCommand("/chatgame random")))
                    .append(Component.text("[ STOP ] ", NamedTextColor.RED).clickEvent(ClickEvent.runCommand("/chatgame stop")))
                    .append(Component.text("[ ANSWER ] ", NamedTextColor.GREEN).clickEvent(ClickEvent.runCommand("/chatgame answer")));

            player.sendMessage(menu);
            return true;
        }

        String subCommand = args[0].toLowerCase();

        // Controllo centralizzato: blocca l'avvio se un gioco è già in corso
        if (Arrays.asList("math", "guess", "word", "random").contains(subCommand)) {
            if (gm.getCurrentGame() != GameManager.GameType.NONE) {
                String alreadyRunningMsg = plugin.getConfig().getString("messages.game-already-running", "&cThere is already an active game running! Stop it first using /chatgame stop.");
                player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(alreadyRunningMsg));
                return true;
            }
        }

        switch (subCommand) {
            case "math":
                String expr = gm.startMath();
                broadcastGameConfigLines("messages.start-math", "%expression%", expr);
                break;
            case "guess":
                gm.startGuess();
                broadcastGameConfigLines("messages.start-guess", "", "");
                break;
            case "word":
                String scrambled = gm.startWord();
                broadcastGameConfigLines("messages.start-word", "%scrambled%", scrambled);
                break;
            case "random":
                // Cattura la stringa visiva generata dal GameManager (espressione o parola mescolata)
                String visualOutput = gm.startRandomGame();

                if (gm.getCurrentGame() == GameManager.GameType.MATH) {
                    broadcastGameConfigLines("messages.start-math", "%expression%", visualOutput);
                } else if (gm.getCurrentGame() == GameManager.GameType.GUESS) {
                    broadcastGameConfigLines("messages.start-guess", "", "");
                } else if (gm.getCurrentGame() == GameManager.GameType.WORD) {
                    broadcastGameConfigLines("messages.start-word", "%scrambled%", visualOutput);
                }
                break;
            case "answer":
                if (gm.getCurrentGame() == GameManager.GameType.NONE) {
                    player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(plugin.getConfig().getString("messages.no-game-running")));
                } else {
                    String msg = plugin.getConfig().getString("messages.current-answer").replace("%answer%", gm.getCurrentAnswer());
                    player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(msg));
                }
                break;
            case "stop":
                if (gm.getCurrentGame() == GameManager.GameType.NONE) {
                    player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(plugin.getConfig().getString("messages.no-game-running")));
                } else {
                    String msg = plugin.getConfig().getString("messages.game-stopped").replace("%player%", player.getName());
                    gm.stopGame();
                    Bukkit.broadcast(LegacyComponentSerializer.legacyAmpersand().deserialize(msg));
                }
                break;
            case "reload":
                if (player.hasPermission("chatgame.start")) {
                    plugin.reloadPluginConfig();
                    String reloadMsg = plugin.getConfig().getString("messages.plugin-reloaded", "&aConfiguration reloaded successfully!");
                    player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(reloadMsg));
                } else {
                    player.sendMessage(Component.text("You do not have permission to execute this command.", NamedTextColor.RED));
                }
                break;
            default:
                player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(plugin.getConfig().getString("messages.invalid-args")));
                break;
        }
        return true;
    }

    private void broadcastGameConfigLines(String path, String placeholder, String replacement) {
        List<String> lines = plugin.getConfig().getStringList(path);
        for (String line : lines) {
            if (!placeholder.isEmpty()) {
                line = line.replace(placeholder, replacement);
            }
            Bukkit.broadcast(LegacyComponentSerializer.legacyAmpersand().deserialize(line));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return subCommands.stream()
                    .filter(sub -> sub.startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}