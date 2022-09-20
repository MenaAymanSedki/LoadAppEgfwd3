package com.menadev.loadappegfwd3

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import com.menadev.loadappegfwd3.databinding.ActivityDetailBinding
import com.menadev.loadappegfwd3.databinding.ContentDetailBinding
import com.menadev.loadappegfwd3.BuildConfig
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*
import kotlinx.android.synthetic.main.main_content.*

class DetailActivity : AppCompatActivity() {
private val fileName by lazy {
    intent.extras?.getString(EXTRA_FILE_NAME,unknownText)?: unknownText
}
    private val downloadStatus by lazy {
        intent.extras?.getString(EXTRA_DOWNLOAD_STATUS,unknownText)?: unknownText
    }
    private val unknownText by lazy { getString(R.string.unknown) }

    private lateinit var binding:ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail)

        binding.apply {
         setSupportActionBar(toolbar)
            contentDetail.initializeView()
        }


    }
        private fun ContentDetailBinding.initializeView(){
            fileNameText.text  =fileName.toString()
            downloadStatusText.text =downloadStatus.toString()
            okButton.setOnClickListener {
                startActivity(Intent(applicationContext,MainActivity::class.java))
            }
                changeViewForDownloadStatus()
        }

    private fun ContentDetailBinding.changeViewForDownloadStatus() {
        when(downloadStatusText.text){
            DownloadStatus.SUCCESSFUL.statusText -> {
            changeDownloadStatusImageTo(R.drawable.ic_check_circle)
            changeDownloadStatusColorTo(R.color.colorPrimaryDark)
            }
            DownloadStatus.FAILED.statusText ->{
                changeDownloadStatusImageTo(R.drawable.ic_error)
                changeDownloadStatusColorTo(R.color.red)
            }
        }
    }

    private fun changeDownloadStatusColorTo(@ColorRes colorRes:Int) {
        ContextCompat.getColor(this@DetailActivity,colorRes)
            .also {color ->
                download_status_image.imageTintList =ColorStateList.valueOf(color)
                download_status_text.setTextColor(color)
            }

    }

    companion object{
        private const val EXTRA_FILE_NAME ="${BuildConfig.APPLICATION_ID}.FILE_NAME"
        private const val EXTRA_DOWNLOAD_STATUS = "${BuildConfig.APPLICATION_ID}.DOWNLOAD_STATUS"


        fun bundleExtrasOf(
            fileName:String,
            downloadStatus: DownloadStatus

        )=bundleOf(
            EXTRA_FILE_NAME to fileName,
            EXTRA_DOWNLOAD_STATUS to downloadStatus.statusText
        )

    }

    private fun ContentDetailBinding.changeDownloadStatusImageTo(@DrawableRes imageRes:Int) {
        downloadStatusImage.setImageResource(imageRes)
    }


}