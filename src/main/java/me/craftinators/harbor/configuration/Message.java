package me.craftinators.harbor.configuration;

import me.craftinators.harbor.HarborPlugin;
import me.craftinators.harbor.utilities.AdventureAPIUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Contains methods for sending configurable messages.
 */
public enum Message {
    INVALID_PATH("configuration.invalid_path"),
    UPDATED_OPTION("configuration.updated_option"),
    SAVE_ERROR("configuration.save_error");

    private final String path;

    Message(final @NotNull String path) {
        this.path = path;
    }

    /**
     * Sends the message to the group of specified {@link CommandSender}s with optional {@link TagResolver}.
     * @param senders The {@link CommandSender}s to send the message to
     * @param placeholders Optional {@link TagResolver} for message formatting
     */
    public void send(final @NotNull Iterable<? extends CommandSender> senders, final @NotNull TagResolver... placeholders) {
        String message = HarborPlugin.get().getConfigurationManager().getMessagesConfiguration().getString(path);
        if (message == null) {
            Message.INVALID_PATH.log(AdventureAPIUtil.resolve("path", path));
            return;
        }
        Component component = AdventureAPIUtil.SERIALIZER.deserialize(message, placeholders);
        senders.forEach(sender -> sender.sendMessage(component));
    }

    /**
     * Sends the message to the specified {@link CommandSender} with optional {@link TagResolver}.
     * @param sender The {@link CommandSender} to send the message to
     * @param placeholders Optional {@link TagResolver} for message formatting
     */
    public void send(final @NotNull CommandSender sender, final @NotNull TagResolver... placeholders) {
        send(List.of(sender), placeholders);
    }

    /**
     * Sends the message to the console with optional {@link TagResolver}.
     * @param placeholders Optional {@link TagResolver} for message formatting
     */
    public void log(final @NotNull TagResolver... placeholders) {
        send(Bukkit.getConsoleSender(), placeholders);
    }
}
