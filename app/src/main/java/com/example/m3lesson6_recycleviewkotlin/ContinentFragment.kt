package com.example.m3lesson6_recycleviewkotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.m3lesson6_recycleviewkotlin.databinding.ActivityMainBinding
import com.example.m3lesson6_recycleviewkotlin.databinding.FragmentContinentBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable


class ContinentFragment : Fragment() {
    private lateinit var binding: FragmentContinentBinding
    private lateinit var adapter: LocationAdapter
    private var continentList = arrayListOf<Location>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding = FragmentContinentBinding.inflate(layoutInflater)
            return binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = LocationAdapter(continentList, requireContext()){
            item -> openCountryFragment(item)
        }
        binding.recycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleView.adapter = adapter

        loadContinents()
    }

    private fun openCountryFragment(continent: Location) {
//        val countryFragment = CountryFragment()
//        val bundle = Bundle()
//        bundle.putString("continentName", continent.name)
//        countryFragment.arguments = bundle

//
//        requireActivity().supportFragmentManager.beginTransaction()
//            .replace(R.id.fragment_container_view, countryFragment)
//            .addToBackStack(null)
//            .commit()
        val continentName = continent.name
        val action = ContinentFragmentDirections.actionContinentFragmentToCountryFragment(continentName)
        findNavController().navigate(action)
        continentList.clear()
    }

    private fun loadContinents() {
        val gson = Gson()
        val inputStream = resources.openRawResource(R.raw.location_data)
        val reader = inputStream.reader()

        val continentListType = object : TypeToken<Map<String, List<Continent>>>() {}.type
        val data: Map<String, List<Continent>>? = gson.fromJson(reader, continentListType)

        data?.get("continents")?.let { continents ->
            continentList.addAll(continents)
            adapter.notifyDataSetChanged()
        }
    }
}