package com.ricky.f.listener;

/**
 * Created by Deak on 16/10/11.
 */

public interface DownloadListener {
    void onStart(long filesize);
    void onProgress(int percent);
    void onSucceed(String path);
    void onError(int code, String message);
}
