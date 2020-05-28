class MainActivity : AppCompatActivity(), MultiplePermissionsListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val permissions = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.SEND_SMS
        )

        requestPermissionsButton.setOnClickListener {
            Dexter.withActivity(this)
                .withPermissions(permissions)
                .withListener(this)
                .check()
        }
    }

    override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>, token: PermissionToken) {
        // This method will be called when the user rejects a permission request
        // You must display a dialog box that explains to the user why the application needs this permission
    }

    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
        // Here you have to check granted permissions
    }
}