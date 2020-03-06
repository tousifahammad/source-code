    public static void writeToFile(String file_name, String data) {
        if (data != null) {
            String path = Environment.getExternalStorageDirectory() + File.separator + "AlevantReqRes";
            // Create the folder.
            File folder = new File(path);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // Create the file.
            File file = new File(folder, file_name + ".txt");

            // Save your stream, don't forget to flush() it before closing it.

            try {
                if (!file.exists()) {
                    file.createNewFile();
                }

                FileOutputStream fOut = new FileOutputStream(file, true);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                myOutWriter.append(data);

                myOutWriter.close();

                fOut.flush();
                fOut.close();
                //Toast.makeText(this, "File created " + file.getPath(), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Log.e("Exception", "File write failed: " + e.toString());
                //Toast.makeText(this, "File error " + e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
