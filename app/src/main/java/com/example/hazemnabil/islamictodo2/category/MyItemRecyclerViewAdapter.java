package com.example.hazemnabil.islamictodo2.category;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hazemnabil.islamictodo2.R;
import com.example.hazemnabil.islamictodo2.dummy.DummyContent.DummyItem;
import com.example.hazemnabil.islamictodo2.objData.Categories;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link ItemFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>  {


    private final int[] theArray_id;
    private final String[] theArray_txt;
    private final String[] theArray_color;

    private Categories categories;
    private  SparseArray<Categories.Category> categoriesSArr;
    private final ItemFragment.OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(Categories categories, ItemFragment.OnListFragmentInteractionListener listener) {
        this.categories = categories;
        categoriesSArr = categories.getCategories();
        mListener = listener;









        theArray_id = new int[categoriesSArr.size()];
        theArray_txt = new String[categoriesSArr.size()];
        theArray_color = new String[categoriesSArr.size()];

        for (int i = 0; i < categoriesSArr.size() ; i++) {
            theArray_id[i] = categoriesSArr.valueAt(i)._id;
            theArray_txt[i] = categoriesSArr.valueAt(i)._name;
            theArray_color[i] = categoriesSArr.valueAt(i)._color;

        }




    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.p12_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = categoriesSArr.valueAt(position);
        holder.txt_id.setText(""+holder.mItem._id);
        holder.txt_id.setVisibility(View.GONE);
        holder.txt_name.setText(holder.mItem._name +" ("+holder.mItem.getTasksCount()+") " );
        holder.img_color.setImageBitmap(holder.drawCircle(holder.mItem._color));
        holder.btn_delete.setBackgroundColor(Color.TRANSPARENT);
        if(holder.mItem.getTasksCount()>0) {
            holder.btn_delete.setAlpha(0.3f);
        }



        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(v, holder.mItem);
                }
            }
        });
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(v,holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriesSArr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView txt_id;
        public final TextView txt_name;
        public Categories.Category mItem;
        public final ImageButton btn_delete;
        public final ImageView img_color;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            txt_id = (TextView) view.findViewById(R.id.id);
            txt_name = (TextView) view.findViewById(R.id.content);
            img_color = (ImageView) view.findViewById(R.id.img_color);
            btn_delete = (ImageButton) view.findViewById(R.id.btn_delete);


        }


        public Bitmap drawCircle( String color ){



            Bitmap bitmap = Bitmap.createBitmap(
                    50, // Width
                    50, // Height
                    Bitmap.Config.ARGB_8888 // Config
            );

            // Initialize a new Canvas instance
            Canvas canvas = new Canvas(bitmap);

            // Draw a solid color to the canvas background
            canvas.drawColor(Color.TRANSPARENT);

            // Initialize a new Paint instance to draw the Circle
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor( color));
            paint.setAntiAlias(true);

            Paint paint2 = new Paint();
            paint2.setStyle(Paint.Style.STROKE);
            paint2.setColor(Color.BLACK);
            paint2.setAntiAlias(true);
            paint2.setStrokeWidth(1);


            // Calculate the available radius of canvas
            int radius = Math.min(canvas.getWidth(),canvas.getHeight()/2);

            // Set a pixels value to padding around the circle
            int padding = 10;


            canvas.drawCircle(
                    canvas.getWidth() / 2,
                    canvas.getHeight() / 2,
                    radius - padding,
                    paint
            );
            canvas.drawCircle(
                    canvas.getWidth() / 2,
                    canvas.getHeight() / 2,
                    radius - padding,
                    paint2
            );

            // Display the newly created bitmap on app interface
            //img_color.setImageBitmap(bitmap);
            return bitmap;


        }

        @Override
        public String toString() {
            return super.toString() + " '" + txt_name.getText() + "'";
        }
    }


}
