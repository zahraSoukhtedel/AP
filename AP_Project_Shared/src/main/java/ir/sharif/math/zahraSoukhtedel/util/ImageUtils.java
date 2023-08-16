package ir.sharif.math.zahraSoukhtedel.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class ImageUtils {

    public String toString(BufferedImage bufferedImage, String format) throws IOException {
        if(bufferedImage == null)
            return null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, format, byteArrayOutputStream);
        return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
    }

    public BufferedImage toBufferedImage(String encoded) throws IOException {
        if(encoded == null)
            return null;
        byte[] bytes = Base64.getDecoder().decode(encoded);
        InputStream inputStream = new ByteArrayInputStream(bytes);
        return ImageIO.read(inputStream);
    }
}

