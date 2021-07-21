package com.tatsutron.remote

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
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
        setMenuButton(view)
        setScriptButton(view)
        setRebootButton(view)
    }

    private fun setHost(view: View) {
        view.findViewById<TextInputEditText>(R.id.host_text).apply {
            setText(Persistence.getConfig()?.host)
            text?.let {
                setSelection(it.length)
            }
            addTextChangedListener(object : TextWatcher {
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
    }

    private fun setMenuButton(view: View) {
        val menuButton = view.findViewById<Button>(R.id.menu_button)
        menuButton.setOnClickListener {
            launch(Dispatchers.IO) {
                runCatching {
                    val session = Ssh.session()
                    Ssh.command(session, "${Constants.MBC_PATH} raw_seq M")
                    session.disconnect()
                }
            }
        }
    }

    private fun setScriptButton(view: View) {
//        val scriptButton = view.findViewById<Button>(R.id.script_button)
//        scriptButton.setOnClickListener {
//            val context = requireContext()
//            MaterialAlertDialogBuilder(context)
//                .setTitle(context.getString(R.string.script))
//                .setNegativeButton(
//                    context.getString(R.string.cancel),
//                    null, // listener
//                )
//                .setPositiveButton(
//                    context.getString(R.string.run)
//                ) { dialog, _ ->
//                    val list = (dialog as AlertDialog).listView
//                    val script = list.adapter.getItem(list.checkedItemPosition)
//                            as? String
//                    val path = File(Constants.SCRIPTS_PATH, script).path
//                    launch(Dispatchers.IO) {
//                        runCatching {
//                            val session = Ssh.session()
//                            Scripts.run(session, path)
//                            session.disconnect()
//                        }
//                    }
//                }
//                .setSingleChoiceItems(
//                    Persistence.getScriptList().toTypedArray(),
//                    0, // checkedItem
//                    null, // listener
//                )
//                .show()
//        }
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
