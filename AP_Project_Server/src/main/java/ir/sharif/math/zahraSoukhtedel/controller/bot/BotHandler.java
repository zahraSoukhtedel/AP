package ir.sharif.math.zahraSoukhtedel.controller.bot;

import ir.sharif.math.zahraSoukhtedel.util.Config;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class BotHandler {

    List<Bot> bots = new ArrayList<>();

    public void loadBot(String jarUrl) throws ClassNotFoundException, MalformedURLException,
            NoSuchMethodException, InvocationTargetException, IllegalAccessException,
            InstantiationException {
        URL url = new URL(jarUrl);
        URL[] urls = new URL[]{url};
        ClassLoader loader = new URLClassLoader(urls);
        Class<?> target = loader.loadClass(Config.getConfig("bot").getProperty("mainClass"));
        Object botObject = target.getDeclaredConstructor().newInstance();
        Bot bot = new Bot(target, botObject);
        bots.add(bot);
        bot.start();
    }

    public void removeBots() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        for (Bot bot : bots)
            bot.remove();
        bots.clear();
    }
}
