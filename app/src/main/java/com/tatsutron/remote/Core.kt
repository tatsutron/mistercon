package com.tatsutron.remote

import com.jcraft.jsch.ChannelSftp
import com.jcraft.jsch.Session
import java.io.File

enum class Core {

    Gameboy {
        override fun sync(session: Session) {
            sync(session, "Gameboy", listOf(".gb"))
        }

        override fun play(session: Session, game: Game) {
            play(session, "GAMEBOY", game.path)
        }
    },

    GBA {
        override fun sync(session: Session) {
            sync(session, "GBA", listOf(".gba"))
        }

        override fun play(session: Session, game: Game) {
            play(session, "GBA", game.path)
        }
    },

    Genesis {
        override fun sync(session: Session) {
            sync(session, "Genesis", listOf(".bin", ".gen", ".md"))
        }

        override fun play(session: Session, game: Game) {
            game.path.let {
                when {
                    it.endsWith(".bin") -> play(session, "MEGADRIVE.BIN", it)
                    it.endsWith(".gen") -> play(session, "GENESIS", it)
                    it.endsWith(".md") -> play(session, "MEGADRIVE", it)
                }
            }
        }
    },

    SNES {
        override fun sync(session: Session) {
            sync(session, "SNES", listOf(".sfc"))
        }

        override fun play(session: Session, game: Game) {
            play(session, "SNES", game.path)
        }
    },

    TGFX16 {
        override fun sync(session: Session) {
            sync(session, "TGFX16", listOf(".pce"))
        }

        override fun play(session: Session, game: Game) {
            play(session, "TGFX16", game.path)
        }
    };

    abstract fun sync(session: Session)

    protected fun sync(
        session: Session,
        core: String,
        extensions: List<String>,
    ) {
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
        try {
            channel.cd(path)
        } catch (exception: Throwable) {
            return
        }
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

    abstract fun play(session: Session, game: Game)

    protected fun play(session: Session, core: String, path: String) {
        val mbc = Persistence.getMbcPath()
        Ssh.command(session, "$mbc load_rom $core \"$path\"")
    }
}
