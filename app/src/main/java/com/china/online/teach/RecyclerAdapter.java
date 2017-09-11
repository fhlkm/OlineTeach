package com.china.online.teach;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hanlu.feng on 9/5/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private Context context;
    private List<Item> itemList;
    private Resources resources;
    private LayoutInflater inflater;

    public RecyclerAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
        this.resources = context.getResources();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Item item = itemList.get(position);
        holder.title.setText(item.getTitle());
        holder.image.setImageResource(item.getResId());
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDetailActivity();
            }
        });
        Bitmap bitmap = BitmapFactory.decodeResource(resources, item.getResId());
        //异步获得bitmap图片颜色值
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                // Palette提取颜色
                Palette.Swatch vibrant = palette.getVibrantSwatch();//有活力
                Palette.Swatch c = palette.getDarkVibrantSwatch();//有活力 暗色
                Palette.Swatch d = palette.getLightVibrantSwatch();//有活力 亮色
                Palette.Swatch f = palette.getMutedSwatch();//柔和
                Palette.Swatch a = palette.getDarkMutedSwatch();//柔和 暗色
                Palette.Swatch b = palette.getLightMutedSwatch();//柔和 亮色

                if (vibrant != null) {
                    int color1 = vibrant.getBodyTextColor();//内容颜色
                    int color2 = vibrant.getTitleTextColor();//标题颜色
                    int color3 = vibrant.getRgb();//rgb颜色
                    if(position<4) {
                        holder.title.setBackgroundColor(f.getRgb());
                        holder.title.setTextColor(f.getTitleTextColor());
                    }else{
                        holder.title.setBackgroundColor(vibrant.getRgb());
                        holder.title.setTextColor(vibrant.getTitleTextColor());
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return null == itemList ? 0 : itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_title);
            image = (ImageView) itemView.findViewById(R.id.item_image);
        }
    }

    private void startDetailActivity(){
        Intent mIntent = new Intent(context,DetailActivity.class);
        context.startActivity(mIntent);
    }
}