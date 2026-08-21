package org.example.core.listener;

import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.input.CharacterTypedEvent;
import org.example.core.ExampleAddon;

public class FarnFarmKeyListener {

  private final ExampleAddon addon;
  private boolean active = false;

  public FarnFarmKeyListener(ExampleAddon addon) {
    this.addon = addon;
  }

  @Subscribe
  public void onCharacterTyped(CharacterTypedEvent event) {
    if (event.character() != 'ä' && event.character() != 'Ä') {
      return;
    }

    this.toggle();
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
