package com.minhnv.c9nvm.agt.ui.menu

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.minhnv.c9nvm.agt.databinding.MenuFragmentBinding
import com.minhnv.c9nvm.agt.ui.base.BaseFragment
import com.minhnv.c9nvm.agt.utils.AGTConstant


class MenuFragment : BaseFragment<MenuViewModel, MenuFragmentBinding>() {
    override val sbindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> MenuFragmentBinding
        get() = MenuFragmentBinding::inflate

    override fun createViewModel(): Class<MenuViewModel> {
        return MenuViewModel::class.java
    }

    override fun initView() {
        val builder =
            AdLoader.Builder(context, AGTConstant.UNIT_ID_ADMOB_NATIVE).forUnifiedNativeAd {
                binding.templateMenu.setNativeAd(it)
            }.withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(p0: LoadAdError?) {
                    super.onAdFailedToLoad(p0)
                    Toast.makeText(context, "error: ${p0?.message}", Toast.LENGTH_SHORT).show()
                }
            }).build()
        builder.loadAd(AdRequest.Builder().build())
    }

    override fun bindViewModel() {
        binding.button.setOnClickListener {
            sendEmail()
        }
    }


    private fun sendEmail() {
        val to = arrayOf("nguyenvanminh12081999@gmail.com")
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.data = Uri.parse("mailto:")
        emailIntent.type = "text/plain"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback AGT")
        emailIntent.putExtra(Intent.EXTRA_TEXT, binding.editTextTextPersonName.text.toString())
        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."))
            println("Finished sending email...")
        } catch (ex: ActivityNotFoundException) {
            println("There is no email client installed.")
        }
    }

}