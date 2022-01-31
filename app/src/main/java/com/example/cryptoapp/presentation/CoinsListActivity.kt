package com.example.cryptoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.databinding.ActivityCoinPriceListBinding
import com.example.cryptoapp.domain.CoinFullInfo
import com.example.cryptoapp.presentation.adapters.CoinInfoAdapter

class CoinsListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    private val binding by lazy{
        ActivityCoinPriceListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinInfo: CoinFullInfo) {
                val intent = CoinDetailActivity.newIntent(
                    this@CoinsListActivity,
                    coinInfo.fromSymbol
                )
                startActivity(intent)
            }
        }
        binding.rvCoinPriceList.adapter = adapter

            //binding.rvCoinPriceList.itemAnimator = null
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.coinInfoList.observe(this, Observer {
            adapter.submitList(it)
        })
    }
}
