package com.minhnv.c9nvm.agt.ui.sport

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.minhnv.c9nvm.agt.databinding.SportFragmentBinding
import com.minhnv.c9nvm.agt.ui.base.BaseFragment
import com.minhnv.c9nvm.agt.ui.sport.adapter.ScoreAdapter

class SportFragment : BaseFragment<SportViewModel, SportFragmentBinding>() {
    private lateinit var scoreAdapter: ScoreAdapter
    override val sbindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> SportFragmentBinding
        get() = SportFragmentBinding::inflate

    override fun createViewModel(): Class<SportViewModel> {
        return SportViewModel::class.java
    }

    override fun initView() {
        scoreAdapter = ScoreAdapter()
        binding.rycScore.apply {
            layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = scoreAdapter
        }
    }

    override fun bindViewModel() {
        viewModel.getListScore()
        viewModel.listScore.observe(viewLifecycleOwner) {
            scoreAdapter.set(it)
        }
    }
}