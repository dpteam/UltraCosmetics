package be.isach.ultracosmetics.cosmetics.deatheffects;

import be.isach.ultracosmetics.UltraCosmetics;
import be.isach.ultracosmetics.cosmetics.type.DeathType;
import be.isach.ultracosmetics.player.UltraPlayer;
import be.isach.ultracosmetics.util.SoundUtil;
import be.isach.ultracosmetics.util.Sounds;
import be.isach.ultracosmetics.util.UtilParticles;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class DeathBomb extends DeathEffect {

    int step = 0;
    float stepY = 0;
    float radius = 1.5f;

    public DeathBomb(UltraPlayer owner, UltraCosmetics ultraCosmetics) {
        super(ultraCosmetics, owner, DeathType.valueOf("deathbomb"));
    }

    @Override
    public void onUpdate() {
        for (int i = 0; i < 6; i++) {
            Location location = getPlayer().getLocation();
            double inc = (2 * Math.PI) / 100;
            double angle = step * inc + stepY + i;
            Vector v = new Vector();
            v.setX(Math.cos(angle) * radius);
            v.setZ(Math.sin(angle) * radius);
            UtilParticles.display(getType().getEffect(), location.add(v).add(0, stepY, 0));
            location.subtract(v).subtract(0, stepY, 0);
            if (stepY < 3) {
                radius -= 0.022;
                stepY += 0.045;
            } else {
                stepY = 0;
                step = 0;
                radius = 1.5f;
                SoundUtil.playSound(getPlayer(), Sounds.DIG_SNOW, .5f, 1.5f);
                UtilParticles.display(getType().getEffect(), location.clone().add(0, 3, 0), 48, 0.3f);
            }
        }
    }

}