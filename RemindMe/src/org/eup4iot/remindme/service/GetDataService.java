package org.eup4iot.remindme.service;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.eup4iot.remindme.RemindMeApplicationContext;
import org.eup4iot.remindme.common.ApplicationConstants;
import org.eup4iot.remindme.common.HttpClientFactory;
import org.eup4iot.remindme.db.DatabaseManager;
import org.eup4iot.remindme.exception.RemindMeException;
import org.eup4iot.remindme.model.ActivityManager;
import org.eup4iot.remindme.model.ChannelManager;
import org.eup4iot.remindme.model.LocationManager;
import org.eup4iot.remindme.model.ObjectPropertyManager;
import org.eup4iot.remindme.model.Program;
import org.eup4iot.remindme.model.RecommendedProgramsManager;
import org.eup4iot.remindme.model.SmartObjectManager;
import org.eup4iot.remindme.model.TaskManager;
import org.eup4iot.remindme.model.UsersManager;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

public class GetDataService extends IntentService {

    /** The tag. */
    private final String TAG = GetDataService.this.getClass().getSimpleName();

    /** The receiver. */
    private ResultReceiver receiver = null;

    public GetDataService(String name) {
        super(name);
    }

    public GetDataService() {
        super("GetDataService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        receiver = intent.getParcelableExtra("receiver");
		Bundle bundle = intent.getExtras();
		Bundle b = new Bundle();
		
		b.putString("response_action", intent.getAction());
        Log.d(TAG, "Action: " + intent.getAction());
        
        if (ApplicationConstants.SERVICE_GET_RECOMMENDATIONS.equals(intent.getAction())) {
			try {
				String url = ApplicationConstants.SERVER_BASE_URL 
						+ ApplicationConstants.GET_RECOMMENDATIONS_URL;

				Log.i(TAG, "url: " + url);					
				RecommendedProgramsManager serverResponse = null;
				
	            try {
	            	serverResponse = postData(url);
	            } catch (RemindMeException e) {
	                b.putString("results", "failed");
	                b.putString("message", e.getErrorMessage());
	                receiver.send(2, b);
	                return;
	            } catch (Exception e) {
	                b.putString("results", "failed");
	                b.putString("message", ApplicationConstants.GENERAL_ERROR_MSG);
	                receiver.send(2, b);
	                return;
	            }

				if (serverResponse == null) {
					b.putString("results", "failed");
					b.putString("message", ApplicationConstants.GENERAL_ERROR_MSG);
					receiver.send(2, b);	
				} else if (serverResponse.getError() != null) {
					b.putString("results", "failed");
					b.putString("message", serverResponse.getError().getMessage());
					receiver.send(2, b);
				} else {
					b.putString("results", "success");
					b.putString("message", "success");
					RemindMeApplicationContext.getRemindMeApplicationContext()
							.setRecommendedProgramsList(serverResponse.getRecommendedProgramsList());
					receiver.send(1, b);
				}				
			} catch (Exception ex) {
				Log.e(TAG, "onHandleIntent: " + ex.toString());
				b.putString("results", "failed");
				b.putString("message", ApplicationConstants.GENERAL_ERROR_MSG);
				receiver.send(2, b);
			}
        } else if (ApplicationConstants.SERVICE_GET_OBJECTS.equals(intent.getAction())) {
			try {
				String url = ApplicationConstants.SERVER_BASE_URL 
						+ ApplicationConstants.GET_SMARTOBJECTS_URL;

				Log.i(TAG, "url: " + url);					
				SmartObjectManager serverResponse = null;
				
	            try {
	            	serverResponse = postData2(url);
	            } catch (RemindMeException e) {
	                b.putString("results", "failed");
	                b.putString("message", e.getErrorMessage());
	                receiver.send(2, b);
	                return;
	            } catch (Exception e) {
	                b.putString("results", "failed");
	                b.putString("message", ApplicationConstants.GENERAL_ERROR_MSG);
	                receiver.send(2, b);
	                return;
	            }

				if (serverResponse == null) {
					b.putString("results", "failed");
					b.putString("message", ApplicationConstants.GENERAL_ERROR_MSG);
					receiver.send(2, b);	
				} else if (serverResponse.getError() != null) {
					b.putString("results", "failed");
					b.putString("message", serverResponse.getError().getMessage());
					receiver.send(2, b);
				} else {
					b.putString("results", "success");
					b.putString("message", "success");
					RemindMeApplicationContext.getRemindMeApplicationContext()
							.setSmartObjectsList(serverResponse.getSmartObjectsList());
					receiver.send(1, b);
				}				
			} catch (Exception ex) {
				Log.e(TAG, "onHandleIntent: " + ex.toString());
				b.putString("results", "failed");
				b.putString("message", ApplicationConstants.GENERAL_ERROR_MSG);
				receiver.send(2, b);
			}
        } else if (ApplicationConstants.SERVICE_GET_TASKS.equals(intent.getAction())) {
			try {
				String url = ApplicationConstants.SERVER_BASE_URL 
						+ ApplicationConstants.GET_TASKS_URL;

				Log.i(TAG, "url: " + url);					
				TaskManager serverResponse = null;
				
	            try {
	            	serverResponse = postData3(url);
	            } catch (RemindMeException e) {
	                b.putString("results", "failed");
	                b.putString("message", e.getErrorMessage());
	                receiver.send(2, b);
	                return;
	            } catch (Exception e) {
	                b.putString("results", "failed");
	                b.putString("message", ApplicationConstants.GENERAL_ERROR_MSG);
	                receiver.send(2, b);
	                return;
	            }

				if (serverResponse == null) {
					b.putString("results", "failed");
					b.putString("message", ApplicationConstants.GENERAL_ERROR_MSG);
					receiver.send(2, b);	
				} else if (serverResponse.getError() != null) {
					b.putString("results", "failed");
					b.putString("message", serverResponse.getError().getMessage());
					receiver.send(2, b);
				} else {
					b.putString("results", "success");
					b.putString("message", "success");
					RemindMeApplicationContext.getRemindMeApplicationContext()
							.setTasksList(serverResponse.getTasksList());
					receiver.send(1, b);
				}				
			} catch (Exception ex) {
				Log.e(TAG, "onHandleIntent: " + ex.toString());
				b.putString("results", "failed");
				b.putString("message", ApplicationConstants.GENERAL_ERROR_MSG);
				receiver.send(2, b);
			}
        } else if (ApplicationConstants.SERVICE_GET_PROPERTIES.equals(intent.getAction())) {
			try {
				String url = ApplicationConstants.SERVER_BASE_URL 
						+ ApplicationConstants.GET_OBJECTPROPERTY_URL;

				Log.i(TAG, "url: " + url);					
				ObjectPropertyManager serverResponse = null;
				
	            try {
	            	serverResponse = postData4(url);
	            } catch (RemindMeException e) {
	                b.putString("results", "failed");
	                b.putString("message", e.getErrorMessage());
	                receiver.send(2, b);
	                return;
	            } catch (Exception e) {
	                b.putString("results", "failed");
	                b.putString("message", ApplicationConstants.GENERAL_ERROR_MSG);
	                receiver.send(2, b);
	                return;
	            }

				if (serverResponse == null) {
					b.putString("results", "failed");
					b.putString("message", ApplicationConstants.GENERAL_ERROR_MSG);
					receiver.send(2, b);	
				} else if (serverResponse.getError() != null) {
					b.putString("results", "failed");
					b.putString("message", serverResponse.getError().getMessage());
					receiver.send(2, b);
				} else {
					b.putString("results", "success");
					b.putString("message", "success");
					RemindMeApplicationContext.getRemindMeApplicationContext()
							.setPropertyList(serverResponse.getObjectPropertiesList());
					receiver.send(1, b);
				}				
			} catch (Exception ex) {
				Log.e(TAG, "onHandleIntent: " + ex.toString());
				b.putString("results", "failed");
				b.putString("message", ApplicationConstants.GENERAL_ERROR_MSG);
				receiver.send(2, b);
			}
        } else if (ApplicationConstants.SERVICE_GET_LOCATIONS.equals(intent.getAction())) {
			try {
				String url = ApplicationConstants.SERVER_BASE_URL 
						+ ApplicationConstants.GET_LOCATION_URL;

				Log.i(TAG, "url: " + url);					
				LocationManager serverResponse = null;
				
	            try {
	            	serverResponse = postData5(url);
	            } catch (RemindMeException e) {
	                b.putString("results", "failed");
	                b.putString("message", e.getErrorMessage());
	                receiver.send(2, b);
	                return;
	            } catch (Exception e) {
	                b.putString("results", "failed");
	                b.putString("message", ApplicationConstants.GENERAL_ERROR_MSG);
	                receiver.send(2, b);
	                return;
	            }

				if (serverResponse == null) {
					b.putString("results", "failed");
					b.putString("message", ApplicationConstants.GENERAL_ERROR_MSG);
					receiver.send(2, b);	
				} else if (serverResponse.getError() != null) {
					b.putString("results", "failed");
					b.putString("message", serverResponse.getError().getMessage());
					receiver.send(2, b);
				} else {
					b.putString("results", "success");
					b.putString("message", "success");
					RemindMeApplicationContext.getRemindMeApplicationContext()
							.setLocationsList(serverResponse.getLocationsList());
					receiver.send(1, b);
				}				
			} catch (Exception ex) {
				Log.e(TAG, "onHandleIntent: " + ex.toString());
				b.putString("results", "failed");
				b.putString("message", ApplicationConstants.GENERAL_ERROR_MSG);
				receiver.send(2, b);
			}
        } else if (ApplicationConstants.SERVICE_GET_ACTIVITIES.equals(intent.getAction())) {
			try {
				String url = ApplicationConstants.SERVER_BASE_URL 
						+ ApplicationConstants.GET_ACTIVITY_URL;

				Log.i(TAG, "url: " + url);					
				ActivityManager serverResponse = null;
				
	            try {
	            	serverResponse = postData6(url);
	            } catch (RemindMeException e) {
	                b.putString("results", "failed");
	                b.putString("message", e.getErrorMessage());
	                receiver.send(2, b);
	                return;
	            } catch (Exception e) {
	                b.putString("results", "failed");
	                b.putString("message", ApplicationConstants.GENERAL_ERROR_MSG);
	                receiver.send(2, b);
	                return;
	            }

				if (serverResponse == null) {
					b.putString("results", "failed");
					b.putString("message", ApplicationConstants.GENERAL_ERROR_MSG);
					receiver.send(2, b);	
				} else if (serverResponse.getError() != null) {
					b.putString("results", "failed");
					b.putString("message", serverResponse.getError().getMessage());
					receiver.send(2, b);
				} else {
					b.putString("results", "success");
					b.putString("message", "success");
					RemindMeApplicationContext.getRemindMeApplicationContext()
							.setActivityList(serverResponse.getActivityList());
					receiver.send(1, b);
				}				
			} catch (Exception ex) {
				Log.e(TAG, "onHandleIntent: " + ex.toString());
				b.putString("results", "failed");
				b.putString("message", ApplicationConstants.GENERAL_ERROR_MSG);
				receiver.send(2, b);
			}
        } else if (ApplicationConstants.SERVICE_GET_USERS.equals(intent.getAction())) {
			try {
				String url = ApplicationConstants.SERVER_BASE_URL 
						+ ApplicationConstants.GET_USERS_URL;

				Log.i(TAG, "url: " + url);					
				UsersManager serverResponse = null;
				
	            try {
	            	serverResponse = postData7(url);
	            } catch (RemindMeException e) {
	                b.putString("results", "failed");
	                b.putString("message", e.getErrorMessage());
	                receiver.send(2, b);
	                return;
	            } catch (Exception e) {
	                b.putString("results", "failed");
	                b.putString("message", ApplicationConstants.GENERAL_ERROR_MSG);
	                receiver.send(2, b);
	                return;
	            }

				if (serverResponse == null) {
					b.putString("results", "failed");
					b.putString("message", ApplicationConstants.GENERAL_ERROR_MSG);
					receiver.send(2, b);	
				} else if (serverResponse.getError() != null) {
					b.putString("results", "failed");
					b.putString("message", serverResponse.getError().getMessage());
					receiver.send(2, b);
				} else {
					b.putString("results", "success");
					b.putString("message", "success");
					RemindMeApplicationContext.getRemindMeApplicationContext()
							.setUsersList(serverResponse.getUsersList());
					receiver.send(1, b);
				}				
			} catch (Exception ex) {
				Log.e(TAG, "onHandleIntent: " + ex.toString());
				b.putString("results", "failed");
				b.putString("message", ApplicationConstants.GENERAL_ERROR_MSG);
				receiver.send(2, b);
			}
        } else if (ApplicationConstants.SERVICE_GET_CHANNELS.equals(intent.getAction())) {
			try {
				String url = ApplicationConstants.SERVER_BASE_URL 
						+ ApplicationConstants.GET_CHANNELS_URL;

				Log.i(TAG, "url: " + url);					
				ChannelManager serverResponse = null;
				
	            try {
	            	serverResponse = postData8(url);
	            } catch (RemindMeException e) {
	                b.putString("results", "failed");
	                b.putString("message", e.getErrorMessage());
	                receiver.send(2, b);
	                return;
	            } catch (Exception e) {
	                b.putString("results", "failed");
	                b.putString("message", ApplicationConstants.GENERAL_ERROR_MSG);
	                receiver.send(2, b);
	                return;
	            }

				if (serverResponse == null) {
					b.putString("results", "failed");
					b.putString("message", ApplicationConstants.GENERAL_ERROR_MSG);
					receiver.send(2, b);	
				} else if (serverResponse.getError() != null) {
					b.putString("results", "failed");
					b.putString("message", serverResponse.getError().getMessage());
					receiver.send(2, b);
				} else {
					b.putString("results", "success");
					b.putString("message", "success");
					RemindMeApplicationContext.getRemindMeApplicationContext().setChannelsList(serverResponse.getChannelsList());
					receiver.send(1, b);
				}				
			} catch (Exception ex) {
				Log.e(TAG, "onHandleIntent: " + ex.toString());
				b.putString("results", "failed");
				b.putString("message", ApplicationConstants.GENERAL_ERROR_MSG);
				receiver.send(2, b);
			}
        } else if (ApplicationConstants.SERVICE_GET_PROGRAMS.equals(intent.getAction())) {
			try {
				
				DatabaseManager dbMgr = new DatabaseManager(this);
				ArrayList<Program> programList = dbMgr.getPrograms();

				if (programList == null) {
					b.putString("results", "failed");
					b.putString("message", ApplicationConstants.GENERAL_ERROR_MSG);
					receiver.send(2, b);	
				} else {
					b.putString("results", "success");
					b.putString("message", "success");
					RemindMeApplicationContext.getRemindMeApplicationContext().setMyProgramsList(programList);
					receiver.send(1, b);
				}				
			} catch (Exception ex) {
				Log.e(TAG, "onHandleIntent: " + ex.toString());
				b.putString("results", "failed");
				b.putString("message", ApplicationConstants.GENERAL_ERROR_MSG);
				receiver.send(2, b);
			}
        }
    }
    
    private RecommendedProgramsManager postData(String url) {
        HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
        Log.d(TAG, "url: " + url);

        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Content-Type", "application/json");
            httpGet.setHeader("Accept", "JSON");

            HttpResponse httpResponse = httpclient.execute(httpGet);
            HttpEntity resEntity = httpResponse.getEntity();
            if (resEntity != null) {
                String resp = EntityUtils.toString(resEntity);
                Log.i(TAG, resp);

                GsonBuilder gsonb = new GsonBuilder();
                Gson gson = gsonb.create();
                
                RecommendedProgramsManager serverResponse = null;
                JsonParser parser = new JsonParser();
                if (parser.parse(resp).isJsonObject()) {
                    serverResponse = gson.fromJson(resp, RecommendedProgramsManager.class);
                }               
                return serverResponse;
            }
            return null;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            throw new RemindMeException(100, ApplicationConstants.REQUEST_PROCESSING_ERROR_MSG);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new RemindMeException(101, ApplicationConstants.USER_INPUTS_ERROR_MSG);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RemindMeException(102, ApplicationConstants.CONNECTION_LOST_ERROR_MSG);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RemindMeException(103, ApplicationConstants.CONNECTION_LOST_ERROR_MSG);
        } catch (JsonParseException e) {
            e.printStackTrace();
            throw new RemindMeException(106, ApplicationConstants.REQUEST_PROCESSING_ERROR_MSG);
        } catch (Exception e) {
            throw new RemindMeException(106, ApplicationConstants.REQUEST_PROCESSING_ERROR_MSG);
        }
    }
    
