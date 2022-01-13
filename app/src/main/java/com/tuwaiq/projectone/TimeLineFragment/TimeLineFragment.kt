package com.tuwaiq.projectone.timeLineFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tuwaiq.projectone.databinding.TimeLineFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class TimeLineFragment : Fragment() {

    private var _binding: TimeLineFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy { ViewModelProvider(this).get(TimeLineViewModel::class.java)}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = TimeLineFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    suspend fun updateUI(){
        viewModel.getAllPosts().onEach {list->
            viewModel.getUserInfo().onEach { user->
                binding.RVTimeLine.adapter = TimeLineAdapter(list,viewModel,lifecycleScope,requireContext(),user)

            }.launchIn(lifecycleScope)
        }.launchIn(lifecycleScope)
    }

    override fun onStart() {
        super.onStart()

        binding.RVTimeLine.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch(context = Dispatchers.IO){
            updateUI()
        }
    }

}