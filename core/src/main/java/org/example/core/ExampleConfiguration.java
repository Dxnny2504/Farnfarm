package org.example.core;

import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.key.Key;
import net.labymod.api.client.gui.screen.widget.widgets.input.KeyBindWidget.KeyBindSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.property.ConfigProperty;

@ConfigName("settings")
public class ExampleConfiguration extends AddonConfig {

  private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

  @KeyBindSetting
  private final ConfigProperty<Key> toggleKey = new ConfigProperty<>(Key.Semicolon);

  @Override
  public ConfigProperty<Boolean> enabled() {
    return this.enabled;
  }

  public ConfigProperty<Key> toggleKey() {
    return this.toggleKey;
  }
}
