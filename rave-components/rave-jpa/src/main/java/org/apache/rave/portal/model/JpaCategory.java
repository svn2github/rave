/*
 * Copyright 2011 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.rave.portal.model;

import org.apache.rave.persistence.BasicEntity;
import org.apache.rave.portal.model.conversion.ConvertingListProxyFactory;
import org.apache.rave.portal.model.conversion.JpaConverter;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "category")
@Access(AccessType.FIELD)
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = JpaCategory.GET_ALL, query = "select c from JpaCategory c order by c.text")
})
public class JpaCategory implements BasicEntity, Serializable, Category {
    // constants for JPA query names
    public static final String GET_ALL = "Category.getAll";

    @Id
    @Column(name = "entity_id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "categoryIdGenerator")
    @TableGenerator(name = "categoryIdGenerator", table = "RAVE_PORTAL_SEQUENCES", pkColumnName = "SEQ_NAME",
                    valueColumnName = "SEQ_COUNT", pkColumnValue = "category",
                    allocationSize = 1, initialValue = 1)
    private Long entityId;

    @Basic
    @Column(name = "text", unique = true)
    private String text;

    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="created_user_id")
    private JpaUser createdUser;

    @Basic
    @Column(name ="created_date")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createdDate;


    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="last_modified_user_id")
    private JpaUser lastModifiedUser;

    @Basic
    @Column(name ="last_modified_date")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="widget_category",
               joinColumns=@JoinColumn(name="category_id", referencedColumnName = "entity_id"),
               inverseJoinColumns=@JoinColumn(name="widget_id", referencedColumnName = "entity_id")
    )
    @OrderBy("title")
    private List<JpaWidget> widgets;

    public JpaCategory() {

    }

    public JpaCategory(Long entityId, String text, User createdUser, Date createdDate, User lastModifiedUser, Date lastModifiedDate) {
        this.entityId = entityId;
        this.text = text;
        this.setCreatedUser(createdUser);
        this.createdDate = createdDate;
        this.setLastModifiedUser(lastModifiedUser);
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public Long getEntityId() {
        return entityId;
    }

    @Override
    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    @Override
    public Long getId() {
        return getEntityId();
    }

    @Override
    public void setId(Long id) {
        setEntityId(id);
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public User getCreatedUser() {
        return createdUser;
    }

    @Override
    public void setCreatedUser(User createdUser) {
        this.createdUser = JpaConverter.getInstance().convert(createdUser, User.class);
    }

    @Override
    public Date getCreatedDate() {
        return createdDate;
    }

    @Override
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public User getLastModifiedUser() {
        return lastModifiedUser;
    }

    @Override
    public void setLastModifiedUser(User lastModifiedUser) {
        this.lastModifiedUser = JpaConverter.getInstance().convert(lastModifiedUser, User.class);
    }

    @Override
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    @Override
    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @JsonIgnore
    @Override
    public List<Widget> getWidgets() {
        return ConvertingListProxyFactory.createProxyList(Widget.class, widgets);
    }

    @Override
    public void setWidgets(List<Widget> widgets) {
        if(this.widgets == null) {
            this.widgets = new ArrayList<JpaWidget>();
        }
        //Ensure that all operations go through the conversion proxy
        this.getWidgets().clear();
        if (widgets != null) {
            this.getWidgets().addAll(widgets);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        JpaCategory category = (JpaCategory) obj;
        if (entityId != null ? !entityId.equals(category.entityId) : category.entityId != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.entityId != null ? this.entityId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Category{" +
                "entityId=" + entityId +
                ", text='" + text + '\'' +
                '}';
    }
}
