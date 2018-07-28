package ta.luthfi.appsuhu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by taufik on 01/07/18.
 */

public class AdapterSuhu extends RecyclerView.Adapter<AdapterSuhu.ViewHolder>{
    List<ItemSuhu> items;
    Context context;

    public AdapterSuhu(Context context, List<ItemSuhu> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public AdapterSuhu.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_suhu, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        /*holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DataDetailActivity.class);
                intent.putExtra("key_nama", items.get(position).getNama());
                intent.putExtra("key_tgl_lahir", items.get(position).getTgl_lahir());
                intent.putExtra("key_jk", items.get(position).getJk());
                intent.putExtra("key_pddkn", items.get(position).getPddkn());
                intent.putExtra("key_n_ortu", items.get(position).getN_ortu());
                intent.putExtra("key_alamat", items.get(position).getAlamat());
                intent.putExtra("key_tgl_periksa", items.get(position).getTgl_periksa());
                intent.putExtra("key_tujuan", items.get(position).getTujuan());
                intent.putExtra("key_kpsp_ke", items.get(position).getKpsp_ke());
                intent.putExtra("key_hasil", items.get(position).getHasil());
                context.startActivity(intent);
            }
        });*/

        holder.txtSuhu.setText(items.get(position).getSuhu());
        holder.txtTime.setText(items.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //CardView card;
        TextView txtSuhu, txtTime;

        public ViewHolder(View itemView){
            super(itemView);
            //disini pemanggilan view
            //card = (CardView) itemView.findViewById(R.id.cardView);
            txtSuhu = (TextView) itemView.findViewById(R.id.txt_suhu);
            txtTime = (TextView) itemView.findViewById(R.id.txt_time);
        }
    }
}
