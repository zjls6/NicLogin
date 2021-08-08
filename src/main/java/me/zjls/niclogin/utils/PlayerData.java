package me.zjls.niclogin.utils;

import lombok.Getter;
import me.zjls.niclogin.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
public class PlayerData {

    private Main plugin;

    private List<UUID> editUsernamePlayers = new ArrayList<>();
    private List<UUID> editPasswordPlayers = new ArrayList<>();

    private List<UUID> saveUsernamePlayers = new ArrayList<>();
    private List<UUID> savePasswordPlayers = new ArrayList<>();

    private Map<UUID, String> playerUsernameMap = new HashMap<>();
    private Map<UUID, String> playerPasswordMap = new HashMap<>();

    private List<UUID> loginPlayers = new ArrayList<>();

    public PlayerData(Main plugin) {
        this.plugin = plugin;
    }

    public boolean isLogged(Player p) {
        return getLoginPlayers().contains(p.getUniqueId());
    }

    public String getUsername(Player p) {
        return getPlayerUsernameMap().getOrDefault(p.getUniqueId(), "");
    }

    public String getPassword(Player p) {
        return getPlayerPasswordMap().getOrDefault(p.getUniqueId(), "");
    }

    public void sendLoginMessage(Player p) {

        for (int i = 0; i < 100; i++) {
            p.sendMessage("");
        }

        TextComponent username = new TextComponent(Color.str("&#76c893用户名： "));
        TextComponent nameInput = new TextComponent(Color.str("&#ffe8d6&n" + plugin.getPlayerData().getPlayerUsernameMap().getOrDefault(p.getUniqueId(), "点此输入")));
        nameInput.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(Color.str("&7点击后直接在聊天框内输入&6用户名"))));
        nameInput.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/l username"));
        username.addExtra(nameInput);

        TextComponent password = new TextComponent(Color.str("&#76c893密码： "));
        TextComponent passInput = new TextComponent(Color.str("&#ffe8d6&n&k" + plugin.getPlayerData().getPlayerPasswordMap().getOrDefault(p.getUniqueId(), "&#ffe8d6&n点此输入")));
        passInput.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(Color.str("&7点击后直接在聊天框内输入&6密码"))));
        passInput.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/l password"));
        password.addExtra(passInput);

        TextComponent saveNameCheckbox = new TextComponent("☐ ");
        TextComponent saveName = new TextComponent("记住用户名");
        saveNameCheckbox.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/l saveName"));

        TextComponent savePassCheckbox = new TextComponent("☐ ");
        TextComponent savePass = new TextComponent("记住密码");

        TextComponent autologinCheckbox = new TextComponent("☐ ");
        TextComponent autologin = new TextComponent("记住用户名");

        TextComponent space = new TextComponent("       ");
        TextComponent login = new TextComponent(Color.str("&a登录"));
        login.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/l login " + plugin.getPlayerData().getUsername(p) + " " + plugin.getPlayerData().getPassword(p)));
        space.addExtra(login);

        TextComponent noAccount = new TextComponent(Color.str("&#f7ede2还没有账号？"));
        TextComponent register = new TextComponent(Color.str("&#f7ede2点击前往注册！"));
        register.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(Color.str("&7点击打开网页，选择&6“注册”即可"))));
        register.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://forum.projnic.net/"));

        noAccount.addExtra(register);

        p.sendMessage(Color.str("&#a5a58d----------------------------------------"));
        p.sendMessage(Color.str("&#699672欢迎来到 &#fd8900P&#fc9800r&#fba700o&#fab600j&#f9c500e&#f8d400c&#f7e200t &#0a8f98N&#157e8di&#206d82c&#2b5d78h&#544999o&#7d36bbl&#a622dda&#cf0fffs"));
        p.sendMessage(Color.str("&#699672推荐使用1.16.5版本以获得最佳体验"));
        p.sendMessage("");
        p.sendMessage(Color.str("&#699672请使用您的 &#fd8900P&#fc9800r&#fba700o&#fab600j&#f9c500e&#f8d400c&#f7e200t &#0a8f98N&#157e8di&#206d82c&#2b5d78h&#544999o&#7d36bbl&#a622dda&#cf0fffs &#699672社区论坛账号登录"));
        p.sendMessage("");
        p.spigot().sendMessage(username);
        p.sendMessage("");
        p.spigot().sendMessage(password);
        p.sendMessage("");
        p.spigot().sendMessage(space);
        p.sendMessage("");
        p.spigot().sendMessage(noAccount);
    }


}
