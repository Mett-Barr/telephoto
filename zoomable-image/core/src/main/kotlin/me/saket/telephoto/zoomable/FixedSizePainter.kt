package me.saket.telephoto.zoomable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.takeOrElse
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter

@Composable
internal fun Painter.withFixedSize(size: Size): Painter {
  return remember(this, size) {
    FixedSizePainter(
      painter = this,
      overriddenSize = size.takeOrElse { intrinsicSize },
    )
  }
}

private class FixedSizePainter(
  private val painter: Painter,
  private val overriddenSize: Size,
) : Painter(), RememberObserver {

  private var alpha: Float = DefaultAlpha
  private var colorFilter: ColorFilter? = null

  override val intrinsicSize get() = overriddenSize

  override fun applyAlpha(alpha: Float): Boolean {
    this.alpha = alpha
    return true
  }

  override fun applyColorFilter(colorFilter: ColorFilter?): Boolean {
    this.colorFilter = colorFilter
    return true
  }

  override fun DrawScope.onDraw() {
    with(painter) {
      draw(size, alpha, colorFilter)
    }
  }

  override fun onAbandoned() {
    (painter as? RememberObserver)?.onAbandoned()
  }

  override fun onForgotten() {
    (painter as? RememberObserver)?.onForgotten()
  }

  override fun onRemembered() {
    (painter as? RememberObserver)?.onRemembered()
  }
}