package io.github.axolotlclient;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class Modpack {

    // ---- Options
    public static final String NAME = "AxolotlClient (Modpack)";
    public static final String AUTHOR = "moehreag";
    public static final String BASE_VERSION = "0.1.0"; // Minecraft version is appended afterwards
    public static final boolean CLEAR_ON_UPDATE = true;
    public static final boolean EXPORT_PACK = true;

    // ---- The stuff
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
        if (CLEAR_ON_UPDATE){
            Path f = Paths.get(s.getVersion());
            try {
                if (Files.exists(f)) {
                    Files.walkFileTree(f, new SimpleFileVisitor<>() {
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                            Files.delete(file);
                            return super.visitFile(file, attrs);
                        }

                        @Override
                        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                            Files.delete(dir);
                            return FileVisitResult.CONTINUE;
                        }
                    });
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        File file = new File(s.getVersion());
        if(file.exists()){
            System.out.println("Updating Modpack for version "+s.getVersion());
            new ModpackUpdater(file, s);
        } else {
            System.out.println("Creating modpack for version "+s.getVersion());
            new ModpackCreator(s);
        }
        if(EXPORT_PACK) {
            System.out.println("Exporting modpack for version " + s.getVersion());
            exportPack(file);
        }
    }

    private static void exportPack(File dir){
        try {
            new ProcessBuilder().inheritIO().command("packwiz", "modrinth", "export", "-y").directory(dir).inheritIO().start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static int installMod(File dir, String slug) throws IOException {
        System.out.println("Installing mod "+slug);
        Process proc = new ProcessBuilder().redirectError(ProcessBuilder.Redirect.INHERIT).command("packwiz", "modrinth", "install", slug).directory(dir).start();
        InputStreamReader reader = new InputStreamReader(proc.getInputStream(), StandardCharsets.UTF_8);
        try (PrintWriter writer = new PrintWriter(proc.outputWriter(StandardCharsets.UTF_8), true)) {
            StringBuilder output = new StringBuilder();
            while (proc.isAlive()) {
                output.append((char) reader.read());
                if (output.toString().endsWith("Would you like to add them? [Y/n]: ")) {
                    writer.println("n");
                }
            }
        }
        System.out.println("Installed mod "+slug+"!");
        return proc.exitValue();
    }
}
