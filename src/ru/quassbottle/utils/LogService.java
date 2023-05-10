package ru.quassbottle.utils;

import ru.quassbottle.XProjectClans;

public class LogService {
    private String prefix = XPClansUtils.getFormat("&8[&6XPClans&8] &r");

    public void logNone(String s) {
        logBy(s, LogType.NONE);
    }

    public void logBy(String s, LogType type) {
        XProjectClans.getInstance().getServer().getConsoleSender().sendMessage(XPClansUtils.getFormat(prefix + type.msg + s));
    }

    public static enum LogType {
        NONE(""),
        CLANS_HANDLER("&8[&aClansHandler&8] &r"),
        WARNING("&4&l[WARNING] &r"),
        CLANS_DESERIALIZER("&8[&aClansDeserializer&8] &r");

        private final String msg;
        LogType(String s) {
            msg = s;
        }

        public String getMsg() {
            return msg;
        }
    }
}
