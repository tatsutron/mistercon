package com.tatsutron.remote

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
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
        setHost(view)
        setGamesPath(view)
        setMenuButton(view)
        setScriptButton(view)
        setRebootButton(view)
        Event.SYNC.subscribe {
            setScriptButton(view)
        }
    }

    private fun setHost(view: View) {
        val card = view.findViewById<EditCard>(R.id.host)
        val value = card.findViewById<EditText>(R.id.value)
        value.setText(Persistence.getHost())
        value.setSelection(value.text.length)
        value.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int,
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int,
            ) {
                Persistence.saveHost(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setGamesPath(view: View) {
        val card = view.findViewById<EditCard>(R.id.games_path)
        val value = card.findViewById<EditText>(R.id.value)
        value.setText(Persistence.getGamesPath())
        value.setSelection(value.text.length)
        value.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int,
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int,
            ) {
                Persistence.saveGamesPath(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setMenuButton(view: View) {
        val menuButton = view.findViewById<Button>(R.id.menu_button)
        menuButton.setOnClickListener {
            launch(Dispatchers.IO) {
                runCatching {
                    val session = Ssh.session()
                    val mbc = Persistence.getMbcPath()
                    Ssh.command(session, "$mbc raw_seq M")
                    session.disconnect()
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
                    val script = list.adapter.getItem(list.checkedItemPosition)
                    val path = "\"${Persistence.getScriptsPath()}/$script\""
                    launch(Dispatchers.IO) {
                        runCatching {
                            val session = Ssh.session()
                            val mbc = Persistence.getMbcPath()
                            Ssh.command(session, "$mbc load_rom SCRIPT $path")
                            session.disconnect()
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
                    val session = Ssh.session()
                    Ssh.command(session, "reboot now")
                    session.disconnect()
                }
            }
        }
    }
}
