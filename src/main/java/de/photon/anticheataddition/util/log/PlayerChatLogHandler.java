package de.photon.anticheataddition.util.log;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * A log handler that sends FINER level messages to all online players in the chat.
 * This allows regular players to see detailed debug information without needing debug permissions.
 */
final class PlayerChatLogHandler extends Handler
{
    private static final Formatter PLAYER_CHAT_FORMATTER = new Formatter()
    {
        @Override
        public String format(LogRecord logRecord)
        {
            // Add a prefix to distinguish these messages
            return "§7[ACA-Debug] §f" + formatMessage(logRecord);
        }
    };

    public PlayerChatLogHandler(Level level)
    {
        this.setFormatter(PLAYER_CHAT_FORMATTER);
        this.setLevel(level);
    }

    @Override
    public void publish(LogRecord logRecord)
    {
        if (!isLoggable(logRecord)) return;

        final var msg = getFormatter().format(logRecord);
        // Send message to all online players
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(msg);
        }
    }

    @Override
    public void flush()
    {
        // We do not buffer.
    }

    @Override
    public void close() throws SecurityException
    {
        // Not necessary for chat.
    }
}
