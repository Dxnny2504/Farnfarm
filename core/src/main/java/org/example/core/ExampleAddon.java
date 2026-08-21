package org.example.core;

import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class ExampleAddon extends LabyAddon<ExampleConfiguration> {

  private boolean farmEnabled = false;

  @Override
  protected void enable() {
    this.registerSettingCategory();

    this.logger().info("FarnFarm wurde geladen!");
  }

  public boolean isFarmEnabled() {
    return this.farmEnabled;
  }

  public void toggleFarm() {
    this.farmEnabled = !this.farmEnabled;

    if (this.farmEnabled) {
      this.logger().info("FarnFarm aktiviert!");
    } else {
      this.logger().info("FarnFarm deaktiviert!");
    }
  }

  @Override
  protected Class<ExampleConfiguration> configurationClass() {
    return ExampleConfiguration.class;
  }
}
