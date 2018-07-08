/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiSlot
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.util.RegistryNamespaced
 */
package ehacks.mod.gui.xraysettings;

import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.RegistryNamespaced;
import ehacks.mod.gui.xraysettings.XRayBlock;
import ehacks.mod.gui.xraysettings.XRayGui;

public class XRayBlockSlot
extends GuiSlot {
    int selectedIndex = -1;
    XRayGui xrayGui;

    public XRayBlockSlot(Minecraft par1Minecraft, int width, int height, int top, int bottom, int slotHeight, XRayGui xrayGui) {
        super(par1Minecraft, width, height, top, bottom, slotHeight);
        this.xrayGui = xrayGui;
        XRayBlock.init();
    }

    protected int getSize() {
        return XRayBlock.blocks.size();
    }

    protected boolean isSelected(int i) {
        return i == this.selectedIndex;
    }

    protected void drawBackground() {
    }

    protected void elementClicked(int i, boolean var2, int var3, int var4) {
        this.selectedIndex = i;
    }

    protected void drawSlot(int i, int j, int k, int var4, Tessellator var5, int var6, int var7) {
        XRayBlock xblock = (XRayBlock)XRayBlock.blocks.get(i);
        XRayGui var10000 = this.xrayGui;
        XRayGui.drawRect((int)(175 + j), (int)(1 + k), (int)(this.xrayGui.width - j - 20), (int)(15 + k), (int)(((51200 | xblock.r) << 8 | xblock.g) << 8 | xblock.b));
        if (xblock.id != null && Block.blockRegistry.containsKey(xblock.id)) {
            this.xrayGui.drawString(this.xrayGui.render, ((Block)Block.blockRegistry.getObject(xblock.id)).getLocalizedName(), j + 2, k + 1, 16777215);
        }
    }
}

