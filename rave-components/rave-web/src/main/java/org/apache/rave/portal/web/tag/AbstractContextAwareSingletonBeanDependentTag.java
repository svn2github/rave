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

package org.apache.rave.portal.web.tag;

import org.apache.rave.portal.web.renderer.model.RenderContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.HashMap;

/**
 * Abstract
 */
public abstract class AbstractContextAwareSingletonBeanDependentTag<T> extends TagSupport {

    private static final String CONTEXT_KEY = "_RENDER_CONTEXT";
    protected Class<T> clazz;
    private T bean;

    protected AbstractContextAwareSingletonBeanDependentTag(Class<T> clazz) {
        this.clazz = clazz;
    }

    protected void writeString(String output) throws JspException {
        try {
            this.pageContext.getOut().print(output);
        } catch (IOException e) {
            throw new JspException("Failed to render", e);
        }
    }

    protected T getBean() throws JspException {
        if(bean == null) {
            bean = getBeanFromContext();
        }
        return bean;
    }

    protected RenderContext getContext() {
       RenderContext context = (RenderContext)this.pageContext.getRequest().getAttribute(CONTEXT_KEY);
       if(context == null) {
           context = new RenderContext();
           context.setProperties(new HashMap());
           this.pageContext.getRequest().setAttribute(CONTEXT_KEY, context);
       }
       return context;
    }

    private T getBeanFromContext() throws JspException {
        return getBeanFromContext(clazz);
    }

    protected <E> E getBeanFromContext(Class<E> clazz ) throws JspException {
        ServletContext currentServletContext = pageContext.getServletContext();
        ApplicationContext springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(currentServletContext);
        return springContext.getBean(clazz);
    }

}
