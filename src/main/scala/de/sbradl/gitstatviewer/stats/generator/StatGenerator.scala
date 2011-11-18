/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.sbradl.gitstatviewer.stats.generator

import scala.xml.Elem
import de.sbradl.gitstatviewer.Log

trait StatGenerator {
  def analyze(log: Log): Elem
  
  def name: String
}
