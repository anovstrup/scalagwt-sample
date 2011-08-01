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

import com.google.gwt.core.client.GWT
import com.google.gwt.event.dom.client.ClickEvent
import com.google.gwt.event.dom.client.KeyCodes
import com.google.gwt.uibinder.client.UiBinder
import com.google.gwt.uibinder.client.UiField
import com.google.gwt.uibinder.client.UiHandler
import com.google.gwt.user.client.Event.NativePreviewEvent
import com.google.gwt.user.client.ui.Button
import com.google.gwt.user.client.ui.DialogBox
import com.google.gwt.user.client.ui.Widget

/**
 * A simple example of an 'about' dialog box.
 */
object AboutDialog {
  private val binder: AboutDialog.Binder = GWT.create(classOf[AboutDialog.Binder])

  private[client] trait Binder extends UiBinder[Widget, AboutDialog]
}

class AboutDialog extends DialogBox {

  @UiField private[client] var closeButton: Button = null

  // Use this opportunity to set the dialog's caption.
  setText("About the Mail Sample")
  setWidget(AboutDialog.binder.createAndBindUi(this))

  setAnimationEnabled(true)
  setGlassEnabled(true)

  override protected def onPreviewNativeEvent(preview: NativePreviewEvent): Unit = {
    super.onPreviewNativeEvent(preview)

    val evt = preview.getNativeEvent
    if (evt.getType == "keydown") {
      // Use the popup's key preview hooks to close the dialog when either
      // enter or escape is pressed.
      // TODO can use pattern alternatives when forward jumps are removed
      evt.getKeyCode match {
        case KeyCodes.KEY_ENTER => hide()
        case KeyCodes.KEY_ESCAPE => hide()
        case _ => {}
      }
    }
  }

  @UiHandler(Array("closeButton"))
  private[client] def onSignOutClicked(event: ClickEvent) = hide()
}

