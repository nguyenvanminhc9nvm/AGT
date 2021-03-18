package com.minhnv.c9nvm.agt.ui.menu

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.minhnv.c9nvm.agt.databinding.MenuFragmentBinding
import com.minhnv.c9nvm.agt.ui.base.BaseFragment


class MenuFragment : BaseFragment<MenuViewModel, MenuFragmentBinding>() {
    override val sbindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> MenuFragmentBinding
        get() = MenuFragmentBinding::inflate

    override fun createViewModel(): Class<MenuViewModel> {
        return MenuViewModel::class.java
    }

    override fun initView() {

    }

    override fun bindViewModel() {
        binding.button.setOnClickListener {
            sendEmail()
        }
    }


    private fun sendEmail() {
        Log.i("Send email", "")
        val TO = arrayOf("nguyenvanminh12081999@gmail.com")
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.data = Uri.parse("mailto:")
        emailIntent.type = "text/plain"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO)
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