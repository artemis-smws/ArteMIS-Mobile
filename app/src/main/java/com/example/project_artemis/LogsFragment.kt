package com.example.project_artemis

import java.util.*
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
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

    private val wasteLatest = BuildConfig.waste_latest

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentLogsBinding.inflate(inflater, container, false)

        val client2 = OkHttpClient()
        val logRequest = Request.Builder()
            .url(wasteLatest)
            .build()

        client2.newCall(logRequest).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                requireActivity().runOnUiThread {
                    showErrorMessage("Please check your Internet Connection")
                }
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call, response: Response) {
                val responseString = response.body?.string()
                val jsonArray = JSONArray(responseString)
                val ceafaObject = jsonArray.getJSONObject(0).getJSONObject("CEAFA")
                val ceafaresValue = ceafaObject.getJSONObject("weight").getInt("residual")
                val ceafainfecValue = ceafaObject.getJSONObject("weight").getInt("infectious")
                val ceafarecValue = if (ceafaObject.getJSONObject("weight").opt("recyclable") is JSONObject) {
                    ceafaObject.getJSONObject("weight").getJSONObject("recyclable").optInt("total")
                } else {
                    ceafaObject.getJSONObject("weight").getInt("recyclable")
                }
                val ceafabiodegradableValue = ceafaObject.getJSONObject("weight").getInt("biodegradable")
                val citObject = jsonArray.getJSONObject(0).getJSONObject("CIT")
                val citresValue = citObject.getJSONObject("weight").getInt("residual")
                val citinfecValue = citObject.getJSONObject("weight").getInt("infectious")
                val citrecValue = if (citObject.getJSONObject("weight").opt("recyclable") is JSONObject) {
                    citObject.getJSONObject("weight").getJSONObject("recyclable").optInt("total")
                } else {
                    citObject.getJSONObject("weight").getInt("recyclable")
                }                
                val citbiodegradableValue = citObject.getJSONObject("weight").getInt("biodegradable")
                val cicsObject = jsonArray.getJSONObject(0).getJSONObject("CICS")
                val cicsresValue = cicsObject.getJSONObject("weight").getInt("residual")
                val cicsinfecValue = cicsObject.getJSONObject("weight").getInt("infectious")
                val cicsrecValue = if (cicsObject.getJSONObject("weight").opt("recyclable") is JSONObject) {
                    cicsObject.getJSONObject("weight").getJSONObject("recyclable").optInt("total")
                } else {
                    cicsObject.getJSONObject("weight").getInt("recyclable")
                }                
                val cicsbiodegradableValue = cicsObject.getJSONObject("weight").getInt("biodegradable")
                val rgrObject = jsonArray.getJSONObject(0).getJSONObject("RGR")
                val rgrresValue = rgrObject.getJSONObject("weight").getInt("residual")
                val rgrinfecValue = rgrObject.getJSONObject("weight").getInt("infectious")
                val rgrrecValue = if (rgrObject.getJSONObject("weight").opt("recyclable") is JSONObject) {
                    rgrObject.getJSONObject("weight").getJSONObject("recyclable").optInt("total")
                } else {
                    rgrObject.getJSONObject("weight").getInt("recyclable")
                }                
                val rgrbiodegradableValue = rgrObject.getJSONObject("weight").getInt("biodegradable")
                val gymObject = jsonArray.getJSONObject(0).getJSONObject("Gymnasium")
                val gymresValue = gymObject.getJSONObject("weight").getInt("residual")
                val gyminfecValue = gymObject.getJSONObject("weight").getInt("infectious")
                val gymrecValue = if (gymObject.getJSONObject("weight").opt("recyclable") is JSONObject) {
                    gymObject.getJSONObject("weight").getJSONObject("recyclable").optInt("total")
                } else {
                    gymObject.getJSONObject("weight").getInt("recyclable")
                }
                val gymbiodegradableValue = gymObject.getJSONObject("weight").getInt("biodegradable")
                val steerObject = jsonArray.getJSONObject(0).getJSONObject("STEER_Hub")
                val steerresValue = steerObject.getJSONObject("weight").getInt("residual")
                val steerinfecValue = steerObject.getJSONObject("weight").getInt("infectious")
                val steerrecValue = if (steerObject.getJSONObject("weight").opt("recyclable") is JSONObject) {
                    steerObject.getJSONObject("weight").getJSONObject("recyclable").optInt("total")
                } else {
                    steerObject.getJSONObject("weight").getInt("recyclable")
                }                
                val steerbiodegradableValue = steerObject.getJSONObject("weight").getInt("biodegradable")
                val sscObject = jsonArray.getJSONObject(0).getJSONObject("SSC")
                val sscresValue = sscObject.getJSONObject("weight").getInt("residual")
                val sscinfecValue = sscObject.getJSONObject("weight").getInt("infectious")
                val sscrecValue = if (sscObject.getJSONObject("weight").opt("recyclable") is JSONObject) {
                    sscObject.getJSONObject("weight").getJSONObject("recyclable").optInt("total")
                } else {
                    sscObject.getJSONObject("weight").getInt("recyclable")
                }                
                val sscbiodegradableValue = sscObject.getJSONObject("weight").getInt("biodegradable")

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
                        if (ceafabiodegradableValue != 0) {
                            binding.nullbiodegradableceafa.visibility = View.GONE
                            binding.biodegradableceafa.visibility = View.VISIBLE
                        }
                        if (ceafainfecValue != 0) {
                            binding.nullinfecceafa.visibility = View.GONE
                            binding.infecceafa.visibility = View.VISIBLE
                        }
                        if (citresValue != 0) {
                            binding.nullrescit.visibility = View.GONE
                            binding.rescit.visibility = View.VISIBLE
                        }
                        if (citrecValue != 0) {
                            binding.nullreccit.visibility = View.GONE
                            binding.reccit.visibility = View.VISIBLE
                        }
                        if (citbiodegradableValue != 0) {
                            binding.nullbiodegradablecit.visibility = View.GONE
                            binding.biodegradablecit.visibility = View.VISIBLE
                        }
                        if (citinfecValue != 0) {
                            binding.nullinfeccit.visibility = View.GONE
                            binding.infeccit.visibility = View.VISIBLE
                        }
                        if (cicsresValue != 0) {
                            binding.nullrescics.visibility = View.GONE
                            binding.rescics.visibility = View.VISIBLE
                        }
                        if (cicsrecValue != 0) {
                            binding.nullreccics.visibility = View.GONE
                            binding.reccics.visibility = View.VISIBLE
                        }
                        if (cicsbiodegradableValue != 0) {
                            binding.nullbiodegradablecics.visibility = View.GONE
                            binding.biodegradablecics.visibility = View.VISIBLE
                        }
                        if (cicsinfecValue != 0) {
                            binding.nullinfeccics.visibility = View.GONE
                            binding.infeccics.visibility = View.VISIBLE
                        }
                        if (rgrresValue != 0) {
                            binding.nullresrgr.visibility = View.GONE
                            binding.resrgr.visibility = View.VISIBLE
                        }
                        if (rgrrecValue != 0) {
                            binding.nullrecrgr.visibility = View.GONE
                            binding.recrgr.visibility = View.VISIBLE
                        }
                        if (rgrbiodegradableValue != 0) {
                            binding.nullbiodegradablergr.visibility = View.GONE
                            binding.biodegradablergr.visibility = View.VISIBLE
                        }
                        if (rgrinfecValue != 0) {
                            binding.nullinfecrgr.visibility = View.GONE
                            binding.infecrgr.visibility = View.VISIBLE
                        }
                        if (gymresValue != 0) {
                            binding.nullresgym.visibility = View.GONE
                            binding.resgym.visibility = View.VISIBLE
                        }
                        if (gymrecValue != 0) {
                            binding.nullrecgym.visibility = View.GONE
                            binding.recgym.visibility = View.VISIBLE
                        }
                        if (gymbiodegradableValue != 0) {
                            binding.nullbiodegradablegym.visibility = View.GONE
                            binding.biodegradablegym.visibility = View.VISIBLE
                        }
                        if (gyminfecValue != 0) {
                            binding.nullinfecgym.visibility = View.GONE
                            binding.infecgym.visibility = View.VISIBLE
                        }
                        if (steerresValue != 0) {
                            binding.nullressteer.visibility = View.GONE
                            binding.ressteer.visibility = View.VISIBLE
                        }
                        if (steerrecValue != 0) {
                            binding.nullrecsteer.visibility = View.GONE
                            binding.recsteer.visibility = View.VISIBLE
                        }
                        if (steerbiodegradableValue != 0) {
                            binding.nullbiodegradablesteer.visibility = View.GONE
                            binding.biodegradablesteer.visibility = View.VISIBLE
                        }
                        if (steerinfecValue != 0) {
                            binding.nullinfecsteer.visibility = View.GONE
                            binding.infecsteer.visibility = View.VISIBLE
                        }
                        if (sscresValue != 0) {
                            binding.nullresssc.visibility = View.GONE
                            binding.resssc.visibility = View.VISIBLE
                        }
                        if (sscrecValue != 0) {
                            binding.nullrecssc.visibility = View.GONE
                            binding.recssc.visibility = View.VISIBLE
                        }
                        if (sscbiodegradableValue != 0) {
                            binding.nullbiodegradablessc.visibility = View.GONE
                            binding.biodegradablessc.visibility = View.VISIBLE
                        }
                        if (sscinfecValue != 0) {
                            binding.nullinfecssc.visibility = View.GONE
                            binding.infecssc.visibility = View.VISIBLE
                        }
                    }
                }

            }

            private fun showErrorMessage(message: String) {
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Error")
                builder.setMessage(message)
                builder.setPositiveButton("OK") { dialog, which ->
                    dialog.dismiss()
                }
                val dialog = builder.create()
                dialog.show()
            }
        })

        return binding.root
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}