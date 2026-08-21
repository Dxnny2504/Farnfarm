package org.example.core.listener;

import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import org.example.core.ExampleAddon;

public class ExampleGameTickListener {

  private final ExampleAddon addon;

  private boolean lastKeyState = false;

  public ExampleGameTickListener(ExampleAddon addon) {
    this.addon = addon;
  }

  @Subscribe
  public void onGameTick(GameTickEvent event) {
    if (event.phase() != Phase.PRE) {
      return;
    }

    boolean keyPressed = this.addon.isÄKeyPressed();

    if (keyPressed && !this.lastKeyState) {
      this.addon.toggleFarm();
    }

    this.lastKeyState = keyPressed;
  }
}
