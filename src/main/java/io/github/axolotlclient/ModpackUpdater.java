package io.github.axolotlclient;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class ModpackUpdater {

    private final File dir;
    private final MinecraftVersion version;

    public ModpackUpdater(File dir, MinecraftVersion version){
        this.dir = dir;
        this.version = version;
        update();
    }

    private void update() {
        try {
            new ProcessBuilder().command("packwiz", "refresh", "-y").inheritIO().directory(dir).start().waitFor();
            new ProcessBuilder().command("packwiz", "update", "--all", "-y").inheritIO().directory(dir).start().waitFor();
            for(String s:version.getMods()){
                if(!dir.toPath().resolve("mods").resolve(s+".pw.toml").toFile().exists()){
                    Modpack.installMod(dir, s);
                }
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateModpackVersion() throws IOException {
        Path packToml = dir.toPath().resolve("pack.toml");
        List<String> lines = Files.readAllLines(packToml);
        String version = lines.stream().filter(s -> s.startsWith("version = ")).collect(Collectors.joining()).split("=")[1];
        lines.removeIf(s -> s.startsWith("version = "));
        lines.add(3, "version = \""+ getUpdatedVersion(version.replaceAll("\"", "")).replaceAll(" ","") + "\"");
        Files.write(packToml, lines);
    }

    private String getUpdatedVersion(String version){
        String versionSuffixes = "";
        String versionPrefixes = "";

        if(version.contains("+")){
            String[] split = version.split("\\+");
            versionSuffixes += "+"+split[1];
            version = split[0];
        }

        if(version.contains("beta.")){
            String[] split = version.split("beta\\.");
            versionPrefixes += split[0] + "beta.";
            version = split[1];
        } else if(version.contains("alpha")){
            String[] split = version.split("alpha\\.");
            versionPrefixes += split[0] + "alpha.";
            version = split[1];
        }

        String[] split = version.split("\\.");
        int length = split.length;
        int update = Integer.parseInt(split[length-1]);
        update++;
        StringBuilder tempVer = new StringBuilder();
        for (int i=0; i<split.length-1; i++){
            if(!tempVer.isEmpty()){
                tempVer.append(".");
            }
            tempVer.append(split[i]);
        }
        tempVer.append(update);
        version = tempVer.toString();

        return versionPrefixes + version + versionSuffixes;
    }
}