    private SmartObjectManager postData2(String url) {
        HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
        Log.d(TAG, "url: " + url);

        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Content-Type", "application/json");
            httpGet.setHeader("Accept", "JSON");

            HttpResponse httpResponse = httpclient.execute(httpGet);
            HttpEntity resEntity = httpResponse.getEntity();
            if (resEntity != null) {
                String resp = EntityUtils.toString(resEntity);
                Log.i(TAG, resp);

                GsonBuilder gsonb = new GsonBuilder();
                Gson gson = gsonb.create();
                
                SmartObjectManager serverResponse = null;
                JsonParser parser = new JsonParser();
                if (parser.parse(resp).isJsonObject()) {
                    serverResponse = gson.fromJson(resp, SmartObjectManager.class);
                }               
                return serverResponse;
            }
            return null;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            throw new RemindMeException(100, ApplicationConstants.REQUEST_PROCESSING_ERROR_MSG);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new RemindMeException(101, ApplicationConstants.USER_INPUTS_ERROR_MSG);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RemindMeException(102, ApplicationConstants.CONNECTION_LOST_ERROR_MSG);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RemindMeException(103, ApplicationConstants.CONNECTION_LOST_ERROR_MSG);
        } catch (JsonParseException e) {
            e.printStackTrace();
            throw new RemindMeException(106, ApplicationConstants.REQUEST_PROCESSING_ERROR_MSG);
        } catch (Exception e) {
            throw new RemindMeException(106, ApplicationConstants.REQUEST_PROCESSING_ERROR_MSG);
        }
    }
    
    private TaskManager postData3(String url) {
        HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
        Log.d(TAG, "url: " + url);

        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Content-Type", "application/json");
            httpGet.setHeader("Accept", "JSON");

            HttpResponse httpResponse = httpclient.execute(httpGet);
            HttpEntity resEntity = httpResponse.getEntity();
            if (resEntity != null) {
                String resp = EntityUtils.toString(resEntity);
                Log.i(TAG, resp);

                GsonBuilder gsonb = new GsonBuilder();
                Gson gson = gsonb.create();
                
                TaskManager serverResponse = null;
                JsonParser parser = new JsonParser();
                if (parser.parse(resp).isJsonObject()) {
                    serverResponse = gson.fromJson(resp, TaskManager.class);
                }               
                return serverResponse;
            }
            return null;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            throw new RemindMeException(100, ApplicationConstants.REQUEST_PROCESSING_ERROR_MSG);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new RemindMeException(101, ApplicationConstants.USER_INPUTS_ERROR_MSG);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RemindMeException(102, ApplicationConstants.CONNECTION_LOST_ERROR_MSG);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RemindMeException(103, ApplicationConstants.CONNECTION_LOST_ERROR_MSG);
        } catch (JsonParseException e) {
            e.printStackTrace();
            throw new RemindMeException(106, ApplicationConstants.REQUEST_PROCESSING_ERROR_MSG);
        } catch (Exception e) {
            throw new RemindMeException(106, ApplicationConstants.REQUEST_PROCESSING_ERROR_MSG);
        }
    }
    
    private ObjectPropertyManager postData4(String url) {
        HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
        Log.d(TAG, "url: " + url);

        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Content-Type", "application/json");
            httpGet.setHeader("Accept", "JSON");

            HttpResponse httpResponse = httpclient.execute(httpGet);
            HttpEntity resEntity = httpResponse.getEntity();
            if (resEntity != null) {
                String resp = EntityUtils.toString(resEntity);
                Log.i(TAG, resp);

                GsonBuilder gsonb = new GsonBuilder();
                Gson gson = gsonb.create();
                
                ObjectPropertyManager serverResponse = null;
                JsonParser parser = new JsonParser();
                if (parser.parse(resp).isJsonObject()) {
                    serverResponse = gson.fromJson(resp, ObjectPropertyManager.class);
                }               
                return serverResponse;
            }
            return null;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            throw new RemindMeException(100, ApplicationConstants.REQUEST_PROCESSING_ERROR_MSG);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new RemindMeException(101, ApplicationConstants.USER_INPUTS_ERROR_MSG);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RemindMeException(102, ApplicationConstants.CONNECTION_LOST_ERROR_MSG);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RemindMeException(103, ApplicationConstants.CONNECTION_LOST_ERROR_MSG);
        } catch (JsonParseException e) {
            e.printStackTrace();
            throw new RemindMeException(106, ApplicationConstants.REQUEST_PROCESSING_ERROR_MSG);
        } catch (Exception e) {
            throw new RemindMeException(106, ApplicationConstants.REQUEST_PROCESSING_ERROR_MSG);
        }
    }
    
    private LocationManager postData5(String url) {
        HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
        Log.d(TAG, "url: " + url);

        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Content-Type", "application/json");
            httpGet.setHeader("Accept", "JSON");

            HttpResponse httpResponse = httpclient.execute(httpGet);
            HttpEntity resEntity = httpResponse.getEntity();
            if (resEntity != null) {
                String resp = EntityUtils.toString(resEntity);
                Log.i(TAG, resp);

                GsonBuilder gsonb = new GsonBuilder();
                Gson gson = gsonb.create();
                
                LocationManager serverResponse = null;
                JsonParser parser = new JsonParser();
                if (parser.parse(resp).isJsonObject()) {
                    serverResponse = gson.fromJson(resp, LocationManager.class);
                }               
                return serverResponse;
            }
            return null;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            throw new RemindMeException(100, ApplicationConstants.REQUEST_PROCESSING_ERROR_MSG);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new RemindMeException(101, ApplicationConstants.USER_INPUTS_ERROR_MSG);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RemindMeException(102, ApplicationConstants.CONNECTION_LOST_ERROR_MSG);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RemindMeException(103, ApplicationConstants.CONNECTION_LOST_ERROR_MSG);
        } catch (JsonParseException e) {
            e.printStackTrace();
            throw new RemindMeException(106, ApplicationConstants.REQUEST_PROCESSING_ERROR_MSG);
        } catch (Exception e) {
            throw new RemindMeException(106, ApplicationConstants.REQUEST_PROCESSING_ERROR_MSG);
        }
    }
    
    private ActivityManager postData6(String url) {
        HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
        Log.d(TAG, "url: " + url);

        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Content-Type", "application/json");
            httpGet.setHeader("Accept", "JSON");

            HttpResponse httpResponse = httpclient.execute(httpGet);
            HttpEntity resEntity = httpResponse.getEntity();
            if (resEntity != null) {
                String resp = EntityUtils.toString(resEntity);
                Log.i(TAG, resp);

                GsonBuilder gsonb = new GsonBuilder();
                Gson gson = gsonb.create();
                
                ActivityManager serverResponse = null;
                JsonParser parser = new JsonParser();
                if (parser.parse(resp).isJsonObject()) {
                    serverResponse = gson.fromJson(resp, ActivityManager.class);
                }               
                return serverResponse;
            }
            return null;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            throw new RemindMeException(100, ApplicationConstants.REQUEST_PROCESSING_ERROR_MSG);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new RemindMeException(101, ApplicationConstants.USER_INPUTS_ERROR_MSG);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RemindMeException(102, ApplicationConstants.CONNECTION_LOST_ERROR_MSG);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RemindMeException(103, ApplicationConstants.CONNECTION_LOST_ERROR_MSG);
        } catch (JsonParseException e) {
            e.printStackTrace();
            throw new RemindMeException(106, ApplicationConstants.REQUEST_PROCESSING_ERROR_MSG);
        } catch (Exception e) {
            throw new RemindMeException(106, ApplicationConstants.REQUEST_PROCESSING_ERROR_MSG);
        }
    }
    
    private UsersManager postData7(String url) {
        HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
        Log.d(TAG, "url: " + url);

        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Content-Type", "application/json");
            httpGet.setHeader("Accept", "JSON");

            HttpResponse httpResponse = httpclient.execute(httpGet);
            HttpEntity resEntity = httpResponse.getEntity();
            if (resEntity != null) {
                String resp = EntityUtils.toString(resEntity);
                Log.i(TAG, resp);

                GsonBuilder gsonb = new GsonBuilder();
                Gson gson = gsonb.create();
                
                UsersManager serverResponse = null;
                JsonParser parser = new JsonParser();
                if (parser.parse(resp).isJsonObject()) {
                    serverResponse = gson.fromJson(resp, UsersManager.class);
                }               
                return serverResponse;
            }
            return null;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            throw new RemindMeException(100, ApplicationConstants.REQUEST_PROCESSING_ERROR_MSG);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new RemindMeException(101, ApplicationConstants.USER_INPUTS_ERROR_MSG);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RemindMeException(102, ApplicationConstants.CONNECTION_LOST_ERROR_MSG);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RemindMeException(103, ApplicationConstants.CONNECTION_LOST_ERROR_MSG);
        } catch (JsonParseException e) {
            e.printStackTrace();
            throw new RemindMeException(106, ApplicationConstants.REQUEST_PROCESSING_ERROR_MSG);
        } catch (Exception e) {
            throw new RemindMeException(106, ApplicationConstants.REQUEST_PROCESSING_ERROR_MSG);
        }
    }
    
    private ChannelManager postData8(String url) {
        HttpClient httpclient = HttpClientFactory.getThreadSafeClient();
        Log.d(TAG, "url: " + url);

        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Content-Type", "application/json");
            httpGet.setHeader("Accept", "JSON");

            HttpResponse httpResponse = httpclient.execute(httpGet);
            HttpEntity resEntity = httpResponse.getEntity();
            if (resEntity != null) {
                String resp = EntityUtils.toString(resEntity);
                Log.i(TAG, resp);

                GsonBuilder gsonb = new GsonBuilder();
                Gson gson = gsonb.create();
                
                ChannelManager serverResponse = null;
                JsonParser parser = new JsonParser();
                if (parser.parse(resp).isJsonObject()) {
                    serverResponse = gson.fromJson(resp, ChannelManager.class);
                }               
                return serverResponse;
            }
            return null;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            throw new RemindMeException(100, ApplicationConstants.REQUEST_PROCESSING_ERROR_MSG);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new RemindMeException(101, ApplicationConstants.USER_INPUTS_ERROR_MSG);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RemindMeException(102, ApplicationConstants.CONNECTION_LOST_ERROR_MSG);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RemindMeException(103, ApplicationConstants.CONNECTION_LOST_ERROR_MSG);
        } catch (JsonParseException e) {
            e.printStackTrace();
            throw new RemindMeException(106, ApplicationConstants.REQUEST_PROCESSING_ERROR_MSG);
        } catch (Exception e) {
            throw new RemindMeException(106, ApplicationConstants.REQUEST_PROCESSING_ERROR_MSG);
        }
    }

}
