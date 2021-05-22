package com.tatsutron.remote

enum class Core {

    Gameboy {
        override fun sync() {
            val dir = "${Paths.GAMES}/Gameboy"
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
            Ssh.command("${Paths.MBC} load_rom GAMEBOY \"${Paths.GAMES}/Gameboy/$filename\"")
        }
    },

    GBA {
        override fun sync() {
            val dir = "${Paths.GAMES}/GBA"
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
            Ssh.command("${Paths.MBC} load_rom GBA \"${Paths.GAMES}/GBA/$filename\"")
        }
    },

    Genesis {
        override fun sync() {
            val dir = "${Paths.GAMES}/Genesis"
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
            Ssh.command("${Paths.MBC} load_rom MEGADRIVE \"${Paths.GAMES}/Genesis/$filename\"")
        }
    },

    SNES {
        override fun sync() {
            val dir = "${Paths.GAMES}/SNES"
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
            Ssh.command("${Paths.MBC} load_rom SNES \"${Paths.GAMES}/SNES/$filename\"")
        }
    },

    TGFX16 {
        override fun sync() {
            val dir = "${Paths.GAMES}/TGFX16"
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
            Ssh.command("${Paths.MBC} load_rom TGFX16 \"${Paths.GAMES}/TGFX16/$filename\"")
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
