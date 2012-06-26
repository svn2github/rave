package org.apache.rave.portal.model.conversion;

import org.apache.rave.model.ModelConverter;
import org.apache.rave.portal.model.JpaWidget;
import org.apache.rave.portal.model.Widget;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Converts from a {@link org.apache.rave.portal.model.Widget} to a {@link org.apache.rave.portal.model.JpaWidget}
 */
@Component
public class JpaWidgetConverter implements ModelConverter<Widget, JpaWidget> {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public JpaWidget convert(Widget source) {
        return source instanceof JpaWidget ? (JpaWidget) source : createEntity(source);
    }

    @Override
    public Class<Widget> getSourceType() {
        return Widget.class;
    }

    private JpaWidget createEntity(Widget source) {
        JpaWidget converted = null;
        if (source != null) {
            converted = new JpaWidget();
            updateProperties(source, converted);
        }
        return converted;
    }

    private void updateProperties(Widget source, JpaWidget converted) {
        converted.setEntityId(source.getId());
        converted.setUrl(source.getUrl());
        converted.setType(source.getType());
        converted.setTitle(source.getTitle());
        converted.setTitleUrl(source.getTitleUrl());
        converted.setUrl(source.getUrl());
        converted.setThumbnailUrl(source.getThumbnailUrl());
        converted.setScreenshotUrl(source.getScreenshotUrl());
        converted.setAuthor(source.getAuthor());
        converted.setAuthorEmail(source.getAuthorEmail());
        converted.setDescription(source.getDescription());
        converted.setWidgetStatus(source.getWidgetStatus());
        converted.setComments(source.getComments());
        converted.setOwner(source.getOwner());
        converted.setDisableRendering(source.isDisableRendering());
        converted.setRatings(source.getRatings());
        converted.setTags(source.getTags());
        converted.setCategories(source.getCategories());
        converted.setFeatured(source.isFeatured());
    }
}
