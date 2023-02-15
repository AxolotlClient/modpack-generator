# AxolotlClient Modpack generator

### Dependencies

These dependencies need to be available in your $PATH for this to work correctly.

- packwiz

### Usage (Why though, there is no reason for anyone to run this)

- when run with no arguments:
  - Create/Update the modpack for all available versions
- with Minecraft version(s) as arguments:
  - Create/Update the modpack for the provided version(s)

The version of the modpack will be incremented automatically.

The mods of each version are defined in [MinecraftVersion](src/main/java/io/github/axolotlclient/MinecraftVersion.java)
