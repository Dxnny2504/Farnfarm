package org.example.core.listener;

import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import org.example.core.ExampleAddon;

public class FarnFarmKeyListener {

  private final ExampleAddon addon;
  private boolean active = false;

  public FarnFarmKeyListener(ExampleAddon addon) {
    this.addon = addon;
  }

  @Subscribe
  public void onGameTick(GameTickEvent event) {
    if (!this.active) {
      return;
    }
  }

  public boolean isActive() {
    return this.active;
  }

  public void toggle() {
    this.active = !this.active;

    this.addon.logger().info(
        this.active ? "FarnFarm aktiviert" : "FarnFarm deaktiviert"
    );
  }
}
