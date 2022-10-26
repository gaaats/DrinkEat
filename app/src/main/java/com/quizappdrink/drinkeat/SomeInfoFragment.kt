package com.quizappdrink.drinkeat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.quizappdrink.drinkeat.databinding.FragmentSomeInfoBinding
import com.quizappdrink.drinkeat.vievmodel.MainVievMooodel

class SomeInfoFragment : Fragment() {
    private var _binding: FragmentSomeInfoBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentStartBinding = null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSomeInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        try {
            binding.btnImgExitOnAbotApp.setOnClickListener {
                initAlertDialogForExitSomeInfoFrag()
            }

        } catch (e: Exception) {
            snackBarErrorSomeInfoFrag()
        }

        super.onViewCreated(view, savedInstanceState)
    }


    private fun snackBarErrorSomeInfoFrag() {
        Snackbar.make(
            binding.root,
            "Oops something went wrong, please try again...",
            Snackbar.LENGTH_LONG
        ).show()
        requireActivity().onBackPressed()
    }

    private fun initAlertDialogForExitSomeInfoFrag() {
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

}