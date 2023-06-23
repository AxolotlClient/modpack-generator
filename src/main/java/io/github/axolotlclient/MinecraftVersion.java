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

    public static MinecraftVersion MC120 = MinecraftVersion.of("1.20.1", "quilt",
            "qsl", "sodium", "lithium",
            "lambdabettergrass", "axolotlclient", "lambdynamiclights", "modmenu",
            "iris", "ferrite-core", "indium", "polymer", "dynamic-fps", "sodium-extra", "reeses-sodium-options",
            "no-telemetry");

    public static MinecraftVersion MC1194 = MinecraftVersion.of("1.19.4", "quilt",
            "qsl", "sodium", "lithium", "starlight",
            "lambdabettergrass", "axolotlclient", "lambdynamiclights", "modmenu",
            "iris", "ferrite-core", "indium", "polymer", "dynamic-fps", "sodium-extra", "reeses-sodium-options",
            "no-telemetry");

    public static MinecraftVersion MC1193 = MinecraftVersion.of("1.19.3", "quilt",
            "qsl", "sodium", "lithium", "starlight",
            "lambdabettergrass", "axolotlclient", "lambdynamiclights", "modmenu",
            "lazydfu", "iris", "ferrite-core", "indium", "polymer", "dynamic-fps", "sodium-extra", "reeses-sodium-options",
            "no-telemetry");
    public static MinecraftVersion MC1192 = MinecraftVersion.of("1.19.2", "quilt",
            "qsl", "sodium", "lithium", "starlight",
            "lambdabettergrass", "lambdynamiclights", "axolotlclient", "modmenu",
            "lazydfu", "iris", "ferrite-core", "indium", "polymer", "dynamic-fps", "sodium-extra", "reeses-sodium-options",
            "no-telemetry");
    public static MinecraftVersion MC1165 = MinecraftVersion.of("1.16.5", "fabric",
            "fabric-api", "axolotlclient", "sodium", "lithium", "modmenu", "no-telemetry", "lambdynamiclights",
            "lambdabettergrass");
    /*public static MinecraftVersion MC116_combat8c = MinecraftVersion.of("1.16_combat-6", false, "fabric", // NOT AVAILABLE ON MODRINTH
            "axolotlclient", "fabric-api");*/
    public static MinecraftVersion MC189 = MinecraftVersion.of("1.8.9", "fabric",
            "legacy-fabric-api", "axolotlclient");

    public static Optional<MinecraftVersion> get(String version){
        return versions.stream().filter(s -> s.getVersion().equals(version)).findFirst();
    }
}
