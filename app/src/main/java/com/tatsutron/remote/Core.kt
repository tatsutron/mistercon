package com.tatsutron.remote

import com.jcraft.jsch.ChannelSftp
import java.io.File

enum class Core {

    Gameboy {
        override fun sync() {
            sync("Gameboy", listOf(".gb"))
        }

        override fun play(game: Game) {
            play("GAMEBOY", game.path)
        }
    },

    GBA {
        override fun sync() {
            sync("GBA", listOf(".gba"))
        }

        override fun play(game: Game) {
            play("GBA", game.path)
        }
    },

    Genesis {
        override fun sync() {
            sync("Genesis", listOf(".bin", ".gen", ".md"))
        }

        override fun play(game: Game) {
            game.path.let {
                when {
                    it.endsWith(".bin") -> play("MEGADRIVE.BIN", it)
                    it.endsWith(".gen") -> play("GENESIS", it)
                    it.endsWith(".md") -> play("MEGADRIVE", it)
                }
            }
        }
    },

    SNES {
        override fun sync() {
            sync("SNES", listOf(".sfc"))
        }

        override fun play(game: Game) {
            play("SNES", game.path)
        }
    },

    TGFX16 {
        override fun sync() {
            sync("TGFX16", listOf(".pce"))
        }

        override fun play(game: Game) {
            play("TGFX16", game.path)
        }
    };

    abstract fun sync()

    protected fun sync(core: String, extensions: List<String>) {
        val session = Ssh.session()
        val channel = Ssh.sftp(session)
        scan(
            channel = channel,
            root = File("${Persistence.getGamesPath()}", core).path,
            extensions = extensions,
        ).forEach { path ->
            Ssh.command(session, "sha1sum \"$path\"")
                .let {
                    Persistence.saveGame(core, path, hash(it))
                }
        }
        channel.disconnect()
        session.disconnect()
    }

    protected fun scan(
        channel: ChannelSftp,
        root: String,
        extensions: List<String>,
    ): List<String> {
        val entries = mutableListOf<String>()
        scan(channel, root, entries, extensions)
        return entries.toList()
    }

    protected fun scan(
        channel: ChannelSftp,
        path: String,
        list: MutableList<String>,
        extensions: List<String>,
    ) {
        channel.cd(path)
        channel.ls(".")
            .map { it as ChannelSftp.LsEntry }
            .forEach {
                if (it.attrs.isDir) {
                    if (it.filename !in listOf(".", "..")) {
                        scan(
                            channel,
                            File(channel.pwd(), it.filename).path,
                            list,
                            extensions,
                        )
                    }
                } else if (
                    !it.filename.startsWith(".") &&
                    extensions.any { ext -> it.filename.endsWith(ext) }
                ) {
                    list.add(File(channel.pwd(), it.filename).path)
                }
            }
    }

    protected fun hash(input: String) =
        Regex("\\b[0-9a-f]{5,40}\\b")
            .find(input)
            ?.groupValues
            ?.first()

    abstract fun play(game: Game)

    protected fun play(core: String, path: String) {
        val mbc = Persistence.getMbcPath()
        Ssh.command("$mbc load_rom $core \"$path\"")
    }
}
