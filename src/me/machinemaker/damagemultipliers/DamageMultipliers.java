package me.machinemaker.damagemultipliers;

import me.machinemaker.damagemultipliers.events.EntityDamage;
import me.machinemaker.damagemultipliers.events.EntityDamageByEntity;
import org.bukkit.plugin.java.JavaPlugin;

public class DamageMultipliers extends JavaPlugin {

    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new EntityDamage(), this);
        this.getServer().getPluginManager().registerEvents(new EntityDamageByEntity(), this);

    }

}
