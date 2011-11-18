/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.sbradl.gitstatviewer

import java.io.File
import scala.sys.process._

object LogFetcher {
  def fetch(directory: String) = Process("git log -p", new File(directory)) !!
}
