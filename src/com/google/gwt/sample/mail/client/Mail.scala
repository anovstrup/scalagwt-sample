/*
 * Copyright 2007 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.sample.mail.client

import com.google.gwt.sample.mail.client.JavaHelper._
import com.google.gwt.core.client.EntryPoint
import com.google.gwt.core.client.GWT
import com.google.gwt.dom.client.Element
import com.google.gwt.resources.client.ClientBundle
import com.google.gwt.resources.client.CssResource
import com.google.gwt.resources.client.CssResource.NotStrict
import com.google.gwt.uibinder.client.UiBinder
import com.google.gwt.uibinder.client.UiField
import com.google.gwt.user.client.Window
import com.google.gwt.user.client.ui.DockLayoutPanel
import com.google.gwt.user.client.ui.RootLayoutPanel
import com.google.gwt.resources.client.ClientBundle.Source

/**
 * This application demonstrates how to construct a relatively complex user
 * interface, similar to many common email readers. It has no back-end,
 * populating its components with hard-coded data.
 */
object Mail {
  protected[Mail] trait Binder extends UiBinder[DockLayoutPanel, Mail]

  private val binder: Binder = GWT.create(classOf[Mail.Binder])

  protected[Mail] trait GlobalResources extends ClientBundle {
    @NotStrict
    @Source(Array("global.css"))
    def css: CssResource
  }

}

/**
 * This application demonstrates how to construct a relatively complex user
 * interface, similar to many common email readers. It has no back-end,
 * populating its components with hard-coded data.
 */
class Mail extends EntryPoint {

  @UiField protected[Mail] var topPanel: TopPanel = null
  @UiField protected[Mail] var mailList: MailList = null
  @UiField protected[Mail] var mailDetail: MailDetail = null
  @UiField protected[Mail] var shortcuts: Shortcuts = null

  /**
   * This method constructs the application user interface by instantiating
   * controls and hooking up event handler.
   */
  def onModuleLoad: Unit = {
    // Inject global styles.
    GWT.create[Mail.GlobalResources](classOf[Mail.GlobalResources]).css.ensureInjected

    // Create the UI defined in Mail.ui.xml.
    val outer = Mail.binder.createAndBindUi(this)

    // Get rid of scrollbars, and clear out the window's built-in margin,
    // because we want to take advantage of the entire client area.
    Window.enableScrolling(false)
    Window.setMargin("0px")

    // Special-case stuff to make topPanel overhang a bit.
    val topElem = outer.getWidgetContainerElement(topPanel)
    topElem.getStyle.setZIndex(2)
    // TODO getOverflowVisible is a workaround for https://github.com/scalagwt/scalagwt-scala/issues/1
    topElem.getStyle.setOverflow(getOverflowVisible)

    // Listen for item selection, displaying the currently-selected item in
    // the detail area.
    mailList.setListener(new MailList.Listener {
      def onItemSelected(item: MailItem): Unit = mailDetail.setItem(item)
    })

    // Add the outer panel to the RootLayoutPanel, so that it will be
    // displayed.
    val root = RootLayoutPanel.get
    root.add(outer)
  }
}

