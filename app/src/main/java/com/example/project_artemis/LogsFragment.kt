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
import com.example.project_artemis.databinding.FragmentLogsBinding
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

                        if (isAdded){
                            requireActivity().runOnUiThread {
                                if (ceafaresValue != 0) {
                                    binding.resceafa.visibility = View.VISIBLE
                                } else {
                                    binding.nullresceafa.visibility = View.VISIBLE
                                }
                                if (ceafarecValue != 0) {
                                    binding.recceafa.visibility = View.VISIBLE
                                } else {
                                    binding.nullrecceafa.visibility = View.VISIBLE
                                }
                                if (ceafafoodValue != 0) {
                                    binding.foodceafa.visibility = View.VISIBLE
                                } else {
                                    binding.nullfoodceafa.visibility = View.VISIBLE
                                }
                                if (citresValue != 0) {
                                    binding.rescit.visibility = View.VISIBLE
                                } else {
                                    binding.nullrescit.visibility = View.VISIBLE
                                }
                                if (citrecValue != 0) {
                                    binding.reccit.visibility = View.VISIBLE
                                } else {
                                    binding.nullreccit.visibility = View.VISIBLE
                                }
                                if (citfoodValue != 0) {
                                    binding.foodcit.visibility = View.VISIBLE
                                } else {
                                    binding.nullfoodcit.visibility = View.VISIBLE
                                }
                                if (cicsresValue != 0) {
                                    binding.rescics.visibility = View.VISIBLE
                                } else {
                                    binding.nullrescics.visibility = View.VISIBLE
                                }
                                if (cicsrecValue != 0) {
                                    binding.reccics.visibility = View.VISIBLE
                                } else {
                                    binding.nullreccics.visibility = View.VISIBLE
                                }
                                if (cicsfoodValue != 0) {
                                    binding.foodcics.visibility = View.VISIBLE
                                } else {
                                    binding.nullfoodcics.visibility = View.VISIBLE
                                }
                                if (rgrresValue != 0) {
                                    binding.resrgr.visibility = View.VISIBLE
                                } else {
                                    binding.nullresrgr.visibility = View.VISIBLE
                                }
                                if (rgrrecValue != 0) {
                                    binding.recrgr.visibility = View.VISIBLE
                                } else {
                                    binding.nullrecrgr.visibility = View.VISIBLE
                                }
                                if (rgrfoodValue != 0) {
                                    binding.foodrgr.visibility = View.VISIBLE
                                } else {
                                    binding.nullfoodrgr.visibility = View.VISIBLE
                                }
                                if (gymresValue != 0) {
                                    binding.resgym.visibility = View.VISIBLE
                                } else {
                                    binding.nullresgym.visibility = View.VISIBLE
                                }
                                if (gymrecValue != 0) {
                                    binding.recgym.visibility = View.VISIBLE
                                } else {
                                    binding.nullrecgym.visibility = View.VISIBLE
                                }
                                if (gymfoodValue != 0) {
                                    binding.foodgym.visibility = View.VISIBLE
                                } else {
                                    binding.nullfoodgym.visibility = View.VISIBLE
                                }
                                if (steerresValue != 0) {
                                    binding.ressteer.visibility = View.VISIBLE
                                } else {
                                    binding.nullressteer.visibility = View.VISIBLE
                                }
                                if (steerrecValue != 0) {
                                    binding.recsteer.visibility = View.VISIBLE
                                } else {
                                    binding.nullrecsteer.visibility = View.VISIBLE
                                }
                                if (steerfoodValue != 0) {
                                    binding.foodsteer.visibility = View.VISIBLE
                                } else {
                                    binding.nullfoodsteer.visibility = View.VISIBLE
                                }
                                if (sscresValue != 0) {
                                    binding.resssc.visibility = View.VISIBLE
                                } else {
                                    binding.nullresssc.visibility = View.VISIBLE
                                }
                                if (sscrecValue != 0) {
                                    binding.recssc.visibility = View.VISIBLE
                                } else {
                                    binding.nullrecssc.visibility = View.VISIBLE
                                }
                                if (sscfoodValue != 0) {
                                    binding.foodssc.visibility = View.VISIBLE
                                } else {
                                    binding.nullfoodssc.visibility = View.VISIBLE
                                }
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