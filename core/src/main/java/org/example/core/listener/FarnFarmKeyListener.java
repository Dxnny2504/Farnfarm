package org.example.core.listener;

import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Keyboard;
import org.example.core.ExampleAddon;

public class FarnFarmKeyListener {

  private static final int FERN_BLOCK_ID = 31;
  private static final int FERN_META = 2;
  private static final int ACTION_DELAY = 2;

  private final ExampleAddon addon;

  private boolean active = false;
  private boolean hotkeyWasDown = false;

  private int step = 0;
  private int cooldown = 0;

  public FarnFarmKeyListener(ExampleAddon addon) {
    this.addon = addon;
  }

  @Subscribe
  public void onGameTick(GameTickEvent event) {
    if (event.phase() != Phase.PRE) {
      return;
    }

    Minecraft minecraft = Minecraft.getMinecraft();

    if (minecraft.theWorld == null || minecraft.thePlayer == null) {
      return;
    }

    boolean keyDown = Keyboard.isKeyDown(25);

    if (keyDown && !this.hotkeyWasDown) {
      this.active = !this.active;
      this.step = 0;
      this.cooldown = 0;

      if (this.active) {
        this.sendMessage("§a[FarnHelfer] §fAktiviert!");
      } else {
        this.sendMessage("§c[FarnHelfer] §fDeaktiviert.");
      }
    }

    this.hotkeyWasDown = keyDown;

    if (!this.active) {
      return;
    }

    this.tickFarn(minecraft);
  }

  private void tickFarn(Minecraft minecraft) {
    if (this.cooldown > 0) {
      this.cooldown--;
      return;
    }

    MovingObjectPosition mouseOver = minecraft.objectMouseOver;

    switch (this.step) {

      case 0:
        if (mouseOver == null
            || mouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) {
          return;
        }

        BlockPos targetPos = mouseOver.getBlockPos();
        Block block = minecraft.theWorld.getBlockState(targetPos).getBlock();

        if (Block.getIdFromBlock(block) != FERN_BLOCK_ID) {
          return;
        }

        int shearsSlot = this.findShears(minecraft);

        if (shearsSlot == -1) {
          this.sendMessage("§c[FarnHelfer] §fKeine Schere im Hotbar!");
          this.active = false;
          return;
        }

        minecraft.thePlayer.inventory.currentItem = shearsSlot;

        this.cooldown = ACTION_DELAY;
        this.step = 1;
        return;

      case 1:
        if (mouseOver == null
            || mouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) {
          this.step = 0;
          return;
        }

        this.sendLeftClick(minecraft);

        this.cooldown = ACTION_DELAY;
        this.step = 2;
        return;

      case 2:
        int fernSlot = this.findFern(minecraft);

        if (fernSlot == -1) {
          this.sendMessage("§c[FarnHelfer] §fKein Farn im Hotbar!");
          this.active = false;
          this.step = 0;
          return;
        }

        minecraft.thePlayer.inventory.currentItem = fernSlot;

        this.cooldown = ACTION_DELAY;
        this.step = 3;
        return;

      case 3:
        if (mouseOver == null
            || mouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) {
          this.step = 0;
          return;
        }

        this.sendRightClick(minecraft, mouseOver);

        this.cooldown = ACTION_DELAY;
        this.step = 0;
        return;

      default:
        this.step = 0;
        return;
    }
  }

  private int findShears(Minecraft minecraft) {
    EntityPlayerSP player = minecraft.thePlayer;

    for (int slot = 0; slot < 9; slot++) {
      ItemStack stack = player.inventory.getStackInSlot(slot);

      if (stack == null) {
        continue;
      }

      Item item = stack.getItem();

      if (item == net.minecraft.init.Items.shears) {
        return slot;
      }
    }

    return -1;
  }

  private int findFern(Minecraft minecraft) {
    EntityPlayerSP player = minecraft.thePlayer;

    for (int slot = 0; slot < 9; slot++) {
      ItemStack stack = player.inventory.getStackInSlot(slot);

      if (stack == null) {
        continue;
      }

      Item item = stack.getItem();

      if (Block.getIdFromBlock(Block.getBlockFromItem(item)) == FERN_BLOCK_ID
          && stack.getMetadata() == FERN_META) {
        return slot;
      }
    }

    return -1;
  }

  private void sendLeftClick(Minecraft minecraft) {
    PlayerControllerMP controller = minecraft.playerController;

    MovingObjectPosition mouseOver = minecraft.objectMouseOver;

    if (mouseOver == null
        || mouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) {
      return;
    }

    controller.clickBlock(
        mouseOver.getBlockPos(),
        mouseOver.sideHit
    );

    minecraft.thePlayer.swingItem();
  }

  private void sendRightClick(
      Minecraft minecraft,
      MovingObjectPosition mouseOver
  ) {
    if (mouseOver == null
        || mouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) {
      return;
    }

    BlockPos pos = mouseOver.getBlockPos();

    minecraft.playerController.onPlayerRightClick(
        minecraft.thePlayer,
        minecraft.theWorld,
        minecraft.thePlayer.getHeldItem(),
        pos,
        mouseOver.sideHit,
        mouseOver.hitVec
    );

    minecraft.thePlayer.swingItem();
  }

  private void sendMessage(String message) {
    if (Minecraft.getMinecraft().thePlayer != null) {
      Minecraft.getMinecraft().thePlayer.addChatMessage(
          new ChatComponentText(message)
      );
    }

    this.addon.logger().info(message.replace("§a", "").replace("§c", "").replace("§f", ""));
  }

  public boolean isActive() {
    return this.active;
  }

  public void toggle() {
    this.active = !this.active;
    this.step = 0;
    this.cooldown = 0;
  }
}
