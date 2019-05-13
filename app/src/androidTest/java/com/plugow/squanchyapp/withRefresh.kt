package com.plugow.squanchyapp

import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher




fun withRefresh(refresh: Boolean): Matcher<View> {
    return object : TypeSafeMatcher<View>() {
        override fun describeTo(description: org.hamcrest.Description?) {

        }

        public override fun matchesSafely(view: View): Boolean {
            return (view as SwipeRefreshLayout).isRefreshing == refresh
        }

    }
}