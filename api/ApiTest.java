package org.example.core.listener;

import net.labymod.api.client.key.Key;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.input.KeyEvent;
import org.example.core.ExampleAddon;

public class FarnFarmKeyListener {

  private final ExampleAddon addon;
  private boolean active = false;

  public FarnFarmKeyListener(ExampleAddon addon) {
    this.addon = addon;
  }

  @Subscribe
  public void onKey(KeyEvent event) {
    Key key = event.key();

    if (key != Key.Semicolon) {
      return;
    }

    this.addon.logger().info("Ä Taste erkannt");
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
