package com.drunken.e_study.ui.mainScreens.payment.paymentInfo

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.drunken.e_study.R
import com.drunken.e_study.databinding.FragmentPaymentInfoBinding
import kotlin.random.Random

class PaymentInfoFragment : Fragment() {

    private lateinit var binding: FragmentPaymentInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentInfoBinding.inflate(inflater)
        val args = PaymentInfoFragmentArgs.fromBundle(requireArguments())
        val randomAccountNum = (100000000000000..999999999999999).random().toString()

        binding.totalPriceTv.text = args.totalPriceString
        binding.tvMethod.text = args.method
        binding.tvNoRekening.text = randomAccountNum

        binding.barPetunjukTfAtm.setOnClickListener {
            when (binding.tipsTfAtm.visibility) {
                View.GONE -> {
                    binding.tipsTfAtm.visibility = View.VISIBLE
                    binding.panahBarPetunjukTfAtm.setImageResource(R.drawable.ic_eva_arrow_ios_back_outline_up)
                }
                View.VISIBLE -> {
                    binding.tipsTfAtm.visibility = View.GONE
                    binding.panahBarPetunjukTfAtm.setImageResource(R.drawable.ic_eva_arrow_ios_back_outline)
                }
            }
        }

        binding.barPetunjukTfMbanking.setOnClickListener {
            when (binding.tipsTfMbanking.visibility) {
                View.GONE -> {
                    binding.tipsTfMbanking.visibility = View.VISIBLE
                    binding.panahBarPetunjukTfMbanking.setImageResource(R.drawable.ic_eva_arrow_ios_back_outline_up)
                }
                View.VISIBLE -> {
                    binding.tipsTfMbanking.visibility = View.GONE
                    binding.panahBarPetunjukTfMbanking.setImageResource(R.drawable.ic_eva_arrow_ios_back_outline)
                }
            }
        }

        binding.okBtn.setOnClickListener {
            findNavController().navigate(PaymentInfoFragmentDirections.actionPaymentInfoFragmentToPaymentInfo2ndFragment(args.method,
                args.totalPriceString, randomAccountNum))
        }

        binding.navigateUpBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }
}