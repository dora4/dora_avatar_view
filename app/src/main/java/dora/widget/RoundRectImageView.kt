package dora.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide

open class RoundRectImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var bitmap: Bitmap? = null
    private var bitmapPaint: Paint? = null
    private var bitmapShader: BitmapShader? = null
    private var cornerRadius = 0f
    private fun initPaints() {
        bitmapPaint = Paint()
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.RoundRectImageView)
        cornerRadius = a.getDimension(
            R.styleable.RoundRectImageView_dora_cornerRadius,
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, resources.displayMetrics)
        )
        a.recycle()
    }

    /**
     * 设置圆角大小。
     *
     * @param cornerRadius 0 ~ width / 2, 0 ~ height / 2
     */
    fun setCornerRadius(cornerRadius: Float) {
        this.cornerRadius = cornerRadius
        invalidate()
    }

    private fun refresh() {
        if (bitmap != null) {
            bitmapShader = BitmapShader(bitmap!!, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
            bitmapPaint!!.isAntiAlias = true
            bitmapPaint!!.shader = bitmapShader
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(
            0f,
            0f,
            width.toFloat(),
            height.toFloat(),
            cornerRadius,
            cornerRadius,
            bitmapPaint!!
        )
    }

    override fun setImageResource(resId: Int) {
        super.setImageResource(resId)
        val drawable = drawable
        if (drawable != null) {
            bitmap = getBitmapFromDrawable(drawable)
            refresh()
        }
    }

    fun loadUrl(url: String) {
        scaleType = ScaleType.CENTER
        Glide.with(this).load(url).centerCrop().into(this)
    }

    override fun setImageURI(uri: Uri?) {
        super.setImageURI(uri)
        val drawable = drawable
        if (drawable != null) {
            bitmap = if (uri != null) getBitmapFromDrawable(drawable) else null
            refresh()
        }
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        bitmap = getBitmapFromDrawable(drawable)
        refresh()
    }

    private fun getBitmapFromDrawable(drawable: Drawable?): Bitmap? {
        if (drawable == null) {
            return null
        }
        return if (drawable is BitmapDrawable) {
            drawable.bitmap
        } else try {
            val bitmap = if (drawable is ColorDrawable) {
                Bitmap.createBitmap(
                    COLOR_DRAWABLE_DIMENSION,
                    COLOR_DRAWABLE_DIMENSION,
                    BITMAP_CONFIG
                )
            } else {
                Bitmap.createBitmap(
                    drawable.intrinsicWidth,
                    drawable.intrinsicHeight,
                    BITMAP_CONFIG
                )
            }
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    companion object {
        private val BITMAP_CONFIG = Bitmap.Config.ARGB_8888
        private const val COLOR_DRAWABLE_DIMENSION = 1
    }

    init {
        initAttrs(context, attrs)
        initPaints()
    }
}