package com.google.gwt.sample.gwtdlx.client

/*
 * Copyright 2011 Google Inc.
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

import scala.util.continuations._
import com.google.gwt.user.client.Window
import com.google.gwt.user.client.rpc.AsyncCallback

/*
 * Control structures for asynchronous RPC.
 *
 * @author Aaron Novstrup
 */
object Async {
   type async = cps[AsyncUnit]
   sealed trait AsyncUnit
   private case object AsyncUnit extends AsyncUnit
   private def asyncUnit: AsyncUnit = AsyncUnit

   /**
    * Delimits a block of code that may make asynchronous remote procedure calls. When a remote procedure call is made,
    * execution jumps to the end of the inner-most async block.  The rest of the computation in the async block proceeds
    * when the RPC returns successfully.  If the RPC fails, an exception is thrown (which can be caught by surrounding
    * the procedure call with a try block).
    *
    * Example use:
    * <code>
    *    async {
    *       try {
    *          val result = callAsync[Type](asyncMethod(arg1, arg2, _))
    *          field.setText(result)
    *       } catch {
    *          case e: IllegalStateException => log(e)
    *       }
    *    }
    * </code>
    */
   def async(body: => Unit @async): Unit = reset {
      body
      asyncUnit
   }

   /**
    * Interface used to call an asynchronous method in an async block.
    *
    * @param f A method that, given an asynchronous callback, makes the actual asynchronous method call.
    */
   def callAsync[R](f: AsyncCallback[R] => Unit): R @async = {
      val result = shift { (k: Either[R, Throwable] => AsyncUnit) =>
         f(Callback(k))
         asyncUnit
      }
      result.fold(identity, throw _)
   }

   private case class Callback[R](k: Either[R, Throwable] => AsyncUnit) extends AsyncCallback[R] {
      def onSuccess(result: R) = k(Left(result))
      def onFailure(caught: Throwable) = k(Right(caught))
   }
}