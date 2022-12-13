package com.example.cryptoanalytic.screens.cryptocurrencyDetails

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.GridLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.cryptoanalytic.R
import com.example.cryptoanalytic.databinding.CryptocurrencyDetailsFragmentBinding
import com.example.cryptoanalytic.screens.cryptocurrencyDetails.api.response.CryptocurrencyDetailsResponse
import com.example.cryptoanalytic.screens.cryptocurrencyDetails.api.response.CryptocurrencyHistoryPrices
import com.example.cryptoanalytic.screens.cryptocurrencyDetails.viewmodel.CryptocurrencyDetailsViewModel
import com.example.cryptoanalytic.utils.formatters.NumberFormatter
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
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import java.util.regex.Pattern
import javax.inject.Inject


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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.cryptocurrency_details_fragment,
            container,
            false
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mWinMgr = activity?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        displayWidth = mWinMgr.defaultDisplay.width
        setUpChart()
        initViews()


        viewModel.viewModelScope.launch {
            viewModel.cryptocurrencyDetailsInfo.collect { cryptocurrencyDetailsInfo ->
                cryptocurrencyDetailsInfo?.let {
                    setChartTimeRange(R.id.day_button)
                }
            }
        }

        viewModel.viewModelScope.launch {
            showChartProgress(true)
            viewModel.cryptocurrencyHistoryPrices.collect { historicalPrices ->
                historicalPrices?.let {
                    viewModel.cryptocurrencyDetailsInfo.value?.let {
                        setCryptocurrencyInfo(it)
                    }
                    setChartData(historicalPrices)
                }
            }
        }
    }

    private fun initViews() {
        binding.chartIntervalButtonGroup.setOnCheckedChangeListener(SingleSelectToggleGroup.OnCheckedChangeListener { group, checkedId ->
            setChartTimeRange(checkedId)
        })
    }

    private fun setChartTimeRange(checkedRadioButtonId: Int) {
        //set date range parameters
        val calendar = Calendar.getInstance()
        val dateEnd = calendar.time

        when (checkedRadioButtonId) {
            R.id.quarter_hour_button -> calendar.add(Calendar.MINUTE, -45)
            R.id.hour_button -> calendar.add(Calendar.HOUR, -1)
            R.id.day_button -> calendar.add(Calendar.DAY_OF_MONTH, -1)
            R.id.week_button -> calendar.add(Calendar.DAY_OF_MONTH, -7)
            R.id.month_button -> calendar.add(Calendar.MONTH, -1)
            R.id.three_month_button -> calendar.add(Calendar.MONTH, -3)
            R.id.year_button -> calendar.add(Calendar.YEAR, -1)
            R.id.all_time_button -> calendar.add(Calendar.HOUR, -1) //TODO: fix and get all data
        }

        val dateStart = calendar.time

        viewModel.cryptocurrencyDetailsInfo.value?.let {
            viewModel.getCryptocurrencyHistoryPrices(
                it.id.lowercase(),
                dateStart.time / 1000,
                dateEnd.time / 1000
            )
        }

    }

    private fun showChartProgress(state: Boolean) {
        if (state) {
            binding.chart.visibility = View.INVISIBLE
            binding.chartProgressBar.visibility = View.VISIBLE
        } else {
            binding.chartProgressBar.visibility = View.GONE
            binding.chart.visibility = View.VISIBLE
        }
    }

    private fun setChartData(historicalPrices: CryptocurrencyHistoryPrices) {
        val list = mutableListOf<Entry>()
        historicalPrices.prices.let {
            it.forEachIndexed { index, currentRow ->
                list.add(Entry(currentRow[0].toFloat(), currentRow[1].toFloat()))
            }
        }

        val chartDataSet = LineDataSet(list, "")

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
        binding.chart.animateX(1000)
        binding.chart.notifyDataSetChanged()
        binding.chart.invalidate()

        showChartProgress(false)
    }

    private fun setCryptocurrencyInfo(cryptocurrencyInfo: CryptocurrencyDetailsResponse) {
        Glide.with(this).load(cryptocurrencyInfo.image.small).into(binding.cryptocurrencyImageView)
        binding.cryptocurrencyName.text = cryptocurrencyInfo.name
        binding.cryptocurrencyPrice.text = "$${cryptocurrencyInfo.market_data.current_price.usd}"
        binding.cryptocurrencyPercentChange.text = "${cryptocurrencyInfo.market_data.price_change_percentage_1h_in_currency.usd}%"
        binding.marketCap.text = "$${
            NumberFormatter.formatDouble(
                cryptocurrencyInfo.market_data.market_cap.usd,
                withUnit = true
            )
        }"
        binding.volume24h.text = "$${
            NumberFormatter.formatDouble(
                cryptocurrencyInfo.market_data.price_change_24h,
                withUnit = true
            )
        }"
        binding.availableSupply.text =
            NumberFormatter.formatDouble(cryptocurrencyInfo.market_data.max_supply, withUnit = true)
        binding.totalSupply.text = NumberFormatter.formatDouble(
            cryptocurrencyInfo.market_data.total_supply,
            withUnit = true
        )
        initGridLayoutWithItems(cryptocurrencyInfo.links.blockchain_site.filter { it.isNotEmpty() })
        binding.website.text = "${cryptocurrencyInfo.links.homepage[0]}"
    }

    private fun initGridLayoutWithItems(explorersList: List<String>) {
        val gridLayout = binding.explorersGrid
        gridLayout.alignmentMode = GridLayout.ALIGN_BOUNDS;
        gridLayout.columnCount = 2
        gridLayout.rowCount = if (explorersList.size / 2 == 0) {
            explorersList.size / 2
        } else {
            (explorersList.size / 2) + 1
        }
        explorersList.forEachIndexed { index, item ->
            val textView = TextView(context).apply {
                text = getDomainFromUrl(item)
                background = requireActivity().getDrawable(R.drawable.explorer_background)
            }

            val gridlayoutParams = GridLayout.LayoutParams().apply {
                height = ViewGroup.LayoutParams.WRAP_CONTENT
                width = ViewGroup.LayoutParams.WRAP_CONTENT
                val currentCol = index % 2
                val currentRow = index / 2
                // The last parameter in the specs is the weight, which gives equal size to the cells
                columnSpec = GridLayout.spec(currentCol, 1, 1f)
                rowSpec = GridLayout.spec(currentRow, 1, 1f)
                // Optional, if you want the text to be centered within the cell
                setGravity(Gravity.CENTER_HORIZONTAL)
            }

            gridLayout.addView(textView, gridlayoutParams)
        }
    }

    private fun getDomainFromUrl(url: String): String {
        val pattern = Pattern.compile("(\\w+)\\.\\w{2,}\\.?\\w+(|\\?|\$)")
        val matcher = pattern.matcher(url)
        var domain = ""
        if (matcher.find())
            domain = matcher.group()
        return domain
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
            override fun onChartGestureStart(
                me: MotionEvent,
                lastPerformedGesture: ChartTouchListener.ChartGesture
            ) {
                val yAxis: YAxis = binding.chart.axisLeft
                // Allow scrolling in the right and left margins
                if (me.x > yAxis.longestLabel.length * yAxis.textSize &&
                    me.x < displayWidth - binding.chart.viewPortHandler.offsetRight()
                ) {
//                    viewPager.setPagingEnabled(false)
//                    nestedScrollView.setScrollingEnabled(false)
                }
            }

            override fun onChartGestureEnd(
                me: MotionEvent,
                lastPerformedGesture: ChartTouchListener.ChartGesture
            ) {
//                viewPager.setPagingEnabled(true)
//                nestedScrollView.setScrollingEnabled(true)
            }

            override fun onChartLongPressed(me: MotionEvent) {}
            override fun onChartDoubleTapped(me: MotionEvent) {}
            override fun onChartSingleTapped(me: MotionEvent) {}
            override fun onChartFling(
                me1: MotionEvent,
                me2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ) {
            }

            override fun onChartScale(me: MotionEvent, scaleX: Float, scaleY: Float) {}
            override fun onChartTranslate(me: MotionEvent, dX: Float, dY: Float) {}
        }
    }


    //TODO: add marker for showing price
    override fun onValueSelected(e: Entry?, h: Highlight?) {
    }

    override fun onNothingSelected() {
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