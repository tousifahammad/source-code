package com.webgrity.tishakds.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.webgrity.tishakds.R
import com.webgrity.tishakds.utilities.extensions.loadGif
import kotlinx.android.synthetic.main.dialog_my_progress.*

class MyProgressDialog(mContext: Context) : Dialog(mContext, R.style.RoundedCornersDialog) {
    init {
        setCancelable(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_my_progress)

        iv_gif_progress.loadGif(R.drawable.ic_progress)
    }
}
