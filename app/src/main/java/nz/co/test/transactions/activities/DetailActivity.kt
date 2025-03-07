package nz.co.test.transactions.activities

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import nz.co.test.transactions.R
import nz.co.test.transactions.activities.models.UiModel
import nz.co.test.transactions.databinding.ActivityDetailBinding
import java.math.BigDecimal
import java.time.format.DateTimeFormatter

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val model = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("EXTRA_TRANSACTION", UiModel::class.java)
        } else {
            intent.getParcelableExtra("EXTRA_TRANSACTION")
        }


        model?.let {
            binding.textSummary.text = it.summary
            binding.textTransactionDate.text = it.transactionDate.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))
            binding.gst.text = "${it.gst.toPlainString()} gst"
            if (it.debit == BigDecimal.ZERO) {
                binding.debit.visibility = View.GONE
                binding.credit.visibility = View.VISIBLE
                binding.credit.setTextColor(Color.GREEN)
                binding.credit.text = "${it.credit.toPlainString()} credit"
            } else {
                binding.debit.visibility = View.VISIBLE
                binding.credit.visibility = View.GONE
                binding.debit.setTextColor(Color.RED)
                binding.debit.text = "${it.debit.toPlainString()} debit"
            }
        }
    }
}