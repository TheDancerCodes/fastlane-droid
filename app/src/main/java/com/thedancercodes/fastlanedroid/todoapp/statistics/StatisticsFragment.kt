package com.thedancercodes.fastlanedroid.todoapp.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.thedancercodes.fastlanedroid.todoapp.R
import com.thedancercodes.fastlanedroid.todoapp.databinding.StatisticsFragBinding
import com.thedancercodes.fastlanedroid.todoapp.util.obtainViewModel

/**
 * Main UI for the statistics screen.
 */
class StatisticsFragment : Fragment() {

    private lateinit var viewDataBinding: StatisticsFragBinding

    private lateinit var statisticsViewModel: StatisticsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.statistics_frag, container,
            false)
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        statisticsViewModel = obtainViewModel(StatisticsViewModel::class.java)
        viewDataBinding.stats = statisticsViewModel
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
    }

    override fun onResume() {
        super.onResume()
        statisticsViewModel.start()
    }
}
