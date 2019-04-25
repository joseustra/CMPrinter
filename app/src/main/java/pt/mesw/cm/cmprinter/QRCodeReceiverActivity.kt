package pt.mesw.cm.cmprinter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.beust.klaxon.Klaxon


class QRCodeReceiverActivity : AppCompatActivity() {

    private var tvPaymentStatus: TextView? = null
    private var tvCard: TextView? = null
    private var tvItems: TextView? = null
    private var tvTotal: TextView? = null

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
                tvPaymentStatus!!.text = result.errorCode
            } else {
                val paymentStatusPattern = "(Payment status:\\s\\S*\\s)".toRegex()
                val paymentStatus = paymentStatusPattern .find(result.memo)

                val cardPattern = "(Card:\\s\\S*\\s)".toRegex()
                val card = cardPattern.find(result.memo)

                val itemsPattern = "(Items:\\s.*-)".toRegex()
                val items = itemsPattern.find(result.memo)

                val totalPattern = "(Total:\\s\\S*\\s)".toRegex()
                val total = totalPattern.find(result.memo)

                tvPaymentStatus!!.text = paymentStatus?.value
                tvCard!!.text = card?.value
                tvItems!!.text = items?.value!!.replace("------------------", "")
                tvTotal!!.text = total?.value
            }


        }
    }

    private fun initViews() {
        this.tvPaymentStatus = findViewById(R.id.tv_payment_status)
        this.tvCard = findViewById(R.id.tv_card)
        this.tvItems = findViewById(R.id.tv_items)
        this.tvTotal = findViewById(R.id.tv_total)
    }
}
