package com.drunken.e_study.ui.mainScreens.payment.paymentInfo

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.drunken.e_study.R
import com.drunken.e_study.databinding.FragmentPaymentInfo2ndBinding
import java.util.concurrent.TimeUnit

class PaymentInfo2ndFragment : Fragment() {

    private lateinit var binding : FragmentPaymentInfo2ndBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentInfo2ndBinding.inflate(inflater)
        val args = PaymentInfo2ndFragmentArgs.fromBundle(requireArguments())

        object : CountDownTimer(86400000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                binding.apply {
                    tvHoursLeft.text = (TimeUnit.MILLISECONDS.toHours(millisUntilFinished)%60).toString()
                    tvMinutesLeft.text = (TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)%60).toString()
                    tvSecondsLeft.text = (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                    )).toString()
                }
            }
            override fun onFinish() {
                Toast.makeText(requireContext(), "Waktu transfer habis, silahkan konfirmasi ulang", Toast.LENGTH_LONG).show()
            }
        }.start()

        binding.apply {
            tvNoRekening.text = args.accountNumString
            tvMethod.text = args.method
            totalPriceTv.text = args.totalPriceString
        }

        binding.navigateUpBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.okBtn.setOnClickListener {
            findNavController().navigate(PaymentInfo2ndFragmentDirections.actionPaymentInfo2ndFragmentToProcessPaymentFragment())
        }

        return binding.root
    }
}