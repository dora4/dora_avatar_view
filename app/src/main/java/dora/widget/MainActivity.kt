package dora.widget

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val avatar = findViewById<CircleImageView>(R.id.avatar_01)
        val avatar2 = findViewById<RoundRectImageView>(R.id.avatar_02)
        avatar.setImageResource(R.drawable.ic_launcher_background)
        avatar2.setImageResource(R.drawable.ic_launcher_background)
    }
}