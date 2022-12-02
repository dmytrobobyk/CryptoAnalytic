package com.example.cryptoanalytic.screens.cryptocurrencyDetails

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import com.example.cryptoanalytic.R
import com.example.cryptoanalytic.databinding.CryptocurrencyDetailsFragmentBinding
import com.example.cryptoanalytic.screens.cryptocurrencyDetails.viewmodel.CryptocurrencyDetailsViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.ChartTouchListener
import com.github.mikephil.charting.listener.OnChartGestureListener
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@AndroidEntryPoint
class CryptocurrencyDetailsFragment : Fragment(), OnChartValueSelectedListener {

    private val args: CryptocurrencyDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var articlesFeedViewModelFactory: CryptocurrencyDetailsViewModel.CryptocurrencyDetailsViewModelFactory
    private val viewModel: CryptocurrencyDetailsViewModel by viewModels {
        CryptocurrencyDetailsViewModel.providesFactory(
            assistedFactory = articlesFeedViewModelFactory,
            cryptocurrencyId = args.cryptocurrencyId
        )
    }
    private lateinit var binding: CryptocurrencyDetailsFragmentBinding
    private var displayWidth: Int = 0

    private val xAxisFormatter: IAxisValueFormatter? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.cryptocurrency_details_fragment, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val mWinMgr = activity?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        displayWidth = mWinMgr.defaultDisplay.width

//        setUpChart()
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val info = viewModel.cryptocurrencyDetailsInfo.value
//        viewModel.viewModelScope.launch {
//        lifecycleScope.launch {
//            viewModel.cryptocurrencyDetailsInfo.collect {cryptocurrencyDetailsInfo ->
//                cryptocurrencyDetailsInfo?.let {
//                    val timeFrom = Calendar.getInstance().time.time
//                    val timeTo = Calendar.getInstance().time.time
//                    viewModel.getCryptocurrencyHistoryPrices(it.symbol, timeFrom, timeTo)
//                }
//            }
//        }

//        viewModel.viewModelScope.launch {
//            viewModel.cryptocurrencyHistoryPrices.collect { historyPrices ->
//                if (historyPrices?.prices?.isNotEmpty() == true) {
//                    //create list
//                    var list = arrayListOf<Entry>()
//                    historyPrices.prices.forEachIndexed { index, currentRow ->
//                        list.add(Entry(currentRow[0].toFloat(), currentRow[1].toFloat()))
//                    }
//                    setChartData(list)
//                }
//            }
//        }
    }

    private fun setChartData(values: ArrayList<Entry>) {
        var chartDataSet = LineDataSet(listOf(), "")
        chartDataSet = LineDataSet(values, "")

        chartDataSet.color = Color.BLUE
        chartDataSet.setDrawCircles(false)
        chartDataSet.setDrawHorizontalHighlightIndicator(false)
        chartDataSet.setDrawVerticalHighlightIndicator(true)
        chartDataSet.lineWidth = 3f
        chartDataSet.setDrawValues(false)


        if (binding.chart.data == null) {
            val data = LineData(chartDataSet)
            binding.chart.data = data
        } else {
            binding.chart.clearValues()
            binding.chart.data.clearValues()
            binding.chart.data.addDataSet(chartDataSet)
        }
        //chart.animateX(1000)
        binding.chart.notifyDataSetChanged()
        binding.chart.invalidate()

//        setChartVisibility(true)
//        setChartLoadingProgressBarVisibility(false)
    }

    fun setUpLineDataSet(entries: List<Entry?>?): LineDataSet {
        val dataSet = LineDataSet(entries, "Price")
        dataSet.color = Color.GREEN
        dataSet.fillColor = Color.BLUE
        dataSet.setDrawHighlightIndicators(true)
        dataSet.setDrawFilled(true)
        dataSet.setDrawCircles(true)
        dataSet.setCircleColor(Color.GREEN)
        dataSet.setDrawCircleHole(false)
        dataSet.setDrawValues(false)
        dataSet.circleRadius = 1f
        dataSet.highlightLineWidth = 2f
        dataSet.isHighlightEnabled = true
        dataSet.setDrawHighlightIndicators(true)
        dataSet.highLightColor = Color.GREEN // color for highlight indicator
        return dataSet
    }

    private fun setUpChart() {
        val xAxis: XAxis = binding.chart.xAxis
        xAxis.setDrawAxisLine(true)
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.setAvoidFirstLastClipping(true)
        binding.chart.axisLeft.isEnabled = true
        binding.chart.axisLeft.setDrawGridLines(false)
        binding.chart.xAxis.setDrawGridLines(false)
        binding.chart.axisRight.isEnabled = false
        binding.chart.legend.isEnabled = false
        binding.chart.isDoubleTapToZoomEnabled = false
        binding.chart.setScaleEnabled(false)
        binding.chart.description.isEnabled = false
        binding.chart.contentDescription = ""
        binding.chart.setNoDataText(getString(R.string.no_chart_data_string))
        binding.chart.setNoDataTextColor(R.color.red)
        binding.chart.setOnChartValueSelectedListener(this)
        binding.chart.onChartGestureListener = object : OnChartGestureListener {
            override fun onChartGestureStart(me: MotionEvent, lastPerformedGesture: ChartTouchListener.ChartGesture) {
                val yAxis: YAxis = binding.chart.axisLeft
                // Allow scrolling in the right and left margins
                if (me.x > yAxis.longestLabel.length * yAxis.textSize &&
                    me.x < displayWidth - binding.chart.viewPortHandler.offsetRight()
                ) {
//                    viewPager.setPagingEnabled(false)
//                    nestedScrollView.setScrollingEnabled(false)
                }
            }

            override fun onChartGestureEnd(me: MotionEvent, lastPerformedGesture: ChartTouchListener.ChartGesture) {
//                viewPager.setPagingEnabled(true)
//                nestedScrollView.setScrollingEnabled(true)
            }

            override fun onChartLongPressed(me: MotionEvent) {}
            override fun onChartDoubleTapped(me: MotionEvent) {}
            override fun onChartSingleTapped(me: MotionEvent) {}
            override fun onChartFling(me1: MotionEvent, me2: MotionEvent, velocityX: Float, velocityY: Float) {}
            override fun onChartScale(me: MotionEvent, scaleX: Float, scaleY: Float) {}
            override fun onChartTranslate(me: MotionEvent, dX: Float, dY: Float) {}
        }
    }
