package com.tatsutron.remote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
        val menuButton = view.findViewById<Button>(R.id.menu_button)
        menuButton.setOnClickListener {
            launch(Dispatchers.IO) {
                runCatching {
                    Ssh.command("${Paths.MBC} raw_seq M")
                }
            }
        }
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
