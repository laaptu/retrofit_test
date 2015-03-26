package com.alchemisthq.retrofit;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.alchemisthq.retrofit.test.CustomCallbackInterface;
import com.alchemisthq.retrofit.test.GeoApi;
import com.alchemisthq.retrofit.test.GeoData;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit.Callback;
import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.MainThreadExecutor;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedInput;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void post(View view) {
        //somePostData();
        //postFormData();
        //postPOJO();
        //postHashMap();
        //postMultiPartData();
        //postMultiPartDatum();
        //postCancellableInterface();
        cancelRequestTest();
    }


    ExecutorService executorService;
    private static String URL_HTTPBIN = "http://httpbin.org";
    private static String URL_REQUESTBIN = "http://requestb.in";


    private void cancelRequestTest() {
        executorService = Executors.newCachedThreadPool();
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(URL_HTTPBIN).
                setErrorHandler(errorHandler).setExecutors(executorService, new MainThreadExecutor()).build();
        GeoApi geoApi = restAdapter.create(GeoApi.class);
        geoApi.cancelRequestTest(5, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Log.i("Some Response", "Some Response");
                try {
                    String out = getPostResponseAsString(response);
                    System.out.println("Response  " + out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();

            }
        });
    }

    private ErrorHandler errorHandler = new ErrorHandler() {
        @Override
        public Throwable handleError(RetrofitError cause) {
            System.out.println("Error Received");
            return null;
        }
    };

    private void postCancellableInterface() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://httpbin.org").build();
        GeoApi geoApi = restAdapter.create(GeoApi.class);
        geoApi.cancelRequestTest(5, cancelCallbackInterface);
    }

    public void cancelRequest(View view) {
        cancelCallbackInterface.cancel();
        if (executorService != null) {
            executorService.shutdownNow();
            executorService = null;
        }
    }


    private CustomCallbackInterface<Response> cancelCallbackInterface = new CustomCallbackInterface<Response>() {

        private boolean cancelTask = false;

        @Override
        public String getTag() {
            return null;
        }

        @Override
        public String setTag(String tag) {
            return null;
        }

        @Override
        public void cancel() {
            cancelTask = true;
        }

        @Override
        public boolean isCancelled() {
            return cancelTask;
        }

        @Override
        public void success(Response response, Response response2) {
            onSuccess(response);

        }

        @Override
        public void failure(RetrofitError error) {
            error.printStackTrace();
        }

        private void onSuccess(Response response) {
            if (response == null) {
                System.out.println("Call Error");
            } else {
                Log.i("Some Response", "Some Response");
                try {
                    String out = getPostResponseAsString(response);
                    System.out.println("Response  " + out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (cancelTask) {
                System.out.println("Don't send anything");
            } else {
                System.out.println("Send the data");
            }
            cancelTask = false;

        }
    };

    public void postMultiPartDatum() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://httpbin.org").build();
        GeoApi geoApi = restAdapter.create(GeoApi.class);
        String photoPath = "/storage/sdcard0/DCIM/Camera/me.jpg";
        Map<String, TypedFile> multiFiles = new HashMap<String, TypedFile>();
        multiFiles.put("FirstImage", new TypedFile("0", new File(photoPath)));
        multiFiles.put("SecondImage", new TypedFile("1", new File(photoPath)));
        geoApi.postMultipartDatum("Auth123456", "Photo Upload", multiFiles, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {

                Log.i("Some Response", "Some Response");
                try {
                    String out = getPostResponseAsString(response);
                    System.out.println("Response  " + out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    public void postMultiPartData() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://httpbin.org").build();
        GeoApi geoApi = restAdapter.create(GeoApi.class);

        String photoPath = "/storage/sdcard0/DCIM/Camera/me.jpg";
        geoApi.postMultipartData("1234", "Photo Title", new TypedFile("Photo", new File(photoPath)), new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Log.i("Some Response", "Some Response");
                try {
                    String out = getPostResponseAsString(response);
                    System.out.println("Response  " + out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    public void postHashMap() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://requestb.in").build();
        GeoApi geoApi = restAdapter.create(GeoApi.class);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("Username", "Santosh");
        params.put("Password", "santosh124");
        params.put("id", 4567);

        geoApi.postHashMap(params, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Log.i("Some Response", "Some Response");
                try {
                    String out = getPostResponseAsString(response);
                    System.out.println("Response  " + out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();

            }
        });
    }

    public void postPOJO() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://httpbin.org").build();
        GeoApi geoApi = restAdapter.create(GeoApi.class);
        GeoData geoData = new GeoData();
        geoData.asn = "This is asn";
        geoData.ip = "102.157.5.7";
        geoData.latitude = 1253453453;
        geoData.longitude = 6464546;
        geoApi.postPOJO(geoData, new Callback<Response>() {


            @Override
            public void success(Response response, Response response2) {
                Log.i("Some Response", "Some Response");
                try {
                    String out = getPostResponseAsString(response);
                    System.out.println("Response  " + out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                // Log.i("error", error.getMessage());
                error.printStackTrace();
            }
        });
    }


    public void postFormData() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://requestb.in").build();
        GeoApi geoApi = restAdapter.create(GeoApi.class);
        geoApi.postFormData("Santosh", "santosh123", new Callback<Response>() {


            @Override
            public void success(Response response, Response response2) {
                Log.i("Some Response", "Some Response");
                try {
                    String out = getPostResponseAsString(response);
                    System.out.println("Response  " + out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                // Log.i("error", error.getMessage());
                error.printStackTrace();
            }
        });

    }

    public void somePostData() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://requestb.in").build();
        GeoApi geoApi = restAdapter.create(GeoApi.class);
        geoApi.postString("Hello", new Callback<Response>() {


            @Override
            public void success(Response response, Response response2) {
                Log.i("Some Response", "Some Response");
                try {
                    String out = getPostResponseAsString(response);
                    System.out.println("Response  " + out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                // Log.i("error", error.getMessage());
                error.printStackTrace();
            }
        });

    }

    private String getPostResponseAsString(Response response) throws IOException {
        TypedInput body = response.getBody();
        BufferedReader reader = new BufferedReader(new InputStreamReader(body.in()));
        StringBuilder out = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
            out.append(newLine);
        }

        return out.toString();
    }


    public void getGeoIp(View view) {
        getGeoIpPath();

    }

    public void getGeoIpPath() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://www.telize.com/").build();
        GeoApi geoApi = restAdapter.create(GeoApi.class);
        GeoData geoData = null;
        geoApi.fetchGeoData("geoip", new Callback<GeoData>() {
            @Override
            public void success(GeoData geoData, Response response) {
                if (geoData != null) {
                    Log.i("response", response.toString());
                    Log.i("GeoData Isp", geoData.isp);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("Error", error.getMessage());

            }

        });
    }

    public void getGeoIp() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://www.telize.com/").build();
        GeoApi geoApi = restAdapter.create(GeoApi.class);
        GeoData geoData = null;
        geoApi.fetchGeoData(new Callback<GeoData>() {
            @Override
            public void success(GeoData geoData, Response response) {
                if (geoData != null) {
                    Log.i("response", response.toString());
                    Log.i("GeoData Isp", geoData.isp);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("Error", error.getMessage());

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
