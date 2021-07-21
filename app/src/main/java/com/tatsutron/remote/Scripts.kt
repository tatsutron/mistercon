package com.tatsutron.remote

import com.jcraft.jsch.Session

object Scripts {

    fun sync(session: Session) {
        Ssh.command(session, "ls ${Persistence.getConfig()?.scriptsPath}")
            .split("\n")
            .filter {
                it.endsWith(".sh")
            }
            .forEach {
                Persistence.saveScript(it)
            }
    }

    fun run(session: Session, path: String) {
        Ssh.command(session, "${Constants.MBC_PATH} load_rom SCRIPT $path")
    }
}
