package com.example.android.trackmysleepquality.sleepquality

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
import com.example.android.trackmysleepquality.databinding.FragmentSleepQualityBinding

//exibe uma lista de icons clicaveis (qualidade do sono)| depois de escolhido vai para o banco
class SleepQualityFragment : Fragment() {

    //chama quando o fragmente sta pronto
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // obtem uma referencia ao objeto de ligação
        val binding: FragmentSleepQualityBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_sleep_quality, container, false)

        val application = requireNotNull(this.activity).application

        val arguments = SleepQualityFragmentArgs.fromBundle(arguments!!)

        // cria uma instacia no viewModel
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
        val viewModelFactory = SleepQualityViewModelFactory(arguments.sleepNightKey, dataSource)

        // pega uma referencia do ViewModel.
        val sleepQualityViewModel =
                ViewModelProviders.of(
                        this, viewModelFactory).get(SleepQualityViewModel::class.java)

        // passa uma referencia ao objeto
        binding.sleepQualityViewModel = sleepQualityViewModel

        // add Observer a variavel de estado para navigating quando o icone é escolhido
        sleepQualityViewModel.navigateToSleepTracker.observe(this, Observer {
            if (it == true) {
                this.findNavController().navigate(
                        SleepQualityFragmentDirections.actionSleepQualityFragmentToSleepTrackerFragment())

                sleepQualityViewModel.doneNavigating()
            }
        })

        return binding.root
    }
}
