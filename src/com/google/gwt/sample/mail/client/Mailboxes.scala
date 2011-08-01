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
import com.google.gwt.resources.client.ClientBundle
import com.google.gwt.resources.client.ImageResource
import com.google.gwt.user.client.ui.AbstractImagePrototype
import com.google.gwt.user.client.ui.Composite
import com.google.gwt.user.client.ui.Tree
import com.google.gwt.user.client.ui.TreeItem
import com.google.gwt.resources.client.ClientBundle.Source

/**
 * A tree displaying a set of email folders.
 */
object Mailboxes {

  /**
   * Specifies the images that will be bundled for this Composite and specify
   * that tree's images should also be included in the same bundle.
   */
  trait Images extends ClientBundle with Tree.Resources {
    def drafts: ImageResource

    def home: ImageResource

    def inbox: ImageResource

    def sent: ImageResource

    def templates: ImageResource

    def trash: ImageResource

    @Source(Array("noimage.png"))
    def treeLeaf: ImageResource
  }

}

/**
 * Constructs a new mailboxes widget with a bundle of images.
 *
 * @param images a bundle that provides the images for this widget
 */
class Mailboxes extends Composite {

  private val images: Mailboxes.Images = GWT.create(classOf[Mailboxes.Images])
  private val tree = new Tree(images)
  val root = new TreeItem(imageItemHTML(images.home, "foo@example.com"))
  tree.addItem(root)
  (List(("Inbox", images.inbox),
        ("Drafts", images.drafts),
        ("Templates", images.templates),
        ("Sent", images.sent),
        ("Trash", images.trash))
      foreach { case (title, img) => addImageItem(root, title, img) })
  root.setState(true)
  initWidget(tree)


  /**
   * A helper method to simplify adding tree items that have attached images.
   * {@link #addImageItem(TreeItem, String, ImageResource) code}
   *
   * @param root the tree item to which the new item will be added.
   * @param title the text associated with this item.
   */
  private def addImageItem(root: TreeItem, title: String, imageProto: ImageResource): TreeItem = {
    val item = new TreeItem(imageItemHTML(imageProto, title))
    root.addItem(item)
    item
  }

  /**
   * Generates HTML for a tree item with an attached icon.
   *
   * @param imageProto the image prototype to use
   * @param title the title of the item
   * @return the resultant HTML
   */
  private def imageItemHTML(imageProto: ImageResource, title: String): String =
    AbstractImagePrototype.create(imageProto).getHTML + " " + title
}

