package me.craftinators.harbor.utilities;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;

public final class AdventureAPIUtil {
    public static final TagResolver AnchorTagResolver = TagResolver.resolver(TagResolver.standard());
    public static final MiniMessage SERIALIZER = MiniMessage.builder().tags(AnchorTagResolver).build();

    private AdventureAPIUtil() {}

    public static @NotNull TagResolver resolve(final @NotNull String string, final @NotNull Object tag) {
        return TagResolver.resolver(string, Tag.selfClosingInserting(AdventureAPIUtil.SERIALIZER.deserialize(String.valueOf(tag))));
    }
}
