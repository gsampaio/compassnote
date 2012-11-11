package com.everhack.compassnote.foursquare;

import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class FoursquareVenueImageService extends FoursquareService {
	
	public static void getVenuesImageInCity(FoursquareVenue venue, final int width, final int height, final FoursquareServiceDelegate<Bitmap> delegate) {
		RequestParams params = new RequestParams();
		//https://api.foursquare.com/v2/?group=venue&
		params.put("group", "venue");
		params.put("oauth_token", "4CMG2JAPIUAGEGN1T3433QYRM3Z5UYKXNCFSEAINNC440402");
    	params.put("v", "20121110");
    	AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {
    		@Override
    		public void onSuccess(String arg0) {
    			super.onSuccess(arg0);
				try {
					JSONObject json = new JSONObject(arg0);
					JSONObject response = json.getJSONObject("response");
					JSONObject photos = response.getJSONObject("photos");
					Integer count = photos.getInt("count");
					if (count != null && count.intValue() > 0) {
						JSONArray photoArray = photos.getJSONArray("items");
						JSONObject photo = photoArray.getJSONObject(0);
						
						String prefix = photo.getString("prefix");
						String sufix = photo.getString("suffix");

						String url = prefix + Integer.valueOf(width) + "x" + Integer.valueOf(height) + sufix;
						System.out.println(url);
						AsyncTask<String, Void, Bitmap> task = new AsyncTask<String, Void, Bitmap>() {

							@Override
							protected Bitmap doInBackground(String... params) {
								return FoursquareVenueImageService.downloadBitmap(params[0]);
							}
							
							protected void onPostExecute(Bitmap result) {
								delegate.receivedResponse(result);
							};
							
						};
						
						task.execute(url);
					}
				} catch (JSONException e) {
					delegate.requestFailed();
				}
    		}
    		@Override
    		public void onFailure(Throwable arg0, String arg1) {
    			super.onFailure(arg0, arg1);
    			delegate.requestFailed();
    		}
    	};
    	String url = BASE_URL + "/venues/" + venue.getId() + "/photos";
    	client.get(url, params, handler);
	}
	
//	private static void getVenuesImageInputStreamInCity(String url, final FoursquareServiceDelegate<InputStream> delegate) {
//		String[] allowedContentTypes = new String[] {"*/*"};
//		AsyncHttpResponseHandler handler = new BinaryHttpResponseHandler(allowedContentTypes) {
//			@Override
//			public void onSuccess(byte[] arg0) {
//				super.onSuccess(arg0);
//				InputStream inputStream = new ByteArrayInputStream(arg0);
//				delegate.receivedResponse(inputStream);
//				System.out.println("inputStream null? " + inputStream == null ? "YES" : "NO");
//			}
//			
//			@Override
//			public void onFailure(Throwable arg0, String arg1) {
//				super.onFailure(arg0, arg1);
//				System.out.println(arg1);
//				delegate.requestFailed();
//			}
//			
//			@Override
//			public void onFailure(Throwable arg0, byte[] arg1) {
//				super.onFailure(arg0, arg1);
//				System.out.println(arg0);
//			}
//		};
//		client.get(url, handler);
//	}
	
	static Bitmap downloadBitmap(String url) {
	    final AndroidHttpClient client = AndroidHttpClient.newInstance("Android");
	    final HttpGet getRequest = new HttpGet(url);

	    try {
	        HttpResponse response = client.execute(getRequest);
	        final int statusCode = response.getStatusLine().getStatusCode();
	        if (statusCode != HttpStatus.SC_OK) { 
	            Log.w("ImageDownloader", "Error " + statusCode + " while retrieving bitmap from " + url); 
	            return null;
	        }
	        
	        final HttpEntity entity = response.getEntity();
	        if (entity != null) {
	            InputStream inputStream = null;
	            try {
	                inputStream = entity.getContent(); 
	                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
	                System.out.println("DOWNLOAD SUCCESSFULY");
	                return bitmap;
	            } finally {
	                if (inputStream != null) {
	                    inputStream.close();  
	                }
	                entity.consumeContent();
	            }
	            
	        }
	    } catch (Exception e) {
	        // Could provide a more explicit error message for IOException or IllegalStateException
	        getRequest.abort();
	        System.out.println("FAILED TO DOWNLOAD");
//	        Log.w("ImageDownloader", "Error while retrieving bitmap from " + url, e.toString());
	    } finally {
	        if (client != null) {
	            client.close();
	        }
	    }
	    return null;
	}
}
