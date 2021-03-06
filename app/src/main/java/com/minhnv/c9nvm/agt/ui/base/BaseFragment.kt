package com.minhnv.c9nvm.agt.ui.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewbinding.ViewBinding
import com.minhnv.c9nvm.agt.ViewModelProviderFactory
import com.minhnv.c9nvm.agt.data.DataManager
import com.minhnv.c9nvm.agt.utils.rx.SchedulerProvider
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

abstract class BaseFragment<V : BaseViewModel, VB : ViewBinding> : DaggerFragment() {

    lateinit var mActivity: Activity

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    @Inject
    lateinit var dataManager: DataManager

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    lateinit var activityController: ActivityController

    val compositeDisposable = CompositeDisposable()

    lateinit var viewModel: V

    private var _binding: ViewBinding? = null
    abstract val sbindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    abstract fun createViewModel(): Class<V>

    abstract fun initView()

    abstract fun bindViewModel()

    private val gridLayoutManager = GridLayoutManager(context, 2)

    private lateinit var mProgressDialog: BaseActivity.ProgressDialog

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB


    fun Disposable.addToDisposable() {
        compositeDisposable.add(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ActivityController) {
            mActivity = context as Activity
            activityController = context
            viewModel = viewModelFactory.create(createViewModel())
            mProgressDialog = BaseActivity.ProgressDialog(mActivity)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = sbindingInflater.invoke(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        bindLoading()
        bindViewModel()
    }


    private fun bindLoading() {
//        viewModel.mProgressBar.subscribeOn(schedulerProvider.ui).subscribe {
//            if (it) {
//                showLoading()
//            } else {
//                hideLoading()
//            }
//        }.addToDisposable()
    }


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }


    private fun showLoading() {
        if (!mProgressDialog.isShowing) {
            mProgressDialog.show()
        }
    }

    private fun hideLoading() {
        if (mProgressDialog.isShowing) {
            mProgressDialog.dismiss()
        }
    }

    fun hideSoftKeyboard() {
        mActivity.currentFocus?.let {
            val inputMethodManager =
                mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

}