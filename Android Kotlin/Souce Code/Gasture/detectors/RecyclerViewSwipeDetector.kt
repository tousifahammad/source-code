package com.webgrity.tishakds.detectors

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.webgrity.tishakds.R
import com.webgrity.tishakds.app.SharedMethods
import com.webgrity.tishakds.ui.activities.home.activity.HomeViewModel

// swipe down to show delete button

abstract class RecyclerViewSwipeDetector(private val context: Context, private val vm: HomeViewModel) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.DOWN) {

    private val deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete_rv)
    private val intrinsicWidth = deleteIcon?.intrinsicWidth
    private val intrinsicHeight = deleteIcon?.intrinsicHeight
    private val background = ColorDrawable()
    private val backgroundColor = Color.parseColor("#FA8072")
    private var currentPos = 0
    private val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.SCREEN) }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        /**
         * To disable "swipe" for specific item return 0 here.
         * For example:
         * if (viewHolder?.itemViewType == YourAdapter.SOME_TYPE) return 0
         * if (viewHolder?.adapterPosition == 0) return 0
         */
        //if (viewHolder.adapterPosition == 10) return 0
        if (!vm.isRunning) {
            return 0
        } else {
            currentPos = viewHolder.adapterPosition
            return super.getMovementFlags(recyclerView, viewHolder)
        }
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        val itemView = viewHolder.itemView
        val itemWidth = itemView.right - itemView.left
        val isCanceled = dX == 0f && !isCurrentlyActive

        if (isCanceled) {
            clearCanvas(c, itemView.left.toFloat(), itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }

        // Draw the red delete background
        background.color = backgroundColor
        background.setBounds(itemView.left, itemView.top, itemView.right, itemView.top + dY.toInt())
        background.draw(c)

        // Calculate position of delete icon
        val deleteIconTop = SharedMethods.getScreenResolution(context).second / 3
        val deleteIconMargin = (itemWidth - intrinsicHeight!!) / 2
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
