/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.sbradl.gitstatviewer.reports.generator

import scala.xml.Elem

trait ReportGenerator {
  
  def name: String

  def generateReport(outputPath: String, stats: Elem): Elem
  
}
