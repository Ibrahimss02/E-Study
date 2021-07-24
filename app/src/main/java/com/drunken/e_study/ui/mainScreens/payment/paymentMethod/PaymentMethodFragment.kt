package com.drunken.e_study.ui.mainScreens.payment.paymentMethod

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.drunken.e_study.R
import com.drunken.e_study.databinding.FragmentPaymentMethodBinding

class PaymentMethodFragment : Fragment() {

    private lateinit var binding : FragmentPaymentMethodBinding

    private var titleList : List<String>? = null
    private val paymentMethods : HashMap<String, List<Pair<Int, String>>>
    get() {
        val listData = HashMap<String, List<Pair<Int, String>>>()

        val transferBank = arrayListOf(
            Pair(R.drawable.ic_icon_park_bank_card, "Bank Mandiri"),
            Pair(R.drawable.ic_icon_park_bank_card, "Bank BCA"),
            Pair(R.drawable.ic_icon_park_bank_card, "Bank BRI"),
            Pair(R.drawable.ic_icon_park_bank_card, "Bank Jatim")
        )

        val debitKredit = arrayListOf(
            Pair(R.drawable.ic_dashicons_insert, "Tambahkan Kartu Kredit")
        )

        val dompetDigital = arrayListOf(
            Pair(R.drawable.ic_icon_park_bank_card, "OVO"),
            Pair(R.drawable.ic_icon_park_bank_card, "DANA"),
            Pair(R.drawable.ic_icon_park_bank_card, "Link Aja"),
            Pair(R.drawable.ic_icon_park_bank_card, "GoPay")
        )

        val bayarCicil = arrayListOf(
            Pair(R.drawable.ic_icon_park_bank_card, "Kredivo"),
            Pair(R.drawable.ic_icon_park_bank_card, "cicil.co.id")
        )

        listData.apply {
            this["Transfer Bank"] = transferBank
            this["Debit/Kredit Online"] = debitKredit
            this["Dompet Digital"] = dompetDigital
            this["Bayar Cicil"] = bayarCicil
        }

        return listData
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentMethodBinding.inflate(inflater)

        val listData = paymentMethods
        titleList = ArrayList(listData.keys)

        val adapter = MethodsListAdapter(requireContext(), titleList as ArrayList<String>, listData, MethodClickListener {
            findNavController().navigate(PaymentMethodFragmentDirections.actionPaymentMethodFragmentToConfirmPaymentFragment(it))
        })
        binding.paymentMethodExpandableList.setAdapter(adapter)

        return binding.root
    }
}