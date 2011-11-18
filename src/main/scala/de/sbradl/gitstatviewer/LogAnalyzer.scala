/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.sbradl.gitstatviewer

import scala.collection.SortedSet
import scala.xml.Elem
import scala.xml.XML
import de.sbradl.gitstatviewer.stats.generator._

class LogAnalyzer {
  
  private var log: Log = null
  
  val generators = List[StatGenerator](
    new GeneralStatisticsGenerator,
    new AuthorStatisticsGenerator,
    new ActivityStatisticsGenerator,
    new AuthorActivityStatisticsGenerator
  )
  
  def analyze(log: Log): Elem = {
    this.log = log
    
    val totalCommits = this.log.commits.length
    
    <statistics>
      {generators map {
          g: StatGenerator => {
            g.analyze(log)
          }
        }
      }
    </statistics>
  }
}
