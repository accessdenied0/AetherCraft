package com.iconmaster.aec.client.gui;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.iconmaster.aec.aether.AVRegistry;
import com.iconmaster.aec.aether.IConsumeBehavior;
import com.iconmaster.aec.aether.IProduceBehavior;
import com.iconmaster.aec.common.AetherCraft;
import com.iconmaster.aec.common.gui.ContainerAetherExtractor;
import com.iconmaster.aec.common.tileentity.TileEntityAetherExtractor;
import com.iconmaster.aec.util.NumberUtils;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiAetherExtractor extends AetherCraftGui {
	
	private static final ResourceLocation gui_texture = new ResourceLocation(
			"aec", "textures/gui/aetherExtractorGui.png");
	private TileEntityAetherExtractor te;

	public GuiAetherExtractor(InventoryPlayer player,
			TileEntityAetherExtractor tileEntity) {
		super(new ContainerAetherExtractor(player, tileEntity));
		
		standardTooltip = false;
		consumeTooltip = true;
		produceTooltip = false;
		
		this.xSize = 176;
		this.ySize = 166;
		this.te = tileEntity;
		this.requestSync();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		GL11.glDisable(GL11.GL_LIGHTING);
		this.fontRenderer.drawString("Aether Extractor", 84, 4, 0x404040);

		this.fontRenderer.drawStringWithShadow("AV: "+NumberUtils.display(this.te.getAether())+"/"+NumberUtils.display(te.max), 54, 58,0x00FF00);

		this.fontRenderer.drawStringWithShadow("Precision: "+(int) (Double.parseDouble(AetherCraft.getOptions("consumeprecision"))) + "%", 54,68,0x00FF00);
		
		te.calcLimit();
		this.fontRenderer.drawStringWithShadow("Limit: "+NumberUtils.display(te.limit), 54, 20,0x00FF00);
		
		int a = 68 - 16;
		float b = (100 - this.te.getProgress()) / 100.0f;
		this.drawGradientRect(148, (int) (16.0f + (a * b)), 164, 68,
				0xFF16FF00,0x990EA600);

		GL11.glEnable(GL11.GL_LIGHTING);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float metaTicks, int mouseX,
			int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(this.gui_texture);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}

	private void requestSync() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream outputStream = new DataOutputStream(bos);

		try {
			outputStream.writeByte(this.te.energyBlockType);
			outputStream.writeInt(this.te.xCoord);
			outputStream.writeInt(this.te.yCoord);
			outputStream.writeInt(this.te.zCoord);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "AecReq";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		PacketDispatcher.sendPacketToServer(packet);
	}
}