package pt.mesw.cm.cmprinter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.beust.klaxon.Klaxon


class QRCodeReceiverActivity : AppCompatActivity() {

    private var tv_receipt: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode_receiver)

        initViews()

        val intent = intent
        val body = intent.getStringExtra("body")

        val result = Klaxon()
            .parse<Receipt>(body)

        if (result != null) {
            if (result.errorCode != null) {
                tv_receipt!!.text = result.errorCode
            } else {
                tv_receipt!!.text = result.memo
            }
        }
    }

    private fun initViews() {
        this.tv_receipt = findViewById(R.id.tv_receipt)
    }
}
