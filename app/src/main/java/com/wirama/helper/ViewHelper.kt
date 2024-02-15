package com.wirama.helper

import android.app.Activity
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.TextView
import com.google.android.gms.common.SignInButton


class ViewHelper {
    companion object {
        fun setUnderlineTextView(activity: Activity, resourceId: Int) {
            val textView = activity.findViewById<TextView>(resourceId)
            val content = SpannableString(textView.text.toString())
            content.setSpan(UnderlineSpan(), 0, content.length, 0)
            textView.text = content
        }

         fun setGooglePlusButtonText(signInButton: SignInButton, buttonText: String?) {
            // Find the TextView that is inside of the SignInButton and set its text
            for (i in 0 until signInButton.childCount) {
                val v = signInButton.getChildAt(i)
                if (v is TextView) {
                    v.text = buttonText
                    return
                }
            }
        }
    }
}