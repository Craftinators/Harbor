plugins {
    id("java")
    id("xyz.jpenilla.run-paper") version "2.1.0"
}

group = "me.craftinators"
version = "0.0.0"

repositories {
    mavenCentral()

    maven("https://papermc.io/repo/repository/maven-public/") // Paper
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://repo.dmulloy2.net/repository/public/") // ProtocolLib
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
    compileOnly("com.comphenix.protocol:ProtocolLib:5.0.0")
}

tasks {
    runServer {
        minecraftVersion("1.20")
    }
}