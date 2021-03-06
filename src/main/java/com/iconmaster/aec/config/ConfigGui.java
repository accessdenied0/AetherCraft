package com.iconmaster.aec.config;

import com.iconmaster.aec.AetherCraft;
import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.client.config.ConfigGuiType;
import cpw.mods.fml.client.config.DummyConfigElement;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConfigGui implements IModGuiFactory {

	public static class ConfigGuiScreen extends GuiConfig {
		public ConfigGuiScreen(GuiScreen parent) {
			super(parent, getConfigElements(), "AetherCraft", false, false, "AetherCraft Options");
		}

		private static List<IConfigElement> getConfigElements() {
			List<IConfigElement> list = new ArrayList<IConfigElement>();

			for (Map.Entry<String, Object> entry : AetherCraft.options.getOptionsMap().entrySet()) {
				if (entry.getValue() instanceof Integer) {
					list.add(new DummyConfigElement<Integer>(entry.getKey(), (Integer) entry.getValue(), ConfigGuiType.INTEGER, AetherCraft.options.getDescs().get(entry.getKey())));
				} else if (entry.getValue() instanceof Boolean) {
					list.add(new DummyConfigElement<Boolean>(entry.getKey(), (Boolean) entry.getValue(), ConfigGuiType.BOOLEAN, AetherCraft.options.getDescs().get(entry.getKey())));
				} else if (entry.getValue() instanceof Float) {
					list.add(new DummyConfigElement<Double>(entry.getKey(), ((Float) entry.getValue()).doubleValue(), ConfigGuiType.DOUBLE, AetherCraft.options.getDescs().get(entry.getKey())));
				} else if (entry.getValue() instanceof Double) {
					list.add(new DummyConfigElement<Double>(entry.getKey(), (Double) entry.getValue(), ConfigGuiType.DOUBLE, AetherCraft.options.getDescs().get(entry.getKey())));
				} else if (entry.getValue() instanceof String) {
					list.add(new DummyConfigElement<String>(entry.getKey(), (String) entry.getValue(), ConfigGuiType.STRING, AetherCraft.options.getDescs().get(entry.getKey())));
				}
			}
			
			return list;
		}
	}

    private Minecraft minecraft;
    @Override
    public void initialize(Minecraft minecraftInstance) {
        this.minecraft = minecraftInstance;
    }

	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass() {
		return ConfigGuiScreen.class;
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}

   @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
        return new RuntimeOptionGuiHandler() {
            @Override
            public void paint(int x, int y, int w, int h) {

            }

            @Override
            public void close() {

            }

            @Override
            public void addWidgets(List<Gui> widgets, int x, int y, int w, int h) {

            }

            @Override
            public void actionCallback(int actionId) {

            }
        };
    }
	
	
}
