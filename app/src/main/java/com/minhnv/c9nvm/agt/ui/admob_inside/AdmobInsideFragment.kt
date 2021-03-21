package com.minhnv.c9nvm.agt.ui.admob_inside

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.minhnv.c9nvm.agt.databinding.AdmobInsideFragmentBinding
import com.minhnv.c9nvm.agt.ui.base.BaseFragment
import com.minhnv.c9nvm.agt.ui.comic.detail.description.ComicDescriptionFragment
import com.minhnv.c9nvm.agt.utils.AGTConstant

class AdmobInsideFragment : BaseFragment<AdmobInsideViewModel, AdmobInsideFragmentBinding>() {
    private lateinit var mInterstitialAd: InterstitialAd
    override val sbindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> AdmobInsideFragmentBinding
        get() = AdmobInsideFragmentBinding::inflate

    override fun createViewModel(): Class<AdmobInsideViewModel> {
        return AdmobInsideViewModel::class.java
    }

    override fun initView() {
        val comicId = arguments?.getInt(AGTConstant.DESCRIPTION_ID)
        val bundle = Bundle()
        bundle.putInt(AGTConstant.DESCRIPTION_ID, comicId ?:0)
        mInterstitialAd = InterstitialAd(mActivity)
        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        mInterstitialAd.adListener = object: AdListener() {
            override fun onAdLoaded() {
                mInterstitialAd.show()
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                println("error show ads: $errorCode")
            }

            override fun onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                activityController.switchFragment(ComicDescriptionFragment(), bundle)
            }
        }
    }

    override fun bindViewModel() {

    }


}