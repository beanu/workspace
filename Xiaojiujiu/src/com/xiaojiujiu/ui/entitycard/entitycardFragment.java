package com.xiaojiujiu.ui.entitycard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.beanu.arad.base.BaseListFragment;
import com.xiaojiujiu.entity.EntityCard;
import com.xiaojiujiu.ui.UIUtil;
import com.xiaojiujiu.ui.adapter.EntityCardListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by beanu on 13-6-2.
 */
public class EntityCardFragment extends BaseListFragment {

    private EntityCardListAdapter adapter;
    private List<EntityCard> data;


    public static EntityCardFragment newInstance() {
        EntityCardFragment fragment=new EntityCardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data=new ArrayList<EntityCard>();
        if (savedInstanceState==null){
            //TODO net request
            //TODO delete test data
            for(int i=0;i<10;i++){
                EntityCard card=new EntityCard();
                card.setName("大润发"+i);
                card.setNumber("335435234234");
                card.setValidPeriod("2012-05-12");
                data.add(card);
            }
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent=new Intent(getSherlockActivity(),EntityCardDetailActivity.class);
        startActivity(intent);
        UIUtil.intentSlidIn(getSherlockActivity());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter=new EntityCardListAdapter(getSherlockActivity(),data);
        setListAdapter(adapter);
    }
}
