package com.emergency.business;

/**
 * Created by elmehdiharabida on 06/06/15.
 */
/**
 * Callback interface through which the fragment will report the
 * task's progress and results back to the Activity.
 */
public interface TaskCallbacks<PARAM, PROGRESS, RESULT> {
	void onPreExecute();
	RESULT doInBackground(PARAM... ignore);
	void onProgressUpdate(PROGRESS... percent);
	void onCancelled();
	void onPostExecute(RESULT result) ;
}