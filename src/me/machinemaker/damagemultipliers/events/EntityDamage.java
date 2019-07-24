package me.machinemaker.damagemultipliers.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.Arrays;
import java.util.List;

public class EntityDamage implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return; // Ensure player
        Player p = (Player) e.getEntity();

        Double multiplier = null;
        boolean found = false;
        for (PermissionAttachmentInfo permission : p.getEffectivePermissions()) {
            List<String> perm = Arrays.asList(permission.getPermission().split("\\."));
            if (perm.size() < 3 || perm.size() > 4 || !perm.get(0).equalsIgnoreCase("damagemultipliers") || e.getCause() != getType(perm.get(1))) { continue; }
            try {
                multiplier = Double.parseDouble(String.join(".", perm.subList(2, perm.size())));
                found = true;
                break;
            } catch(NumberFormatException ex) {
                System.out.println("[DamageMultipliers] ERROR: Possible issue with permissions!");
            }
        }
        if (!found) { return; }
//        System.out.println("B: " + e.getFinalDamage());
        Double finalDamage = e.getFinalDamage() * multiplier;
        e.setDamage(finalDamage);
//        System.out.println("A: " + finalDamage);
    }

    private EntityDamageEvent.DamageCause getType(String type) {
        for (EntityDamageEvent.DamageCause d : EntityDamageEvent.DamageCause.values()) {
            if (d.name().equalsIgnoreCase(type))
                return d;
        }
        return null;
    }
}
