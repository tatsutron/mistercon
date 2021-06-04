package com.tatsutron.remote

import androidx.appcompat.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class SystemFragment : Fragment(), CoroutineScope by MainScope() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_system, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMenuButton(view)
        setScriptButton(view)
        setRebootButton(view)
        Event.SYNC.subscribe {
            setScriptButton(view)
        }
    }

    private fun setMenuButton(view: View) {
        val menuButton = view.findViewById<Button>(R.id.menu_button)
        menuButton.setOnClickListener {
            launch(Dispatchers.IO) {
                runCatching {
                    Ssh.command("${Paths.MBC} raw_seq M")
                }
            }
        }
    }

    private fun setScriptButton(view: View) {
        val scripts = Persistence.getScriptList()
        val scriptButton = view.findViewById<Button>(R.id.script_button)
        scriptButton.visibility = if (scripts.isNotEmpty()) {
            View.VISIBLE
        } else {
            View.GONE
        }
        scriptButton.setOnClickListener {
            val context = requireContext()
            MaterialAlertDialogBuilder(context)
                .setTitle(context.getString(R.string.script))
                .setNegativeButton(
                    context.getString(R.string.cancel),
                    null, // listener
                )
                .setPositiveButton(
                    context.getString(R.string.run)
                ) { dialog, _ ->
                    val list = (dialog as AlertDialog).listView
                    val script =
                        list.adapter.getItem(list.checkedItemPosition)
                    val path = "\"${Paths.SCRIPTS}/$script\""
                    launch(Dispatchers.IO) {
                        runCatching {
                            Ssh.command("${Paths.MBC} load_rom SCRIPT $path")
                        }
                    }
                }
                .setSingleChoiceItems(
                    Persistence.getScriptList().toTypedArray(),
                    0, // checkedItem
                    null, // listener
                )
                .show()
        }
    }

    private fun setRebootButton(view: View) {
        val rebootButton = view.findViewById<Button>(R.id.reboot_button)
        rebootButton.setOnClickListener {
            launch(Dispatchers.IO) {
                runCatching {
                    Ssh.command("reboot now")
                }
            }
        }
    }
}
