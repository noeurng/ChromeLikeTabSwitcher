package de.mrapp.android.tabswitcher.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import de.mrapp.android.tabswitcher.Tab;
import de.mrapp.android.tabswitcher.TabSwitcher;
import de.mrapp.android.tabswitcher.TabSwitcherDecorator;
import de.mrapp.android.util.view.AbstractViewRecycler;

import static de.mrapp.android.util.Condition.ensureNotNull;

/**
 * @author Michael Rapp
 */
public class ChildRecyclerAdapter extends AbstractViewRecycler.Adapter<Tab, Void> {

    private final TabSwitcher tabSwitcher;

    private final TabSwitcherDecorator decorator;

    private final Map<Tab, Bundle> savedInstanceStates;

    public ChildRecyclerAdapter(@NonNull final TabSwitcher tabSwitcher,
                                @NonNull final TabSwitcherDecorator decorator) {
        ensureNotNull(tabSwitcher, "The tab switcher may not be null");
        ensureNotNull(decorator, "The decorator may not be null");
        this.tabSwitcher = tabSwitcher;
        this.decorator = decorator;
        this.savedInstanceStates = new HashMap<>();
    }

    @NonNull
    @Override
    public final View onInflateView(@NonNull final LayoutInflater inflater,
                                    @Nullable final ViewGroup parent, @NonNull final Tab item,
                                    final int viewType, @NonNull final Void... params) {
        int index = tabSwitcher.indexOf(item);
        return decorator.inflateView(inflater, parent, item, index);
    }

    @Override
    public final void onShowView(@NonNull final Context context, @NonNull final View view,
                                 @NonNull final Tab item, final boolean inflated,
                                 @NonNull final Void... params) {
        int index = tabSwitcher.indexOf(item);
        Bundle savedInstanceState = savedInstanceStates.get(item);
        decorator.applyDecorator(context, tabSwitcher, view, item, index, savedInstanceState);
    }

    @Override
    public final void onRemoveView(@NonNull final View view, @NonNull final Tab item) {
        int index = tabSwitcher.indexOf(item);
        Bundle outState = decorator.saveInstanceState(view, item, index);
        savedInstanceStates.put(item, outState);
    }

    @Override
    public final int getViewTypeCount() {
        return decorator.getViewTypeCount();
    }

    @Override
    public final int getViewType(@NonNull final Tab item) {
        int index = tabSwitcher.indexOf(item);
        return decorator.getViewType(item, index);
    }

}