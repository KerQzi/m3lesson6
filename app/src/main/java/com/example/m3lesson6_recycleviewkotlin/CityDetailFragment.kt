package com.example.m3lesson6_recycleviewkotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.m3lesson6_recycleviewkotlin.databinding.FragmentCityDetailBinding


class CityDetailFragment : Fragment() {
    private lateinit var binding: FragmentCityDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCityDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cityName = CityDetailFragmentArgs.fromBundle(requireArguments()).name
        val cityImage = CityDetailFragmentArgs.fromBundle(requireArguments()).image

        setParametersForCityDetail(cityName, cityImage)
        openImageFullscreen()
    }

    private fun setParametersForCityDetail(cityName: String, cityImage: String){
        binding.textViewName.setText(cityName)
        Glide.with(binding.imageView).load(cityImage).into(binding.imageView)
    }

    private fun openImageFullscreen(){
        binding.imageView.setOnClickListener{
//            val imageFullscreenFragment = ImageFullscreenFragment()
//            val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
////            val bundle = Bundle()
            val cityImage = CityDetailFragmentArgs.fromBundle(requireArguments()).image
//
//            transaction.addSharedElement(binding.imageView, "sharedImage")
//            transaction.replace(R.id.fragment_container_view, imageFullscreenFragment)
//            transaction.addToBackStack(null)
//            transaction.commit()
//
////            bundle.putString("imageUrl", cityImage)
////            imageFullscreenFragment.arguments = bundle
////
////            parentFragmentManager.beginTransaction()
////                .replace(R.id.fragment_container_view, imageFullscreenFragment)
////                .addToBackStack(null)
////                .commit()
            val action = CityDetailFragmentDirections.actionCityDetailFragmentToImageFullscreenFragment(cityImage)
            findNavController().navigate(action)
        }
    }
}