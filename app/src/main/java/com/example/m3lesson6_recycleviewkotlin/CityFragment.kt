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

class CityFragment : Fragment() {
    private lateinit var binding: FragmentCountryBinding
    private lateinit var adapter: LocationAdapter
    private var cityList = arrayListOf<Location>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCountryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val countryName = arguments?.getString("countryName") ?: ""

        adapter = LocationAdapter(cityList, requireContext()) { item -> openCityDetailFragment(item) }
        binding.recycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleView.adapter = adapter

        loadCitiesForCountry(countryName)
    }

    private fun loadCitiesForCountry(countryName: String) {
        val gson = Gson()
        val inputStream = resources.openRawResource(R.raw.location_data)
        val reader = inputStream.reader()

        val continentListType = object : TypeToken<Map<String, List<Continent>>>() {}.type
        val data: Map<String, List<Continent>>? = gson.fromJson(reader, continentListType)

        data?.get("continents")?.forEach { continent ->
            continent.countries.find { it.name == countryName }?.let { country ->
                val cities = country.cities.map { City(it.name, it.imageUrl) }
                cityList.addAll(cities)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun openCityDetailFragment(city: Location) {
        val cityDetailFragment = CityDetailFragment()
        val bundle = Bundle()
        bundle.putString("cityName", city.name)
        bundle.putString("cityImageUrl", city.imageUrl)
        cityDetailFragment.arguments = bundle
        cityList.clear()

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, cityDetailFragment)
            .addToBackStack(null)
            .commit()
    }
}