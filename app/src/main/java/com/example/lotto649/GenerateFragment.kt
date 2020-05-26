@file:Suppress("DEPRECATION")

package com.example.lotto649

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.lotto649.databinding.FragmentGenerateBinding


class GenerateFragment : Fragment() {

    // Set up a int that will bind to xml file
    private var revenue = 100

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentGenerateBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_generate, container, false)

        // Get reference to ViewModel
        val generateFragmentViewModel =
            ViewModelProviders.of(
                this).get(GenerateFragmentViewModel::class.java)

        binding.generateFragmentViewModel = generateFragmentViewModel

        binding.lifecycleOwner = this

        // If the lottery is finished, user either wins Payout-CostOfTicket(5) or just pays the CostOfTicket
        generateFragmentViewModel.lotteryFinish.observe(viewLifecycleOwner, Observer { finish ->
            if (finish) {
                val CostOfTicket = 5
                val payout: Int = when (generateFragmentViewModel.wins) {
                    2-> 50
                    3 -> 200
                    4 -> 1000
                    5 -> 2000
                    6 -> 10000
                    else -> 0
                }
                revenue += payout - CostOfTicket
                binding.revenue = revenue
                // A toast is shown with the gravity set slightly off to
                // the side so not to interfere with the FAB
                if (generateFragmentViewModel.wins > 1) {
                    Toast.makeText(activity, "Congrats, you won the lottery! \nYour reward is \$$payout!\"", Toast.LENGTH_SHORT).apply {setGravity(Gravity.CENTER_HORIZONTAL, -50, 660); show() }
                }
                else Toast.makeText(activity, "Better luck next time! The ticket costed $$CostOfTicket", Toast.LENGTH_SHORT).apply {setGravity(Gravity.CENTER_HORIZONTAL, -50, 660); show() }
            }
        })

        // updates the UI
        binding.revenue = revenue

        return binding.root
    }



}
