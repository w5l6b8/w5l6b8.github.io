package net.ebaolife.husqvarna.framework.bean;

import java.io.Serializable;

public class Name implements Serializable {

  private static final long serialVersionUID = -5636844802457118126L;
  private String name;

  public Name() {}

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
