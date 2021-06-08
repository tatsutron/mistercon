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
                    val path = "$dir/$filename"
                    Ssh.command("sha1sum \"$path\"")
                        .let {
                            Persistence.saveGame("Gameboy", path, hash(it))
                        }

                }
        }

        override fun play(game: Game) {
            val mbc = Persistence.getMbcPath()
            Ssh.command("$mbc load_rom GAMEBOY \"${game.path}\"")
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
                    val path = "$dir/$filename"
                    Ssh.command("sha1sum \"$path\"")
                        .let {
                            Persistence.saveGame("GBA", path, hash(it))
                        }

                }
        }

        override fun play(game: Game) {
            val mbc = Persistence.getMbcPath()
            Ssh.command("$mbc load_rom GBA \"${game.path}\"")
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
                    val path = "$dir/$filename"
                    Ssh.command("sha1sum \"$path\"")
                        .let {
                            Persistence.saveGame("Genesis", path, hash(it))
                        }
                }
        }

        override fun play(game: Game) {
            val mbc = Persistence.getMbcPath()
            when {
                game.path.endsWith(".bin") -> "MEGADRIVE.BIN"
                game.path.endsWith(".gen") -> "GENESIS"
                game.path.endsWith(".md") -> "MEGADRIVE"
                else -> null
            }?.let {
                Ssh.command("$mbc load_rom $it \"${game.path}\"")
            }
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
                    val path = "$dir/$filename"
                    Ssh.command("sha1sum \"$path\"")
                        .let {
                            Persistence.saveGame("SNES", path, hash(it))
                        }
                }
        }

        override fun play(game: Game) {
            val mbc = Persistence.getMbcPath()
            Ssh.command("$mbc load_rom SNES \"${game.path}\"")
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
                    val path = "$dir/$filename"
                    Ssh.command("sha1sum \"$path\"")
                        .let {
                            Persistence.saveGame("TGFX16", path, hash(it))
                        }
                }
        }

        override fun play(game: Game) {
            val mbc = Persistence.getMbcPath()
            Ssh.command("$mbc load_rom TGFX16 \"${game.path}\"")
        }
    };

    abstract fun sync()

    abstract fun play(game: Game)

    protected fun hash(input: String) =
        Regex("\\b[0-9a-f]{5,40}\\b")
            .find(input)
            ?.groupValues
            ?.first()
}
