java -Xmx712M -Xss2M -XX:MaxPermSize=512M -XX:+CMSClassUnloadingEnabled -jar `dirname $0`/sbt-launcher.jar "$@"
