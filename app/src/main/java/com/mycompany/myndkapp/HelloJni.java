package com.mycompany.myndkapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
public class HelloJni extends Activity {

    private ProgressBar progressBar;
    private TextView progressText;
    private Button btnPause, btnCancel;

    private boolean isPaused = false;
    private boolean isCanceled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

   
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText inputText = new EditText(this);
        inputText.setHint("Enter GitHub Repo URL");
        layout.addView(inputText);

        Button submitButton = new Button(this);
        submitButton.setText("Download");
        layout.addView(submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String repoUrl = inputText.getText().toString().trim();
                    if (!repoUrl.isEmpty()) {
                        showDownloadOverlay();
                        downloadGitHubRepo(repoUrl);
                    } else {
                        Toast.makeText(HelloJni.this, "Please enter a valid URL", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        setContentView(layout);
    }

    private void showDownloadOverlay() {
        View overlay = getLayoutInflater().inflate(R.layout.overlay_download, null);

        // Find UI elements
        progressBar = overlay.findViewById(R.id.progressBar);
        progressText = overlay.findViewById(R.id.progressText);
        btnPause = overlay.findViewById(R.id.btnPause);
        btnCancel = overlay.findViewById(R.id.btnCancel);
        
        setContentView(overlay);
        btnPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isPaused = true;
                    btnPause.setEnabled(false);
                    btnPause.setEnabled(true);
                    Toast.makeText(HelloJni.this, "Download paused", Toast.LENGTH_SHORT).show();
                }
            });

        

        // Cancel Button Logic
        btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isCanceled = true;
                    Toast.makeText(HelloJni.this, "Download canceled", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
    }

    private void downloadGitHubRepo(final String repoUrl) {
        Thread downloadThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String[] urlParts = repoUrl.split("/");
                        String owner = urlParts[urlParts.length - 2];
                        String repo = urlParts[urlParts.length - 1];
                        String zipUrl = "https://github.com/" + owner + "/" + repo + "/archive/refs/heads/main.zip";

                        URL url = new URL(zipUrl);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.connect();

                        int fileLength = connection.getContentLength();

                        if (fileLength <= 0) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setIndeterminate(true);
                                        progressText.setText("Downloading...");
                                    }
                                });
                        } else {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setIndeterminate(false);
                                        progressBar.setMax(100);
                                    }
                                });
                        }

                        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                            InputStream inputStream = connection.getInputStream();
                            final File downloadFile = new File(getExternalFilesDir(null), repo + ".zip");
                            FileOutputStream outputStream = new FileOutputStream(downloadFile);

                            byte[] buffer = new byte[4096];
                            long totalBytesRead = 0;
                            int bytesRead;

                            while ((bytesRead = inputStream.read(buffer)) != -1) {
                                totalBytesRead += bytesRead;
                                outputStream.write(buffer, 0, bytesRead);

                                if (fileLength > 0) {
                                    final int progress = (int) ((totalBytesRead * 100) / fileLength);
                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressBar.setProgress(progress);
                                                progressText.setText("Downloading: " + progress + "%");
                                            }
                                        });
                                }
                            }

                            outputStream.close();
                            inputStream.close();

                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(HelloJni.this, "Download complete: " + downloadFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
                                        progressText.setText("Saved at: " + downloadFile.getAbsolutePath());
                                        progressBar.setVisibility(View.GONE);
                                    }
                                });
                        } else {
                            showToast("Download failed. Response code: " + connection.getResponseCode());
                        }
                        connection.disconnect();
                    } catch (final Exception e) {
                        e.printStackTrace();
                        showToast("Error: " + e.getMessage());
                    }
                }
            });
        downloadThread.start();
    }

    private void showToast(final String message) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(HelloJni.this, message, Toast.LENGTH_SHORT).show();
                }
            });
    }
}
