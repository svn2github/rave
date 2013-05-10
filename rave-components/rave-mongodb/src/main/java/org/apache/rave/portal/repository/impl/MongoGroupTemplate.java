/*
 * Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.apache.rave.portal.repository.impl;

import org.apache.rave.model.Group;
import org.apache.rave.portal.model.impl.GroupImpl;
import org.apache.rave.portal.repository.MongoGroupOperations;
import org.springframework.stereotype.Component;

import static org.apache.rave.portal.repository.util.CollectionNames.GROUP_COLLECTION;

@Component
public class MongoGroupTemplate  extends MongoModelTemplate<Group, GroupImpl> implements MongoGroupOperations {
    public MongoGroupTemplate() {
        super(Group.class, GroupImpl.class, GROUP_COLLECTION);
    }
}
