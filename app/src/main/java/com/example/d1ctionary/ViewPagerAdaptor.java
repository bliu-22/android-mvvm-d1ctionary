package com.example.d1ctionary;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdaptor extends FragmentStateAdapter {


    public ViewPagerAdaptor(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new WordInfoFragment();
            case 1:
                return new WordNoteFragment();
            default:
                return new WordInfoFragment();

        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
