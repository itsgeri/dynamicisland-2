package dev.android.dynamicisland.settings;

import static org.junit.Assert.*;
import android.content.Context;
import android.content.SharedPreferences;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk=35)
public class MaterialStyleTest {
    @Test public void classicDefaultAndExplicitFrostedPersistWithoutChangingOtherAppearancePreferences() {
        SharedPreferences prefs=RuntimeEnvironment.getApplication().getSharedPreferences("material-test",Context.MODE_PRIVATE);
        prefs.edit().clear().putInt(ModuleSettings.KEY_GLOW_COLOUR,0xffccbbaa).commit();
        ModuleSettings.configurePreview(prefs);assertFalse(ModuleSettings.frostedMaterial());
        prefs.edit().putInt(ModuleSettings.KEY_MATERIAL_STYLE,ModuleSettings.MATERIAL_FROSTED).commit();
        ModuleSettings.configurePreview(prefs);assertTrue(ModuleSettings.frostedMaterial());
        prefs.edit().putInt(ModuleSettings.KEY_MATERIAL_STYLE,ModuleSettings.MATERIAL_CLASSIC).commit();
        ModuleSettings.configurePreview(prefs);assertFalse(ModuleSettings.frostedMaterial());
        assertEquals(0xffccbbaa,prefs.getInt(ModuleSettings.KEY_GLOW_COLOUR,0));
        prefs.edit().putInt(ModuleSettings.KEY_MATERIAL_STYLE,99).commit();
        ModuleSettings.configurePreview(prefs);assertFalse(ModuleSettings.frostedMaterial());
        prefs.edit().clear().commit();ModuleSettings.configurePreview(prefs);
    }

    @Test public void liquidGlassToggleCanBeEnabledOrDisabledAndRespectsLowPerformanceMode() {
        SharedPreferences prefs=RuntimeEnvironment.getApplication().getSharedPreferences("liquid-glass-test",Context.MODE_PRIVATE);
        prefs.edit().clear().commit();
        ModuleSettings.configurePreview(prefs);
        assertFalse("Progressive liquid glass is disabled by default",ModuleSettings.liquidGlass());
        prefs.edit().putBoolean(ModuleSettings.KEY_LIQUID_GLASS,true).commit();
        ModuleSettings.configurePreview(prefs);
        assertTrue("Progressive liquid glass enables when toggled on",ModuleSettings.liquidGlass());
        prefs.edit().putBoolean(ModuleSettings.KEY_LOW_PERFORMANCE,true).commit();
        ModuleSettings.configurePreview(prefs);
        assertFalse("Low performance mode disables liquid glass shader",ModuleSettings.liquidGlass());
        prefs.edit().putBoolean(ModuleSettings.KEY_LOW_PERFORMANCE,false).putBoolean(ModuleSettings.KEY_LIQUID_GLASS,false).commit();
        ModuleSettings.configurePreview(prefs);
        assertFalse("Progressive liquid glass disables when toggled off",ModuleSettings.liquidGlass());
        prefs.edit().clear().commit();ModuleSettings.configurePreview(prefs);
    }
}
