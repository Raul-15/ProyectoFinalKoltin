package com.example.android_proyecto.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.android_proyecto.R
import com.example.android_proyecto.activities.CartListActivity
import com.example.android_proyecto.firestore.FirestoreClass
import com.example.android_proyecto.models.Cart
import com.example.android_proyecto.utils.Constants
import com.example.android_proyecto.utils.GlideLoader
import kotlinx.android.synthetic.main.item_cart_layout.view.*

open class CartItemsListAdapter(
    private val context: Context,
    private var list: ArrayList<Cart>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_cart_layout,
                parent,
                false
            )
        )
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {

            GlideLoader(context).loadProductPicture(model.image, holder.itemView.iv_cart_item_image)

            holder.itemView.tv_cart_item_title.text = model.title
            holder.itemView.tv_cart_item_price.text = "$${model.price}"
            holder.itemView.tv_cart_quantity.text = model.cart_quantity


            if (model.cart_quantity == "0") {
                holder.itemView.ib_remove_cart_item.visibility = View.GONE
                holder.itemView.ib_add_cart_item.visibility = View.GONE

                holder.itemView.tv_cart_quantity.text =
                    context.resources.getString(R.string.lbl_out_of_stock)

                holder.itemView.tv_cart_quantity.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorSnackBarError
                    )
                )
            } else {
                holder.itemView.ib_remove_cart_item.visibility = View.VISIBLE
                holder.itemView.ib_add_cart_item.visibility = View.VISIBLE

                holder.itemView.tv_cart_quantity.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorSecondaryText
                    )
                )
            }


            holder.itemView.ib_remove_cart_item.setOnClickListener {


                if (model.cart_quantity == "1") {
                    FirestoreClass().removeItemFromCart(context, model.id)
                } else {

                    val cartQuantity: Int = model.cart_quantity.toInt()

                    val itemHashMap = HashMap<String, Any>()

                    itemHashMap[Constants.CART_QUANTITY] = (cartQuantity - 1).toString()

                    // Show the progress dialog.

                    if (context is CartListActivity) {
                        context.showProgressDialog(context.resources.getString(R.string.please_wait))
                    }

                    FirestoreClass().updateMyCart(context, model.id, itemHashMap)
                }


        }
            holder.itemView.ib_add_cart_item.setOnClickListener {
                val cartQuantity: Int = model.cart_quantity.toInt()

                if (cartQuantity < model.stock_quantity.toInt()) {

                    val itemHashMap = HashMap<String, Any>()

                    itemHashMap[Constants.CART_QUANTITY] = (cartQuantity + 1).toString()

                    // Show the progress dialog.
                    if (context is CartListActivity) {
                        context.showProgressDialog(context.resources.getString(R.string.please_wait))
                    }

                    FirestoreClass().updateMyCart(context, model.id, itemHashMap)
                } else {
                    if (context is CartListActivity) {
                        context.showErrorSnackBar(
                            context.resources.getString(
                                R.string.msg_for_available_stock,
                                model.stock_quantity
                            ),
                            true
                        )
                    }
                }
                // END
            }
            // END
        }
        // END
        holder.itemView.ib_delete_cart_item.setOnClickListener {


            when (context) {
                is CartListActivity -> {
                    context.showProgressDialog(context.resources.getString(R.string.please_wait))
                }
            }

            FirestoreClass().removeItemFromCart(context, model.id)
            // END
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}