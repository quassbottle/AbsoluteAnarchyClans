package ru.quassbottle;

import org.bukkit.plugin.java.JavaPlugin;
import ru.quassbottle.commands.CommandManager;
import ru.quassbottle.events.listeners.ClanListener;
import ru.quassbottle.handlers.ClansHandler;
import ru.quassbottle.utils.LogService;

public class XProjectClans extends JavaPlugin {
    private static XProjectClans instance;
    private ClansHandler clansHandler;
    private LogService logService;
    private CommandManager commandManager;

    public ClansHandler getClansHandler() {
        return clansHandler;
    }

    public LogService getLogService() {
        return logService;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public static XProjectClans getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        this.clansHandler = new ClansHandler();
        this.logService = new LogService();
        this.commandManager = new CommandManager();
        this.getServer().getPluginCommand("clan").setExecutor(commandManager);
        this.getServer().getPluginManager().registerEvents(new ClanListener(), this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        //this.clansHandler.deserializeAll();
        super.onDisable();
    }

    /*private void createDefaultAirdrop() {
        if (defaultAirdropFile == null) {
            defaultAirdropFile = new File(airdropDirectory, "default.yml");
        }
        if (defaultAirdropFile.exists())
            return;
        defaultAirdropConfiguration = YamlConfiguration.loadConfiguration(defaultAirdropFile);
        Reader configStream = new InputStreamReader(AirdropsPlugin.getInstance().getResource("default.yml"), StandardCharsets.UTF_8);
        if (configStream != null) {
            YamlConfiguration streamConfig = YamlConfiguration.loadConfiguration(configStream);
            defaultAirdropConfiguration.setDefaults(streamConfig);
            try {
                streamConfig.save(defaultAirdropFile);
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    } // TODO
     */
}
