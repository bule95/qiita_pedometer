package jp.les.kasa.sample.mykotlinapp

import android.graphics.drawable.StateListDrawable
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher


fun atPositionOnView(
    position: Int, itemMatcher: Matcher<View>, targetViewId: Int
): Matcher<View> {

    return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("has view id $itemMatcher at position $position")
        }

        override fun matchesSafely(recyclerView: RecyclerView): Boolean {
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
            val targetView = viewHolder!!.itemView.findViewById<View>(targetViewId)
            return itemMatcher.matches(targetView)
        }
    }
}

class DrawableMatcher(private val expectedId: Int) : TypeSafeMatcher<View>(View::class.java) {
    private var resourceName: String? = null

    override fun matchesSafely(target: View): Boolean {
        if (target is ImageView) {
            if (expectedId < 0) {
                return target.drawable == null
            }
            val resources = target.getContext().resources
            val expectedDrawable = resources.getDrawable(expectedId, null)
            resourceName = resources.getResourceEntryName(expectedId)

            if (expectedDrawable == null) {
                return false
            }

            var drawable = target.drawable
            if (drawable is StateListDrawable) {
                drawable = drawable.getCurrent()
            }

            val bitmap = drawable.toBitmap()
            val otherBitmap = expectedDrawable.toBitmap()
            return bitmap.sameAs(otherBitmap)
        }
        return false
    }


    override fun describeTo(description: Description) {
        description.appendText("with drawable from resource id: ")
        description.appendValue(expectedId)
        if (resourceName != null) {
            description.appendText("[")
            description.appendText(resourceName)
            description.appendText("]")
        }
    }
}

fun withDrawable(resourceId: Int): Matcher<View> {
    return DrawableMatcher(resourceId)
}
