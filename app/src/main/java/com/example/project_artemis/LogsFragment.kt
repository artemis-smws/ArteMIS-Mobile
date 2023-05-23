package com.example.project_artemis

import android.widget.ArrayAdapter
import android.widget.Button
import java.util.*
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException



class LogsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentLogsBinding.inflate(inflater, container, false)

                val client2 = OkHttpClient()
                val url = "https://us-central1-artemis-b18ae.cloudfunctions.net/server/waste/latest"
                val request2 = Request.Builder()
                    .url(url)
                    .build()

                client2.newCall(request2).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        // Handle network errors here
                    }
            
                    @SuppressLint("SetTextI18n")
                    override fun onResponse(call: Call, response: Response) {
                        val responseString = response.body?.string()
                        val jsonArray = JSONArray(responseString)
                        val ceafaObject = jsonArray.getJSONObject(0).getJSONObject("CEAFA")
                        val ceafaresValue = ceafaObject.getJSONObject("weight").getInt("residual")
                        val ceafarecValue = ceafaObject.getJSONObject("weight").getInt("recyclable")
                        val ceafafoodValue = ceafaObject.getJSONObject("weight").getInt("food_waste")
                        val citObject = jsonArray.getJSONObject(0).getJSONObject("CIT")
                        val citresValue = citObject.getJSONObject("weight").getInt("residual")
                        val citrecValue = citObject.getJSONObject("weight").getInt("recyclable")
                        val citfoodValue = citObject.getJSONObject("weight").getInt("food_waste")
                        val cicsObject = jsonArray.getJSONObject(0).getJSONObject("CICS")
                        val cicsresValue = cicsObject.getJSONObject("weight").getInt("residual")
                        val cicsrecValue = cicsObject.getJSONObject("weight").getInt("recyclable")
                        val cicsfoodValue = cicsObject.getJSONObject("weight").getInt("food_waste")
                        val rgrObject = jsonArray.getJSONObject(0).getJSONObject("RGR")
                        val rgrresValue = rgrObject.getJSONObject("weight").getInt("residual")
                        val rgrrecValue = rgrObject.getJSONObject("weight").getInt("recyclable")
                        val rgrfoodValue = rgrObject.getJSONObject("weight").getInt("food_waste")
                        val gymObject = jsonArray.getJSONObject(0).getJSONObject("Gymnasium")
                        val gymresValue = gymObject.getJSONObject("weight").getInt("residual")
                        val gymrecValue = gymObject.getJSONObject("weight").getInt("recyclable")
                        val gymfoodValue = gymObject.getJSONObject("weight").getInt("food_waste")
                        val steerObject = jsonArray.getJSONObject(0).getJSONObject("STEER_Hub")
                        val steerresValue = steerObject.getJSONObject("weight").getInt("residual")
                        val steerrecValue = steerObject.getJSONObject("weight").getInt("recyclable")
                        val steerfoodValue = steerObject.getJSONObject("weight").getInt("food_waste")
                        val sscObject = jsonArray.getJSONObject(0).getJSONObject("SSC")
                        val sscresValue = sscObject.getJSONObject("weight").getInt("residual")
                        val sscrecValue = sscObject.getJSONObject("weight").getInt("recyclable")
                        val sscfoodValue = sscObject.getJSONObject("weight").getInt("food_waste")
                        runOnUiThread {
                            if (ceafaresValue != 0) {
                                button.visibility = View.VISIBLE
                            } else {
                                button.visibility = View.GONE
                            }
                            if (ceafarecValue != 0) {
                                button.visibility = View.VISIBLE
                            } else {
                                button.visibility = View.GONE
                            }
                            if (ceafafoodValue != 0) {
                                button.visibility = View.VISIBLE
                            } else {
                                button.visibility = View.GONE
                            }
                            if (citresValue != 0) {
                                button.visibility = View.VISIBLE
                            } else {
                                button.visibility = View.GONE
                            }
                            if (citrecValue != 0) {
                                button.visibility = View.VISIBLE
                            } else {
                                button.visibility = View.GONE
                            }
                            if (citfoodValue != 0) {
                                button.visibility = View.VISIBLE
                            } else {
                                button.visibility = View.GONE
                            }
                            if (cicsresValue != 0) {
                                button.visibility = View.VISIBLE
                            } else {
                                button.visibility = View.GONE
                            }
                            if (cicsrecValue != 0) {
                                button.visibility = View.VISIBLE
                            } else {
                                button.visibility = View.GONE
                            }
                            if (cicsfoodValue != 0) {
                                button.visibility = View.VISIBLE
                            } else {
                                button.visibility = View.GONE
                            }
                            if (rgrresValue != 0) {
                                button.visibility = View.VISIBLE
                            } else {
                                button.visibility = View.GONE
                            }
                            if (rgrrecValue != 0) {
                                button.visibility = View.VISIBLE
                            } else {
                                button.visibility = View.GONE
                            }
                            if (rgrfoodValue != 0) {
                                button.visibility = View.VISIBLE
                            } else {
                                button.visibility = View.GONE
                            }
                            if (gymresValue != 0) {
                                button.visibility = View.VISIBLE
                            } else {
                                button.visibility = View.GONE
                            }
                            if (gymrecValue != 0) {
                                button.visibility = View.VISIBLE
                            } else {
                                button.visibility = View.GONE
                            }
                            if (gymfoodValue != 0) {
                                button.visibility = View.VISIBLE
                            } else {
                                button.visibility = View.GONE
                            }
                            if (steerresValue != 0) {
                                button.visibility = View.VISIBLE
                            } else {
                                button.visibility = View.GONE
                            }
                            if (steerrecValue != 0) {
                                button.visibility = View.VISIBLE
                            } else {
                                button.visibility = View.GONE
                            }
                            if (steerfoodValue != 0) {
                                button.visibility = View.VISIBLE
                            } else {
                                button.visibility = View.GONE
                            }
                            if (sscresValue != 0) {
                                button.visibility = View.VISIBLE
                            } else {
                                button.visibility = View.GONE
                            }
                            if (sscrecValue != 0) {
                                button.visibility = View.VISIBLE
                            } else {
                                button.visibility = View.GONE
                            }
                            if (sscfoodValue != 0) {
                                button.visibility = View.VISIBLE
                            } else {
                                button.visibility = View.GONE
                            }
                        }
                
                    }
                })

        return binding.root
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}