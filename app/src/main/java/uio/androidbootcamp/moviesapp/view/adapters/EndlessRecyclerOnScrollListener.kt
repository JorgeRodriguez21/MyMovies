package uio.androidbootcamp.moviesapp.view.adapters

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

//This class is taken from:
//https://github.com/shandilyaaman/SampleEndlessRecyclerView/blob/master/SampleEndlessRecyclerView/endlessrecyclerview/src/main/java/com/endlessrecyclerview/android/EndlessRecyclerOnScrollListener.java
//and converted to Kotlin

abstract class EndlessRecyclerOnScrollListener : RecyclerView.OnScrollListener() {
    //    public static String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();

    /**
     * The total number of items in the dataset after the last load
     */
    private var mPreviousTotal = 0
    /**
     * True if we are still waiting for the last set of data to load.
     */
    private var mLoading = true

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = recyclerView!!.childCount
        val totalItemCount = recyclerView.layoutManager.itemCount
        val firstVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

        if (mLoading) {
            if (totalItemCount > mPreviousTotal) {
                mLoading = false
                mPreviousTotal = totalItemCount
            }
        }
        val visibleThreshold = 5
        if (!mLoading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
            // End has been reached

            onLoadMore()

            mLoading = true
        }
    }

    abstract fun onLoadMore()
}