package com.minhnv.c9nvm.agt.ui.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.viewbinding.ViewBinding
import com.minhnv.c9nvm.agt.R
import com.minhnv.c9nvm.agt.ViewModelProviderFactory
import com.minhnv.c9nvm.agt.data.DataManager
import com.minhnv.c9nvm.agt.utils.rx.SchedulerProvider
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject


abstract class BaseActivity<V : BaseViewModel, VB : ViewBinding> : DaggerAppCompatActivity() {
    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    @Inject
    lateinit var dataManager: DataManager

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    private val compositeDisposable = CompositeDisposable()

    lateinit var viewModel: V

    abstract fun createViewModel(): Class<V>

    abstract fun initView()

    abstract fun bindViewModel()

    abstract fun getContentView(): Int

    private lateinit var mProgressDialog: ProgressDialog

    private fun Disposable.addToDisposable() {
        compositeDisposable.add(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentView())
        mProgressDialog = ProgressDialog(this)
        viewModel = viewModelFactory.create(createViewModel())
        initView()
        bindLoading()
        bindViewModel()
    }

    private fun bindLoading() {
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

    fun hideSoftKeyboard() {
        currentFocus?.let {
            val inputMethodManager =
                getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
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

    class ProgressDialog constructor(context: Context) : Dialog(context) {
        init {
            setCancelable(false)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_progress)
            val window = window
            if (window != null) {
                getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent)
            }
        }
    }
}