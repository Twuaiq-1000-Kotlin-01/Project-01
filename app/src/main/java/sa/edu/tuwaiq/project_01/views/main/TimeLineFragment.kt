package sa.edu.tuwaiq.project_01.views.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import sa.edu.tuwaiq.project_01.R
import sa.edu.tuwaiq.project_01.databinding.FragmentTimeLineBinding
import sa.edu.tuwaiq.project_01.util.BottomAppBarHelper

class TimeLineFragment : Fragment() {
    lateinit var binding: FragmentTimeLineBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTimeLineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Show the nav bar & the floating action bottom
        BottomAppBarHelper.get().show()
    }
}