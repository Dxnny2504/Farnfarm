package org.example.core.listener;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.chat.ChatMessage;
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
    if (!"P".equalsIgnoreCase(event.key().toString())) {
      return;
    }

    this.toggle();
  }

  public boolean isActive() {
    return this.active;
  }

  public void toggle() {
    this.active = !this.active;

    if (this.active) {
      this.addon.logger().info("FarnHelfer aktiviert");
    } else {
      this.addon.logger().info("FarnHelfer deaktiviert");
    }
  }
}
