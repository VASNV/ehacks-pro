/*
 * Decompiled with CFR 0_128.
 */
package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Mod;
import ehacks.mod.wrapper.ModuleCategories;

public class Criticals
extends Mod {
    public static boolean isActive = false;

    public Criticals() {
        super(ModuleCategories.COMBAT);
    }

    @Override
    public String getName() {
        return "Criticals";
    }

    @Override
    public void onEnableMod() {
        isActive = true;
    }

    @Override
    public void onDisableMod() {
        isActive = false;
    }
}

