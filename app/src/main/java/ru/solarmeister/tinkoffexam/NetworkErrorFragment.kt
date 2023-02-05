package ru.solarmeister.tinkoffexam

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import ru.solarmeister.tinkoffexam.databinding.FragmentNetworkErrorBinding
import ru.solarmeister.tinkoffexam.listfilms.ListFilmsFragment

class NetworkErrorFragment : Fragment() {

    private lateinit var binding: FragmentNetworkErrorBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNetworkErrorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnNetworkRepeat.setOnClickListener {
            if (requireContext().isNetworkConnected()) {
                parentFragmentManager.commit {
                    setReorderingAllowed(true)
                    add<ListFilmsFragment>(R.id.fragment_container)
                    remove(this@NetworkErrorFragment)
                }
            }
        }
    }

}