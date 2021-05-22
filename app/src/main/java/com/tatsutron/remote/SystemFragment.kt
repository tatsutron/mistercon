package com.tatsutron.remote

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
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
        val host = view.findViewById<EditText>(R.id.host)
        val refresh = {
            host.setText(Persistence.getHost())
            host.setSelection(host.text.length)
        }
        Event.HOST_SET.subscribe(refresh)
        refresh()
        host.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                Persistence.saveHost(s.toString())
            }

            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int,
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int,
            ) {
            }
        })
        val menuButton = view.findViewById<Button>(R.id.menu_button)
        menuButton.setOnClickListener {
            launch(Dispatchers.IO) {
                runCatching {
                    Ssh.command("${Paths.MBC} raw_seq M")
                }
            }
        }
    }
}
