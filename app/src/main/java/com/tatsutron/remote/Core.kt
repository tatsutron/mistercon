package com.tatsutron.remote

enum class Core {

    Gameboy {
        override fun sync() {
            val games = Persistence.getGamesPath()
            val dir = "$games/Gameboy"
            Ssh.command("ls $dir")
                .split("\n")
                .filter {
                    it.endsWith(".gb")
                }
                .forEach { filename ->
                    Ssh.command("sha1sum \"$dir/$filename\"")
                        .let {
                            Persistence.saveGame("Gameboy", filename, hash(it))
                        }

                }
        }

        override fun play(filename: String) {
            val mbc = Persistence.getMbcPath()
            val games = Persistence.getGamesPath()
            Ssh.command("$mbc load_rom GAMEBOY \"$games/Gameboy/$filename\"")
        }
    },

    GBA {
        override fun sync() {
            val games = Persistence.getGamesPath()
            val dir = "$games/GBA"
            Ssh.command("ls $dir")
                .split("\n")
                .filter {
                    it.endsWith(".gba")
                }
                .forEach { filename ->
                    Ssh.command("sha1sum \"$dir/$filename\"")
                        .let {
                            Persistence.saveGame("GBA", filename, hash(it))
                        }

                }
        }

        override fun play(filename: String) {
            val mbc = Persistence.getMbcPath()
            val games = Persistence.getGamesPath()
            Ssh.command("$mbc load_rom GBA \"$games/GBA/$filename\"")
        }
    },

    Genesis {
        override fun sync() {
            val games = Persistence.getGamesPath()
            val dir = "$games/Genesis"
            Ssh.command("ls $dir")
                .split("\n")
                .filter {
                    it.endsWith(".bin") ||
                            it.endsWith(".gen") ||
                            it.endsWith(".md")
                }
                .forEach { filename ->
                    Ssh.command("sha1sum \"$dir/$filename\"")
                        .let {
                            Persistence.saveGame("Genesis", filename, hash(it))
                        }
                }
        }

        override fun play(filename: String) {
            val mbc = Persistence.getMbcPath()
            val games = Persistence.getGamesPath()
            Ssh.command("$mbc load_rom MEGADRIVE \"$games/Genesis/$filename\"")
        }
    },

    SNES {
        override fun sync() {
            val games = Persistence.getGamesPath()
            val dir = "$games/SNES"
            Ssh.command("ls $dir")
                .split("\n")
                .filter {
                    it.endsWith(".sfc")
                }
                .forEach { filename ->
                    Ssh.command("sha1sum \"$dir/$filename\"")
                        .let {
                            Persistence.saveGame("SNES", filename, hash(it))
                        }
                }
        }

        override fun play(filename: String) {
            val mbc = Persistence.getMbcPath()
            val games = Persistence.getGamesPath()
            Ssh.command("$mbc load_rom SNES \"$games/SNES/$filename\"")
        }
    },

    TGFX16 {
        override fun sync() {
            val games = Persistence.getGamesPath()
            val dir = "$games/TGFX16"
            Ssh.command("ls $dir")
                .split("\n")
                .filter {
                    it.endsWith(".pce")
                }
                .forEach { filename ->
                    Ssh.command("sha1sum \"$dir/$filename\"")
                        .let {
                            Persistence.saveGame("TGFX16", filename, hash(it))
                        }
                }
        }

        override fun play(filename: String) {
            val mbc = Persistence.getMbcPath()
            val games = Persistence.getGamesPath()
            Ssh.command("$mbc load_rom TGFX16 \"$games/TGFX16/$filename\"")
        }
    };

    abstract fun sync()

    abstract fun play(filename: String)

    protected fun hash(input: String) =
        Regex("\\b[0-9a-f]{5,40}\\b")
            .find(input)
            ?.groupValues
            ?.first()
}
