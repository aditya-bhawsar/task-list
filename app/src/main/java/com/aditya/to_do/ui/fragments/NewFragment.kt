package com.aditya.to_do.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aditya.to_do.R
import com.aditya.to_do.databinding.FragmentNewBinding
import com.aditya.to_do.model.TaskModel
import com.aditya.to_do.util.Utils.parsePriority
import com.aditya.to_do.util.Utils.verifyData
import com.aditya.to_do.viewModel.NewViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewFragment : Fragment(R.layout.fragment_new) {

    private val mNewViewModel: NewViewModel by viewModels()

    private var _binding : FragmentNewBinding? = null
    private val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewBinding.bind(view)

        binding.apply {
            val options: Array<String> = resources.getStringArray(R.array.priorities)
            val adapter: ArrayAdapter<String> = ArrayAdapter(requireContext(), R.layout.dropdown_item, options)
            optionDropdown.setAdapter(adapter)
            optionDropdown.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id -> val selected = position+1 }
            saveFab.setOnClickListener { insertData() }
        }
    }

    private fun insertData() {
        val mTitle = binding.titleEt.text.toString()
        val mPriority = binding.optionDropdown.text.toString()
        val mDescription = binding.descEt.text.toString()

        val validation = verifyData(mTitle,mDescription,mPriority)
        if(validation){
            val data = TaskModel(
                0,
                mTitle,
                mDescription,
                parsePriority(mPriority)
            )
            mNewViewModel.insertData(data)
            Toast.makeText(requireContext(),"Added SuccessFully", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_newFragment_to_listingFragment)
        }else{ Toast.makeText(requireContext(),"Please Fill all data", Toast.LENGTH_LONG).show() }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}