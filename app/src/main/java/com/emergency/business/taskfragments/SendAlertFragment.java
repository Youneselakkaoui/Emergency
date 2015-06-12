package com.emergency.business.taskfragments;

/**
 * Created by elmehdiharabida on 06/06/15.
 */

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;

import com.emergency.business.TaskCallbacks;


/**
 * This Fragment manages a single background task and retains
 * itself across configuration changes.
 */
public class SendAlertFragment<PARAM, PROGRESS, RESULT> extends Fragment {


	private TaskCallbacks<PARAM, PROGRESS, RESULT> mCallbacks;

	public TaskCallbacks<PARAM, PROGRESS, RESULT> getmCallbacks () {
		return mCallbacks;
	}



	public void setmCallbacks (TaskCallbacks<PARAM, PROGRESS, RESULT> mCallbacks) {
		this.mCallbacks = mCallbacks;
	}

	private DummyTask mTask;

	/**
	 * Hold a reference to the parent Activity so we can report the
	 * task's current progress and results. The Android framework
	 * will pass us a reference to the newly created Activity after
	 * each configuration change.
	 */
	@Override
	public void onAttach (Activity activity) {
		super.onAttach(activity);
		//mCallbacks = (TaskCallbacks) activity;
	}

	/**
	 * This method will only be called once when the retained
	 * Fragment is first created.
	 */
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Retain this fragment across configuration changes.
		setRetainInstance(true);


	}

	/**
	 * Set the callback to null so we don't accidentally leak the
	 * Activity instance.
	 */
	@Override
	public void onDetach () {
		super.onDetach();
		mCallbacks = null;
	}


	public void runTask (PARAM... params) {
		// Create and execute the background task.
		mTask = new DummyTask();
		mTask.execute(params);
	}



	/**
	 * A dummy task that performs some (dumb) background work and
	 * proxies progress updates and results back to the Activity.
	 * <p/>
	 * Note that we need to check if the callbacks are null in each
	 * method in case they are invoked after the Activity's and
	 * Fragment's onDestroy() method have been called.
	 */
	private class DummyTask extends AsyncTask<PARAM, PROGRESS, RESULT> {


		@Override
		protected void onPreExecute () {

			if (mCallbacks != null) {
				mCallbacks.onPreExecute();
			}
		}

		/**
		 * Note that we do NOT call the callback object's methods
		 * directly from the background thread, as this could result
		 * in a race condition.
		 */
		@Override
		protected RESULT doInBackground (PARAM... params) {

			if (mCallbacks != null) {
				return mCallbacks.doInBackground(params);
			}
			return null;
		}

		@Override
		protected void onProgressUpdate (PROGRESS... percent) {
			if (mCallbacks != null) {
				mCallbacks.onProgressUpdate(percent);
			}
		}

		@Override
		protected void onCancelled () {

			if (mCallbacks != null) {
				mCallbacks.onCancelled();
			}
		}

		@Override
		protected void onPostExecute (RESULT result) {

			if (mCallbacks != null) {
				mCallbacks.onPostExecute(result);
			}
		}
	}
}
