package com.quizappdrink.drinkeat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.quizappdrink.drinkeat.databinding.FragmentPhotoGalllleryBinding
import com.quizappdrink.drinkeat.databinding.FragmentRulesQuizzzzBinding
import com.quizappdrink.drinkeat.utilitas.CustomPagerAdapter

class RulesQuizzzzFragment : Fragment() {
    private var _binding: FragmentRulesQuizzzzBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentStartBinding = null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRulesQuizzzzBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        try {
            binding.btnImgExitRulesGame.setOnClickListener {
                initAlertDialogForExitGameRules()
            }
            binding.btnUnderstandGameRules.setOnClickListener {
                findNavController().navigate(R.id.action_rulesQuizzzzFragment_to_firstFragment)
            }



        } catch (e: Exception) {
            snackBarErrorRules()
        }

        super.onViewCreated(view, savedInstanceState)
    }


    private fun snackBarErrorRules() {
        Snackbar.make(
            binding.root,
            "Oops something went wrong, please try again...",
            Snackbar.LENGTH_LONG
        ).show()
        requireActivity().onBackPressed()
    }

    private fun initAlertDialogForExitGameRules() {
        AlertDialog.Builder(requireContext())
            .setTitle("Exit")
            .setMessage("Current data will not be saved, are you really want to log out ?")
            .setPositiveButton("Yes, Exit") { _, _ ->
                findNavController().navigate(R.id.action_rulesQuizzzzFragment_to_firstFragment)
            }
            .setNegativeButton("Deny") { _, _ ->
            }
            .setCancelable(true)
            .create()
            .show()
    }

}