package me.craftinators.harbor.utilities;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.UUID;

public final class Base64TextureUtil {
    private Base64TextureUtil() {}

    public static @NotNull ItemStack getTexturedHeadFromBase64(final @NotNull String base64) {
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta playerHeadMeta = (SkullMeta) playerHead.getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), "");
        profile.getProperties().put("textures", new Property("textures", base64));

        try {
            Field metaProfileField = playerHeadMeta.getClass().getDeclaredField("profile");
            metaProfileField.setAccessible(true);
            metaProfileField.set(playerHeadMeta, profile);
        } catch (NoSuchFieldException | IllegalAccessException fieldExceptions) {
            fieldExceptions.printStackTrace();
        }

        playerHead.setItemMeta(playerHeadMeta);
        return playerHead;
    }
}
