package com.torres.valmir.kotlin_mvp_dagger2.adapter

import android.content.Context
import android.graphics.Bitmap
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.torres.valmir.kotlin_mvp_dagger2.R
import com.torres.valmir.kotlin_mvp_dagger2.model.Cast
import com.torres.valmir.kotlin_mvp_dagger2.utils.Constants
import de.hdodenhof.circleimageview.CircleImageView

class CastingAdapter (private val context: Context,
                      private val itemListener: ItemListener<Cast>) : RecyclerView.Adapter<CastingAdapter.CastingViewHolder>(){

    private val casting = ArrayList<Cast>(0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastingViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val noteView = inflater.inflate(R.layout.item_casting, parent, false)
        return CastingViewHolder(noteView)
    }

    override fun getItemCount(): Int = casting.size

    override fun onBindViewHolder(holder: CastingViewHolder, position: Int) {
        val cast = casting[position]

        holder.fillData(cast, context)
        holder.itemView.setOnClickListener {
            itemListener.onClick(cast)
        }
    }

    fun replaceData(data: List<Cast>){
        this.casting.clear()
        this.casting.addAll(data)
        notifyDataSetChanged()
    }

    inner class CastingViewHolder(noteView: View): RecyclerView.ViewHolder(noteView) {
        private val avatar = noteView.findViewById<CircleImageView>(R.id.avatar_casting)
        private val character = noteView.findViewById<TextView>(R.id.character_name_casting)
        private val name = noteView.findViewById<TextView>(R.id.actor_name_casting)

        fun fillData(cast: Cast, context: Context){
            var bitmap: Bitmap
            avatar.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_account_circle_default))
            character.text = ""
            name.text = ""
            cast.profilePath?.let { poster->
                Glide.with(avatar.context)
                        .asBitmap()
                        .load(Constants.BASE_URL_IMAGE_W185+poster)
                        .listener(object: RequestListener<Bitmap> {
                            override fun onLoadFailed(e: GlideException?, model: Any?, target: com.bumptech.glide.request.target.Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                                return false
                            }

                            override fun onResourceReady(resource: Bitmap?, model: Any?, target: com.bumptech.glide.request.target.Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                                resource?.let {
                                    bitmap = it
                                    avatar.setImageBitmap(bitmap)
                                }
                                return true
                            }
                        })
                        .submit()
            }

            character.text = cast.character
            name.text = cast.name
        }
    }
}