/*
    private fun getCMCChart() {
//        val percentChangeText: TextView = rootView.findViewById<TextView>(R.id.percent_change)
//        val currPriceText: TextView = rootView.findViewById<TextView>(R.id.current_price)
        binding.chart.isEnabled = true
        binding.chart.clear()
//        chartProgressBar.setVisibility(View.VISIBLE)
        CoinMarketCapService.getCMCChartData(activity, cryptoID, object : afterTaskCompletion<CMCChartData?>() {
            fun onTaskCompleted(cmcChartData: CMCChartData) {
                val closePrices: MutableList<Entry> = ArrayList()
                if (tsymbol == "USD") {
                    for (priceTimeUnit in cmcChartData.getPriceUSD()) {
                        closePrices.add(Entry(priceTimeUnit[0]!!, priceTimeUnit[1]!!))
                    }
                } else {
                    for (priceTimeUnit in cmcChartData.getPriceBTC()) {
                        closePrices.add(Entry(priceTimeUnit[0]!!, priceTimeUnit[1]!!))
                    }
                }
                if (closePrices.size == 0) {
                    binding.chart.data = null
                    binding.chart.isEnabled = false
                    binding.chart.invalidate()
//                    percentChangeText.text = ""
//                    currPriceText.text = ""
                    binding.chart.setNoDataText(getString(R.string.no_chart_data_string))
//                    chartProgressBar.setVisibility(View.GONE)
                    return
                }
                val xAxis: XAxis = binding.chart.xAxis
                xAxis.valueFormatter = xAxisFormatter
//                val currentPriceTextView: TextView = binding.root.findViewById(R.id.current_price)
//                val currPrice = closePrices[closePrices.size - 1].y
//                val chartDateTextView: TextView = binding.root.findViewById(R.id.graphFragmentDateTextView)
//                chartDateTextView.setText(getFormattedFullDate(closePrices[closePrices.size - 1].x))
//                if (tsymbol == "USD") {
//                    currentPriceTextView.text = String.format(getString(R.string.unrounded_usd_chart_price_format), currPrice.toString())
//                } else {
//                    currentPriceTextView.setText(currencyFormatter.format(currPrice, "BTC"))
//                }
//                currentPriceTextView.setTextColor(Color.BLACK)
                var firstPrice = closePrices[0].y
                // Handle edge case where we dont have data for the interval on the chart. E.g. user selects
                // 3 month window, but we only have data for last month
                for (e in closePrices) {
                    firstPrice = if (firstPrice != 0f) {
                        break
                    } else {
                        e.y
                    }
                }
//                val difference = currPrice - firstPrice
//                val percentChange = difference / firstPrice * 100
//                if (percentChange < 0) {
//                    if (tsymbol == "USD") {
//                        percentChangeText.text = String.format(
//                            getString(R.string.negative_variable_pct_change_with_dollars_format),
//                            currentTimeWindow,
//                            percentChange,
//                            Math.abs(difference)
//                        )
//                    } else {
//                        percentChangeText.text =
//                            String.format(getString(R.string.negative_variable_pct_change_without_dollars_format), currentTimeWindow, percentChange)
//                    }
//                } else {
//                    if (tsymbol == "USD") {
//                        percentChangeText.text = String.format(
//                            getString(R.string.positive_variable_pct_change_with_dollars_format),
//                            currentTimeWindow,
//                            percentChange,
//                            Math.abs(difference)
//                        )
//                    } else {
//                        percentChangeText.text =
//                            String.format(getString(R.string.positive_variable_pct_change_without_dollars_format), currentTimeWindow, percentChange)
//                    }
//                }
//                setColors(percentChange)
//                percentChangeText.setTextColor(percentageColor)
                val dataSet: LineDataSet = setUpLineDataSet(closePrices)
                val lineData = LineData(dataSet)
                binding.chart.data = lineData
                binding.chart.animateX(800)
//                chartProgressBar.setVisibility(View.GONE)
            }
        }, object : afterTaskFailure() {
            fun onTaskFailed(o: Any?, e: Exception) {
                Log.e("ERROR", "Server Error: " + e.message)
                lineChart.setNoDataText(getString(R.string.noChartDataString))
                chartProgressBar.setVisibility(View.GONE)
            }
        }, true)
    }
*/

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        TODO("Not yet implemented")
    }

    override fun onNothingSelected() {
        TODO("Not yet implemented")
    }

