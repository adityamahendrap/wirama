package com.wirama

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.ViewUtils
import com.wirama.adapter.ExampleCardViewAdapter
import com.wirama.databinding.ActivityExampleCardViewBinding
import com.wirama.model.ExampleCardViewModel

class ExampleCardViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExampleCardViewBinding
    private lateinit var modelList: MutableList<ExampleCardViewModel>
    private lateinit var adapter: ExampleCardViewAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExampleCardViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        recyclerView = findViewById(R.id.recyclerView)
        modelList = ArrayList()
        adapter = ExampleCardViewAdapter(this, modelList)

        var layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager

        recyclerView.addItemDecoration(GridSpacingItemDecoration(2, dpToPx(10), true))
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter

        this.insertDataIntoCards()
    }

    private fun insertDataIntoCards() {
        var titles: Array<String> = arrayOf("a", "b")
        var images: Array<Int> = arrayOf(
            R.drawable.logo,
            R.drawable.logo2
        )

        for (i in titles.indices) {
            modelList.add(ExampleCardViewModel(titles[i], images[i]))
        }

        adapter.notifyDataSetChanged()
    }

    private fun dpToPx(dp: Int): Int {
        var r: Resources = resources
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics))
    }

    class GridSpacingItemDecoration(
        var spanCount: Int,
        var spacing: Int,
        var includeEdge: Boolean
    ) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            var position: Int = parent.getChildAdapterPosition(view)
            var column: Int = position % spanCount;

            if (includeEdge) {
                outRect.left = spacing - column * spacing /spanCount
                outRect.right = (column + 1) * spacing / spanCount

                if (position < spanCount) {
                    outRect.top = spacing
                }
                outRect.bottom = spacing
            } else {
                outRect.left = column * spacing / spanCount
                outRect.right = spacing - (column + 1) * spacing / spanCount
                if (position >= spanCount) {
                    outRect.top = spacing
                }
            }
        }
    }

}