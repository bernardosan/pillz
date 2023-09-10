package eu.bernardosan.pillz.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import eu.bernardosan.pillz.R
import eu.bernardosan.pillz.adapters.DayCalendarAdapter
import eu.bernardosan.pillz.adapters.PillAdapter
import eu.bernardosan.pillz.databinding.FragmentHomeBinding
import eu.bernardosan.pillz.model.ItemPill
import eu.bernardosan.pillz.utils.LastItemMarginItemDecoration
import eu.bernardosan.pillz.viewmodel.HomeViewModel
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var adapterDayCalendar: DayCalendarAdapter
    private var _binding: FragmentHomeBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onStart() {
        binding.rvCalendar.scrollToPosition(homeViewModel.day.value!! - 1)
        super.onStart()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        adapterDayCalendar = DayCalendarAdapter(homeViewModel)


        homeViewModel.month.observe(viewLifecycleOwner, {
            binding.tvMonth.text = getMonthFromNumber(it)
        })

        homeViewModel.year.observe(viewLifecycleOwner, {
            binding.tvYear.text = it.toString()
        })

        homeViewModel.day.observe(viewLifecycleOwner, {
            binding.rvCalendar.smoothScrollToPosition(it - 1)
            setupPillsRV()
        })

        setupCalendarRV()

        val navController = findNavController()

        binding.fabAddPill.setOnClickListener {
            navController.navigate(R.id.navigation_dashboard)
        }


        binding.ivToTakeRight.setOnClickListener {
            if(homeViewModel.day.value!! < homeViewModel.calendar.value!!.getMaximum(Calendar.DAY_OF_MONTH)) {
                homeViewModel.day.value = homeViewModel.day.value!!.plus(1)
                binding.rvCalendar.adapter!!.notifyItemChanged(homeViewModel.day.value!! - 1)
                binding.rvCalendar.adapter!!.notifyItemChanged(homeViewModel.day.value!! - 2)
            }
        }

        binding.ivToTakeLeft.setOnClickListener {
            if(homeViewModel.day.value!! > 1) {
                homeViewModel.day.value = homeViewModel.day.value!!.minus(1)
                binding.rvCalendar.adapter!!.notifyItemChanged(homeViewModel.day.value!! - 1)
                binding.rvCalendar.adapter!!.notifyItemChanged(homeViewModel.day.value!!)
            }
        }

        binding.llDateSelect.setOnClickListener {

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { view, selectedYear, selectedMonth, selectedDay ->
                    binding.rvCalendar.adapter!!.notifyItemChanged(homeViewModel.day.value!! - 1)
                    if(homeViewModel.day.value != selectedDay){
                        homeViewModel.day.value = selectedDay
                    }
                    if(homeViewModel.year.value != selectedYear || homeViewModel.month.value != selectedMonth ) {
                        homeViewModel.year.value = selectedYear
                        homeViewModel.month.value = selectedMonth
                        adapterDayCalendar.updateList(homeViewModel.generateDayItems())
                    }
                    //binding.tvMonth.text = getMonthFromNumber(selectedMonth)
                    //binding.tvYear.text = selectedYear.toString()
                    binding.rvCalendar.smoothScrollToPosition(homeViewModel.day.value!! - 1)
                }, homeViewModel.year.value!!,
                homeViewModel.month.value!!,
                homeViewModel.day.value!!
            )

            datePickerDialog.show()
        }

        return root
    }

    private fun setupPillsRV() {
        val itemPillList = homeViewModel.dayList.value!![homeViewModel.day.value!!-1].pills
        val linearLayoutManagerVertical =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvPills.layoutManager = linearLayoutManagerVertical
        binding.rvPills.setHasFixedSize(false)
        binding.rvPills.isNestedScrollingEnabled = true
        binding.rvPills.adapter = PillAdapter(requireContext(), itemPillList)
    }

    private fun setupCalendarRV() {
        val itemMargin =
            resources
                .getDimensionPixelOffset(
                    R.dimen.item_margin
                )

        binding
            .rvCalendar
            .addItemDecoration(
                LastItemMarginItemDecoration(lastItemMargin = itemMargin)
            )

        adapterDayCalendar.setOnClickListener(
            object : DayCalendarAdapter.OnClickListener {
                override fun onClick(position: Int) {
                    val lastpos = homeViewModel.day.value!! - 1
                    homeViewModel.day.value = position + 1
                    binding.rvCalendar.adapter!!.notifyItemChanged(position)
                    binding.rvCalendar.adapter!!.notifyItemChanged(lastpos)

                }
            }
        )

        val linearLayoutManagerHorizontal =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCalendar.layoutManager = linearLayoutManagerHorizontal
        binding.rvCalendar.scrollToPosition(homeViewModel.day.value!! - 1)
        binding.rvCalendar.setHasFixedSize(false)
        binding.rvCalendar.isNestedScrollingEnabled = false
        binding.rvCalendar.adapter = adapterDayCalendar
    }

    private fun getMonthFromNumber(monthNumber: Int): String {
        val monthNames = arrayOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )

        // Check if the month number is within a valid range
        if (monthNumber in 0..11) {
            return monthNames[monthNumber]
        }

        return "Invalid Month"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}