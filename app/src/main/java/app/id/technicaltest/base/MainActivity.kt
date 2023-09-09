package app.id.technicaltest.base

import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import app.id.technicaltest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val gravity: Int = Gravity.BOTTOM
    private val isSystemBarUsed: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initWindowInsets()
    }

    private fun initWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, windowInsets ->
            val imeVisible = windowInsets.isVisible(WindowInsetsCompat.Type.ime())
            val imeHeight = windowInsets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            val insetsSystemBar = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            if (isSystemBarUsed) when (gravity) {
                Gravity.BOTTOM -> {
                    if (imeVisible) view.updatePadding(bottom = insetsSystemBar.bottom + imeHeight)
                    else view.updatePadding(bottom = insetsSystemBar.bottom)
                }
            }
            else when (gravity) {
                Gravity.BOTTOM -> {
                    if (imeVisible) view.updatePadding(bottom = insetsSystemBar.bottom + imeHeight)
                    else view.updatePadding(bottom = insetsSystemBar.bottom)
                }
            }
            windowInsets
        }
    }
}
