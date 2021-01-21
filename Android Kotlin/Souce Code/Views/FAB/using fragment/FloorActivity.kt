package com.webgrity.tisha.ui.activities.floor.main_floor




class FloorActivity : BaseActivity(), KodeinAware, InternetListener {
    override val kodein by kodein()

    private lateinit var binding: ActivityFloorBinding
    private lateinit var viewModel: FloorViewModel

    var floorList: MutableList<Floor> = mutableListOf()
    var pagerAdapter: ViewPagerAdapter? = null
    var receiver: BroadcastReceiver? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_floor)
        viewModel = getViewModel { FloorViewModel(kodein) }
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        seUpFab()
    }


  

    private fun seUpFab() {
        try {
            val fragment = OrderFabFragment(onFabClicked)
            supportFragmentManager
                .beginTransaction()
                .add(R.id.ll_fb_container, fragment, "OrderFabFragment")
                .disallowAddToBackStack()
                .commit()
        } catch (error: Exception) {
            error.printStackTrace()
        }
    }

    private val onFabClicked = fun(fab: Fab) {
        if (fab.type == "order" && fab.count > 0) {
            Intent(this, RegisterTakeawayActivity::class.java).also {
                it.putExtra("position", 3)
                startActivity(it)
            }
        } else if (fab.type == "bill") {
            viewModel.gotoNextScreen(this, fab.invoiceId)
        }
    }
}
