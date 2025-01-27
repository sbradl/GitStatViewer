/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.sbradl.gitstatviewer.stats.generator

import java.text.SimpleDateFormat
import java.util.Date
import scala.collection.SortedMap
import scala.collection.mutable.HashMap
import scala.xml.Elem
import de.sbradl.gitstatviewer.Log
import de.sbradl.gitstatviewer.util.Cache
import de.sbradl.gitstatviewer.Commit
import de.sbradl.gitstatviewer.util.DateHelper

class AuthorActivityStatisticsGenerator extends StatGenerator {

  def name = "AuthorActivity"
  
  private var log: Log = null
  
  def analyze(log: Log): Elem = {
    this.log = log
    
    <author-activity>
      <authors>
        {
          log.authors.sorted map {
            authorName: String => {
              val commitsFromAuthor = Cache.commitsByAuthor.getOrElse(authorName, List())
              
              <author name={authorName}>
                {
                  commitsByDay(commitsFromAuthor) map {
                    (kv: (String, List[Commit])) => {
                      <activity date={kv._1}>
                        <addedLines>{Cache.addedLinesFor(kv._2)}</addedLines>
                        <deletedLines>{Cache.deletedLinesFor(kv._2)}</deletedLines>
                        <totalLines>{Cache.totalLinesFor(kv._2)}</totalLines>
                      </activity>
                    }
                  }
                }
              </author>
            }
          }
        }
      </authors>
    </author-activity>
  }
  
  private def commitsByDay(commits: List[Commit]) = {
    commits.groupBy {
      c: Commit => {
        DateHelper.day(c.date)
      }
    }.map {
      (kv: (Date, List[Commit])) => {
        (DateHelper.format(kv._1), kv._2)
      }
    }
  }
}
