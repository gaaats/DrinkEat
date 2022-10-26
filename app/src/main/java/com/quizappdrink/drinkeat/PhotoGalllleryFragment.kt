package com.quizappdrink.drinkeat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.quizappdrink.drinkeat.databinding.FragmentPhotoGalllleryBinding
import com.quizappdrink.drinkeat.utilitas.CustomPagerAdapter

class PhotoGalllleryFragment : Fragment() {
    private var _binding: FragmentPhotoGalllleryBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentStartBinding = null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoGalllleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        try {
            binding.btnImgExit.setOnClickListener {
                initAlertDialogForExitPhotoPfagment()
            }

            val listOfImages = generateImgForPager()
            val pagerAdapter = CustomPagerAdapter(listOfImages)
            binding.pager.adapter = pagerAdapter
            binding.circleIndicatorPhotoGalery.setViewPager(binding.pager)


        } catch (e: Exception) {
            snackBarErrorPhotoFragment()
        }

        super.onViewCreated(view, savedInstanceState)
    }


    private fun snackBarErrorPhotoFragment() {
        Snackbar.make(
            binding.root,
            "Oops something went wrong, please try again...",
            Snackbar.LENGTH_LONG
        ).show()
        requireActivity().onBackPressed()
    }

    private fun initAlertDialogForExitPhotoPfagment() {
        AlertDialog.Builder(requireContext())
            .setTitle("Exit")
            .setMessage("Current data will not be saved, are you really want to log out ?")
            .setPositiveButton("Yes, Exit") { _, _ ->
                requireActivity().onBackPressed()
            }
            .setNegativeButton("Deny") { _, _ ->
            }
            .setCancelable(true)
            .create()
            .show()
    }

    private fun generateImgForPager(): List<Int> {
        return listOf(
            R.drawable.gggg,
            R.drawable.opportunity,
            R.drawable.kjoiulolo,
            R.drawable.brainstorming,
            R.drawable.think111,
            R.drawable.thinking,
            R.drawable.confusedddd,
            R.drawable.erghju,
        )
    }
}