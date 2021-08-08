package me.zjls.niclogin.commands;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import jdk.internal.net.http.HttpRequestBuilderImpl;
import me.zjls.niclogin.Main;
import me.zjls.niclogin.utils.Color;
import me.zjls.niclogin.utils.Requests;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.util.EntityUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Login implements CommandExecutor {

    private Main plugin;
    private Requests requests;

    public Login(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            if (args[0].equalsIgnoreCase("reload")) {
                plugin.reloadConfig();
            }
            return true;
        }
        Player p = (Player) sender;
        requests = new Requests(plugin);
        if (args.length == 0) {

            return true;
        }

        if (args[0].equalsIgnoreCase("username")) {
            p.sendMessage(Color.str("&#70e000请在下方聊天框内输入您的&#ff9100用户名"));
            plugin.getPlayerData().getEditUsernamePlayers().add(p.getUniqueId());
            return true;
        } else if (args[0].equalsIgnoreCase("password")) {
            p.sendMessage(Color.str("&#70e000请在下方聊天框内输入您的&#ff9100密码"));
            plugin.getPlayerData().getEditPasswordPlayers().add(p.getUniqueId());
            return true;
        } else if (args[0].equalsIgnoreCase("saveName")) {
            plugin.getPlayerData().getSaveUsernamePlayers().add(p.getUniqueId());

        } else if (args[0].equalsIgnoreCase("login")) {
            if (args.length < 3) {
                p.sendMessage(Color.str("&7[&#d00000✘&7] &#e85d04请输入用户名和密码"));
                return true;
            }

//            StringBuilder sb = new StringBuilder();
//            for (int i = 2; i < args.length; i++) {
//                if (i==args.length-1){
//                    sb.append(args[i]);
//                }
//                sb.append(args[i]).append(" ");
//            }
//            String pass = sb.toString();

            p.sendMessage(Color.str("&7[ &#ff9e00&l! &7] &#70e000正在尝试登录&#a5a58d..."));
            requests.login(args[1], args[2], new FutureCallback<HttpResponse>() {
                @Override
                public void completed(HttpResponse result) {
                    JSONObject json = null;
                    try {
                        json = new JSONObject(EntityUtils.toString(result.getEntity()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (json == null || json.isEmpty()) {
                        p.sendMessage(Color.str("&7[&#d00000✘&7] &#dc2f02登录失败，服务器无响应"));
                        return;
                    }
                    if (!json.has("token")) {
                        p.sendMessage(Color.str("&7[&#d00000✘&7] &#e85d04用户名或密码错误"));
                        return;
                    }
                    p.sendMessage(Color.str("&7[&#27ae60✔&7] &#2ecc71登录成功！"));
                    plugin.getPlayerData().getLoginPlayers().add(p.getUniqueId());
                    //todo:teleport/save
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    String token = json.getString("token");
                    int userId = json.getInt("userId");
                    FileConfiguration loginConfig = plugin.getData().getLoginConfig();
                    ConfigurationSection section = loginConfig.createSection(p.getUniqueId().toString());

                    section.set("token", token);
                    section.set("username", args[1]);
                    section.set("userId", userId);
                    section.set("login-time", date.getTime());
                    section.set("exp-time", date.getTime() + 604800000);
                    plugin.getData().saveLoginConfig();

                    if (!p.isOp()) {
                        p.sendMessage(Color.str("&7[ &#ff9e00&l! &7] &#80ed99即将传送&#a5a58d..."));
                        ByteArrayDataOutput out = ByteStreams.newDataOutput();
                        out.writeUTF("Connect");
                        out.writeUTF(plugin.getConfig().getString("lobby"));
                        p.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
                    }

                    plugin.sqldata.setPlayerLogged(p, userId, args[1], args[2], token);

//                    ByteArrayDataOutput out = ByteStreams.newDataOutput();
//                    out.writeUTF("Logged");
//                    out.writeUTF(p.getUniqueId().toString());
//                    p.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
                }

                @Override
                public void failed(Exception ex) {
                    ex.printStackTrace();
                    p.sendMessage(Color.str("&7[&#d00000✘&7] &#dc2f02登录失败，原因未知"));
                }

                @Override
                public void cancelled() {
                    p.sendMessage(Color.str("&7[&#d00000✘&7] &#dc2f02登录失败，任务被中断"));
                }
            });
        }

        return true;
    }
}
