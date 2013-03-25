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

rave = rave || {};

/*
 Rave RegionWidget Interface

 Dependencies:
 rave.ui
 rave.api
 */

rave.RegionWidget = (function () {
    /*
     rave widget constructor
     */
    var Widget = function (definition) {
        var provider = definition.type;

        _.extend(this, definition);

        this._provider = rave.getProvider(provider);

        if(!this._provider) {
            throw new Error('Cannot render widget '+definition.widgetUrl+ '. ' +
                'Provider '+provider+' is not registered.');
        }

        this._provider.initWidget(this);
    }

    Widget.extend = function (mixin) {
        _.extend(this.prototype, mixin);
    }

    /*
     el: valid dom element to which the widget will be injected
     OR valid view name that has been registered via rave.registerView
     opts: rendering options
     */
    Widget.prototype.render = function (el, opts) {
        if (this.error) {
            this.view = rave.renderView('errorWidget', el, this);
            return;
        }
        if (_.isString(el)) {
            var view = rave.renderView(el, this);
            el = view.getWidgetSite();
            this._view = view;
        }
        this._el = el;
        this._provider.renderWidget(this, el, opts);
        return this;
    }

    Widget.prototype.hide = function () {
        this.collapsed = true;

        rave.api.rest.saveWidgetCollapsedState({
            regionWidgetId: this.regionWidgetId,
            collapsed: this.collapsed
        });
    }

    Widget.prototype.show = function () {
        this.collapsed = false;

        rave.api.rest.saveWidgetCollapsedState({
            regionWidgetId: this.regionWidgetId,
            collapsed: this.collapsed
        });
    }

    Widget.prototype.close = function (opts) {
        this._provider.closeWidget(this, opts);
        if (this._view) {
            rave.destroyView(this._view);
        }

        rave.api.rpc.removeWidget({
            regionWidgetId: this.regionWidgetId
        });
    }

    Widget.prototype.moveToPage = function (toPageId, cb) {
        rave.api.rpc.moveWidgetToPage({
            toPageId: toPageId,
            regionWidgetId: this.regionWidgetId,
            successCallback: cb
        });
    }

    Widget.prototype.moveToRegion = function (fromRegionId, toRegionId, toIndex) {
        rave.api.rpc.moveWidgetToRegion({
            regionWidgetId: this.regionWidgetId,
            fromRegionId: fromRegionId,
            toRegionId: toRegionId,
            toIndex: toIndex
        });
    }

    Widget.prototype.savePreferences = function (updatedPrefs) {
        this.userPrefs = updatedPrefs;
        rave.api.rest.saveWidgetPreferences({regionWidgetId: this.regionWidgetId, userPrefs: updatedPrefs});
    }


    return Widget;

})();