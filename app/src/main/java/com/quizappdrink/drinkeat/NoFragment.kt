package com.quizappdrink.drinkeat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.quizappdrink.drinkeat.databinding.FragmentNoBinding
import com.quizappdrink.drinkeat.utilitas.Constances

class NoFragment : Fragment() {
    private var _binding: FragmentNoBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentStartBinding = null")

    private val args: NoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val currentType = args.quizzzzzType

        try {
            binding.btnExitNo.setOnClickListener {
                initAlertDialogForExitAll()
            }

            when (currentType) {
                Constances.TYPE_QUIZ_FOOD -> {
                    binding.btnNextQuestionNo.setOnClickListener {
                        findNavController().navigate(R.id.action_noFragment_to_foodQuizFragment)
                    }
                }
                Constances.TYPE_QUIZ_DRINKS -> {
                    binding.btnNextQuestionNo.setOnClickListener {
                        findNavController().navigate(R.id.action_noFragment_to_drinkQuizFragment)
                    }

                }
            }

        } catch (e: Exception) {
            snackBarError()
        }

        super.onViewCreated(view, savedInstanceState)
    }


    private fun snackBarError() {
        Snackbar.make(
            binding.root,
            "Oops something went wrong, please try again...",
            Snackbar.LENGTH_LONG
        ).show()
        requireActivity().onBackPressed()
    }

    private fun initAlertDialogForExitAll() {
        AlertDialog.Builder(requireContext())
            .setTitle("Exit")
            .setMessage("The current data will not be saved, are you definitely want to log out?")
            .setPositiveButton("Yes, Exit") { _, _ ->
                findNavController().navigate(R.id.action_noFragment_to_firstFragment)
            }
            .setNegativeButton("No") { _, _ ->
            }
            .setCancelable(true)
            .create()
            .show()
    }
}