package eu.bernardosan.pillz.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import eu.bernardosan.pillz.R
import eu.bernardosan.pillz.adapters.PillAdapter
import eu.bernardosan.pillz.adapters.PillSelectorAdapter
import eu.bernardosan.pillz.adapters.WeekDayAdapter
import eu.bernardosan.pillz.databinding.FragmentAddBinding
import eu.bernardosan.pillz.utils.HorizontalMarginItemDecoration
import eu.bernardosan.pillz.utils.PageList
import eu.bernardosan.pillz.viewmodel.AddPageViewModel
import java.lang.Math.abs

class AddPageFragment : Fragment() {

    private lateinit var addPageViewModel: AddPageViewModel
    private var _binding: FragmentAddBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addPageViewModel =
            ViewModelProvider(this).get(AddPageViewModel::class.java)

        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val root: View = binding.root

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.Periodicity_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spinnerPeriodicity.adapter = adapter
        }

        binding.ivBack.setOnClickListener {
            parentFragment?.exitTransition
            parentFragment?.findNavController()?.navigate(R.id.navigation_home)
        }

        setupViewPager(binding)

        binding.rvWeeks.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvWeeks.setHasFixedSize(true)
        binding.rvWeeks.isNestedScrollingEnabled = false
        val adapter = WeekDayAdapter()
        adapter.setOnClickListener(object : WeekDayAdapter.OnClickListener {
            override fun onClick(position: Int) {

            }
        })

        binding.rvWeeks.adapter = adapter

        binding.rbDaily.setOnClickListener {
            binding.clStartDatetime.visibility = View.VISIBLE
            binding.spinnerPeriodicity.visibility = View.VISIBLE
            binding.tvTitleAdjustableStartDate.visibility = View.VISIBLE
            binding.tvAdjustableStartDate.visibility = View.VISIBLE
            binding.tvAdjustableStartHour.visibility = View.VISIBLE
            binding.rvWeeks.visibility = View.GONE
            binding.tvTitleFixedStartHour.visibility = View.GONE
            binding.tvTextFixedStartHour.visibility = View.GONE

        }

        binding.rbWeekly.setOnClickListener {
            binding.clStartDatetime.visibility = View.GONE
            binding.spinnerPeriodicity.visibility = View.GONE
            binding.tvTitleAdjustableStartDate.visibility = View.GONE
            binding.tvAdjustableStartDate.visibility = View.GONE
            binding.tvAdjustableStartHour.visibility = View.GONE
            binding.rvWeeks.visibility = View.VISIBLE
            binding.tvTextFixedStartHour.visibility = View.VISIBLE
            binding.tvTitleFixedStartHour.visibility = View.VISIBLE

        }

        binding.tvTextFixedStartHour.setOnClickListener {
            //TODO
        }

        binding.tvAdjustableStartDate.setOnClickListener {
            //TODO
        }

        binding.tvAdjustableStartHour.setOnClickListener {
            //TODO
        }

        val textView: TextView = binding.textDashboard
        addPageViewModel.count.observe(viewLifecycleOwner, Observer {
            textView.text = it.toString()
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViewPager(binding: FragmentAddBinding){

        val adapter = PillSelectorAdapter(PageList.itemSlides)
        val viewPager2 = binding.vp2PillSelector
        viewPager2.adapter = adapter

        viewPager2.offscreenPageLimit = 1


        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            page.scaleY = 1 - (0.25f * abs(position))
        }
        viewPager2.setPageTransformer(pageTransformer)

        val itemDecoration = HorizontalMarginItemDecoration(
            requireContext(),
            R.dimen.viewpager_current_item_horizontal_margin
        )
        viewPager2.addItemDecoration(itemDecoration)
    }
}