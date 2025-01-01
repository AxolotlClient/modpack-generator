package io.github.axolotlclient;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class MinecraftVersion {

    public static Set<MinecraftVersion> versions = new HashSet<>();

    private final String version;
    private final String[] mods;
    private final String loader;

    private MinecraftVersion(String version, String loader, String... mods){
        this.version = version;
        this.loader = loader;
        this.mods = mods;
    }

    public static MinecraftVersion of(String version, String loader, String... mods){
        MinecraftVersion v = new MinecraftVersion(version, loader, mods);
        versions.add(v);
        return v;
    }

    public String getVersion(){
        return  version;
    }

    public String[] getMods() {
        return mods;
    }

    public String getLoader(){
        return loader;
    }

    public static MinecraftVersion MC1214 = MinecraftVersion.of("1.21.4", "fabric",
            "fabric-api", "sodium", "lithium", "axolotlclient", "modmenu", "iris",
            "ferrite-core", "polymer", "sodium-extra", "reeses-sodium-options",
            "no-telemetry", "noxesium", "world-host");

    public static MinecraftVersion MC121 = MinecraftVersion.of("1.21.1", "fabric",
            "fabric-api", "sodium", "lithium", "axolotlclient", "modmenu", "iris",
            "ferrite-core", "polymer", "sodium-extra", "reeses-sodium-options",
            "no-telemetry", "noxesium", "world-host");

    public static MinecraftVersion MC120 = MinecraftVersion.of("1.20.1", "quilt",
            "qsl", "sodium", "lithium",
            "axolotlclient", "modmenu",
            "iris", "ferrite-core", "indium", "polymer", "dynamic-fps", "sodium-extra", "reeses-sodium-options",
            "no-telemetry");

    /*public static MinecraftVersion MC116_combat8c = MinecraftVersion.of("1.16_combat-6", false, "fabric", // NOT AVAILABLE ON MODRINTH
            "axolotlclient", "fabric-api");*/
    public static MinecraftVersion MC189 = MinecraftVersion.of("1.8.9", "fabric",
            "osl", "axolotlclient");

    public static Optional<MinecraftVersion> get(String version){
        return versions.stream().filter(s -> s.getVersion().equals(version)).findFirst();
    }
}
