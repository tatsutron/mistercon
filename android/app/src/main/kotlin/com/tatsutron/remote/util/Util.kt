import android.content.Context
import com.tatsutron.remote.util.Assets
import com.tatsutron.remote.util.Constants
import com.tatsutron.remote.util.Ssh

object Util {

    fun listFiles(
        context: Context,
        extensions: String,
        path: String,
    ): List<String> {
        val session = Ssh.session()
        Assets.require(context, session, "mister_util.py")
        val command = StringBuilder().apply {
            append("python3 ${Constants.MISTER_UTIL_PATH} list")
            append(" ")
            append("\"${path}\" $extensions")
        }.toString()
        val output = Ssh.command(session, command)
        session.disconnect()
        return output.split(";")
    }
}
