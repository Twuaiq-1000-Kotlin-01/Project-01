package com.tuwaiq.projectone.TimeLineFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tuwaiq.projectone.R

class TimeLineFragment : Fragment() {

    companion object {
        fun newInstance() = TimeLineFragment()
    }

    private lateinit var viewModel: TimeLineViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.time_line_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TimeLineViewModel::class.java)
        // TODO: Use the ViewModel
    }

}