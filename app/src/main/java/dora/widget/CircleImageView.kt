package dora.widget

import android.content.Context
import android.util.AttributeSet

class CircleImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RoundRectImageView(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setCornerRadius(Math.min(measuredWidth / 2, measuredHeight / 2).toFloat())
    }
}