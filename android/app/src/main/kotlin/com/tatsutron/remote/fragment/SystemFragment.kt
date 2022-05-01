package com.tatsutron.remote.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.tatsutron.remote.R
import com.tatsutron.remote.util.*

class SystemFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_scan, menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_system, container, false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.scan -> {
                Navigator.showScreen(
                    activity as AppCompatActivity,
                    FragmentMaker.scan(),
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(view?.findViewById(R.id.toolbar))
            supportActionBar?.title = context?.getString(R.string.system)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHost(view)
        setCreditsButton(view)
        setScriptsButton(view)
        setMenuButton(view)
    }

    private fun setHost(view: View) {
        view.findViewById<TextInputEditText>(R.id.host_or_ip_text).apply {
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
                    Persistence.saveConfig(
                        Persistence.getConfig()!!.copy(
                            host = s.toString(),
                        ),
                    )
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }

    private fun setCreditsButton(view: View) {
        view.findViewById<Button>(R.id.credits_button).apply {
            setOnClickListener {
                Navigator.showScreen(
                    activity as AppCompatActivity,
                    FragmentMaker.credits(),
                )
            }
        }
    }

    private fun setScriptsButton(view: View) {
        view.findViewById<Button>(R.id.scripts_button).apply {
            setOnClickListener {
                Navigator.showScreen(
                    activity as AppCompatActivity,
                    FragmentMaker.scriptList(),
                )
            }
        }
    }

    private fun setMenuButton(view: View) {
        view.findViewById<Button>(R.id.menu_button).apply {
            setOnClickListener {
                Navigator.showLoadingScreen()
                Coroutine.launch(
                    activity = requireActivity(),
                    run = {
                        val session = Ssh.session()
                        Assets.require(requireContext(), session, "mbc")
                        Ssh.command(session, "${Constants.MBC_PATH} raw_seq M")
                        session.disconnect()
                    },
                    finally = {
                        Navigator.hideLoadingScreen()
                    },
                )
            }
        }
    }
}
