package io.github.axolotlclient;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class Modpack {

    public static String NAME = "AxolotlClient (Modpack)";
    public static String AUTHOR = "moehreag";
    public static String BASE_VERSION = "0.1.0-beta.1";

    public static void main(String[] args){
        if(args.length == 0){
            MinecraftVersion.versions.forEach(Modpack::processVersion);
        } else {
            Arrays.stream(args).filter(
                    MinecraftVersion.versions.stream().map(MinecraftVersion::getVersion)
                            .collect(Collectors.toUnmodifiableSet())::contains)
                    .map(MinecraftVersion::get).filter(Optional::isPresent).map(Optional::get)
                    .forEach(Modpack::processVersion);
        }
    }

    private static void processVersion(MinecraftVersion s){
        File file = new File(s.getVersion());
        if(file.exists()){
            System.out.println("Updating Modpack for version "+s.getVersion());
            new ModpackUpdater(file, s);
        } else {
            System.out.println("creating modpack for version "+s.getVersion());
            new ModpackCreator(s);
        }
        System.out.println("Exporting modpack for version "+s.getVersion());
        exportPack(file);
    }

    private static void exportPack(File dir){
        try {
            new ProcessBuilder().inheritIO().command("packwiz", "modrinth", "export", "-y").directory(dir).inheritIO().start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
