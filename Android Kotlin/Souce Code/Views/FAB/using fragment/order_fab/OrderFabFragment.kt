package com.webgrity.tisha.ui.fragment.order_fab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.webgrity.tisha.databinding.FragmentOrderFabBinding
import com.webgrity.tisha.util.getViewModel
import com.webgrity.tisha.util.playNotificationSound
import kotlinx.android.synthetic.main.layout_fab.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein


class OrderFabFragment(private val onFabClicked: (fab: Fab) -> Unit) : Fragment(), KodeinAware {
    override val kodein by kodein()

    private lateinit var binding: FragmentOrderFabBinding
    private lateinit var viewModel: OrderFabViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentOrderFabBinding.inflate(inflater)
        viewModel = getViewModel { OrderFabViewModel(kodein) }
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpFab()
        initObservers()
    }

    private fun setUpFab() {
        viewModel.fabAdapter = FabAdapter(viewModel.totalFabList, onFabClicked)
        rv_fab.adapter = viewModel.fabAdapter
    }

    private fun initObservers() {
        viewModel.observeOnlineOrder().observe(requireActivity(), {
            lifecycleScope.launch {
                viewModel.onlineFab.count = it.size
                viewModel.updateFabView()
            }
        })

        viewModel.observeSeatedInvoice().observe(requireActivity()) {
            viewModel.updateBills(it.toMutableList())
        }

        viewModel.playSoundLD.observe(requireActivity()) {
            requireContext().playNotificationSound()
        }
    }
}