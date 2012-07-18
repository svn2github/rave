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

import org.apache.rave.portal.model.Region;
import org.apache.rave.portal.model.RegionWidget;
import org.apache.rave.portal.model.impl.RegionImpl;
import org.apache.rave.portal.model.impl.RegionWidgetImpl;
import org.apache.rave.portal.model.impl.WidgetImpl;
import org.apache.rave.portal.web.renderer.RenderScope;
import org.apache.rave.portal.web.renderer.RenderService;
import org.apache.rave.portal.web.renderer.ScriptLocation;
import org.apache.rave.portal.web.renderer.ScriptManager;
import org.apache.rave.portal.web.renderer.model.RenderContext;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**

 */
public class RegionWidgetTagTest {

    private static final String WIDGET_TYPE = "TEST";
    private static final String RENDERED = "RENDERED";
    private RegionWidgetTag tag;
    private RenderService service;
    private PageContext pageContext;
    private RenderContext context;
    private ScriptManager scriptManager;

    @Before
    public void setup() throws JspException {
        context = new RenderContext();
        service = createNiceMock(RenderService.class);
        scriptManager = createMock(ScriptManager.class);
        WebApplicationContext wContext = createNiceMock(WebApplicationContext.class);
        expect(wContext.getBean(RenderService.class)).andReturn(service).anyTimes();
        expect(wContext.getBean(ScriptManager.class)).andReturn(scriptManager).anyTimes();
        replay(wContext);
        ServletContext servletContext = createNiceMock(ServletContext.class);
        expect(servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE)).andReturn(wContext).anyTimes();
        replay(servletContext);
        ServletRequest request = new MockHttpServletRequest();
        request.setAttribute("_RENDER_CONTEXT", context);
        pageContext = createNiceMock(PageContext.class);
        expect(pageContext.getServletContext()).andReturn(servletContext).anyTimes();
        expect(pageContext.getRequest()).andReturn(request).anyTimes();
        tag = new RegionWidgetTag();
        tag.setPageContext(pageContext);
    }

    @Test
    public void doStartTag_valid() throws IOException, JspException {
        RegionWidget regionWidget = new RegionWidgetImpl();
        WidgetImpl widget = new WidgetImpl();
        regionWidget.setWidget(widget);
        widget.setType(WIDGET_TYPE);

        Set<String> strings = new HashSet<String>();
        strings.add(WIDGET_TYPE);

        expect(service.getSupportedWidgetTypes()).andReturn(strings);
        expect(service.render(regionWidget, context)).andReturn(RENDERED);
        replay(service);

        JspWriter writer = createNiceMock(JspWriter.class);
        writer.print(RENDERED);
        expectLastCall();
        replay(writer);

        expect(pageContext.getOut()).andReturn(writer);
        replay(pageContext);

        tag.setRegionWidget(regionWidget);
        int result = tag.doStartTag();
        assertThat(result, is(equalTo(1)));
        verify(writer);
    }

    @Test(expected = JspException.class)
    public void doStartTag_nullWidget() throws JspException {
        replay(pageContext);
        tag.doStartTag();
    }


    @Test(expected = JspException.class)
    public void doStartTag_IOException() throws JspException, IOException {

        RegionWidget regionWidget = new RegionWidgetImpl();
        WidgetImpl widget = new WidgetImpl();
        regionWidget.setWidget(widget);
        widget.setType("INVALID");

        Set<String> strings = new HashSet<String>();
        strings.add(WIDGET_TYPE);

        expect(service.getSupportedWidgetTypes()).andReturn(strings);
        replay(service);

        JspWriter writer = createNiceMock(JspWriter.class);
        writer.print(RENDERED);
        expectLastCall().andThrow(new IOException());
        replay(writer);

        replay(pageContext);

        tag.doStartTag();
        tag.doStartTag();
    }

    @Test(expected = JspException.class)
    public void doStartTag_unsupportedWidget() throws JspException {
        replay(pageContext);

        RegionWidget regionWidget = new RegionWidgetImpl();
        Region region = new RegionImpl(25L);
        WidgetImpl widget = new WidgetImpl();
        regionWidget.setWidget(widget);
        regionWidget.setRegion(region);
        widget.setType("INVALID");

        Set<String> strings = new HashSet<String>();
        strings.add(WIDGET_TYPE);

        expect(service.getSupportedWidgetTypes()).andReturn(strings);
        replay(service);

        tag.setRegionWidget(regionWidget);
        tag.doStartTag();
    }

    @Test
    public void doStartTag_disabledWidget() throws IOException, JspException {
        final String DISABLED_WIDGET_MESSAGE = "THIS IS DISABLED";

        WidgetImpl widget = new WidgetImpl();
        widget.setId(8L);
        widget.setType(WIDGET_TYPE);
        widget.setDisableRendering(true);
        widget.setDisableRenderingMessage(DISABLED_WIDGET_MESSAGE);

        RegionWidget regionWidget = new RegionWidgetImpl();
        regionWidget.setId(99L);
        regionWidget.setWidget(widget);
        regionWidget.setRegion(new RegionImpl(2L));

        Set<String> strings = new HashSet<String>();
        strings.add(WIDGET_TYPE);

        expect(service.getSupportedWidgetTypes()).andReturn(strings);
        scriptManager.registerScriptBlock(anyObject(String.class), anyObject(String.class), anyObject(ScriptLocation.AFTER_RAVE.getClass()), anyObject(RenderScope.CURRENT_REQUEST.getClass()), anyObject(RenderContext.class));
        expectLastCall();
        replay(service, scriptManager);

        JspWriter writer = createNiceMock(JspWriter.class);
        expect(pageContext.getOut()).andReturn(writer);
        replay(pageContext, writer);

        tag.setRegionWidget(regionWidget);
        int result = tag.doStartTag();
        assertThat(result, is(equalTo(1)));
        verify(writer);
    }

    @Test
    public void getRegionWidget() throws IOException, JspException {
        RegionWidget regionWidget = new RegionWidgetImpl();
        tag.setRegionWidget(regionWidget);
        assertThat(tag.getRegionWidget(), sameInstance(regionWidget));
    }
}
