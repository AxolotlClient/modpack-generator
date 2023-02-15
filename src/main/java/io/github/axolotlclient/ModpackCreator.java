package io.github.axolotlclient;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ModpackCreator {

    private final File dir;
    private final MinecraftVersion version;

    public ModpackCreator(MinecraftVersion version) {
        this.dir = new File(version.getVersion());
        this.version = version;
        create();
    }

    private void create() {
        try {
            Files.createDirectory(dir.toPath());
            Process init = new ProcessBuilder().command("packwiz", "init", "--name", Modpack.NAME,
                    "--author", Modpack.AUTHOR, "--version", Modpack.BASE_VERSION + "+" + version.getVersion(), "--quilt-latest", "--fabric-latest",
                    "--mc-version", version.getVersion(), "--modloader", version.getLoader(), "-y").directory(dir).inheritIO().start();
            if (init.waitFor() == 0) {
                System.out.println("Initialized modpack for version " + version.getVersion());
            } else {
                System.err.println("Failed to initialize pack for version " + version.getVersion());
                return;
            }
            for (String s : version.getMods()) {
                if (Modpack.installMod(dir, s) != 0) {
                    System.err.println("Failed to install mod " + s + " to pack version " + version.getVersion());
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
