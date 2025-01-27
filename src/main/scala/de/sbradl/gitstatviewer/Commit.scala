/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.sbradl.gitstatviewer

import java.util.Date
import scala.collection.mutable.ListBuffer

class Commit(val id: String) {
  var author: String = ""
  var date: Date = null
  var message: String = ""
  
  def addDiffLine(line: String) = diffBuffer += (line)
  def diff = this.diffBuffer.toList
  
  lazy val addedLines = {
    diff.count {
      line => {line.startsWith("+") && !line.startsWith("+++")}
    }
  }
  
  lazy val deletedLines = {
    diff.count {
      line => {line.startsWith("-") && !line.startsWith("---")}
    }
  }
  
  lazy val approximatedLines = addedLines - deletedLines
  
  private var diffBuffer = ListBuffer[String]()
}
