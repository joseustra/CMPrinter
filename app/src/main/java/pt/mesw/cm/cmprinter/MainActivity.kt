package pt.mesw.cm.cmprinter


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*
import pt.mesw.cm.cmprinter.permissions.Permissions


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val permissions = Permissions()

    private lateinit var receiverActivity: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        receiverActivity = Intent(this, ReceiverActivity::class.java)

        btn_nfc.setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.app_bar_qrcode -> IntentIntegrator(this).initiateScan()
        }

        return true
    }

    override fun onClick(view: View?) {
        val id = view?.id
        if (id == R.id.btn_nfc) {
            if (permissions.checkPersmission(this)) {
                startActivity(receiverActivity)
            } else {
                permissions.requestPermission(this)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                tvResult.text = result.contents
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}

