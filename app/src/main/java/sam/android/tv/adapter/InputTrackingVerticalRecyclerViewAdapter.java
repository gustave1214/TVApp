package sam.android.tv.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

public abstract class InputTrackingVerticalRecyclerViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private Context mContext;
    private int mSelectedItem = 0;
    private RecyclerView mRecyclerView;

    public InputTrackingVerticalRecyclerViewAdapter(Context context){
        mContext = context;
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        mRecyclerView = recyclerView;
        // Handle key up and key down and attempt to move selection
        recyclerView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();

                // Return false if scrolled to the bounds and allow focus to move off the list
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (isConfirmButton(event)) {
                        if ((event.getFlags() & KeyEvent.FLAG_LONG_PRESS) == KeyEvent.FLAG_LONG_PRESS) {
                            mRecyclerView.findViewHolderForAdapterPosition(mSelectedItem).itemView.performLongClick();
                        } else {
                            event.startTracking();
                        }
                        return true;
                    } else {
                        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                            Log.d("Direction", "Down");
                            return tryMoveSelection(lm, 1);
                        } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                            Log.d("Direction", "Up");
                            return tryMoveSelection(lm, -1);
                        }
                    }
                } else if (event.getAction() == KeyEvent.ACTION_UP && isConfirmButton(event)
                        && ((event.getFlags() & KeyEvent.FLAG_LONG_PRESS) != KeyEvent.FLAG_LONG_PRESS)) {
                    mRecyclerView.findViewHolderForAdapterPosition(mSelectedItem).itemView.performClick();
                    return true;
                }
                return false;
            }
        });
    }

    private boolean tryMoveSelection(LinearLayoutManager lm, int direction) {
        int nextSelectItem = mSelectedItem + direction;

        // If still within valid bounds, move the selection, notify to redraw, and scroll
        if (nextSelectItem >= 0 && nextSelectItem < getItemCount()) {
            notifyItemChanged(mSelectedItem);
            mSelectedItem = nextSelectItem;
            notifyItemChanged(mSelectedItem);
            lm.scrollToPositionWithOffset(mSelectedItem, 0);

            Log.d("Selected", mSelectedItem + "");
//            mRecyclerView.smoothScrollToPosition(mSelectedItem);
            return true;
        }

        return false;
    }

    public Context getContext(){ return mContext; }

    public int getSelectedItem() { return mSelectedItem; }
    public void setSelectedItem(int selectedItem) { mSelectedItem = selectedItem; }

    public RecyclerView getRecyclerView() { return mRecyclerView; }


    @Override
    public void onBindViewHolder(VH holder, int position) {
        onBindViewHolder(holder, position);
    }

    public static boolean isConfirmButton(KeyEvent event){
        switch (event.getKeyCode()){
            case KeyEvent.KEYCODE_ENTER:
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_BUTTON_A:
                return true;
            default:
                return false;
        }
    }
}
