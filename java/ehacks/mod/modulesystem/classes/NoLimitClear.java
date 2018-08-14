/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.client.multiplayer.PlayerControllerMP
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.world.World
 */
package ehacks.mod.modulesystem.classes;

import cpw.mods.fml.relauncher.ReflectionHelper;
import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;
import ehacks.api.module.Mod;
import ehacks.api.module.ModStatus;
import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.gui.window.WindowPlayerIds;
import ehacks.mod.modulesystem.classes.AimBot;
import ehacks.mod.modulesystem.classes.AutoBlock;
import ehacks.mod.modulesystem.classes.Criticals;
import ehacks.mod.util.Mappings;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.lang.reflect.Method;
import net.minecraft.client.multiplayer.ChunkProviderClient;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.MouseEvent;
import org.lwjgl.input.Mouse;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

public class NoLimitClear
extends Mod {
    public NoLimitClear() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "NoLimitClear";
    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("powercrystals.minefactoryreloaded.net.ServerPacketHandler");
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }
    
    @Override
    public String getDescription() {
        return "Clears all containers around you";
    }
    
    @Override
    public void onEnableMod() {
        try {
            Class.forName("powercrystals.minefactoryreloaded.net.ServerPacketHandler");
            int count = 0;
            IChunkProvider chunkProvider = Wrapper.INSTANCE.world().getChunkProvider();
            if (chunkProvider instanceof ChunkProviderClient)
            {
                ChunkProviderClient clientProvider = (ChunkProviderClient)chunkProvider;
                List<Chunk> chunks = ReflectionHelper.getPrivateValue(ChunkProviderClient.class, clientProvider, Mappings.chunkListing);
                for (Chunk chunk : chunks)
                {
                    for (Object entityObj : chunk.chunkTileEntityMap.values())
                    {
                        if (!(entityObj instanceof TileEntity))
                            return;
                        TileEntity entity = (TileEntity)entityObj;
                        if (entity instanceof IInventory)
                        {
                            IInventory inv = (IInventory)entity;
                            TileEntity ent = entity;
                            for (int i = 0; i < inv.getSizeInventory(); i++)
                                setSlot(i, ent.xCoord, ent.yCoord, ent.zCoord);
                            count++;
                        }
                    }
                }
            }
            EHacksClickGui.log("[NoLimitClear] Cleared " + String.valueOf(count) + " containers");
            this.off();
        }
        catch (Exception ex) {
            this.off();
            EHacksClickGui.log("[NoLimitClear] Not working");
        }
    }

    @Override
    public void onDisableMod() {
        
    }

    private float getDistanceToEntity(Entity from, Entity to) {
        return from.getDistanceToEntity(to);
    }

    private boolean isWithinRange(float range, Entity e) {
        return this.getDistanceToEntity(e, (Entity)Wrapper.INSTANCE.player()) <= range;
    }
    
    @Override
    public void onMouse(MouseEvent event) {
        
    }
    
    public void setSlot(int slotId, int x, int y, int z) {
        int playerId = Wrapper.INSTANCE.player().getEntityId();
        ByteBuf buf = Unpooled.buffer(0);
        buf.writeByte(0);
        buf.writeInt(Wrapper.INSTANCE.player().dimension);
        buf.writeShort(20);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(playerId);
        buf.writeInt(slotId);
        buf.writeByte(0);
        C17PacketCustomPayload packet = new C17PacketCustomPayload("MFReloaded", buf);
        Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(packet);
    }
    
    @Override
    public String getModName() {
        return "MFR";
    }
}
