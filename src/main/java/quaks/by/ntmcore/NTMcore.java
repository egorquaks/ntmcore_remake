package quaks.by.ntmcore;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import github.scarsz.discordsrv.DiscordSRV;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import quaks.by.ntmcore.commands.*;
import quaks.by.ntmcore.files.*;
import quaks.by.ntmcore.listeners.*;
import quaks.by.ntmcore.listeners.chat.*;
import quaks.by.ntmcore.listeners.scheduleTimers.ScheduledBanMuteRoleRemover;

public final class NTMcore extends JavaPlugin {

    static NTMcore main; // Экземпляр Ntmcore
    public static NTMcore getInstance(){
        return main;
    } // Это нужно для того, чтобы можно было получить экземпляр Ntmcore (переменная main) из других классов и юзать приколы из JavaPlugin
    private DiscordSRVListener discordsrvListener = new DiscordSRVListener(this); // Переменная типа DiscordSRVListener. Использование см. ниже
    public static ProtocolManager protocolManager;

    public static Scoreboard scoreboard;
    public static Team freeze_team;
    public static Team team;

    @Override
    public void onEnable() {

        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        team = scoreboard.registerNewTeam("hide_nametag");
        team.setNameTagVisibility(NameTagVisibility.NEVER);
        team.setCanSeeFriendlyInvisibles(false);
        freeze_team = scoreboard.registerNewTeam("freeze_tag");
        freeze_team.setNameTagVisibility(NameTagVisibility.NEVER);
        freeze_team.setCanSeeFriendlyInvisibles(false);

        ScheduledBanMuteRoleRemover scheduledBanMuteRoleRemover = new ScheduledBanMuteRoleRemover();
        scheduledBanMuteRoleRemover.scheduleTimer(this, Bukkit.getWorld("world"));

        protocolManager = ProtocolLibrary.getProtocolManager();

        WarnList.setup();
        SpectatorHistory.setup();
        RoleList.setup();
        AphList.setup();
        MuteList.setup();// Подключаем файл в систему

        getCommand("autograph").setExecutor(new AutographCMD());// Регистрация команды
        getCommand("do").setExecutor(new DoCMD());
        getCommand("gdo").setExecutor(new GdoCMD());
        getCommand("me").setExecutor(new MeCMD());
        getCommand("gme").setExecutor(new GmeCMD());
        getCommand("mute").setExecutor(new MuteCMD());
        getCommand("report").setExecutor(new ReportCMD());
        getCommand("toggleglobalchat").setExecutor(new ToggleGlobalChatCMD());
        getCommand("msg").setExecutor(new TellCMD());
        getCommand("reply").setExecutor(new ReplyCMD());
        getCommand("whisper").setExecutor(new WhisperCMD());
        getCommand("spectator").setExecutor(new SpectatorCMD());
        getCommand("protect").setExecutor(new ProtectCMD());
        getCommand("ban").setExecutor(new BanCMD());
        getCommand("warn").setExecutor(new WarnCMD());
        getCommand("ignore").setExecutor(new IgnoreCMD());
        getCommand("god").setExecutor(new GodCMD());
        getCommand("freeze").setExecutor(new FreezeCMD());

        DiscordSRV.api.subscribe(discordsrvListener); // Подсос к прослушиванию Discord

        getServer().getPluginManager().registerEvents(new LinkListener(), this); // Регистрация проверки связи аккаунта с Discord
        getServer().getPluginManager().registerEvents(new ChatLimiter(), this);
        getServer().getPluginManager().registerEvents(new ChatCapsFixer(), this);
        getServer().getPluginManager().registerEvents(new VanillaChatDisabler(), this);
        getServer().getPluginManager().registerEvents(new GlobalChatListener(), this);
        getServer().getPluginManager().registerEvents(new AdminChatListener(), this);
        getServer().getPluginManager().registerEvents(new TradeChatListener(), this);
        getServer().getPluginManager().registerEvents(new LocalChatListener(), this);
        getServer().getPluginManager().registerEvents(new JoinTabUpdate(), this);
        getServer().getPluginManager().registerEvents(new ProtectListener(), this);
        getServer().getPluginManager().registerEvents(new GodListener(), this);
        getServer().getPluginManager().registerEvents(new FreezeListener(), this);
        getServer().getPluginManager().registerEvents(new NameTagHideListener(), this);
        getServer().getPluginManager().registerEvents(new ClickOnPlayerEventListener(), this);
        getServer().getPluginManager().registerEvents(new DeathListener(), this);


        getLogger().info("NTM - Основной плагин запущен");
    } // onEnable выполняется когда, запускается плагин
    @Override
    public void onDisable() {
        DiscordSRV.api.unsubscribe(discordsrvListener);// Отсос от прослушивания Discord
    } // onDisable выполняется когда, останавливается плагин
}
