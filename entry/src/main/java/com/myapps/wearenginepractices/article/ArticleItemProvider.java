package com.myapps.wearenginepractices.article;

import com.bumptech.glide.Glide;
import com.myapps.wearenginepractices.ResourceTable;
import com.myapps.wearenginepractices.model.Article;
import ohos.agp.components.*;
import ohos.app.Context;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class ArticleItemProvider extends BaseItemProvider {

    private final Context context;
    private final List<Article> items;
    private final ArticleClickedListener articleClickedListener;

    public ArticleItemProvider(final Context context,
                               final List<Article> items,
                               final ArticleClickedListener articleClickedListener) {
        this.context = context;
        this.items = items;
        this.articleClickedListener = articleClickedListener;
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return items.get(i).hashCode();
    }

    @Override
    public Component getComponent(int i, Component component, ComponentContainer componentContainer) {
        if (component == null) {
            component = LayoutScatter.getInstance(context).parse(ResourceTable.Layout_item_article, null, false);
            component.setTag(new ItemHolder(component));
        }

        final ItemHolder holder = (ItemHolder) component.getTag();
        final Article item = items.get(i);

        String abbrTitle = StringUtils.abbreviate(item.getTitle(), 25);
        holder.preTitle.setText(abbrTitle);
        String abbrDesc = StringUtils.abbreviate(item.getDescription(), 28);
        holder.preSubTitle.setText(abbrDesc);

        Glide.with(context)
                .load(item.getUrlToImage())
                .into(holder.image);

        holder.container.setClickedListener(component1 -> articleClickedListener.onArticleItemClicked(item));

        return component;
    }

    public static class ItemHolder {

        final Text preTitle;
        final Text preSubTitle;
        final Image image;
        final Component container;

        public ItemHolder(final Component component) {
            this.container = component.findComponentById(ResourceTable.Id_dl_item_article_container);
            this.preTitle = (Text) component.findComponentById(ResourceTable.Id_t_item_article_pretitle);
            this.preSubTitle = (Text) component.findComponentById(ResourceTable.Id_t_item_article_presubtitle);
            this.image = (Image) component.findComponentById(ResourceTable.Id_i_item_article);
        }
    }
}
