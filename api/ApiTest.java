package org.example.api;

import net.labymod.api.client.key.Key;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.input.KeyEvent;

public class ApiTest {

  @Subscribe
  public void onKey(KeyEvent event) {
    Key key = event.key();
  }
}
