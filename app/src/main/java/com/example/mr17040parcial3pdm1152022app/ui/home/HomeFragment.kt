package com.example.mr17040parcial3pdm1152022app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mr17040parcial3pdm1152022app.databinding.FragmentHomeBinding
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        val textView2: TextView = binding.textHome2
        val textView3: TextView = binding.textHome3
        val textView4: TextView = binding.textHome4
        val textView5: TextView = binding.textHome5
        val btn_test: Button = binding.buttonApiTest
        btn_test.setOnClickListener{
            Fuel.get(
                "http://pdm-115.duckdns.org/conductor/",
                listOf("licencia" to textView.text, "nombre" to textView2.text, "edad" to textView3.text, "estado_civil" to textView4, "tipo_licencia" to textView5)
            ).responseJson { _, _, result ->
                when (result) {
                    is Result.Failure -> {
                        textView.text = result.getException().toString()
                    }
                    is Result.Success -> {
                        val data = result.get().obj()
                        //textView.text = data.getString("licencia")
                        textView.text = result.get().obj().getString("licencia")
                        textView2.text = result.get().obj().getString("nombre")
                        textView3.text = result.get().obj().getString("edad")
                        textView4.text = result.get().obj().getString("estado_civil")
                        textView5.text = result.get().obj().getString("tipo_licencia")
                    }
                }
            }

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}