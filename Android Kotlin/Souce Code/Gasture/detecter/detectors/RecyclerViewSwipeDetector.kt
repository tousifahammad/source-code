package com.webgrity.tisha.detectors

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.webgrity.tisha.R


abstract class RecyclerViewSwipeDetector(context: Context, list: MutableList<*>? = null) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete_white_24)
    private val intrinsicWidth = deleteIcon?.intrinsicWidth
    private val intrinsicHeight = deleteIcon?.intrinsicHeight
    private val background = ColorDrawable()
    private val backgroundColor = Color.parseColor("#f44336")
    private var currentPos = 0
    private val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }
    private val dataList = list

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        /**
         * To disable "swipe" for specific item return 0 here.
         * For example:
         * if (viewHolder?.itemViewType == YourAdapter.SOME_TYPE) return 0
         * if (viewHolder?.adapterPosition == 0) return 0
         */
        //if (viewHolder.adapterPosition == 10) return 0
        currentPos = viewHolder.adapterPosition
        return super.getMovementFlags(recyclerView, viewHolder)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top
        val isCanceled = dX == 0f && !isCurrentlyActive

        if (isCanceled) {
            clearCanvas(c, itemView.right + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }

        /*val bg = RectF(
            (itemView.right + dX.toInt()).toFloat(), itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat()
        )
        val p = Paint()
        p.color = backgroundColor
        c.drawRoundRect(bg, 20F, 20F, p)*/
        val bg = RectF(
            (itemView.right + dX.toInt()).toFloat(), itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat()
        )
        val p = Paint()
        p.color = backgroundColor
        var corners = floatArrayOf()
        corners = floatArrayOf(
            0f, 0f,   // Top left radius in px
            0f, 0f,   // Top right radius in px
            0f, 0f,     // Bottom right radius in px
            0f, 0f      // Bottom left radius in px
        )
        /*corners = when(currentPos){
            0 -> floatArrayOf(
                0f, 0f,   // Top left radius in px
                20f, 20f,   // Top right radius in px
                0f, 0f,     // Bottom right radius in px
                0f, 0f      // Bottom left radius in px
            )
            else -> floatArrayOf(
                0f, 0f,   // Top left radius in px
                0f, 0f,   // Top right radius in px
                0f, 0f,     // Bottom right radius in px
                0f, 0f      // Bottom left radius in px
            )
        }
        if(dataList != null && currentPos == dataList.size - 1){
            corners = floatArrayOf(
                0f, 0f,   // Top left radius in px
                0f, 0f,   // Top right radius in px
                20f, 20f,     // Bottom right radius in px
                0f, 0f      // Bottom left radius in px
            )
        }
        if(dataList != null && dataList.size == 1){
            corners = floatArrayOf(
                0f, 0f,   // Top left radius in px
                20f, 20f,   // Top right radius in px
                20f, 20f,     // Bottom right radius in px
                0f, 0f      // Bottom left radius in px
            )
        }
        if(dataList == null){
            corners = floatArrayOf(
                0f, 0f,   // Top left radius in px
                0f, 0f,   // Top right radius in px
                0f, 0f,     // Bottom right radius in px
                0f, 0f      // Bottom left radius in px
            )
        }*/

        val path = Path()
        path.addRoundRect(bg, corners, Path.Direction.CW)
        c.drawPath(path, p)

        // Draw the red delete background
        //background.color = backgroundColor
        //background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
        background.draw(c)

        // Calculate position of delete icon
        val deleteIconTop = itemView.top + (itemHeight - intrinsicHeight!!) / 2
        val deleteIconMargin = (itemHeight - intrinsicHeight) / 2
        val deleteIconLeft = itemView.right - deleteIconMargin - intrinsicWidth!!
        val deleteIconRight = itemView.right - deleteIconMargin
        val deleteIconBottom = deleteIconTop + intrinsicHeight

        // Draw the delete icon
        deleteIcon?.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
        deleteIcon?.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun clearCanvas(c: Canvas?, left: Float, top: Float, right: Float, bottom: Float) {
        c?.drawRect(left, top, right, bottom, clearPaint)
    }
}
