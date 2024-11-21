package com.example.m3lesson6_recycleviewkotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.m3lesson6_recycleviewkotlin.databinding.FragmentCountryBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CountryFragment : Fragment() {
    private lateinit var binding: FragmentCountryBinding
    private lateinit var adapter: LocationAdapter
    private var countryList = arrayListOf<Location>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCountryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val continentName = arguments?.getString("continentName") ?: ""

        adapter = LocationAdapter(countryList, requireContext()) { item -> openCityFragment(item) }
        binding.recycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleView.adapter = adapter

        loadCountriesForContinent(continentName)
    }

    private fun loadCountriesForContinent(continentName: String) {
        val gson = Gson()
        val inputStream = resources.openRawResource(R.raw.location_data)
        val reader = inputStream.reader()

        val continentListType = object : TypeToken<Map<String, List<Continent>>>() {}.type
        val data: Map<String, List<Continent>>? = gson.fromJson(reader, continentListType)

        data?.get("continents")?.let { continents ->
            continents.find { it.name == continentName }?.let { continent ->
                val countries = continent.countries.map { Country(it.name, it.imageUrl, emptyList()) }
                countryList.addAll(countries)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun openCityFragment(country: Location) {
        val cityFragment = CityFragment()
        val bundle = Bundle()
        bundle.putString("countryName", country.name)
        cityFragment.arguments = bundle
        countryList.clear()

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, cityFragment)
            .addToBackStack(null)
            .commit()
    }
}