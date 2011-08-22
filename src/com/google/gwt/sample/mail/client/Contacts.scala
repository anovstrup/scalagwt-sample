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

import com.google.gwt.sample.mail.client.ClickHandlers._

import com.google.gwt.core.client.GWT
import com.google.gwt.dom.client.Element
import com.google.gwt.event.dom.client.ClickEvent
import com.google.gwt.event.dom.client.ClickHandler
import com.google.gwt.resources.client.CssResource
import com.google.gwt.uibinder.client.UiBinder
import com.google.gwt.uibinder.client.UiField
import com.google.gwt.uibinder.client.UiTemplate
import com.google.gwt.user.client.ui.Anchor
import com.google.gwt.user.client.ui.ComplexPanel
import com.google.gwt.user.client.ui.Composite
import com.google.gwt.user.client.ui.PopupPanel
import com.google.gwt.user.client.ui.Widget

/**
 * A component that displays a list of contacts.
 */
object Contacts {
  /**
   * Simple data structure representing a contact.
   */
  private class Contact(var email: String, var name: String)

  /**
   * A simple popup that displays a contact's information.
   */
  protected[Contacts] object ContactPopup {
    private val binder: Binder = GWT.create(classOf[Binder])

    @UiTemplate("ContactPopup.ui.xml")
    protected[ContactPopup] trait Binder extends UiBinder[Widget, Contacts.ContactPopup]
  }

  /**
   * A simple popup that displays a contact's information.
   */
  protected[Contacts] class ContactPopup(contact: Contacts.Contact)
  // The popup's constructor's argument is a boolean specifying that it
  // auto-close itself when the user clicks outside of it.
      extends PopupPanel(true) {
    add(ContactPopup.binder.createAndBindUi(this))

    nameDiv.setInnerText(contact.name)
    emailDiv.setInnerText(contact.email)

    @UiField protected[ContactPopup] var nameDiv: Element = null
    @UiField protected[ContactPopup] var emailDiv: Element = null
  }


  protected[Contacts] trait Binder extends UiBinder[Widget, Contacts]
  protected[Contacts] trait Style extends CssResource {
    def item: String
  }

  private val binder: Contacts.Binder = GWT.create(classOf[Contacts.Binder])

}

class Contacts extends Composite {
  private val contacts = List(
    new Contacts.Contact("Benoit Mandelbrot", "benoit@example.com"),
    new Contacts.Contact("Albert Einstein", "albert@example.com"),
    new Contacts.Contact("Rene Descartes", "rene@example.com"),
    new Contacts.Contact("Bob Saget", "bob@example.com"),
    new Contacts.Contact("Ludwig von Beethoven", "ludwig@example.com"),
    new Contacts.Contact("Richard Feynman", "richard@example.com"),
    new Contacts.Contact("Alan Turing", "alan@example.com"),
    new Contacts.Contact("John von Neumann", "john@example.com"))

  @UiField protected[Contacts] var panel: ComplexPanel = null
  @UiField protected[Contacts] var style: Contacts.Style = null

  initWidget(Contacts.binder.createAndBindUi(this))
  contacts foreach addContact

  private def addContact(contact: Contacts.Contact): Unit = {
    val link = new Anchor(contact.name)
    link.setStyleName(style.item)
    panel.add(link)

    // Add a click handler that displays a ContactPopup when it is clicked.
    link onClick { _ =>
      val popup = new Contacts.ContactPopup(contact)
      val left = link.getAbsoluteLeft + 14
      var top = link.getAbsoluteTop + 14
      popup.setPopupPosition(left, top)
      popup.show()
    }
  }


}

