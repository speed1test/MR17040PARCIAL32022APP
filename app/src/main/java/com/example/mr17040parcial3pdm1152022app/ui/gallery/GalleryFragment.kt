package com.example.mr17040parcial3pdm1152022app.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mr17040parcial3pdm1152022app.databinding.FragmentGalleryBinding
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result


class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        val btn_test: Button = binding.buttonApiTest
        btn_test.setOnClickListener{
            Fuel.get(
                "http://pdm-115.duckdns.org/vehiculo",
                listOf("licencia" to "Prueba")
            ).responseJson { _, _, result ->
                when (result) {
                    is Result.Failure -> {
                        textView.text = result.getException().toString()
                    }
                    is Result.Success -> {
                        val data = result.get().obj()
                        textView.text = data.getString("licencia")
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