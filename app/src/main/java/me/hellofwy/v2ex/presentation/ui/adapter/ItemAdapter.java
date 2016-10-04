package me.hellofwy.v2ex.presentation.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import me.hellofwy.v2ex.R;
import me.hellofwy.v2ex.domain.model.TopicModel;
import me.hellofwy.v2ex.network.V2EXapi;
import me.hellofwy.v2ex.presentation.ui.activities.WebViewActivity;
import me.hellofwy.v2ex.util.LatestTimeFormat;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static java.lang.System.load;
import static me.hellofwy.v2ex.R.id.layout_content;

/**
 * Created by fwy on 2016/9/28.
 */

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OpenUrlListener {
        void openUrl(String url);
    }
    private enum ITEM_TYPE {
        NORMAL,
        END
    }

    private Activity mContext;
    private List<TopicModel> mTopics;
    private Set<Integer> mSelectedItems;

    public ItemAdapter(Activity context) {
        super();
        mContext = context;
        mTopics = new ArrayList<>(0);
        mSelectedItems = new HashSet<>();
    }
    public void addTopics(List<TopicModel> topics) {
        this.mTopics = topics;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.image_view_avatar)
        ImageView avatar;

        @Bind(R.id.text_view_title)
        TextView title;

        @Bind(R.id.text_view_node)
        TextView node;

        @Bind(R.id.text_view_member)
        TextView member;

        @Bind(R.id.text_view_last_modified)
        TextView last_modified;

        @Bind(R.id.text_view_replies)
        TextView replies;

        @Bind(R.id.text_view_content)
        TextView content;

        @Bind(R.id.layout_content)
        View layout_content;

        private ItemAdapter mAdapter;
        private TopicModel mTopic;
        private OpenUrlListener mParentActivity;

        public ViewHolder(View itemView, ItemAdapter adapter) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        public void setup(OpenUrlListener context, TopicModel topic) {
            mParentActivity = context;
            mTopic = topic;
            Picasso.with((Activity)context)
                    .load("https:" + topic.getMember().getAvatar_normal())
                    .placeholder(R.drawable.avatar_default)
                    .error(R.drawable.avatar_fail)
//                    .transform(new RoundedCornersTransformation(4, 0))
                    .into(avatar);
            title.setText(topic.getTitle());
            node.setText(topic.getNode().getTitle());
            member.setText(topic.getMember().getUsername());
            replies.setText(String.valueOf(topic.getReplies()));
            last_modified.setText(
                    LatestTimeFormat.format(topic.getLast_modified()));
//            content.setText(Html.fromHtml(topic.getContent_rendered()));
            content.setText(topic.getContent());
        }

        public void setContentVisibility(boolean visibility) {
            if(visibility) {
                layout_content.setVisibility(View.VISIBLE);
            } else {
                layout_content.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
//            Boolean isContain = mAdapter.mSelectedItems.contains(position);
//            if(isContain) {
//                mAdapter.mSelectedItems.remove(position);
//            } else {
//                mAdapter.mSelectedItems.add(position);
//            }
//            setContentVisibility(mAdapter.mSelectedItems.contains(position));
            mParentActivity.openUrl(mTopic.getUrl());
        }
    }

    public static class ViewHolderEnd extends RecyclerView.ViewHolder {

        public ViewHolderEnd(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
//        if(getItemCount() - 1 == position) {
//            return ITEM_TYPE.END.ordinal();
//        } else {
//            return ITEM_TYPE.NORMAL.ordinal();
//        }
        return ITEM_TYPE.NORMAL.ordinal();
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        if(viewType == ITEM_TYPE.NORMAL.ordinal()) {
            View view = inflater.inflate(R.layout.card_view, parent, false);
            return new ViewHolder(view, this);
        } else {
            View view = inflater.inflate(R.layout.item_end_view, parent, false);
            return new ViewHolderEnd(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if(holder instanceof ViewHolder) {
//            ((ViewHolder) holder).setup("title");
//        }
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).setup((OpenUrlListener) mContext, mTopics.get(position));
            ((ViewHolder) holder).setContentVisibility(mSelectedItems.contains(position));
        }
    }
    @Override
    public int getItemCount() {
        return mTopics.size();
    }
}
