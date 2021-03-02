package com.minhnv.c9nvm.agt.ui.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import com.minhnv.c9nvm.agt.MainActivity
import com.minhnv.c9nvm.agt.R
import com.minhnv.c9nvm.agt.ViewModelProviderFactory
import com.minhnv.c9nvm.agt.data.DataManager
import com.minhnv.c9nvm.agt.utils.rx.SchedulerProvider
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

abstract class BaseFragment<V: BaseViewModel> : DaggerFragment() {

    lateinit var mActivity: Activity

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    @Inject
    lateinit var dataManager: DataManager

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    private val compositeDisposable = CompositeDisposable()

    lateinit var viewModel: V

    abstract fun createViewModel(): Class<V>

    abstract fun getContentView(): Int

    abstract fun initView()

    abstract fun bindViewModel()

    private lateinit var mProgressDialog: BaseActivity.ProgressDialog

    fun Disposable.addToDisposable() {
        compositeDisposable.add(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            mActivity = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = viewModelFactory.create(createViewModel())
        initView()
        bindLoading()
        bindViewModel()
    }


    private fun bindLoading(){
        viewModel.mProgressBar.subscribeOn(schedulerProvider.ui).subscribe {
            if (it) {
                showLoading()
            } else {
                hideLoading()
            }
        }.addToDisposable()
    }


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }


    private fun showLoading() {
        if (!mProgressDialog.isShowing ) {
            mProgressDialog.show()
        }
    }

    private fun hideLoading() {
        if (mProgressDialog.isShowing ) {
            mProgressDialog.dismiss()
        }
    }


}