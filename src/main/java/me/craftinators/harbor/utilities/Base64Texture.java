package me.craftinators.harbor.utilities;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

public enum Base64Texture {
    ;

    private final String base64;

    Base64Texture(String base64) {
        this.base64 = base64;
    }

    public @NotNull ItemStack getTexturedHead() {
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta playerHeadMeta = (SkullMeta) playerHead.getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), "");
        profile.getProperties().put("textures", new Property("textures", base64));

        try {
            Method methodSetProfileMethod = playerHeadMeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
            methodSetProfileMethod.setAccessible(true);
            methodSetProfileMethod.invoke(playerHeadMeta, profile);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException methodExceptions) {
            try {
                Field metaProfileField = playerHeadMeta.getClass().getDeclaredField("profile");
                metaProfileField.setAccessible(true);
                metaProfileField.set(playerHeadMeta, profile);
            } catch (NoSuchFieldException | IllegalAccessException fieldExceptions) {
                fieldExceptions.printStackTrace();
            }
        }

        playerHead.setItemMeta(playerHeadMeta);
        return playerHead;
    }
}
