package com.example.hazemnabil.islamictodo2.calenderDay;

import android.content.Context;

import com.example.hazemnabil.islamictodo2.objData.Task;

/**
 * Created by hazem.nabil on 4/20/2017.
 */
//  public interface OnListFragmentInteractionListener {
public interface FragmentListener {
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

        // TODO: Update argument type and name
        void onFragmentListClicked(Task item);
        Context getContext();


}
