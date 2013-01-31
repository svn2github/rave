/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.rave.portal.model;


import org.apache.rave.persistence.BasicEntity;
import org.apache.rave.portal.model.impl.ActivityStreamsMediaLinkImpl;

import javax.persistence.*;
import java.util.HashMap;



@Entity
@Access(AccessType.FIELD)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "activitystreams_medialink")
@SequenceGenerator(name="activityMediaLinkSequence", sequenceName = "activity_media_link_sequence")
@NamedQueries({
        @NamedQuery(name = JpaActivityStreamsMediaLink.FIND_BY_ID, query = "SELECT a FROM JpaActivityStreamsMediaLink a WHERE a.id = :id")
})
public class JpaActivityStreamsMediaLink implements ActivityStreamsMediaLink, BasicEntity {

    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_ID = "JpaActivityStreamsMediaLink.findById";

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "activityMediaLinkSequence")
    private Long entityId;

    @Basic
    private String id;

    @Basic
    private Integer duration;

    @Basic
    private Integer height;

    @Basic
    private String url;

    @Basic
    private Integer width;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private HashMap openSocial;


    /**
     * Create a new MediaLink
     */
    public JpaActivityStreamsMediaLink() {
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /** {@inheritDoc} */

    public Integer getDuration() {
        return duration;
    }

    /** {@inheritDoc} */
    public void setDuration(Integer duration) {
        this.duration = duration;

    }

    /** {@inheritDoc} */

    public Integer getHeight() {
        return height;
    }

    /** {@inheritDoc} */
    public void setHeight(Integer height) {
        this.height = height;

    }

    /** {@inheritDoc} */

    public String getUrl() {
        return url;
    }

    /** {@inheritDoc} */
    public void setUrl(String url) {
        this.url = url;

    }

    /** {@inheritDoc} */

    public Integer getWidth() {
        return width;
    }

    /** {@inheritDoc} */
    public void setWidth(Integer width) {
        this.width = width;

    }

    /** {@inheritDoc} */

    public HashMap getOpenSocial() {
        return openSocial;
    }

    /** {@inheritDoc} */
    public void setOpenSocial(HashMap openSocial) {
        this.openSocial = openSocial;

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final JpaActivityStreamsMediaLink other = (JpaActivityStreamsMediaLink) obj;
        if (this.entityId != other.entityId && (this.entityId == null || !this.entityId.equals(other.entityId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.entityId != null ? this.entityId.hashCode() : 0);
        return hash;
    }

}
