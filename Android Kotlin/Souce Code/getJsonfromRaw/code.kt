    private fun getCountries() {
        val `is`: InputStream = activity.resources.openRawResource(R.raw.countries)
        val writer: Writer = StringWriter()
        val buffer = CharArray(1024)
        try {
            val reader: Reader = BufferedReader(InputStreamReader(`is`, "UTF-8"))
            var n: Int = 0
            while (reader.read(buffer).also({ n = it }) != -1) {
                writer.write(buffer, 0, n)
            }
            //val jsonString: String = writer.toString()
            val jsonArray = JSONArray(writer.toString())
            for (i in 0..jsonArray.length()) {
                countryList.add(jsonArray.getJSONObject(i).getString("name"))
            }
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            `is`.close()
        }
    }