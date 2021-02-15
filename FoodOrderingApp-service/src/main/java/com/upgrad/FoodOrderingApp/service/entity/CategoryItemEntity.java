package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;


@Entity
@Table (name = "category_item" , schema = "public")
@NamedQueries(
        {
                @NamedQuery(name = "getItemsByCategory" , query = "select category_item from CategoryItemEntity category_item where category_item.categoryEntity.uuid = :uuid order by category_item.categoryEntity.categoryName")
        }
)
public class CategoryItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemEntity itemEntity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryEntity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ItemEntity getItemEntity() {
        return itemEntity;
    }

    public void setItemEntity(ItemEntity itemEntity) {
        this.itemEntity = itemEntity;
    }

    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }
}
