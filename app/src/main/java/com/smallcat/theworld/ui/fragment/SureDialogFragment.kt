package com.smallcat.theworld.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.smallcat.theworld.R
import com.smallcat.theworld.model.callback.SureCallBack


/**
 * @author hui
 * @date 2019-07-08
 */
class SureDialogFragment(private var mText: String = "", var callback: SureCallBack) : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.dialog_sure, container, false)
        val tvText = v.findViewById<TextView>(R.id.tv_text)
        val tvSure = v.findViewById<TextView>(R.id.tv_sure)
        val tvCancel = v.findViewById<TextView>(R.id.tv_cancel)
        tvText.text = mText
        tvCancel.setOnClickListener { dismiss() }
        tvSure.setOnClickListener {
            callback.onSure()
            dismiss()
        }
        return v
    }

}