//    private fun setUpChart() {
//        // // chart Style // //
//        // background color
//        binding.chart.setBackgroundColor(Color.WHITE)
//
//        // disable description text
//        binding.chart.description.isEnabled = false
//
//        // enable touch gestures
//        binding.chart.setTouchEnabled(true)
//
//        // set listeners
//        binding.chart.setOnChartValueSelectedListener(this)
//        binding.chart.setDrawGridBackground(false)
//
//        // create marker to display box when values are selected
//        val mv = MyMarkerView(this, R.layout.custom_marker_view)
//
//        // Set the marker to the binding.chart
//        mv.setbinding.chartView(binding.chart)
//        binding.chart.marker = mv
//
//        // enable scaling and dragging
//        binding.chart.isDragEnabled = true
//        binding.chart.setScaleEnabled(true)
//        // binding.chart.setScaleXEnabled(true);
//        // binding.chart.setScaleYEnabled(true);
//
//        // force pinch zoom along both axis
//        binding.chart.setPinchZoom(true)
//
//
//        // // X-Axis Style // //
//        var xAxis: XAxis = binding.chart.xAxis
//
//        // vertical grid lines
//        xAxis.enableGridDashedLine(10f, 10f, 0f)
//
//
//        // // Y-Axis Style // //
//        var yAxis: YAxis = binding.chart.axisLeft
//
//        // disable dual axis (only use LEFT axis)
//        binding.chart.axisRight.isEnabled = false
//
//        // horizontal grid lines
//        yAxis.enableGridDashedLine(10f, 10f, 0f)
//
//        // axis range
//        yAxis.axisMaximum = 200f
//        yAxis.axisMinimum = -50f
//
//
//        // // Create Limit Lines // //
//        val llXAxis = LimitLine(9f, "Index 10")
//        llXAxis.lineWidth = 4f
//        llXAxis.enableDashedLine(10f, 10f, 0f)
//        llXAxis.labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM
//        llXAxis.textSize = 10f
//        llXAxis.typeface = tfRegular
//        val ll1 = LimitLine(150f, "Upper Limit")
//        ll1.lineWidth = 4f
//        ll1.enableDashedLine(10f, 10f, 0f)
//        ll1.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
//        ll1.textSize = 10f
//        ll1.typeface = tfRegular
//        val ll2 = LimitLine(-30f, "Lower Limit")
//        ll2.lineWidth = 4f
//        ll2.enableDashedLine(10f, 10f, 0f)
//        ll2.labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM
//        ll2.textSize = 10f
//        ll2.typeface = tfRegular
//
//        // draw limit lines behind data instead of on top
//        yAxis.setDrawLimitLinesBehindData(true)
//        xAxis.setDrawLimitLinesBehindData(true)
//
//        // add limit lines
//        yAxis.addLimitLine(ll1)
//        yAxis.addLimitLine(ll2)
//        //xAxis.addLimitLine(llXAxis);
//
//
//        // add data
//
//        // add data
//        seekBarX.setProgress(45)
//        seekBarY.setProgress(180)
//        setData(45, 180f)
//
//        // draw points over time
//
//        // draw points over time
//        binding.chart.animateX(1500)
//
//        // get the legend (only possible after setting data)
//
//        // get the legend (only possible after setting data)
//        val l: Legend = binding.chart.getLegend()
//
//        // draw legend entries as lines
//
//        // draw legend entries as lines
//        l.form = Legend.LegendForm.LINE
//
//    }

