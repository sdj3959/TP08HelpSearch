package com.sdj2022.tp08helpsearch.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sdj2022.tp08helpsearch.R
import com.sdj2022.tp08helpsearch.activities.PlaceUrlActivity
import com.sdj2022.tp08helpsearch.databinding.RecyclerItemListFragmentBinding
import com.sdj2022.tp08helpsearch.model.Place

class PlaceListRecyclerAdapter(val context:Context, var documents:MutableList<Place>) : Adapter<PlaceListRecyclerAdapter.VH>() {
    inner class VH(itemView:View) : ViewHolder(itemView){
        val binding:RecyclerItemListFragmentBinding by lazy { RecyclerItemListFragmentBinding.bind(itemView) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView:View = LayoutInflater.from(context).inflate(R.layout.recycler_item_list_fragment, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val place:Place = documents[position]

        holder.binding.tvPlaceName.text = place.place_name
        holder.binding.tvAddress.text = if(place.road_address_name=="") place.address_name else place.road_address_name
        holder.binding.tvDistance.text = "${place.distance}m"

        // 아이템뷰를 클릭했을때 장소에 대한 세부정보 웹사이트를 보여주는 화면으로 이동.
        holder.binding.root.setOnClickListener {
            val intent:Intent = Intent(context, PlaceUrlActivity::class.java)
            intent.putExtra("place_url", place.place_url)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = documents.size
}