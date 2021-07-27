package com.drunken.e_study.ui.mainScreens.account

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.drunken.e_study.R
import com.drunken.e_study.database.Database
import com.drunken.e_study.databinding.FragmentAccountBinding
import com.drunken.e_study.ui.mainScreens.MainActivity
import com.drunken.e_study.ui.welcomeScreens.WelcomeActivity

class AccountFragment : Fragment() {

    private lateinit var binding : FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val application = requireActivity().application
        val viewModelFactory = AccountViewModelFactory(Database.getInstance(application).userDatabaseDao, Database.getInstance(application).courseDatabaseDAO)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(AccountViewModel::class.java)

        binding.viewModel = viewModel

        val adapter = AccountCourseAdapter(AccountCourseListener { courseId ->
            findNavController().navigate(AccountFragmentDirections.actionAccountFragmentToCourseDetailFragment2(courseId))
        })

        binding.rvAccount.adapter = adapter

        viewModel.notifyEmptyList.observe(viewLifecycleOwner, {
            if (it == true){
                (activity as MainActivity).showSnackbar("Kelas kamu masih kosong! Tambahkan yuk")
                viewModel.doneNotifyingEmpty()
            }
        })

        viewModel.user.observe(viewLifecycleOwner, {
            it?.let {
                viewModel.refreshUserData()
            }
        })

        viewModel.course.observe(viewLifecycleOwner, {
            val tv = binding.classInfo.findViewById<TextView>(R.id.tv_class_counter)
            if(!it.isNullOrEmpty()){
                binding.tvAccountFragment.visibility = View.VISIBLE
                tv.text = it.size.toString()
            } else {
                tv.text = "0"
                binding.tvAccountFragment.visibility = View.INVISIBLE
            }
        })

        viewModel.signOut.observe(viewLifecycleOwner, {
            if (it){
                val firstRun = requireActivity().getSharedPreferences("PREFERENCE",
                    Context.MODE_PRIVATE
                ).getBoolean("firstrun", true)
                if (!firstRun){
                    requireActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit().putBoolean("firstrun", true).apply()
                }
                viewModel.doneSigningOut()
                startActivity(Intent(requireContext(), WelcomeActivity::class.java))
                requireActivity().finish()
            }
        })

        return binding.root
    }
}