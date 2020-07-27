package com.example.android.trackmysleepquality.sleepdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.databinding.FragmentSleepDetailBinding


class SleepDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        //obtem a referencia ao objeto de ligação
        val binding: FragmentSleepDetailBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_sleep_detail, container, false)

        val application = requireNotNull(this.activity).application

        val arguments = SleepDetailFragmentArgs.fromBundle(arguments!!)

        // cria uma instancia no viewModel
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
        val viewModelFactory = SleepDetailViewModelFactory(arguments.sleepNightKey, dataSource)


        val sleepDetailViewModel =
                ViewModelProviders.of(
                        this, viewModelFactory).get(SleepDetailViewModel::class.java)

        //precisa passar ao objeto uma refeerencia
        binding.sleepDetailViewModel = sleepDetailViewModel

        binding.setLifecycleOwner(this)

        // add um Observer a variavel para nanvegar quando um icone de qualidade é tocado
        sleepDetailViewModel.navigateToSleepTracker.observe(this, Observer {
            if (it == true) {
                this.findNavController().navigate(
                        SleepDetailFragmentDirections.actionSleepDetailFragmentToSleepTrackerFragment())
                // garante q seja feita somente uma vez a navegação, mesmo com alteração na confg
                sleepDetailViewModel.doneNavigating()
            }
        })

        return binding.root
    }
}
