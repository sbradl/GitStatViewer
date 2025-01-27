/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.sbradl.gitstatviewer

import scala.xml.XML
import scala.util.logging.ConsoleLogger
import scala.xml.PrettyPrinter
import de.sbradl.gitstatviewer.util.Cache
import de.sbradl.gitstatviewer.reports.generator.GeneralReport
import de.sbradl.gitstatviewer.reports.generator.AuthorReport
import de.sbradl.gitstatviewer.reports.generator.ActivityReport

object GitStatViewer extends App with ConsoleLogger {
  
  val directory = args match {
    case Array() => {
        println("No diretory specified - using current working directory")
        "."
      }
    case Array(x) => {
        println("using specified directory")
        x
      }
    case _ => {
        println("Invalid number of arguments: %d (%s)".format(args.size, args.mkString(",")))
        sys.exit(1)
      }
  }
  
  log("Output will be saved to directory gitstats")
    
  val start = System.currentTimeMillis
    
  log("Fetching logs")
  val logData = LogFetcher.fetch(directory)
  
  log("Extracting logs")
  val logExtractor = new LogExtractor
  val log = logExtractor.extractFrom(logData)
  
  Cache.init(log)
    
  log("Analyzing logs")
  val analyzer = new LogAnalyzer
  val statistics = analyzer.analyze(log)
  
  val prettyPrinter = new PrettyPrinter(80, 4)
  val prettyXml = XML.loadString(prettyPrinter.format(statistics))
  XML.save("statistics.xml", prettyXml, xmlDecl = true)
   
  val visualizer = new LogVisualizer(statistics) {  
    override val reporters = new GeneralReport :: new AuthorReport :: new ActivityReport :: Nil
  }
  
  log("Generating final report")
  visualizer.generateReport("gitstats")
  
  val end = System.currentTimeMillis
    
  println("Finished after " + (end-start) + "ms")
}
