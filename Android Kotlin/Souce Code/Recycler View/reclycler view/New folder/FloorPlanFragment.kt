

class FloorPlanFragment : Fragment(), FloorAdapterListener {

   
    private var rvFloor: RecyclerView? = null

    private var results: ArrayList<Floor> = ArrayList()
    private var mPlanAdapter: FloorPlanAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_floor_plan, container, false)

        initUI(view)

        initListeners()

        return view
    }

    override fun onResume() {
        super.onResume()
        initSavedFloors()
    }


    private fun initUI(view: View) {
        rvFloor = view.findViewById(R.id.rvFloor)

        rvFloor?.layoutManager = GridLayoutManager(context, 2)
    }
}


    private fun initSavedFloors() {
        try {
            results.clear()
            results = floorRepository.getAllData() ?: return
            setAdapter()
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        }
    }


    override fun onSubmitClicked() {
        initSavedFloors()
    }


    private fun setAdapter() {
        mPlanAdapter = FloorPlanAdapter(results, this)
        rvFloor?.adapter = mPlanAdapter
    }

    override fun onFloorClicked(floorId: Int, floorName: String) {
        Intent(context, FloorPlanActivity::class.java).also {
            it.putExtra("floorId", floorId)
            it.putExtra("floorName", floorName)
            context?.startActivity(it)
        }
    }

    

}
