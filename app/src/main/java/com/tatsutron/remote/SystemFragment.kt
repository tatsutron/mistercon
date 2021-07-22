package com.tatsutron.remote

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
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
        setCreditsButton(view)
        setRebootButton(view)
        setScriptsButton(view)
        setMenuButton(view)
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

    private fun setCreditsButton(view: View) {
        view.findViewById<Button>(R.id.credits_button).apply {
            setOnClickListener {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_NONE)
                    .add(R.id.root, FragmentMaker.credits())
                    .addToBackStack(null)
                    .commit()
            }
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

    private fun setScriptsButton(view: View) {
        view.findViewById<Button>(R.id.scripts_button).apply {
            setOnClickListener {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_NONE)
                    .add(R.id.root, FragmentMaker.scriptList())
                    .addToBackStack(null)
                    .commit()
            }
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
}
