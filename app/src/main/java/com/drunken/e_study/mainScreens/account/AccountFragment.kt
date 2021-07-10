package com.drunken.e_study.mainScreens.account

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.drunken.e_study.R
import com.drunken.e_study.databinding.FragmentAccountBinding
import com.drunken.e_study.mainScreens.MainActivity
import com.drunken.e_study.welcomeScreens.WelcomeActivity

class AccountFragment : Fragment() {

    private lateinit var binding : FragmentAccountBinding
    private val viewModel : AccountViewModel by lazy {
        ViewModelProvider(this).get(AccountViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = AccountCourseAdapter()
        binding.rvAccount.adapter = adapter

        viewModel.courses.observe(viewLifecycleOwner, {
            val tv = binding.classInfo.findViewById<TextView>(R.id.tv_class_counter)
            if(it.isNotEmpty()){
                binding.tvAccountFragment.visibility = View.VISIBLE
                tv.text = it.size.toString()
            } else {
                tv.text = "0"
                binding.tvAccountFragment.visibility = View.INVISIBLE
            }
        })

        viewModel.signOut.observe(viewLifecycleOwner, {
            if (it){
                viewModel.doneSigningOut()
                startActivity(Intent(requireContext(), WelcomeActivity::class.java))
                requireActivity().finish()
            }
        })

        viewModel.notifyEmptyCourse.observe(viewLifecycleOwner, {
            if (it){
                (activity as MainActivity).showSnackbar("Kelas kamu masih kosong! Tambahkan yuk")
                viewModel.doneNotifying()
            }
        })


        return binding.root
    }
}