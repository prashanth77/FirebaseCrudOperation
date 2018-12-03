package sample.firebaseexpense.com;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>  {

    private ArrayList<DataModel> dataSet;
    Context context;
   public static DataModel dataModel;
  public CustomAdapter(ArrayList<DataModel> data, Context mContext) {
        this.dataSet = data;
        this.context=mContext;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_view, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view,context,dataSet);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        TextView textViewName = holder.textViewName;
        TextView textViewCategory = holder.textViewCategory;
       TextView textViewPrice = holder.textViewPrice;
        TextView textViewDate = holder.textViewDate;
     dataModel=dataSet.get(listPosition);
        textViewName.setText(dataModel.getName());
        textViewCategory.setText(dataModel.getCategory());
        textViewPrice.setText(""+dataModel.getPrice());
        textViewDate.setText(dataModel.getDate());
       Log.e("agetNamae", dataSet.get(listPosition).getName());

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewName;
        TextView textViewDate;
        TextView textViewCategory;
        TextView textViewPrice;
        Context mContx1;
        ArrayList<DataModel> dataModellist;

        public MyViewHolder(View itemView,Context mContx1, ArrayList<DataModel> dataModelArrayList) {
            super(itemView);
            this.mContx1=mContx1;
            this.dataModellist=dataModelArrayList;
            itemView.setOnClickListener(this);
            this.textViewDate = (TextView) itemView.findViewById(R.id.la_date);
            this.textViewName = (TextView) itemView.findViewById(R.id.la_name);
            this.textViewCategory = (TextView) itemView.findViewById(R.id.la_category);
            this.textViewPrice = (TextView) itemView.findViewById(R.id.la_price);


        }

        @Override
        public void onClick(View v) {
            DataModel dataModel=this.dataModellist.get(getAdapterPosition());

            Intent intent = new Intent(mContx1, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          intent.putExtra("isUpdate", true);
            intent.putExtra("_cate", dataModel.getCategory());
            intent.putExtra("_date", dataModel.getDate());
            intent.putExtra("_name", dataModel.getName());
            intent.putExtra("_deta", dataModel.getDetails());
            intent.putExtra("_pric", dataModel.getPrice());
            intent.putExtra("_paym", dataModel.getPayment());
            intent.putExtra("_id", dataModel.getId());

            this.mContx1.startActivity(intent);

        }
    }



}
