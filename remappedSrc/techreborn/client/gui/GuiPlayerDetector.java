package techreborn.client.gui;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import reborncore.client.gui.builder.GuiBase;
import reborncore.client.gui.builder.widget.GuiButtonUpDown;
import reborncore.client.screen.builder.BuiltScreenHandler;
import reborncore.common.network.NetworkManager;
import techreborn.blockentity.machine.tier1.PlayerDetectorBlockEntity;
import techreborn.packets.ServerboundPackets;

public class GuiPlayerDetector extends GuiBase<BuiltScreenHandler> {

	PlayerDetectorBlockEntity blockEntity;

	public GuiPlayerDetector(int syncID, final PlayerEntity player, final PlayerDetectorBlockEntity blockEntity) {
		super(player, blockEntity, blockEntity.createScreenHandler(syncID, player));
		this.blockEntity = blockEntity;
	}

	private void onClick(int amount) {
		NetworkManager.sendToServer(ServerboundPackets.createPacketPlayerDetector(amount, blockEntity));
	}

	@Override
	public void init() {
		super.init();

		addChild(new GuiButtonUpDown(x + 64, y + 40, this, b -> onClick(16), GuiButtonUpDown.UpDownButtonType.FASTFORWARD));
		addChild(new GuiButtonUpDown(x + 64 + 12, y + 40, this, b -> onClick(1), GuiButtonUpDown.UpDownButtonType.FORWARD));
		addChild(new GuiButtonUpDown(x + 64 + 24, y + 40, this, b -> onClick(-1), GuiButtonUpDown.UpDownButtonType.REWIND));
		addChild(new GuiButtonUpDown(x + 64 + 36, y + 40, this, b -> onClick(-16), GuiButtonUpDown.UpDownButtonType.FASTREWIND));
	}

	@Override
	protected void drawBackground(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
		super.drawBackground(matrixStack, partialTicks, mouseX, mouseY);
		final Layer layer = Layer.BACKGROUND;

		if (hideGuiElements()) return;

		Text text = new LiteralText("Radius: ").append(String.valueOf(blockEntity.getCurrentRadius()));
		drawCentredText(matrixStack, text, 25, 4210752, layer);
	}

	@Override
	protected void drawForeground(MatrixStack matrixStack, final int mouseX, final int mouseY) {
		super.drawForeground(matrixStack, mouseX, mouseY);
		final Layer layer = Layer.FOREGROUND;

		builder.drawMultiEnergyBar(matrixStack, this, 9, 19, (int) blockEntity.getEnergy(), (int) blockEntity.getMaxStoredPower(), mouseX, mouseY, 0, layer);
	}
}