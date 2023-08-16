package ir.sharif.math.zahraSoukhtedel.controller.bot;

import ir.sharif.math.zahraSoukhtedel.util.Config;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Bot {
    private final Class<?> target;
    private final Object botObject;

    public Bot(Class<?> target, Object botObject) {
        this.target = target;
        this.botObject = botObject;
    }

    public void start() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method startMethod = target.getDeclaredMethod(Config.getConfig("bot").getProperty("startMethod"));
        startMethod.setAccessible(true);
        startMethod.invoke(botObject);
    }

    public void remove() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method removeMethod = target.getDeclaredMethod(Config.getConfig("bot").getProperty("removeMethod"));
        removeMethod.setAccessible(true);
        removeMethod.invoke(botObject);
    }
}
