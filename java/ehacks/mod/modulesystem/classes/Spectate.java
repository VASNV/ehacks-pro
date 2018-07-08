/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.world.World
 */
package ehacks.mod.modulesystem.classes;

import ehacks.api.module.APICEMod;
import ehacks.api.module.Mod;
import ehacks.mod.internal.FreecamEntity;
import ehacks.mod.modulesystem.classes.DynamicFly;
import ehacks.mod.modulesystem.handler.ModuleManagement;
import ehacks.mod.wrapper.ModuleCategories;
import ehacks.mod.wrapper.Wrapper;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class Spectate
extends Mod {
    private LocationHelper loc;

    public Spectate() {
        super(ModuleCategories.RENDER);
    }

    @Override
    public String getName() {
        return "Spectator";
    }

    @Override
    public String getDescription() {
        return "Creates another player entity (EntityFreecam) for be able to spectate your own player";
    }

    @Override
    public void onEnableMod() {
        APICEMod.INSTANCE.call(DynamicFly.class).toggle();
        this.doSpectate();
    }

    @Override
    public void onDisableMod() {
        APICEMod.INSTANCE.call(DynamicFly.class).toggle();
        this.undoSpectate();
    }

    public void doSpectate() {
        if (Wrapper.INSTANCE.world() instanceof WorldClient) {
            this.loc = new LocationHelper((Entity)Wrapper.INSTANCE.player());
            FreecamEntity spectator = new FreecamEntity((World)Wrapper.INSTANCE.world(), Wrapper.INSTANCE.player().getGameProfile());
            spectator.setPositionAndRotation(this.loc.posX, this.loc.posY - 1.5, this.loc.posZ, this.loc.rotationYaw, this.loc.rotationPitch);
            spectator.boundingBox.setBB(Wrapper.INSTANCE.player().boundingBox.copy());
            spectator.inventory.copyInventory(Wrapper.INSTANCE.player().inventory);
            Wrapper.INSTANCE.world().addEntityToWorld(-1, (Entity)spectator);
        }
    }

    public void undoSpectate() {
        Wrapper.INSTANCE.world().removeEntityFromWorld(-1);
        Wrapper.INSTANCE.player().setPositionAndRotation(this.loc.posX, this.loc.posY, this.loc.posZ, this.loc.rotationYaw, this.loc.rotationPitch);
    }

    public class LocationHelper {
        public double posX;
        public double posY;
        public double posZ;
        public float rotationYaw;
        public float rotationPitch;
        public String name;

        public LocationHelper clone() {
            return new LocationHelper(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch, this.name);
        }

        public LocationHelper(Entity entity) {
            this(entity, "");
        }

        public LocationHelper(Entity entity, String s) {
            this(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch, s);
        }

        public LocationHelper() {
            this(0.0, 0.0, 0.0, 0.0f, 0.0f, "");
        }

        public LocationHelper(double d, double d1, double d2, String s) {
            this(d, d1, d2, 0.0f, 0.0f, s);
        }

        public LocationHelper(double d, double d1, double d2) {
            this(d, d1, d2, 0.0f, 0.0f, "");
        }

        public LocationHelper(double d, double d1, double d2, float f, float f1) {
            this(d, d1, d2, f, f1, "");
        }

        public LocationHelper(double d, double d1, double d2, float f, float f1, String s) {
            this.posX = d;
            this.posY = d1;
            this.posZ = d2;
            this.rotationYaw = f;
            this.rotationPitch = f1;
            this.name = s;
        }

        public double distance(LocationHelper Location) {
            return Math.sqrt(this.distanceSquare(Location));
        }

        public double distanceSquare(LocationHelper Location) {
            double d = Location.posX - this.posX;
            double d1 = Location.posY - this.posY;
            double d2 = Location.posZ - this.posZ;
            return d * d + d1 * d1 + d2 * d2;
        }

        public double distance2D(LocationHelper Location) {
            return Math.sqrt(this.distance2DSquare(Location));
        }

        public double distance2DSquare(LocationHelper Location) {
            double d = Location.posX - this.posX;
            double d1 = Location.posZ - this.posZ;
            return d * d + d1 * d1;
        }

        public double distanceY(LocationHelper Location) {
            return Location.posY - this.posY;
        }

        public LocationHelper(String s) throws Exception {
            String[] as = s.split(";", 6);
            if (as.length != 6) {
                throw new Exception("Invalid line!");
            }
            this.name = as[5];
            this.posX = Double.parseDouble(as[0]);
            this.posY = Double.parseDouble(as[1]);
            this.posZ = Double.parseDouble(as[2]);
            this.rotationYaw = Float.parseFloat(as[3]);
            this.rotationPitch = Float.parseFloat(as[4]);
        }

        public String export() {
            return "" + this.posX + ";" + this.posY + ";" + this.posZ + ";" + this.rotationYaw + ";" + this.rotationPitch + ";" + this.name;
        }
    }

}

