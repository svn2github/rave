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

import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;
import java.util.List;

/**
 * A category for a widget.
 */
@XmlTransient
public interface Category {
    public Long getId();
    public void setId(Long id);

    public String getText();
    public void setText(String text);

    public User getCreatedUser();
    public void setCreatedUser(User createdUser);

    public Date getCreatedDate();
    public void setCreatedDate(Date createdDate);

    public User getLastModifiedUser();
    public void setLastModifiedUser(User lastModifiedUser);

    public Date getLastModifiedDate();
    public void setLastModifiedDate(Date lastModifiedDate);

    public List<Widget> getWidgets();
    public void setWidgets(List<Widget> widgets);
}