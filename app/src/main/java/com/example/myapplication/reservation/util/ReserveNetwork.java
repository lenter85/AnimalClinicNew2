package com.example.myapplication.reservation.util;

import android.os.AsyncTask;
import android.util.Log;

class ReserveNetwork extends AsyncTask<String, Integer, Long> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Long doInBackground(String... urls) {
        Log.i("test", "doInBackground...");
        int count = urls.length;
        long totalSize = 0;
        for (int i = 0; i < count; i++) {
            // 내려받는 파일 size 합산 작업
            // totalSize += Downloader.downloadFile(urls[i]);

            publishProgress((int) ((i / (float) count) * 100));
            // Escape early if cancel() is called
            if (isCancelled()) break;
        }
        return totalSize;

    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        // 파일 다운로드 퍼센티지 표시 작업
        // setProgressPercent(progress[0]);
    }

    @Override
    protected void onPostExecute(Long result) {
        // 다 받아진 후 받은 파일 총용량 표시 작업
        // showDialog("Downloaded " + result + " bytes");
    }

    @Override
    protected void onCancelled(Long aLong) {
        super.onCancelled(aLong);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

}