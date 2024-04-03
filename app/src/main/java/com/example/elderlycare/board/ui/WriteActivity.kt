package com.example.elderlycare.board.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.elderlycare.R
import com.example.elderlycare.databinding.BoardWriteBinding

class WriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.board_write)
        var binding = BoardWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.iconBack.setOnClickListener{
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
            finish()
        }



    }
}