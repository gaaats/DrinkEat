package com.quizappdrink.drinkeat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.quizappdrink.drinkeat.databinding.FragmentFirstBinding
import com.quizappdrink.drinkeat.databinding.FragmentHelpBinding
import com.quizappdrink.drinkeat.vievmodel.MainVievMooodel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HelpFragment : Fragment() {
    private var _binding: FragmentHelpBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentStartBinding = null")

    private val vievModel by activityViewModels<MainVievMooodel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHelpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        try {
            binding.btnImgExitHelpFrag.setOnClickListener {
                initAlertDialogForExitHelpFrag()
            }
            binding.btnAppInfo.setOnClickListener {
                findNavController().navigate(R.id.action_helpFragment_to_someInfoFragment)
            }
            binding.btnRules.setOnClickListener {
                findNavController().navigate(R.id.action_helpFragment_to_rulesQuizzzzFragment)
            }
            binding.btnGalleryPhotos.setOnClickListener {
                findNavController().navigate(R.id.action_helpFragment_to_photoGalllleryFragment)
            }

        } catch (e: Exception) {
            snackBarErrorHelpFragment()
        }

        super.onViewCreated(view, savedInstanceState)
    }


    private fun snackBarErrorHelpFragment() {
        Snackbar.make(
            binding.root,
            "Oops something went wrong, please try again...",
            Snackbar.LENGTH_LONG
        ).show()
        requireActivity().onBackPressed()
    }

    private fun initAlertDialogForExitHelpFrag() {
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