package com.tatsutron.remote

object Scripts {

    fun sync() {
        val scripts = Persistence.getScriptsPath()
        Ssh.command("ls $scripts")
            .split("\n")
            .filter {
                it.endsWith(".sh")
            }
            .forEach {
                Persistence.saveScript(it)
            }
    }
}
