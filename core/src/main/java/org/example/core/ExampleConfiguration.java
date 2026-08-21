package org.example.core;

import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.key.Key;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.Setting;
import net.labymod.api.configuration.settings.annotation.SettingSection;
import net.labymod.api.configuration.settings.annotation.types.KeyBindSetting;
import net.labymod.api.configuration.settings.annotation.types.SwitchSetting;

public class ExampleConfiguration extends AddonConfig {

  @SwitchSetting
  private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

  @SettingSection("farnfarm")
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
