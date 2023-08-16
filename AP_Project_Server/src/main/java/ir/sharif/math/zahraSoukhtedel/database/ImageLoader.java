package ir.sharif.math.zahraSoukhtedel.database;

import ir.sharif.math.zahraSoukhtedel.util.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ImageLoader {
    public final static Integer DEFAULT_AVATAR_ID = 1;
    private final File dbDirectory = Config.getConfig("serverConfig").getProperty(Config.class,"main").getProperty(File.class, "imageLoader");
    static private final Logger logger = LogManager.getLogger(ImageLoader.class);

    private Integer getFreeID() {
        File[] files = dbDirectory.listFiles();
        Integer id = 1;
        while (true) {
            boolean found = false;
            for (File file : Objects.requireNonNull(files))
                if(file.getName().equals(id + ".png"))
                    found = true;
            if(!found)
                return id;
            else
                id++;
        }
    }

    public BufferedImage getByID(Integer id) {
        if(id == null)
            return null;
        File data = new File(dbDirectory, id + ".png");
        BufferedImage image;
        try {
            image = ImageIO.read(data);
            logger.info(String.format("read the image from file %s", data.getName()));
            return image;
        } catch (IOException e) {
            logger.info(String.format("exception occurred while loading image %s", data.getName()));
            e.printStackTrace();
        }
        return null;
    }

    public Integer saveIntoDB(BufferedImage image) {
        Integer id = getFreeID();
        File data = null;
        try {
            data = new File(dbDirectory, id + ".png");
            ImageIO.write(image, "png", data);
            logger.info(String.format("saved image %s", data.getName()));
            return id;
        } catch (IOException e) {
            logger.warn(String.format("exception occurred while saving image %s", data.getName()));
            e.printStackTrace();
        }
        return null;
    }
}
