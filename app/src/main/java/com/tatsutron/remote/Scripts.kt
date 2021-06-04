package com.tatsutron.remote

object Scripts {

    fun sync() {
        Ssh.command("ls ${Paths.SCRIPTS}")
            .split("\n")
            .filter {
                it.endsWith(".sh")
            }
            .forEach {
                Persistence.saveScript(it)
            }
    }
}
