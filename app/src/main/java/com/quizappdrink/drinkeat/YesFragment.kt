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
import com.quizappdrink.drinkeat.databinding.FragmentYesBinding
import com.quizappdrink.drinkeat.utilitas.Constances

class YesFragment : Fragment() {
    private var _binding: FragmentYesBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentStartBinding = null")

    private val args: YesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentYesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val currentType = args.quizType

        try {
            binding.btnExitQuizz.setOnClickListener {
                initAlertDialogForExitToEnterFrag()
            }
            when (currentType) {
                Constances.TYPE_QUIZ_FOOD -> {
                    binding.btnNextQuestionQuiz.setOnClickListener {
                        findNavController().navigate(R.id.action_yesFragment_to_foodQuizFragment)
                    }
                }
                Constances.TYPE_QUIZ_DRINKS -> {

                    binding.btnNextQuestionQuiz.setOnClickListener {
                        findNavController().navigate(R.id.action_yesFragment_to_drinkQuizFragment)
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

    private fun initAlertDialogForExitToEnterFrag() {
        AlertDialog.Builder(requireContext())
            .setTitle("Exit")
            .setMessage("Are you definitely want to log out, the current data will not be saved?")
            .setPositiveButton("Yes, Exit") { _, _ ->
                findNavController().navigate(R.id.action_yesFragment_to_firstFragment)
            }
            .setNegativeButton("Deny") { _, _ ->
            }
            .setCancelable(true)
            .create()
            .show()
    }

}