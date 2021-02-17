package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category" , schema = "public")
@NamedQueries({
        @NamedQuery(name = "getCategoryByUUID" , query = "select categoryEntity from CategoryEntity categoryEntity where categoryEntity.uuid = :uuid"),
        @NamedQuery(name = "getAllCategories" , query = "select categoryEntity from CategoryEntity categoryEntity order by categoryEntity.categoryName asc ")
})
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @Size(max = 200)
    private String uuid;

    @Column(name = "category_name")
    @Size(max = 255)
    private String categoryName;

    @OneToMany
    @JoinTable(name = "category_item" , joinColumns = {@JoinColumn(name = "category_id" , referencedColumnName = "id")}
    , inverseJoinColumns = {@JoinColumn(name = "item_id" , referencedColumnName = "id")})
    private List<ItemEntity> items = new ArrayList<ItemEntity>();

    public Integer getId() {
        return id;
    }

    public List<ItemEntity> getItems() {
        return items;
    }

    public void setItems(List<ItemEntity> items) {
        this.items = items;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