//    private fun setData(count: Int, range: Float) {
//        val values = ArrayList<Entry>()
//        for (i in 0 until count) {
//            val `val` = (Math.random() * range).toFloat() - 30
//            values.add(Entry(i.toFloat(), `val`, resources.getDrawable(R.drawable.star)))
//        }
//        val set1: LineDataSet
//        if (binding.chart.getData() != null &&
//            binding.chart.getData().getDataSetCount() > 0
//        ) {
//            set1 = binding.chart.getData().getDataSetByIndex(0) as LineDataSet
//            set1.values = values
//            set1.notifyDataSetChanged()
//            binding.chart.getData().notifyDataChanged()
//            binding.chart.notifyDataSetChanged()
//        } else {
//            // create a dataset and give it a type
//            set1 = LineDataSet(values, "DataSet 1")
//            set1.setDrawIcons(false)
//
//            // draw dashed line
//            set1.enableDashedLine(10f, 5f, 0f)
//
//            // black lines and points
//            set1.color = Color.BLACK
//            set1.setCircleColor(Color.BLACK)
//
//            // line thickness and point size
//            set1.lineWidth = 1f
//            set1.circleRadius = 3f
//
//            // draw points as solid circles
//            set1.setDrawCircleHole(false)
//
//            // customize legend entry
//            set1.formLineWidth = 1f
//            set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
//            set1.formSize = 15f
//
//            // text size of values
//            set1.valueTextSize = 9f
//
//            // draw selection line as dashed
//            set1.enableDashedHighlightLine(10f, 5f, 0f)
//
//            // set the filled area
//            set1.setDrawFilled(true)
//            set1.fillFormatter = IFillFormatter { dataSet, dataProvider -> binding.chart.getAxisLeft().getAxisMinimum() }
//
//            // set color of filled area
//            if (Utils.getSDKInt() >= 18) {
//                // drawables only supported on api level 18 and above
//                val drawable = ContextCompat.getDrawable(requireActivity(), R.drawable.fade_red)
//                set1.fillDrawable = drawable
//            } else {
//                set1.fillColor = Color.BLACK
//            }
//            val dataSets = ArrayList<ILineDataSet>()
//            dataSets.add(set1) // add the data sets
//
//            // create a data object with the data sets
//            val data = LineData(dataSets)
//
//            // set data
//            binding.chart.setData(data)
//        }
//    }
}