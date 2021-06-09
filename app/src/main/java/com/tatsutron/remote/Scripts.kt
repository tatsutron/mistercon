package com.tatsutron.remote

import com.jcraft.jsch.Session

object Scripts {

    fun sync(session: Session) {
        val scripts = Persistence.getScriptsPath()
        Ssh.command(session, "ls $scripts")
            .split("\n")
            .filter {
                it.endsWith(".sh")
            }
            .forEach {
                Persistence.saveScript(it)
            }
    }
}
