package io.github.tivecs.theolib.util.item;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ItemStackConvert {

    public static String toBase64(ItemStack item){
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            BukkitObjectOutputStream os = new BukkitObjectOutputStream(bos);

            os.writeObject(item);

            os.close();
            return Base64Coder.encodeLines(bos.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ItemStack fromBase64(String encodedString){
        try {
            byte[] decoded = Base64Coder.decodeLines(encodedString);
            ByteArrayInputStream bis = new ByteArrayInputStream(decoded);
            BukkitObjectInputStream is = new BukkitObjectInputStream(bis);

            return (ItemStack) is.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
