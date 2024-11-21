package com.example.m3lesson6_recycleviewkotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.example.m3lesson6_recycleviewkotlin.databinding.FragmentImageFullscreenBinding

class ImageFullscreenFragment : Fragment() {
    private lateinit var binding: FragmentImageFullscreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageFullscreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cityImage = arguments?.getString("imageUrl") ?: ""
        Glide.with(binding.imageView).load(cityImage).into(binding.imageView)

        setSharedElementEnterTransition(
            TransitionInflater.from(requireContext())
                .inflateTransition(android.R.transition.move)
        )

        binding.imageView.setOnClickListener{
            parentFragmentManager.popBackStack()
        }
    }
}