class MainActivity : AppCompatActivity(), PermissionListener {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startCameraButton.setOnClickListener {
            Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(this)
                .check()
        }
    }

    override fun onPermissionGranted(response: PermissionGrantedResponse) {
        // This method will be called when the permission is denied
    }

    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken?) {
        // This method will be called when the user rejects a permission request
        // You must display a dialog box that explains to the user why the application needs this permission
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse) {
        // This method will be called when the permission is granted
    }
}