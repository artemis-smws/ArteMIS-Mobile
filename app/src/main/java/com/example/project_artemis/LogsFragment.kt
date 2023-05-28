package com.example.project_artemis

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
import org.json.JSONException
import org.json.JSONObject
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
                        val gymObject: JSONObject? = try {
                            jsonArray.getJSONObject(0).getJSONObject("Gymnasium")
                        } catch (e: JSONException) {
                            null
                        }
                        if (gymObject != null) {
                            val gymresValue = gymObject.getJSONObject("weight").getInt("residual")
                            val gymrecValue = gymObject.getJSONObject("weight").getInt("recyclable")
                            val gymfoodValue = gymObject.getJSONObject("weight").getInt("food_waste")

                            if (isAdded) {
                                requireActivity().runOnUiThread {
                                    if (gymresValue != 0) {
                                        binding.nullresgym.visibility = View.GONE
                                        binding.resgym.visibility = View.VISIBLE
                                    }
                                    if (gymrecValue != 0) {
                                        binding.nullrecgym.visibility = View.GONE
                                        binding.recgym.visibility = View.VISIBLE
                                    }
                                    if (gymfoodValue != 0) {
                                        binding.nullfoodgym.visibility = View.GONE
                                        binding.foodgym.visibility = View.VISIBLE
                                    }
                                }
                            }
                        }

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
                                    binding.nullresceafa.visibility = View.GONE
                                    binding.resceafa.visibility = View.VISIBLE
                                }
                                if (ceafarecValue != 0) {
                                    binding.nullrecceafa.visibility = View.GONE
                                    binding.recceafa.visibility = View.VISIBLE
                                }
                                if (ceafafoodValue != 0) {
                                    binding.nullfoodceafa.visibility = View.GONE
                                    binding.foodceafa.visibility = View.VISIBLE
                                }
                                if (citresValue != 0) {
                                    binding.nullrescit.visibility = View.GONE
                                    binding.rescit.visibility = View.VISIBLE
                                }
                                if (citrecValue != 0) {
                                    binding.nullreccit.visibility = View.GONE
                                    binding.reccit.visibility = View.VISIBLE
                                }
                                if (citfoodValue != 0) {
                                    binding.nullfoodcit.visibility = View.GONE
                                    binding.foodcit.visibility = View.VISIBLE
                                }
                                if (cicsresValue != 0) {
                                    binding.nullrescics.visibility = View.GONE
                                    binding.rescics.visibility = View.VISIBLE
                                }
                                if (cicsrecValue != 0) {
                                    binding.nullreccics.visibility = View.GONE
                                    binding.reccics.visibility = View.VISIBLE
                                }
                                if (cicsfoodValue != 0) {
                                    binding.nullfoodcics.visibility = View.GONE
                                    binding.foodcics.visibility = View.VISIBLE
                                }
                                if (rgrresValue != 0) {
                                    binding.nullresrgr.visibility = View.GONE
                                    binding.resrgr.visibility = View.VISIBLE
                                }
                                if (rgrrecValue != 0) {
                                    binding.nullrecrgr.visibility = View.GONE
                                    binding.recrgr.visibility = View.VISIBLE
                                }
                                if (rgrfoodValue != 0) {
                                    binding.nullfoodrgr.visibility = View.GONE
                                    binding.foodrgr.visibility = View.VISIBLE
                                }
                                if (steerresValue != 0) {
                                    binding.nullressteer.visibility = View.GONE
                                    binding.ressteer.visibility = View.VISIBLE
                                }
                                if (steerrecValue != 0) {
                                    binding.nullrecsteer.visibility = View.GONE
                                    binding.recsteer.visibility = View.VISIBLE
                                }
                                if (steerfoodValue != 0) {
                                    binding.nullfoodsteer.visibility = View.GONE
                                    binding.foodsteer.visibility = View.VISIBLE
                                }
                                if (sscresValue != 0) {
                                    binding.nullresssc.visibility = View.GONE
                                    binding.resssc.visibility = View.VISIBLE
                                }
                                if (sscrecValue != 0) {
                                    binding.nullrecssc.visibility = View.GONE
                                    binding.recssc.visibility = View.VISIBLE
                                }
                                if (sscfoodValue != 0) {
                                    binding.nullfoodssc.visibility = View.GONE
                                    binding.foodssc.visibility = View.VISIBLE
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